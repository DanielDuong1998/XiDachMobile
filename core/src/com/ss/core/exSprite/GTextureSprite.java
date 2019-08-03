package com.ss.core.exSprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GTextureSprite extends Actor {
   private float skewX;
   private float skewY;
   private TextureAtlas.AtlasSprite sprite;

   public GTextureSprite(Texture var1) {
      this((Texture)var1, 0, 0, var1.getWidth(), var1.getHeight());
   }

   public GTextureSprite(Texture var1, int var2, int var3, int var4, int var5) {
      this(new TextureAtlas.AtlasRegion(var1, var2, var3, var4, var5));
   }

   public GTextureSprite(TextureAtlas.AtlasRegion var1) {
      this.sprite = new TextureAtlas.AtlasSprite(var1);
   }

   public GTextureSprite(TextureRegion var1) {
      this((TextureRegion)var1, 0, 0, var1.getRegionWidth(), var1.getRegionHeight());
   }

   public GTextureSprite(TextureRegion var1, int var2, int var3, int var4, int var5) {
      this(var1.getTexture(), var2, var3, var4, var5);
   }

   public void draw(Batch var1, float var2) {
      this.sprite.draw(var1, var2);
   }

   public void rotate(float var1) {
      super.rotateBy(var1);
      this.sprite.rotate(var1);
   }

   public void scale(float var1) {
      super.scaleBy(var1);
      this.sprite.setScale(this.sprite.getScaleX() + var1, this.sprite.getScaleY() + var1);
   }

   public void setBounds(float var1, float var2, float var3, float var4) {
      super.setBounds(var1, var2, var3, var4);
      this.sprite.setBounds(var1, var2, var3, var4);
   }

   public void setColor(float var1, float var2, float var3, float var4) {
      super.setColor(var1, var2, var3, var4);
      this.sprite.setColor(var1, var2, var3, var4);
   }

   public void setColor(Color var1) {
      super.setColor(var1);
      this.sprite.setColor(var1);
   }

   public void setHeight(float var1) {
      super.setHeight(var1);
      this.sprite.setSize(this.sprite.getWidth(), var1);
   }

   public void setLeftBottom(float var1, float var2) {
      float[] var3 = this.sprite.getVertices();
      var3[5] += var1;
      var3[6] += var2;
   }

   public void setLeftTop(float var1, float var2) {
      float[] var3 = this.sprite.getVertices();
      var3[0] += var1;
      var3[1] += var2;
   }

   public void setOrigin(float var1, float var2) {
      super.setOrigin(var1, var2);
      this.sprite.setOrigin(var1, var2);
   }

   public void setOriginX(float var1) {
      super.setOriginX(var1);
      this.sprite.setOrigin(var1, this.getOriginY());
   }

   public void setOriginY(float var1) {
      super.setOriginY(var1);
      this.sprite.setOrigin(this.getOriginX(), var1);
   }

   public void setPosition(float var1, float var2) {
      super.setPosition(var1, var2);
      this.sprite.setPosition(var1, var2);
   }

   public void setPosition(float var1, float var2, int alignment) {
      super.setPosition(var1, var2);
      this.sprite.setPosition(var1, var2);
   }

   public void setRegion(TextureRegion var1) {
      this.sprite.setRegion(var1);
   }

   public void setRightBottom(float var1, float var2) {
      float[] var3 = this.sprite.getVertices();
      var3[10] += var1;
      var3[11] += var2;
   }

   public void setRightTop(float var1, float var2) {
      float[] var3 = this.sprite.getVertices();
      var3[15] += var1;
      var3[16] += var2;
   }

   public void setRotation(float var1) {
      super.setRotation(var1);
      this.sprite.setRotation(var1);
   }

   public void setScale(float var1, float var2) {
      super.setScale(var1, var2);
      this.sprite.setScale(var1, var2);
   }

   public void setScaleX(float var1) {
      super.setScaleX(var1);
      this.sprite.setScale(var1, this.getScaleY());
   }

   public void setScaleY(float var1) {
      super.setScaleY(var1);
      this.sprite.setScale(this.getScaleX(), var1);
   }

   public void setSize(float var1, float var2) {
      super.setSize(var1, var2);
      this.sprite.setSize(var1, var2);
   }

   public void setSkew(float var1, float var2) {
      this.skewX = var1;
      this.skewY = var2;
      float[] var8 = this.sprite.getVertices();

      for(int var6 = 0; var6 < var8.length; var6 += 5) {
         float var3 = var8[var6];
         float var4 = this.getX();
         float var5 = this.getOriginX();
         var8[var6] += (var8[var6 + 1] - (this.getY() + this.getOriginY())) * var1;
         int var7 = var6 + 1;
         var8[var7] += (var3 - (var4 + var5)) * var2;
      }

   }

   public void setWidth(float var1) {
      super.setWidth(var1);
      this.sprite.setSize(var1, this.sprite.getHeight());
   }

   public void setX(float var1) {
      super.setX(var1);
      this.sprite.setX(var1);
   }

   public void setY(float var1) {
      super.setY(var1);
      this.sprite.setY(var1);
   }

   public void size(float var1) {
      super.sizeBy(var1);
      this.sprite.setSize(this.sprite.getWidth() + var1, this.sprite.getHeight() + var1);
   }

   public void translate(float var1, float var2) {
      super.moveBy(var1, var2);
      this.sprite.translate(var1, var2);
   }

   public void moveBy(float var1, float var2) {
      super.moveBy(var1, var2);
      this.sprite.translate(var1, var2);
   }
}
