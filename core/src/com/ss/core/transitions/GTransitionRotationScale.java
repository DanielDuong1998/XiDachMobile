package com.ss.core.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.ss.core.transitions.GTransition;

public class GTransitionRotationScale extends GTransition {
   public static final int ROTATION_SCALE = 1;
   public static final int SCALE = 0;
   private static final GTransitionRotationScale instance = new GTransitionRotationScale();
   private int type;

   public static GTransitionRotationScale init(float var0, int var1) {
      instance.duration = var0;
      instance.type = var1;
      return instance;
   }

   public void render(Batch var1, Texture var2, Texture var3, float var4) {
      float var6 = (float)var2.getWidth();
      float var7 = (float)var2.getHeight();
      float offsetx = 0;//(Gdx.graphics.getWidth() - var6)/2;
      float offsety = 0;//(Gdx.graphics.getHeight() - var7)/2;
      float var5;
      if(this.type == 1) {
         var5 = Interpolation.sineIn.apply(var4);
      } else {
         var5 = 0.0F;
      }


      var4 = Interpolation.fade.apply(var4);
      Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
      Gdx.gl.glClear(16384);
      var1.begin();
      var1.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      var1.draw(var3, offsetx, offsety, var6 / 2.0F, var7 / 2.0F, var6, var7, 1.0F, 1.0F, -360.0F * var5, 0, 0, var3.getWidth(), var3.getHeight(), false, true);
      var1.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      var1.draw(var2, offsetx, offsety, var6 / 2.0F, var7 / 2.0F, var6, var7, 1.0F - var4, 1.0F - var4, 360.0F * var5, 0, 0, var2.getWidth(), var2.getHeight(), false, true);
      var1.end();
   }
}
