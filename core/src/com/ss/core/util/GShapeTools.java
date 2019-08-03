package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Polygon;

public class GShapeTools {
   private static ShapeRenderer sRender = new ShapeRenderer(1000);

   private static void begin(Batch var0, ShapeRenderer.ShapeType var1, Color var2) {
      var0.end();
      Gdx.gl.glEnable(3042);
      Gdx.gl.glBlendFunc(770, 771);
      sRender.setProjectionMatrix(var0.getProjectionMatrix());
      sRender.setTransformMatrix(var0.getTransformMatrix());
      sRender.setColor(var2);
      sRender.begin(var1);
   }

   public static void drawARC(Batch var0, Color var1, float var2, float var3, float var4, float var5, float var6, boolean var7) {
      ShapeRenderer.ShapeType var8;
      if(var7) {
         var8 = ShapeRenderer.ShapeType.Filled;
      } else {
         var8 = ShapeRenderer.ShapeType.Line;
      }

      begin(var0, var8, var1);
      sRender.arc(var2, var3, var4, var5, var6);
      end(var0);
   }

   public static void drawBox(Batch var0, Color var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      begin(var0, ShapeRenderer.ShapeType.Line, var1);
      sRender.box(var2, var3, var4, var5, var6, var7);
      end(var0);
   }

   public static void drawCircle(Batch var0, Color var1, float var2, float var3, float var4, boolean var5) {
      ShapeRenderer.ShapeType var6;
      if(var5) {
         var6 = ShapeRenderer.ShapeType.Filled;
      } else {
         var6 = ShapeRenderer.ShapeType.Line;
      }

      begin(var0, var6, var1);
      sRender.circle(var2, var3, var4);
      end(var0);
   }

   public static void drawCurve(Batch var0, Color var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, float var9) {
      begin(var0, ShapeRenderer.ShapeType.Line, var1);
      sRender.curve(var2, var3, var4, var5, var6, var7, var8, var9, 1);
      end(var0);
   }

   public static void drawEllipse(Batch var0, Color var1, float var2, float var3, float var4, float var5, boolean var6) {
      ShapeRenderer.ShapeType var7;
      if(var6) {
         var7 = ShapeRenderer.ShapeType.Filled;
      } else {
         var7 = ShapeRenderer.ShapeType.Line;
      }

      begin(var0, var7, var1);
      sRender.ellipse(var2, var3, var4, var5);
      end(var0);
   }

   public static void drawLine(Batch var0, Color var1, float var2, float var3, float var4, float var5) {
      begin(var0, ShapeRenderer.ShapeType.Line, var1);
      sRender.line(var2, var3, var4, var5);
      end(var0);
   }

   public static void drawPoint(Batch var0, Color var1, float var2, float var3) {
      begin(var0, ShapeRenderer.ShapeType.Point, var1);
      sRender.point(var2, var3, 0.0F);
      end(var0);
   }

   public static void drawPolygon(Batch var0, Color var1, Polygon var2) {
      begin(var0, ShapeRenderer.ShapeType.Line, var1);
      sRender.polygon(var2.getTransformedVertices());
      end(var0);
   }

   public static void drawRectangle(Batch var0, Color var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8, boolean var9) {
      ShapeRenderer.ShapeType var10;
      if(var9) {
         var10 = ShapeRenderer.ShapeType.Filled;
      } else {
         var10 = ShapeRenderer.ShapeType.Line;
      }

      begin(var0, var10, var1);
      //sRender.rect(var2, var3, var4, var5, var6, var7, var8);
      sRender.rect(var2, var3, var4, var5, var6, var7, var8, var8, 0); //xxxx
      end(var0);
   }

   public static void drawRectangle(Batch batch, Color color, float x, float y, float width, float height, boolean var6) {
      ShapeRenderer.ShapeType var7;
      if(var6) {
         var7 = ShapeRenderer.ShapeType.Filled;
      } else {
         var7 = ShapeRenderer.ShapeType.Line;
      }

      begin(batch, var7, color);
      sRender.rect(x, y, width, height);
      end(batch);
   }

   public static void drawTriangle(Batch var0, Color var1, float var2, float var3, float var4, float var5, float var6, float var7, boolean var8) {
      ShapeRenderer.ShapeType var9;
      if(var8) {
         var9 = ShapeRenderer.ShapeType.Filled;
      } else {
         var9 = ShapeRenderer.ShapeType.Line;
      }

      begin(var0, var9, var1);
      sRender.triangle(var2, var3, var4, var5, var6, var7);
      end(var0);
   }

   public static void drawX(Batch var0, Color var1, float var2, float var3, float var4) {
      begin(var0, ShapeRenderer.ShapeType.Line, var1);
      sRender.x(var2, var3, var4);
      end(var0);
   }

   private static void end(Batch var0) {
      sRender.end();
      var0.begin();
   }

   public static ShapeRenderer getShapeRenderer() {
      return sRender;
   }
}
