package com.ss.core.exSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.GMain;
import com.ss.core.util.GShapeTools;

public class GShapeSprite extends Group {
   private GShapeSprite.GShape shape = new GShapeSprite.GShape((GShapeSprite.GShape)null);

   public GShapeSprite() {
      this.addActor(this.shape);
   }

   public void createARC(boolean var1, float var2, float var3, float var4, float var5, float var6) {
      this.shape.createARC(var1, var2, var3, var4, var5, var6);
   }

   public void createCircle(boolean var1, float var2, float var3, float var4) {
      this.shape.createCircle(var1, var2, var3, var4);
   }

   public void createCurve(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      this.shape.createCurve(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public void createEllipse(boolean var1, float var2, float var3, float var4, float var5) {
      this.shape.createEllipse(var1, var2, var3, var4, var5);
   }

   public void createLine(float x, float y, float x2, float y2) {
      this.shape.createLine(x, y, x2, y2);
   }

   public void createPoint(float var1, float var2) {
      this.shape.createPoint(var1, var2);
   }

   public void createPolygon(boolean var1, float[] var2) {
      this.shape.createPolygon(var1, var2);
   }

   public void createRectangle(boolean var1, float var2, float var3, float var4, float var5) {
      this.shape.createRectangle(var1, var2, var3, var4, var5);
   }

   public void createTriangle(boolean var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.shape.createTriangle(var1, var2, var3, var4, var5, var6, var7);
   }

   public void createX(float var1, float var2, float var3) {
      this.shape.createX(var1, var2, var3);
   }

   public void setColor(float var1, float var2, float var3, float var4) {
      this.shape.setColor(var1, var2, var3, var4);
   }

   public void setColor(Color var1) {
      this.shape.setColor(var1);
   }

   private class GShape extends Actor {
      private static final int TYPE_ARC = 9;
      private static final int TYPE_BOX = 10;
      private static final int TYPE_CIRCLE = 4;
      private static final int TYPE_CONE = 12;
      private static final int TYPE_CURVE = 5;
      private static final int TYPE_ELLIPSE = 8;
      private static final int TYPE_LINE = 1;
      private static final int TYPE_POINT = 0;
      private static final int TYPE_POLYGON = 6;
      private static final int TYPE_POLYGON_LINE = 7;
      private static final int TYPE_RECTANGLE = 3;
      private static final int TYPE_TRIANGLE = 2;
      private static final int TYPE_X = 11;
      float cx1;
      float cx2;
      float cy1;
      float cy2;
      float radius;
      private ShapeRenderer.ShapeType shapeType;
      private int type;
      float[] vertices;
      float x2;
      float x3;
      float y2;
      float y3;

      private GShape() {
         this.shapeType = null;
         this.type = -1;
      }

      // $FF: synthetic method
      GShape(GShapeSprite.GShape var2) {
         this();
      }

      private void createARC(boolean var1, float var2, float var3, float var4, float var5, float var6) {
         ShapeRenderer.ShapeType var7;
         if(var1) {
            var7 = ShapeRenderer.ShapeType.Filled;
         } else {
            var7 = ShapeRenderer.ShapeType.Line;
         }

         this.shapeType = var7;
         this.type = 9;
         this.setX(var2);
         this.setY(var3);
         this.radius = var4;
         this.cx1 = var5;
         this.cy1 = var6;
      }

      private void createCircle(boolean var1, float var2, float var3, float var4) {
         ShapeRenderer.ShapeType var5;
         if(var1) {
            var5 = ShapeRenderer.ShapeType.Filled;
         } else {
            var5 = ShapeRenderer.ShapeType.Line;
         }

         this.shapeType = var5;
         this.type = 4;
         this.setX(var2);
         this.setY(var3);
         this.radius = var4;
      }

      private void createCurve(float var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
         this.shapeType = ShapeRenderer.ShapeType.Line;
         this.type = 5;
         this.setX(var1);
         this.setY(var2);
         this.cx1 = var3;
         this.cy1 = var4;
         this.cx2 = var5;
         this.cy2 = var6;
         this.x2 = var7;
         this.y2 = var8;
      }

      private void createEllipse(boolean var1, float var2, float var3, float var4, float var5) {
         ShapeRenderer.ShapeType var6;
         if(var1) {
            var6 = ShapeRenderer.ShapeType.Filled;
         } else {
            var6 = ShapeRenderer.ShapeType.Line;
         }

         this.shapeType = var6;
         this.type = 8;
         this.setBounds(var2, var3, var4, var5);
      }

      private void createLine(float x, float y, float x2, float y2) {
         this.shapeType = ShapeRenderer.ShapeType.Line;
         this.type = 1;
         this.setX(x);
         this.setY(y);
         this.x2 = x2;
         this.y2 = y2;
      }

      private void createPoint(float var1, float var2) {
         this.shapeType = ShapeRenderer.ShapeType.Point;
         this.type = 0;
         this.setX(var1);
         this.setY(var2);
      }

      private void createPolygon(boolean var1, float[] var2) {
         this.vertices = var2;
         this.shapeType = ShapeRenderer.ShapeType.Line;
         if(var1) {
            this.type = 6;
         } else {
            this.type = 7;
         }
      }

      private void createRectangle(boolean isFilled, float x, float y, float width, float height) {
         ShapeRenderer.ShapeType var6;
         if(isFilled) {
            var6 = ShapeRenderer.ShapeType.Filled;
         } else {
            var6 = ShapeRenderer.ShapeType.Line;
         }

         this.shapeType = var6;
         this.type = 3;
         this.setBounds(x, y, width, height);
      }

      private void createTriangle(boolean var1, float var2, float var3, float var4, float var5, float var6, float var7) {
         ShapeRenderer.ShapeType var8;
         if(var1) {
            var8 = ShapeRenderer.ShapeType.Filled;
         } else {
            var8 = ShapeRenderer.ShapeType.Line;
         }

         this.shapeType = var8;
         this.type = 2;
         this.setX(var2);
         this.setY(var3);
         this.x2 = var4;
         this.y2 = var5;
         this.x3 = var6;
         this.y3 = var7;
      }

      private void createX(float var1, float var2, float var3) {
         this.shapeType = ShapeRenderer.ShapeType.Line;
         this.type = 11;
         this.setX(var1);
         this.setY(var2);
         this.radius = var3;
      }

      public void draw(Batch var1, float var2) {
         if(this.type < 0) {
            GMain.platform.log("no set shape !!!!!!!!  " + this.type);
         } else {
            ShapeRenderer var6 = GShapeTools.getShapeRenderer();
            var1.end();
            Gdx.gl.glEnable(3042);
            Gdx.gl.glBlendFunc(770, 771);
            var6.setProjectionMatrix(var1.getProjectionMatrix());
            var6.setTransformMatrix(var1.getTransformMatrix());
            Color var7 = this.getColor();
            var6.setColor(var7.r, var7.g, var7.b, var7.a * var2);
            var6.begin(this.shapeType);
            var2 = this.getX();
            float var3 = this.getY();
            float var4 = this.getWidth();
            float var5 = this.getHeight();
            switch(this.type) {
            case 0:
               var6.point(var2, var3, 0.0F);
               break;
            case 1:
               var6.line(var2, var3, this.x2, this.y2);
               break;
            case 2:
               var6.triangle(var2, var3, this.x2, this.y2, this.x3, this.y3);
               break;
            case 3:
               var6.rect(var2, var3, var4, var5);
               break;
            case 4:
               var6.circle(var2, var3, this.radius);
               break;
            case 5:
               var6.curve(var2, var3, this.cx1, this.cy1, this.cx2, this.cy2, this.x2, this.y2, 1);
               break;
            case 6:
               var6.polygon(this.vertices);
               break;
            case 7:
               var6.polyline(this.vertices);
               break;
            case 8:
               var6.ellipse(var2, var3, var4, var5);
               break;
            case 9:
               var6.arc(var2, var3, this.radius, this.cx1, this.cy1);
               break;
            case 10:
               var6.box(var2, var3, 0.0F, var4, var5, 0.0F);
               break;
            case 11:
               var6.x(var2, var3, this.radius);
               break;
            case 12:
               var6.cone(var2, var3, 0.0F, this.radius, var5);
            }

            var6.end();
            var1.begin();
         }
      }
   }
}
