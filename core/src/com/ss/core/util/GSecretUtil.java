package com.ss.core.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;


public class GSecretUtil {
   private static boolean[] giftGet = new boolean[20];
   private static boolean[] giftGetEx = new boolean[16];
   private static boolean[] giftGetEx2 = new boolean[10];
   private static boolean[] giftGetEx3 = new boolean[100];
   private static boolean[] giftGetEx4 = new boolean[200];
   private static byte[] iv = new byte[]{(byte)1, (byte)2, (byte)3, (byte)4, (byte)5, (byte)6, (byte)7, (byte)8};
   private static byte[] key = new byte[8];

   public static String byte2hex(byte[] var0) {
      String var2 = "";

      for(int var1 = 0; var1 < var0.length; ++var1) {
         String var3 = Integer.toHexString(var0[var1] & 255);
         if(var3.length() == 1) {
            var2 = var2 + "0" + var3;
         } else {
            var2 = var2 + var3;
         }
      }

      return var2.toUpperCase();
   }

   /*public static int checkNum(String string) {
      try {
         String string2 = GSecretUtil.decodeByDES((String) string.toUpperCase());
         int n = Integer.parseInt(string2);
         if (n >= 6000000 && n < 6300000) {
            return 85 + (-300 + n / 20000);
         }
         if (n >= 5950000 && n < 6000000) {
            return 84;
         }
         if (n >= 4000000 && n < 4950000) {
            return -35 + n / 50000;
         }
         if (n >= 5000000 && n < 5950000) {
            return -35 + n / 50000;
         }
         if (n >= 2000000 && n < 2100000) {
            return 38;
         }
         if (n >= 2100000 && n < 3000000) {
            return 39;
         }
         if (n >= 3000000 && n < 3100000) {
            return 40;
         }
         if (n >= 3100000 && n < 3200000) {
            return 41;
         }
         if (n >= 1000000 && n < 2000000) {
            return 34 + n / 500000;
         }
         int n2 = n / 10000;
         if (n2 >= giftGet.length) {
            n2 = giftGet.length + (n - 10000 * giftGet.length) / 50000;
         }
         if (n < 0) return -1;
         if (n2 >= giftGet.length + giftGetEx.length + giftGetEx2.length + giftGetEx3.length + giftGetEx4.length) {
            return -1;
         }
         if (n == 0) return n2;
         if (!string2.startsWith("0")) return n2;
      }
      catch(Exception e){

      }
      return -1;
   }*/
/*
   public static String decodeByDES(String var0) {
      IvParameterSpec var2 = new IvParameterSpec(iv);
      byte[] var1 = new byte[1];

      byte[] var6;
      try {
         setKey("raiden");
         SecretKeySpec var3 = new SecretKeySpec(key, "DES");
         Cipher var4 = Cipher.getInstance("DES/CBC/PKCS5Padding");
         var4.init(2, var3, var2);
         var6 = var4.doFinal(hex2byte(var0));
      } catch (Exception var5) {
         var5.printStackTrace();
         var6 = var1;
      }

      return new String(var6);
   }*/

   public static byte[] hex2byte(String var0) {
      byte[] var2 = new byte[8];
      byte[] var3 = var0.getBytes();

      for(int var1 = 0; var1 < 8; ++var1) {
         var2[var1] = uniteBytes(var3[var1 * 2], var3[var1 * 2 + 1]);
      }

      return var2;
   }

   public static boolean isGiftGet(int var0) {
      return var0 >= giftGet.length + giftGetEx.length + giftGetEx2.length + giftGetEx3.length?giftGetEx4[var0 - (giftGet.length + giftGetEx.length + giftGetEx2.length + giftGetEx3.length)]:(var0 >= giftGet.length + giftGetEx.length + giftGetEx2.length?giftGetEx3[var0 - giftGet.length - giftGetEx.length - giftGetEx2.length]:(var0 >= giftGet.length + giftGetEx.length?giftGetEx2[var0 - giftGet.length - giftGetEx.length]:(var0 < giftGet.length?giftGet[var0]:giftGetEx[var0 - giftGet.length])));
   }

   public static void readGiftGet(DataInputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGet.length; ++var1) {
         giftGet[var1] = var0.readBoolean();
      }

   }

   public static void readGiftGetEx(DataInputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx.length; ++var1) {
         giftGetEx[var1] = var0.readBoolean();
      }

   }

   public static void readGiftGetEx2(DataInputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx2.length; ++var1) {
         giftGetEx2[var1] = var0.readBoolean();
      }

   }

   public static void readGiftGetEx3(DataInputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx3.length; ++var1) {
         giftGetEx3[var1] = var0.readBoolean();
      }

   }

   public static void readGiftGetEx4(DataInputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx4.length; ++var1) {
         giftGetEx4[var1] = var0.readBoolean();
      }

   }

   public static void setGiftGet(int var0) {
      if(var0 >= giftGet.length + giftGetEx.length + giftGetEx2.length + giftGetEx3.length) {
         giftGetEx4[var0 - (giftGet.length + giftGetEx.length + giftGetEx2.length + giftGetEx3.length)] = true;
      }

      if(var0 >= giftGet.length + giftGetEx.length + giftGetEx2.length) {
         giftGetEx3[var0 - giftGet.length - giftGetEx.length - giftGetEx2.length] = true;
      } else if(var0 >= giftGet.length + giftGetEx.length) {
         giftGetEx2[var0 - giftGet.length - giftGetEx.length] = true;
      } else if(var0 < giftGet.length) {
         giftGet[var0] = true;
      } else {
         giftGetEx[var0 - giftGet.length] = true;
      }
   }

   public static void setKey(String var0) {
      for(int var1 = 0; var1 < key.length; ++var1) {
         if(var1 < var0.length()) {
            key[var1] = (byte)var0.charAt(var1);
         } else {
            key[var1] = 0;
         }
      }

   }

   public static byte uniteBytes(byte var0, byte var1) {
      return (byte)((byte)(Byte.decode("0x" + new String(new byte[]{var0})).byteValue() << 4) ^ Byte.decode("0x" + new String(new byte[]{var1})).byteValue());
   }

   public static void writeGiftGet(DataOutputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGet.length; ++var1) {
         var0.writeBoolean(giftGet[var1]);
      }

   }

   public static void writeGiftGetEx(DataOutputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx.length; ++var1) {
         var0.writeBoolean(giftGetEx[var1]);
      }

   }

   public static void writeGiftGetEx2(DataOutputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx2.length; ++var1) {
         var0.writeBoolean(giftGetEx2[var1]);
      }

   }

   public static void writeGiftGetEx3(DataOutputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx3.length; ++var1) {
         var0.writeBoolean(giftGetEx3[var1]);
      }

   }

   public static void writeGiftGetEx4(DataOutputStream var0) throws IOException {
      for(int var1 = 0; var1 < giftGetEx4.length; ++var1) {
         var0.writeBoolean(giftGetEx4[var1]);
      }

   }
}
