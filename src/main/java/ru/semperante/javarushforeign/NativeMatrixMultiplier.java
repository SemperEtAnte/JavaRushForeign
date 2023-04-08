package ru.semperante.javarushforeign;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;


public class NativeMatrixMultiplier {
   private static final MethodHandle HANDLE;
   private static final MethodHandle HANDLE_PARALLEL;

   static {
      System.loadLibrary("test_matrix");
      FunctionDescriptor fd = FunctionDescriptor.of(
              //Возвращаемый тип функции в C
              ValueLayout.ADDRESS,
              //Формальные параметры функции в C
              ValueLayout.ADDRESS,
              ValueLayout.ADDRESS,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT,
              ValueLayout.JAVA_INT);

      HANDLE = Linker.nativeLinker().downcallHandle(
              SymbolLookup.loaderLookup().lookup("multiplyMatrix").orElseThrow(),
              fd
      );
      HANDLE_PARALLEL = Linker.nativeLinker().downcallHandle(
              SymbolLookup.loaderLookup().lookup("multiplyMatrixParallel").orElseThrow(),
              fd
      );
   }

   public static double[][] multiplyMatrix(double[][] A, double[][] B) throws Throwable {
      return mult(A, B, HANDLE);
   }

   public static double[][] multiplyMatrixParallel(double[][] A, double[][] B) throws Throwable {
      return mult(A, B, HANDLE_PARALLEL);
   }

   private static double[][] mult(double[][] A, double[][] B, MethodHandle handle) throws Throwable {
      int width1 = A[0].length;
      int height1 = A.length;
      int width2 = B[0].length;
      int height2 = B.length;
      try (MemorySession session = MemorySession.openConfined()) {
         MemorySegment m1 = NativeUtils.allocateMatrix(A, session);
         MemorySegment m2 = NativeUtils.allocateMatrix(B, session);
         MemoryAddress result = (MemoryAddress) handle.invoke(m1.address(), m2.address(), width1, height1, width2, height2);
         return NativeUtils.readMatrix(result, width2, height1);
      }
   }
}
