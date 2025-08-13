void fmm(int n, int *m1, int *m2, int *result) {
    int *col1 = malloc(n * sizeof(int));
    int *col2 = malloc(n * sizeof(int));
    int s = 0, t = 0, u = 0, v = 0;
    long int square = n * n;

    for (int j = 0; j < n; j += 2) {
        for (int k = 0; k < n; k += 2) {
            *col1++ = *m2++;
            *col2++ = *m2;
            m2 += n - 1;
            *col1++ = *m2++;
            *col2++ = *m2;
            m2 += n - 1;
        }
        m2 -= square;
        col1 -= n;
        col2 -= n;
        for (int i = 0; i < n; i += 2) {
            for (int k = 0; k < n; k += 2) {
                t += *m1 * *col2++;
                s += *m1++ * *col1++;
                t += *m1 * *col2++;
                s += *m1 * *col1++;
                m1 += n - 1;
                col1 -= 2;
                col2 -= 2;
                u += *m1 * *col2++;
                v += *m1++ * *col1++;
                u += *m1 * *col2++;
                v += *m1 * *col1++;
                m1 -= n - 1;
            }
            m1 += n;
            col1 -= n;
            col2 -= n;
            *result++ = s;
            *result = t;
            result += n - 1;
            *result++ = v;
            *result = u;
            result += n - 1;
            v = u = t = s = 0;
        }
        m1 -= square;
        m2 += 2;
        result -= square;
        result += 2;
    }
}
