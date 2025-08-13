#include <thread>
#include <vector>
#include <iostream>
#include <fstream>
#include <sstream>
#include <string>
#include <mutex>
#include <random>
#include <chrono>
#include <condition_variable>
#include <queue>
#include <atomic>

using namespace std;

std::vector<std::string> split(const std::string &s, char delim) {
    std::vector<std::string> result;
    std::stringstream ss(s);
    std::string item;

    while (getline(ss, item, delim)) {
        result.push_back(item);
    }

    return result;
}

class CountingSemaphore {
private:
    int m_value;
    std::mutex m_mutex;
    std::condition_variable m_cv;

public:
    CountingSemaphore(int startValue) : m_value(startValue) {}

    void down() {
        std::unique_lock<std::mutex> lock(m_mutex);
        m_cv.wait(lock, [this] { return m_value > 0; });
        m_value--;
    }

    void up() {
        std::unique_lock<std::mutex> lock(m_mutex);
        m_value++;
        m_cv.notify_one();
    }

    int value() const {
        return m_value;
    }
};

class BoundedQueue {
private:
    int max_size;
    std::queue<std::string> queue;
    mutable std::mutex mutex;
    CountingSemaphore empty_sem;
    CountingSemaphore full_sem;

public:
    BoundedQueue(int size) : max_size(size), empty_sem(size), full_sem(0) {}

    void enqueue(const std::string &s) {
        empty_sem.down();
        std::lock_guard<std::mutex> lock(mutex);
        queue.push(s);
        full_sem.up();
    }

    std::string dequeue() {
        full_sem.down();
        std::lock_guard<std::mutex> lock(mutex);
        std::string s = queue.front();
        queue.pop();
        empty_sem.up();
        return s;
    }

    bool empty() const {
        std::lock_guard<std::mutex> lock(mutex);
        return queue.empty();
    }

    std::mutex& getMutex() const {
        return mutex;
    }
};

std::vector<std::unique_ptr<BoundedQueue>> producers_queues;
std::vector<std::unique_ptr<BoundedQueue>> co_editors_queues;

void producer(int id, int numberOfProducts) {
    static const std::vector<std::string> types = {"SPORTS", "NEWS", "WEATHER"};
    std::random_device rd;
    std::mt19937 gen(rd());
    std::uniform_int_distribution<> type_dist(0, 2);

    for (int i = 0; i < numberOfProducts; ++i) {
        std::string message = "producer " + std::to_string(id + 1) + " " + types[type_dist(gen)] + " " + std::to_string(i);
        producers_queues[id]->enqueue(message);
    }
    producers_queues[id]->enqueue("DONE");
}

void dispatcher() {
    std::vector<int> done_producers(producers_queues.size(), 0);
    int total_done = 0;

    while (total_done < producers_queues.size()) {
        for (size_t i = 0; i < producers_queues.size(); ++i) {
            if (done_producers[i]) continue;
            if (producers_queues[i]->empty()) continue;

            std::string message = producers_queues[i]->dequeue();
            if (message == "DONE") {
                done_producers[i] = 1;
                total_done++;
            } else {
                std::string type = split(message, ' ')[2];
                if (type == "SPORTS") {
                    co_editors_queues[0]->enqueue(message);
                } else if (type == "NEWS") {
                    co_editors_queues[1]->enqueue(message);
                } else if (type == "WEATHER") {
                    co_editors_queues[2]->enqueue(message);
                }
            }
        }
    }

    for (auto& queue : co_editors_queues) {
        queue->enqueue("DONE");
    }
}

void co_editor(int id, BoundedQueue* screen_queue) {
    while (true) {
        std::string message = co_editors_queues[id]->dequeue();
        if (message == "DONE") {
            screen_queue->enqueue("DONE");
            break;
        }
        std::this_thread::sleep_for(std::chrono::milliseconds(100));
        screen_queue->enqueue(message);
    }
}

int main(int argc, char** argv) {
    if (argc != 2) {
        std::cerr << "Usage: " << argv[0] << " <config_file>" << std::endl;
        return 1;
    }

    std::ifstream t(argv[1]);
    std::string config((std::istreambuf_iterator<char>(t)), std::istreambuf_iterator<char>());
    std::vector<std::string> v = split(config, '\n');

    std::vector<std::thread> producer_threads;

    for (size_t i = 0; i < v.size() - 1; i += 4) {
        int id = std::stoi(split(v[i], ' ')[1]) - 1;
        int numberOfProducts = std::stoi(v[i + 1]);
        int queue_size = std::stoi(split(v[i + 2], '=')[1]);

        producers_queues.push_back(std::make_unique<BoundedQueue>(queue_size));
        producer_threads.emplace_back(producer, id, numberOfProducts);

        if (co_editors_queues.size() < 3) {
            co_editors_queues.emplace_back(std::make_unique<BoundedQueue>(queue_size));
        }
    }

    int co_editors_queues_size = std::stoi(split(v.back(), '=')[1]);
    BoundedQueue screen_queue(co_editors_queues_size);

    std::thread disp_thread(dispatcher);

    std::vector<std::thread> co_editors_threads;
    for (int i = 0; i < 3; ++i) {
        co_editors_threads.emplace_back(co_editor, i, &screen_queue);
    }

    int ended = 0;
    while (ended != 3) {
        std::string s = screen_queue.dequeue();
        if (s == "")
            continue;
        if (s == "DONE") {
            ended++;
            continue;
        }
        std::cout << s << std::endl;
    }

    for (auto& thread : producer_threads) {
        thread.join();
    }
    disp_thread.join();
    for (auto &thread : co_editors_threads) {
        thread.join();
    }

    std::cout << "DONE" << std::endl;

    return 0;
}
