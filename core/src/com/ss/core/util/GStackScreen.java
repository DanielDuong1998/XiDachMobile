package com.ss.core.util;

import com.ss.GMain;

import java.util.Vector;

public class GStackScreen {
   private static Vector<String> vector = new Vector();

   public static boolean isEmpty() {
      return vector == null || vector.size() <= 0;
   }

   public static String peek() {
      String var0 = (String)vector.get(vector.size() - 1);
      return var0.substring(0, var0.indexOf("@"));
   }

   public static void pop() {
      vector.removeElementAt(vector.size() - 1);
   }

   private static void printStack() {
      for(int var0 = 0; var0 < vector.size(); ++var0) {
         GMain.platform.log((String)vector.get(var0));
      }

   }

   public static void push(String var0) {
      vector.addElement(var0);
      printStack();
   }

   public static int search(String var0) {
      return 0;
   }
}
