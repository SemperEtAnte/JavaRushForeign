#include "library.h"

extern "C"
{
double **multiplyMatrix(double **A, double **B, size_t width1, size_t height1, size_t width2, size_t height2) {
    if (width1 != height2)
        return nullptr;

    double **res = (double **) malloc(sizeof(double *) * height1);
    for (size_t i = 0; i < height1; ++i) {
        res[i] = (double *) malloc(sizeof(double) * width2);
    }
    for (size_t i = 0; i < height1; i++) {
        for (size_t j = 0; j < width2; j++) {
            res[i][j] = 0;
            for (size_t k = 0; k < height2; k++) {
                res[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    return res;
}

double **multiplyMatrixParallel(double **A, double **B, size_t width1, size_t height1, size_t width2, size_t height2) {
    if (width1 != height2)
        return nullptr;

    double **res = (double **) malloc(sizeof(double *) * height1);
    for (size_t i = 0; i < height1; ++i) {
        res[i] = (double *) malloc(sizeof(double) * width2);
    }
    size_t i, j, k;
#pragma omp parallel for private(j, k)
    for (i = 0; i < height1; i++) {

        for (j = 0; j < width2; j++) {
            res[i][j] = 0;
            for (k = 0; k < height2; k++) {
                res[i][j] += A[i][k] * B[k][j];
            }
        }
    }
    return res;
}
};

