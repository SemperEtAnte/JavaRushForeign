package ru.semperante.javarushforeign;

import java.util.Arrays;
import java.util.Random;

public class JavaRushForeignMain {
   private static final int N = 1000;
   private static final Random RANDOM = new Random();

   public static void main(String... args) throws Throwable {
      double[][] m1 = new double[N][N];
      double[][] m2 = new double[N][N];
      for (int i = 0; i < N; ++i) {
         for (int j = 0; j < N; ++j) {
            m1[i][j] = round2((RANDOM.nextDouble() + 1D) * 10D);
            m2[i][j] = round2((RANDOM.nextDouble() + 1D) * 10D);
         }
      }
      new NativeMatrixMultiplier(); //Просто чтобы вызвался статический блок
      long time = System.currentTimeMillis();
      NativeMatrixMultiplier.multiplyMatrix(m1, m2); //Последовательный С
      System.out.printf("Native seq %dms.\n", System.currentTimeMillis() - time);
      time = System.currentTimeMillis();
      NativeMatrixMultiplier.multiplyMatrixParallel(m1, m2); //Параллельный С
      System.out.printf("Native parallel %dms.\n", System.currentTimeMillis() - time);
      time = System.currentTimeMillis();
      JavaMatrixMultiplier.multiplyMatrix(m1, m2); //Последовательная Java
      System.out.printf("Java seq %dms.\n", System.currentTimeMillis() - time);
      time = System.currentTimeMillis();
      JavaMatrixMultiplier.parallelMultiplyMatrix(m1, m2); //Параллельная Java
      System.out.printf("Java parallel %dms.\n", System.currentTimeMillis() - time);
   }

   private static void printMatrix(double[][] matrix) {
      for (double[] d : matrix) {
         System.out.println(Arrays.toString(d));
      }
   }


   private static double round2(double a) {
      return (int) (a * 100) / 100.0;
   }
}
