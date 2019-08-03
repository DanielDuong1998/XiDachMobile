package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;

public class GDevice {
   private static GDevice.GDeviceKeyListener deviceKey;
   private static boolean isKeyPressed = false;

   public static void playVibrate(int var0) {
      Gdx.input.vibrate(var0);
   }

   public static void playVibrate(long[] var0, int var1) {
      Gdx.input.vibrate(var0, var1);
   }

   public static void registerDeviceKeyUpdate() {
   }

   public static void setDeviceKeyListener(GDevice.GDeviceKeyListener var0) {
      if(var0 == null) {
         Gdx.input.setCatchBackKey(false);
         Gdx.input.setCatchMenuKey(false);
      } else {
         deviceKey = var0;
         GStage.registerUpdateService("deviceKeyUpdate", new GStage.GUpdateService() {
            public boolean update(float var1) {
               if(GDevice.deviceKey != null) {
                  if(!GDevice.isKeyPressed) {
                     if(GScreen.isKeyPressed(4)) {
                        GDevice.deviceKey.runBackKey();
                        GDevice.isKeyPressed = true;
                     } else if(GScreen.isKeyPressed(82)) {
                        GDevice.deviceKey.runMenuKey();
                        GDevice.isKeyPressed = true;
                        return false;
                     }
                  } else if(!GScreen.isKeyPressed(4) && !GScreen.isKeyPressed(82)) {
                     GDevice.isKeyPressed = false;
                     return false;
                  }
               }

               return false;
            }
         });
      }
   }

   public static void stopVibrate() {
      Gdx.input.cancelVibrate();
   }

   public float getAccelerometerX() {
      return Gdx.input.getAccelerometerX();
   }

   public float getAccelerometerY() {
      return Gdx.input.getAccelerometerY();
   }

   public int getDeviceRotion() {
      return Gdx.input.getRotation();
   }

   public boolean isAccelerometerAvailable() {
      return Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
   }

   public boolean isVibratorAvailable() {
      return Gdx.input.isPeripheralAvailable(Input.Peripheral.Vibrator);
   }

   public void setOnscreenKeyboardVisible(boolean var1) {
      Gdx.input.setOnscreenKeyboardVisible(var1);
   }

   public abstract static class GDeviceKeyListener {
      public GDeviceKeyListener(boolean var1, boolean var2) {
         if(var1) {
            Gdx.input.setCatchBackKey(true);
         }

         if(var2) {
            Gdx.input.setCatchMenuKey(true);
         }

      }

      public abstract void runBackKey();

      public abstract void runMenuKey();
   }
}
