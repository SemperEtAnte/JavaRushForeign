package ru.semperante.javarushforeign;


import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.ValueLayout;

public class NativeUtils {

   public static MemorySegment allocateMatrix(int width, int height, MemorySession session) {
      long[] memories = new long[height];
      for (int i = 0; i < height; ++i) {
         MemorySegment ms = MemorySegment.allocateNative(8L * width, session);
         memories[i] = ms.address().toRawLongValue();
      }
      MemorySegment ms = MemorySegment.allocateNative(8L * height, session);
      ms.copyFrom(MemorySegment.ofArray(memories));
      return ms;
   }

   public static MemorySegment allocateMatrix(double[][] matrix, MemorySession session) {
      long[] memories = new long[matrix.length];
      for (int i = 0; i < matrix.length; ++i) {
         MemorySegment ms = allocateArray(matrix[i], session);
         memories[i] = ms.address().toRawLongValue();
      }
      MemorySegment ms = MemorySegment.allocateNative(8L * memories.length, session);
      ms.copyFrom(MemorySegment.ofArray(memories));
      return ms;
   }

   public static MemorySegment allocateArray(double[] array, MemorySession session) {
      MemorySegment ms = MemorySegment.allocateNative(8L * array.length, session);
      ms.copyFrom(MemorySegment.ofArray(array));
      return ms;
   }

   public static double[][] readMatrix(MemoryAddress from, int width, int height) {
      double[][] result = new double[height][width];
      for (int i = 0; i < height; ++i) {
         MemoryAddress ms = MemoryAddress.ofLong(from.getAtIndex(ValueLayout.JAVA_LONG, i));
         for (int j = 0; j < width; ++j) {
            result[i][j] = ms.getAtIndex(ValueLayout.JAVA_DOUBLE, j);
         }
      }
      return result;
   }
}
