package com.ss.core.util;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GImage extends Actor {
   TextureRegion region;
   boolean rotate;

   public GImage(Texture var1) {
      this.region = new TextureRegion(var1, 0, 0, var1.getWidth(), var1.getHeight());
      this.setSize((float)this.region.getRegionWidth(), (float)this.region.getRegionHeight());
   }

   public GImage(TextureRegion var1) {
      this.region = var1;
      if(var1.getClass() == TextureAtlas.AtlasRegion.class && ((TextureAtlas.AtlasRegion)var1).rotate) {
         this.setSize((float)var1.getRegionHeight(), (float)var1.getRegionWidth());
         this.rotate = true;
      } else {
         this.setSize((float)var1.getRegionWidth(), (float)var1.getRegionHeight());
         this.rotate = false;
      }
   }

   public void draw(Batch var1, float var2) {
      Color var11 = this.getColor();
      var1.setColor(var11.r, var11.g, var11.b, var11.a * var2);
      var2 = this.getX();
      float var3 = this.getY();
      float var4 = this.getOriginX();
      float var5 = this.getOriginY();
      float var6 = this.getScaleX();
      float var7 = this.getScaleY();
      float var8 = this.getWidth();
      float var9 = this.getHeight();
      float var10 = this.getRotation();
      if(this.rotate) {
         var1.draw(this.region, var2, var3, var4, var5, var8, var9, var6, var7, var10, false);
      } else {
         var1.draw(this.region, var2, var3, var4, var5, var8, var9, var6, var7, var10);
      }

      this.setColor(var11);
   }
}
