package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;

public class GTools {
   public static Preferences getPreferences(String var0) {
      return Gdx.app.getPreferences(var0);
   }

   public static float[] getVertices(float[] var0, float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      float var12;
      float var13;
      label15: {
         var5 = -var5;
         var6 = -var6;
         float var10 = var5 + var3;
         float var11 = var6 + var4;
         var13 = var1 - var5;
         var12 = var2 - var6;
         if(var8 == 1.0F) {
            var3 = var11;
            var1 = var10;
            var2 = var6;
            var4 = var5;
            if(var9 == 1.0F) {
               break label15;
            }
         }

         var4 = var5 * var8;
         var2 = var6 * var9;
         var1 = var10 * var8;
         var3 = var11 * var9;
      }

      if(var7 != 0.0F) {
         var5 = MathUtils.cosDeg(var7);
         var6 = MathUtils.sinDeg(var7);
         var7 = var4 * var5;
         var8 = var4 * var6;
         var4 = var5 * var3;
         var9 = var3 * var6;
         var3 = var7 - var2 * var6 + var13;
         var2 = var2 * var5 + var8 + var12;
         var0[0] = var3;
         var0[1] = var2;
         var7 = var7 - var9 + var13;
         var8 = var8 + var4 + var12;
         var0[2] = var7;
         var0[3] = var8;
         var5 = var1 * var5 - var9 + var13;
         var1 = var1 * var6 + var4 + var12;
         var0[4] = var5;
         var0[5] = var1;
         var0[6] = var5 + var3 - var7;
         var0[7] = var1 - (var8 - var2);
         return var0;
      } else {
         var4 += var13;
         var2 += var12;
         var1 += var13;
         var3 += var12;
         var0[0] = var4;
         var0[1] = var2;
         var0[2] = var4;
         var0[3] = var3;
         var0[4] = var1;
         var0[5] = var3;
         var0[6] = var1;
         var0[7] = var2;
         return var0;
      }
   }

   public static String[] jsonValueToStringArray(JsonValue var0) {
      if(var0 == null) {
         return null;
      } else {
         String[] var2 = new String[var0.size];

         for(int var1 = 0; var1 < var2.length; ++var1) {
            var2[var1] = var0.getString(var1);
         }

         return var2;
      }
   }

   public static String[] splitString(String var0, String var1) {
      boolean var2 = false;
      if(var0 == null) {
         return null;
      } else {
         Array var6 = new Array();

         int var3;
         for(int var4 = 0; !var2; var4 = var1.length() + var3) {
            var3 = var0.indexOf(var1, var4);
            if(var3 == -1) {
               var3 = var0.length();
               var2 = true;
            }

            int var5;
            if(var3 > 0 && var0.charAt(var3 - 1) == 13) {
               var5 = var3 - 1;
            } else {
               var5 = var3;
            }

            String var7 = var0.substring(var4, var5).trim();
            if(!var7.equals("")) {
               var6.add(var7);
            }
         }

         return (String[])var6.toArray(String.class);
      }
   }

   public static void toParentCoordinate(Actor var0) {
      float var6 = var0.getScaleX();
      float var7 = var0.getScaleY();
      float var5 = var0.getRotation();
      float var2 = -var0.getOriginX();
      float var1 = -var0.getOriginY();
      float var3 = var0.getX() - var2;
      float var4 = var0.getY() - var1;
      if(var6 != 1.0F || var7 != 1.0F) {
         var1 *= var7;
         var2 *= var6;
      }

      if(var5 != 0.0F) {
         var6 = MathUtils.cosDeg(var5);
         var5 = MathUtils.sinDeg(var5);
         var3 += var2 * var6 - var1 * var5;
         var1 = var1 * var6 + var2 * var5 + var4;
         var2 = var3;
      } else {
         var2 += var3;
         var1 += var4;
      }

      var0.setPosition(var2, var1);
   }

   public static void toScreenCoordinate(Actor var0) {
      Group var4 = var0.getParent();
      float var1 = var0.getRotation();
      float var3 = var0.getScaleX();

      float var2;
      for(var2 = var0.getScaleY(); var4 != null; var4 = var4.getParent()) {
         toParentCoordinate(var0);
         var0.setRotation(var4.getRotation());
         var0.setScale(var4.getScaleX(), var4.getScaleY());
         var3 *= var4.getScaleX();
         var2 *= var4.getScaleY();
         var1 += var4.getRotation();
         var0.setOriginX(var4.getOriginX() - var0.getX());
         var0.setOriginY(var4.getOriginY() - var0.getY());
         var0.setX(var0.getX() + var4.getX());
         var0.setY(var0.getY() + var4.getY());
      }

      var0.setRotation(var1);
      var0.setScale(var3, var2);
      var0.setOrigin(0.0F, 0.0F);
   }

   public static float[] toScreenPoint(Actor var0) {
      float[] var1 = new float[]{var0.getX(), var0.getY()};

      for(Group var2 = var0.getParent(); var2 != null; var2 = var2.getParent()) {
         if(var2 instanceof Group) {
            var1[0] += var2.getX();
            var1[1] += var2.getY();
         }
      }

      return var1;
   }
}
