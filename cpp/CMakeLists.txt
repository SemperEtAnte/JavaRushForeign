cmake_minimum_required(VERSION 3.22)
project(test_matrix)

set(CMAKE_CXX_STANDARD 14)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++14 -fopenmp")

add_library(test_matrix SHARED library.cpp)
