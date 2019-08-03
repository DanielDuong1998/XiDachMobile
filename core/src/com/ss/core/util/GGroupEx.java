package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.utils.SnapshotArray;
import com.ss.core.util.GStage;

public class GGroupEx extends Group {
   private final Matrix4 combinedMatrix = new Matrix4();
   private Rectangle cullingArea;
   private Matrix4 lastMatrix;
   private final Matrix4 oldMatrix = new Matrix4();
   private final Matrix4 originMatrix = new Matrix4();

   private void resetLastMatrix(Batch var1) {
   }

   private void setOriginMatrix(Batch var1) {
      this.lastMatrix = var1.getTransformMatrix();
      var1.getTransformMatrix().set(this.originMatrix);
      this.setupMatrices(var1);
   }

   private void setupMatrices(Batch var1) {
      Matrix4 var2 = var1.getProjectionMatrix();
      Matrix4 var3 = var1.getTransformMatrix();
      /*if(!Gdx.graphics.isGL20Available()) {
         GL10 var4 = Gdx.gl10;
         var4.glMatrixMode(5889);
         var4.glLoadMatrixf(var2.val, 0);
         var4.glMatrixMode(5888);
         var4.glLoadMatrixf(var3.val, 0);
      } else */{
         this.combinedMatrix.set(var2).mul(var3);
         GStage.shader.setUniformMatrix("u_projTrans", this.combinedMatrix);
         GStage.shader.setUniformi("u_texture", 0);
         var1.setShader(GStage.shader);
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

   protected void drawChildrenc(Batch var1, float var2) {
      byte var10 = 0;
      int var9 = 0;
      var2 *= this.getColor().a;
      SnapshotArray var12 = this.getChildren();
      Actor[] var13 = (Actor[])var12.begin();
      Rectangle var14 = this.cullingArea;
      Actor var16;
      if(var14 != null) {
         float var3 = var14.x;
         float var4 = var14.width;
         float var5 = var14.y;
         float var6 = var14.height;

         for(int var15 = var12.size; var9 < var15; ++var9) {
            var16 = var13[var9];
            if(var16.isVisible()) {
               float var7 = var16.getX();
               float var8 = var16.getY();
               if(var7 <= var4 + var3 && var8 <= var6 + var5 && var7 + var16.getWidth() >= var3 && var16.getHeight() + var8 >= var5) {
                  var16.draw(var1, var2);
               }
            }
         }
      } else {
         int var11 = var12.size;

         for(var9 = var10; var9 < var11; ++var9) {
            var16 = var13[var9];
            if(var16.isVisible()) {
               var16.draw(var1, var2);
            }
         }
      }

      var12.end();
   }

   public void setCullingArea(Rectangle var1) {
      this.cullingArea = var1;
   }
}
