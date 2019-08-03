package com.ss.core.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.ss.core.transitions.GTransition;

public class GTransitionSlide extends GTransition {
   public static final int DOWN = 4;
   public static final int LEFT = 1;
   public static final int RIGHT = 2;
   public static final int UP = 3;
   private static final GTransitionSlide instance = new GTransitionSlide();
   private int direction;
   private Interpolation easing;
   private boolean slideOut;

   public static GTransitionSlide init(float var0, int var1, boolean var2, Interpolation var3) {
      instance.duration = var0;
      instance.direction = var1;
      instance.slideOut = var2;
      instance.easing = var3;
      return instance;
   }

   public void render(Batch var1, Texture var2, Texture var3, float var4) {
      float var7 = (float)var2.getWidth();
      float var8 = (float)var2.getHeight();
      float var5 = var4;
      if(this.easing != null) {
         var5 = this.easing.apply(var4);
      }

      int var9 = this.direction;
      float var6 = 0.0F;
      var4 = 0.0F;
      boolean var10;
      switch(var9) {
      case 1:
         var6 = var5 * -var7;
         var10 = this.slideOut;
         var4 = 0.0F;
         var5 = var6;
         if(!var10) {
            var4 = 0.0F;
            var5 = var6 + var7;
         }
         break;
      case 2:
         var6 = var7 * var5;
         var10 = this.slideOut;
         var4 = 0.0F;
         var5 = var6;
         if(!var10) {
            var4 = 0.0F;
            var5 = var6 - var7;
         }
         break;
      case 3:
         var6 = var8 * var5;
         var10 = this.slideOut;
         var5 = 0.0F;
         var4 = var6;
         if(!var10) {
            var4 = var6 - var8;
            var5 = 0.0F;
         }
         break;
      case 4:
         var6 = -var8 * var5;
         var10 = this.slideOut;
         var5 = 0.0F;
         var4 = var6;
         if(!var10) {
            var4 = var6 + var8;
            var5 = 0.0F;
         }
         break;
      default:
         var5 = var6;
      }

      Texture var11;
      if(this.slideOut) {
         var11 = var3;
      } else {
         var11 = var2;
      }

      Texture var12;
      if(this.slideOut) {
         var12 = var2;
      } else {
         var12 = var3;
      }

      Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
      Gdx.gl.glClear(16384);
      var1.begin();
      var1.draw(var11, 0.0F, 0.0F, 0.0F, 0.0F, var7, var8, 1.0F, 1.0F, 0.0F, 0, 0, var2.getWidth(), var2.getHeight(), false, true);
      var1.draw(var12, var5, var4, 0.0F, 0.0F, var7, var8, 1.0F, 1.0F, 0.0F, 0, 0, var3.getWidth(), var3.getHeight(), false, true);
      var1.end();
   }
}
