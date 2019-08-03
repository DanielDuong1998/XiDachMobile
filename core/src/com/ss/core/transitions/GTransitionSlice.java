package com.ss.core.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.utils.Array;
import com.ss.core.transitions.GTransition;

public class GTransitionSlice extends GTransition {
   public static final int DOWN = 2;
   public static final int UP = 1;
   public static final int UP_DOWN = 3;
   private static final GTransitionSlice instance = new GTransitionSlice();
   private int direction;
   private Interpolation easing;
   private Array sliceIndex = new Array();

   public static GTransitionSlice init(float var0, int var1, int var2, Interpolation var3) {
      instance.duration = var0;
      instance.direction = var1;
      instance.easing = var3;
      instance.sliceIndex.clear();

      for(var1 = 0; var1 < var2; ++var1) {
         instance.sliceIndex.add(Integer.valueOf(var1));
      }

      instance.sliceIndex.shuffle();
      return instance;
   }

   public void render(Batch var1, Texture var2, Texture var3, float var4) {
      float var5 = (float)var2.getWidth();
      float var6 = (float)var2.getHeight();
      float offsetx = 0;//(Gdx.graphics.getWidth() - var5)/2;
      float offsety = 0;//(Gdx.graphics.getHeight() - var6)/2;
      int var10 = (int)(var5 / (float)this.sliceIndex.size);
      Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
      Gdx.gl.glClear(16384);

      var1.begin();
      var1.draw(var2, offsetx, offsety, 0.0F, 0.0F, var5, var6, 1.0F, 1.0F, 0.0F, 0, 0, var2.getWidth(), var2.getHeight(), false, true);
      var5 = var4;
      if(this.easing != null) {
         var5 = this.easing.apply(var4);
      }

      var4 = 0.0F;

      for(int var9 = 0; var9 < this.sliceIndex.size; ++var9) {
         float var7 = (float)(var9 * var10);
         float var8 = ((float)((Integer)this.sliceIndex.get(var9)).intValue() / (float)this.sliceIndex.size + 1.0F) * var6;
         switch(this.direction) {
         case 1:
            var4 = -var8 + var8 * var5;
            break;
         case 2:
            var4 = var8 - var8 * var5;
            break;
         case 3:
            if(var9 % 2 == 0) {
               var4 = -var8 + var8 * var5;
            } else {
               var4 = var8 - var8 * var5;
            }
         }

         var1.draw(var3, offsetx+var7, offsety+var4, 0.0F, 0.0F, (float)var10, var6, 1.0F, 1.0F, 0.0F, var9 * var10, 0, var10, var3.getHeight(), false, true);
      }

      var1.end();
   }
}
