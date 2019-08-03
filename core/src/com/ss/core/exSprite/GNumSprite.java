package com.ss.core.exSprite;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.ss.core.util.GRes;
import java.util.HashMap;

public class GNumSprite extends Actor {
   public static final byte HCENTER_BOTTOM = 5;
   public static final byte HCENTER_TOP = 3;
   public static final byte HCENTER_VCENTER = 4;
   public static final byte LEFT_BOTTOM = 2;
   public static final byte LEFT_TOP = 0;
   public static final byte LEFT_VCENTER = 1;
   public static final byte RIGHT_BOTTOM = 8;
   public static final byte RIGHT_TOP = 6;
   public static final byte RIGHT_VCENTER = 7;
   private int anchor;
   private HashMap charMap;
   private String extraSymbol;
   private int num;
   private int numH;
   private String numStr;
   private int numW;
   private int spacing;
   private int type;

   public GNumSprite(TextureAtlas.AtlasRegion var1, int var2, int var3) {
      this(var1, var2, (String)null, var3, 0);
   }

   public GNumSprite(TextureAtlas.AtlasRegion var1, int var2, int var3, byte var4) {
      this(var1, var2, (String)null, var3, var4);
   }

   public GNumSprite(TextureAtlas.AtlasRegion var1, int var2, String var3, int var4, int var5) {
      this.numStr = "0";
      this.charMap = new HashMap();
      this.type = 0;
      this.setAtlasRegion(var1, var3);
      this.spacing = var4;
      this.anchor = var5;
      this.setNum(var2);
   }

   public GNumSprite(TextureAtlas.AtlasRegion var1, String var2, String var3, int var4, int var5) {
      this.numStr = "0";
      this.charMap = new HashMap();
      this.type = 1;
      this.setAtlasRegion(var1, var3);
      this.spacing = var4;
      this.anchor = var5;
      this.setNum(var2);
   }

   public void draw(Batch var1, float var2) {
      if(this.numStr != null) {
         int var13 = this.numStr.length();
         int var14 = this.numW;
         int var15 = this.spacing;
         int var16 = this.spacing;
         float var3 = this.getScaleX();
         float var4 = this.getScaleY();
         float var5 = this.getRotation();
         float var6 = MathUtils.sinDeg(var5);
         float var7 = MathUtils.cosDeg(var5);
         float var8 = (float)((var13 * (var14 + var15) - var16) * (-this.anchor / 3)) * var3 / 2.0F;
         float var9 = (float)(-this.anchor % 3 * this.numH / 2);

         for(var13 = 0; var13 < this.numStr.length(); ++var13) {
            TextureAtlas.AtlasRegion var17 = (TextureAtlas.AtlasRegion)this.charMap.get(Character.valueOf(this.numStr.charAt(var13)));
            if(var17 != null) {
               float var10 = (float)((this.numW + this.spacing) * var13) * var3 + var8;
               float var11 = this.getX();
               float var12 = this.getY();
               Color var18 = this.getColor();
               var1.setColor(var18.r, var18.g, var18.b, var18.a * var2);
               var1.draw(var17, var11 + var10 * var7, var10 * var6 + var12 + var9, 0.0F, -var9, (float)this.numW, (float)this.numH, var3, var4, var5);
            }
         }
      }

   }

   public float getHeight() {
      return (float)this.numH;
   }

   public float getWidth() {
      return (float)(this.numStr.length() * (this.numW + this.spacing) - this.spacing);
   }

   public void setAnchor(int var1) {
      this.anchor = var1;
   }

   public void setAtlasRegion(TextureAtlas.AtlasRegion var1) {
      this.setAtlasRegion(var1, this.extraSymbol);
   }

   public void setAtlasRegion(TextureAtlas.AtlasRegion var1, String var2) {
      int var4;
      if(var2 == null) {
         var4 = 10;
      } else {
         var4 = var2.length() + 10;
      }

      this.numW = var1.getRegionWidth() / var4;
      this.numH = var1.getRegionHeight();
      this.setHeight((float)this.numH);
      this.charMap.clear();
      char var3 = 48;

      for(int var5 = 0; var5 < var4; ++var5) {
         TextureAtlas.AtlasRegion var6 = GRes.createRegionFromAtlasRegion(var1, this.numW * var5, 0, this.numW, this.numH);
         if(var5 < 10) {
            this.charMap.put(Character.valueOf(var3), var6);
            ++var3;
         } else {
            this.charMap.put(Character.valueOf(var2.charAt(var5 - 10)), var6);
         }
      }

   }

   public void setNum(int var1) {
      if(this.num != var1 || this.type != 0) {
         this.type = 0;
         this.num = var1;
         this.numStr = Integer.toString(var1);
      }
   }

   public void setNum(String var1) {
      this.type = 1;
      this.numStr = var1;
   }

   public void setSpacing(int var1) {
      this.spacing = var1;
   }
}
