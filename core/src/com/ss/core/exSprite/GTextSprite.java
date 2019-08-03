package com.ss.core.exSprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Disposable;
import com.ss.core.util.GClipGroup;
import com.ss.core.util.GRes;

public class GTextSprite extends GClipGroup implements Disposable {
   private static String usedTextSample = "";
   private GTextSprite.GText gText;

   public GTextSprite(String var1, String var2, int var3, Color var4) {
      this.gText = new GTextSprite.GText(var1, var2, var3, var4, (GTextSprite.GText)null);
      this.addActor(this.gText);
   }

   private static String getTextSampling(String var0) {
      if(var0 == null) {
         return null;
      } else {
         StringBuffer var3 = new StringBuffer();
         var3.append(var0.charAt(0));

         for(int var2 = 1; var2 < var0.length(); ++var2) {
            char var1 = var0.charAt(var2);
            if(var3.indexOf("" + var1) == -1) {
               var3.append(var1);
            }
         }

         return var3.toString();
      }
   }

   public static String getUsedTextSample() {
      return usedTextSample;
   }

   private static void updateUsedTextSample(String var0) {
      usedTextSample = getTextSampling(usedTextSample + var0);
   }

   public void dispose() {
      this.gText.dispose();
   }

   public float getHeight() {
      return this.getTextBounds().height;
   }

   public GlyphLayout getTextBounds() {
      return this.gText.getTextBounds();
   }

   public float getWidth() {
      return this.getTextBounds().width;
   }

   public void setAlignent(int var1, int var2) {
      this.gText.setAlignent(var1, var2);
   }

   public void setColor(float var1, float var2, float var3, float var4) {
      this.gText.setColor(var1, var2, var3, var4);
   }

   public void setColor(Color var1) {
      this.gText.setColor(var1);
   }

   public void setText(String var1) {
      this.gText.setText(var1);
   }

   public void setWrapped(Boolean var1) {
      this.gText.setWrapped(var1);
   }

   private class GText extends Actor implements Disposable {
      private int align;

      private float alignWidth;
      private BitmapFont font;
      private Color fontColor;
      private int fontSize;
     // private FreeTypeFontGenerator generator;
      private boolean isWrapped;
      private String text;

      private GText(String var2, String var3, int var4, Color var5) {
         this.alignWidth = 25.0F;
         this.align = Align.left;
         this.isWrapped = false;
         this.fontSize = var4;
         this.fontColor = var5;
         this.alignWidth = (float)var4;
         this.align = Align.left;
        // this.generator = new FreeTypeFontGenerator(GRes.openFileHandle(GRes.getFontPath(var3)));
         this.createFont(var2);
      }

      // $FF: synthetic method
      GText(String var2, String var3, int var4, Color var5, GTextSprite.GText var6) {
    	  this.alignWidth = 25.0F;
          this.align = Align.left;
          this.isWrapped = false;
          this.fontSize = var4;
          this.fontColor = var5;
          this.alignWidth = (float)var4;
          this.align = Align.left;
         // this.generator = new FreeTypeFontGenerator(GRes.openFileHandle(GRes.getFontPath(var3)));
          this.createFont(var2);
      }

      private void createFont(String var1) {
         GTextSprite.updateUsedTextSample(var1);
         this.text = var1;
         /*FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
         parameter.size = 12;
         parameter.characters =GTextSprite.getTextSampling(var1);
         parameter.flip = true;
        // FreeTypeBitmapFontData var2 =
         this.font = this.generator.generateFont(parameter); //new BitmapFont(var2, var2.getTextureRegion(), false);*/

         BitmapFont.BitmapFontData fontData = new BitmapFont.BitmapFontData(Gdx.files.internal("font/Saira_Regular.fnt"),false);
         TextureRegion fontRegion=new TextureRegion(new Texture("font/Saira_Regular.png"));

         this.font = new BitmapFont(fontData,fontRegion,false);
         this.font.setColor(this.fontColor);
      }

      private GlyphLayout getTextBounds() {

         GlyphLayout layout = new GlyphLayout(); // Obviously stick this in a field to avoid allocation each frame.
         layout.setText(this.font, this.text);
         return layout;
         //return this.isWrapped?this.font.getWrappedBounds(this.text, this.alignWidth):this.font.getMultiLineBounds(this.text);
      }

      private void setAlignent(int var1, int var2) {
         this.alignWidth = (float)(this.fontSize * var1);
         this.align = var2;
      }

      private void setText(String var1) {
         if(!this.textEquals(var1)) {
            this.createFont(var1);
         }

      }

      private void setWrapped(Boolean var1) {
         this.isWrapped = var1.booleanValue();
      }

      private boolean textEquals(String var1) {
         int var3 = this.text.length();
         if(var3 == var1.length()) {
            int var2 = 0;

            while(true) {
               if(var2 >= var3) {
                  return true;
               }

               if(this.text.charAt(var2) != var1.charAt(var2)) {
                  break;
               }

               ++var2;
            }
         }

         return false;
      }

      public void dispose() {
         //this.generator.dispose();
         this.font.dispose();
      }

      public void draw(Batch var1, float var2) {
         Color var3 = this.getColor();
         var1.setColor(var3.r, var3.g, var3.b, var3.a * var2);


         if(this.isWrapped) {
            this.font.draw(var1, this.text, 0, 0, alignWidth, this.align, true);
           // this.font.drawWrapped(var1, this.text, 0.0F, 0.0F, this.alignWidth, this.align);
         } else {
            this.font.draw(var1, this.text, 0, 0);
           // this.font.drawMultiLine(var1, this.text, 0.0F, 0.0F, this.alignWidth, this.align);
         }
      }

     /* public void setColor(float var1) {
         //this.font.setColor();
      }*/

      public void setColor(float var1, float var2, float var3, float var4) {
         this.font.setColor(var1, var2, var3, var4);
      }

      public void setColor(Color var1) {
         this.font.setColor(var1);
      }
   }
}
