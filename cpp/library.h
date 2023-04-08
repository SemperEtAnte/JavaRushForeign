#ifndef TEST_MATRIX_LIBRARY_H
#define TEST_MATRIX_LIBRARY_H

#include <cstdlib>
#include "omp.h"

extern "C"
{

double **multiplyMatrix(double **, double **, size_t, size_t, size_t, size_t);
double **multiplyMatrixParallel(double **, double **, size_t, size_t, size_t, size_t);
};
#endif //TEST_MATRIX_LIBRARY_H
