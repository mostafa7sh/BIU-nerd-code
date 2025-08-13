#include <stdio.h>
#include <stdlib.h>

typedef unsigned char uchar;

typedef struct cache_line_s {
    uchar valid;
    uchar frequency;
    long int tag;
    uchar *block;
} cache_line_t;

typedef struct cache_s {
    uchar s;
    uchar t;
    uchar b;
    uchar E;
    cache_line_t **cache;
} cache_t;

cache_t initialize_cache(uchar s, uchar t, uchar b, uchar E);
uchar read_byte(cache_t cache, uchar *start, long int off);
void write_byte(cache_t cache, uchar *start, long int off, uchar new);
void print_cache(cache_t cache);

int main() {
    int n;
    printf("Size of data: ");
    scanf("%d", &n);
    uchar *mem = malloc(n);
    printf("Input data >> ");
    for (int i = 0; i < n; i++)
        scanf("%hhd", mem + i);

    int s, t, b, E;
    printf("s t b E: ");
    scanf("%d %d %d %d", &s, &t, &b, &E);
    cache_t cache = initialize_cache(s, t, b, E);

    while (1) {
        scanf("%d", &n);
        if (n < 0)
            break;
        read_byte(cache, mem, n);
    }

    puts("");
    print_cache(cache);

    free(mem);
    return 0;
}
/************************************************************************
 * function name: initialize_cache
 * The Input: s, t, b, E (too much to explain).
 * The output: a cache !!!
 * The Function operation: create a simulator of cache.
 *************************************************************************/
cache_t initialize_cache(uchar s, uchar t, uchar b, uchar E) {
    cache_t new;
    new.s = s;
    new.t = t;
    new.b = b;
    new.E = E;

    int S = 1 << s;
    int B = 1 << b;
    cache_line_t **array = malloc(sizeof(cache_line_t) * S * E);

    for (int i = 0; i < S; ++i) {
        array[i] = malloc(sizeof(cache_line_t));
        for (int j = 0; j < E; ++j) {
            array[i][j].valid = 0;
            array[i][j].frequency = 0;
            array[i][j].tag = 0;
            array[i][j].block = (uchar *)malloc(sizeof(uchar) * B);
            uchar zero = 0;
            for (int k = 0; k < B; ++k) {
                array[i][j].block[k] = zero;
            }
        }
    }

    new.cache = array;
    return new;
}
/************************************************************************
 * function name: read_byte.
 * The Input: cache (the cache), start (u can say it is a memory), off (what place in memory).
 * The output: (idk why I return here lol) a byte in the "cache".
 * The Function operation: operates like a real cache.
 *************************************************************************/
uchar read_byte(cache_t cache, uchar *start, long int off) {
    int set = (off >> cache.b) % (1 << cache.s);
    long int tag = (off >> (cache.s + cache.b)) % (1 << cache.t);
    int block = off % (1 << cache.b);
    int B = 1 << cache.b;

    // that loop checks if the data is already there
    for (int i = 0; i < cache.E; ++i) {
        if (cache.cache[set][i].tag == tag && cache.cache[set][i].valid) {
            if (cache.cache[set][i].block[block] == start[off]) {
                cache.cache[set][i].frequency += 1;
                return start[off];
            }
        }
    }

    uchar new = *(start + off);
    write_byte(cache, start, off, new);

    // stam just to keep the memory clean XD (don't remove it, bugs will show up like its spring)
    uchar zero = 0;
    for (int i = 0; i < (1 << cache.s); i++) {
        for (int j = 0; j < cache.E; j++) {
            if (!cache.cache[i][j].valid) {
                for (int k = 0; k < B; k++) {
                    cache.cache[i][j].block = (uchar *)malloc(sizeof(uchar) * B);
                    cache.cache[i][j].block[k] = zero;
                }
            }
        }
    }

    return new;
}

void write_byte(cache_t cache, uchar *start, long int off, uchar new) {

    int set = (off >> cache.b) % (1 << cache.s);
    long int tag = (off >> (cache.s + cache.b)) % (1 << cache.t);
    int B = 1 << cache.b;

    int lowest_frequency = cache.cache[set][0].frequency;
    int index_lowest_frequency = 0;
    for (int i = 0; i < cache.E; ++i) {
        if (lowest_frequency > cache.cache[set][i].frequency) {
            lowest_frequency = cache.cache[set][i].frequency;
            index_lowest_frequency = i;
        }
    }

    cache.cache[set][index_lowest_frequency].frequency = 1;
    cache.cache[set][index_lowest_frequency].valid = 1;
    cache.cache[set][index_lowest_frequency].tag = tag;
    cache.cache[set][index_lowest_frequency].block = (uchar *)malloc(sizeof(uchar) * B);
    int index = (off / B) * B;
    for (int i = 0; i < B; i++) {
        cache.cache[set][index_lowest_frequency].block[i] = start[index + i];
    }
}

/************************************************************************
 * function name: print_cache.
 * The Input: your cache sir!
 * The output: prints your cache on the screen sir!
 * The Function operation: same as I said before, sir!
 *************************************************************************/
void print_cache(cache_t cache) {
    int S = 1 << cache.s;
    int B = 1 << cache.b;
    for (int i = 0; i < S; i++) {
        printf("Set %d\n", i);
        for (int j = 0; j < cache.E; j++) {
            printf("%1d %d 0x%0*lx ", cache.cache[i][j].valid, cache.cache[i][j].frequency, cache.t, cache.cache[i][j].tag);
            for (int k = 0; k < B; k++) {
                printf("%02x ", cache.cache[i][j].block[k]);
            }
            puts("");
        }
    }
}
