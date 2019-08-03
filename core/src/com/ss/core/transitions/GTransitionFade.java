package com.ss.core.transitions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Interpolation;

public class GTransitionFade extends GTransition {
   private static final GTransitionFade instance = new GTransitionFade();

   public static GTransitionFade init(float var0) {
      instance.duration = var0;
      return instance;
   }

   public void render(Batch var1, Texture var2, Texture var3, float var4) {
      float var5 = (float)var2.getWidth();
      float var6 = (float)var2.getHeight();
      var4 = Interpolation.fade.apply(var4);
      Gdx.gl.glClearColor(0.0F, 0.0F, 0.0F, 1.0F);
      Gdx.gl.glClear(16384);
      var1.begin();
      var1.setColor(1.0F, 1.0F, 1.0F, 1.0F);
      var1.draw(var2, 0.0F, 0.0F, 0.0F, 0.0F, var5, var6, 1.0F, 1.0F, 0.0F, 0, 0, var2.getWidth(), var2.getHeight(), false, true);
      var1.setColor(1.0F, 1.0F, 1.0F, var4);
      var1.draw(var3, 0.0F, 0.0F, 0.0F, 0.0F, var5, var6, 1.0F, 1.0F, 0.0F, 0, 0, var3.getWidth(), var3.getHeight(), false, true);
      var1.end();
   }
}
