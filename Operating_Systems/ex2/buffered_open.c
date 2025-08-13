#include "buffered_open.h"
#include <errno.h>
#include <stdarg.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

static int flush_write_buffer(buffered_file_t *bf) {
    if (bf->write_buffer_pos > 0) {
        ssize_t written = write(bf->fd, bf->write_buffer, bf->write_buffer_pos);
        if (written == -1) {
            return -1;
        }
        bf->write_buffer_pos = 0;
    }
    return 0;
}

static int refill_read_buffer(buffered_file_t *bf) {
    ssize_t read_bytes = read(bf->fd, bf->read_buffer, BUFFER_SIZE);
    if (read_bytes == -1) {
        return -1;
    }
    bf->read_buffer_size = read_bytes;
    bf->read_buffer_pos = 0;
    return 0;
}

buffered_file_t *buffered_open(const char *pathname, int flags, ...) {
    buffered_file_t *bf = malloc(sizeof(buffered_file_t));
    if (!bf) {
        return NULL;
    }

    int open_flags = flags & ~O_PREAPPEND;
    va_list args;
    va_start(args, flags);
    mode_t mode = 0;
    if (flags & O_CREAT) {
        mode = va_arg(args, mode_t);
    }
    va_end(args);

    bf->fd = open(pathname, open_flags, mode);
    if (bf->fd == -1) {
        free(bf);
        return NULL;
    }

    bf->read_buffer = malloc(BUFFER_SIZE);
    bf->write_buffer = malloc(BUFFER_SIZE);
    if (!bf->read_buffer || !bf->write_buffer) {
        close(bf->fd);
        free(bf->read_buffer);
        free(bf->write_buffer);
        free(bf);
        return NULL;
    }

    bf->read_buffer_size = 0;
    bf->write_buffer_size = 0;
    bf->read_buffer_pos = 0;
    bf->write_buffer_pos = 0;
    bf->flags = flags;
    bf->preappend = (flags & O_PREAPPEND) ? 1 : 0;

    return bf;
}

ssize_t buffered_write(buffered_file_t *bf, const void *buf, size_t count) {
    if (bf->preappend) {
        off_t file_size = lseek(bf->fd, 0, SEEK_END);
        if (file_size == -1) {
            return -1;
        }
        char *temp_buf = malloc(file_size);
        if (!temp_buf) {
            return -1;
        }
        lseek(bf->fd, 0, SEEK_SET);
        ssize_t read_size = read(bf->fd, temp_buf, file_size);
        if (read_size == -1) {
            free(temp_buf);
            return -1;
        }

        if (flush_write_buffer(bf) == -1) {
            free(temp_buf);
            return -1;
        }
        lseek(bf->fd, 0, SEEK_SET);
        if (write(bf->fd, buf, count) == -1) {
            free(temp_buf);
            return -1;
        }

        if (write(bf->fd, temp_buf, read_size) == -1) {
            free(temp_buf);
            return -1;
        }

        free(temp_buf);
        bf->preappend = 0;
        return count;
    }

    size_t to_write = count;
    size_t total_written = 0;

    while (to_write > 0) {
        size_t space_left = BUFFER_SIZE - bf->write_buffer_pos;
        size_t write_now = (to_write < space_left) ? to_write : space_left;

        memcpy(bf->write_buffer + bf->write_buffer_pos, (char *)buf + total_written, write_now);
        bf->write_buffer_pos += write_now;
        total_written += write_now;
        to_write -= write_now;

        if (bf->write_buffer_pos == BUFFER_SIZE) {
            if (flush_write_buffer(bf) == -1) {
                return -1;
            }
        }
    }

    return total_written;
}

ssize_t buffered_read(buffered_file_t *bf, void *buf, size_t count) {
    if (flush_write_buffer(bf) == -1) {
        return -1;
    }

    size_t to_read = count;
    size_t total_read = 0;

    while (to_read > 0) {
        if (bf->read_buffer_pos == bf->read_buffer_size) {
            if (refill_read_buffer(bf) == -1) {
                return -1;
            }
            if (bf->read_buffer_size == 0) {
                break;
            }
        }

        size_t read_now = (to_read < (bf->read_buffer_size - bf->read_buffer_pos)) ? to_read : (bf->read_buffer_size - bf->read_buffer_pos);
        memcpy((char *)buf + total_read, bf->read_buffer + bf->read_buffer_pos, read_now);
        bf->read_buffer_pos += read_now;
        total_read += read_now;
        to_read -= read_now;
    }

    return total_read;
}

int buffered_flush(buffered_file_t *bf) {
    return flush_write_buffer(bf);
}

int buffered_close(buffered_file_t *bf) {
    if (buffered_flush(bf) == -1) {
        return -1;
    }
    if (close(bf->fd) == -1) {
        return -1;
    }
    free(bf->read_buffer);
    free(bf->write_buffer);
    free(bf);
    return 0;
}
