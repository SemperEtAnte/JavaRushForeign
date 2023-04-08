package ru.semperante.javarushforeign;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;

public class JavaMatrixMultiplier {
   public static double[][] multiplyMatrix(double[][] A, double[][] B) {
      double[][] res = new double[A.length][B[0].length];
      for (int i = 0; i < A.length; ++i) {
         for (int j = 0; j < B[0].length; ++j) {
            for (int k = 0; k < B.length; ++k) {
               res[i][j] += A[i][k] * B[k][j];
            }
         }
      }
      return res;
   }

   public static double[][] parallelMultiplyMatrix(double[][] A, double[][] B) {
      try (ForkJoinPool pool = new ForkJoinPool()) {
         double[][] res = new double[A.length][B[0].length];
         for (int i = 0; i < A.length; ++i) {
            final int idx = i;
            pool.submit(() -> {
               for (int j = 0; j < B[0].length; ++j) {
                  for (int k = 0; k < B.length; ++k) {
                     res[idx][j] += A[idx][k] * B[k][j];
                  }
               }
            });
         }
         pool.shutdown();
         pool.awaitTermination(10000, TimeUnit.DAYS);
         return res;
      }
      catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
}
