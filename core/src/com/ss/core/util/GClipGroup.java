package com.ss.core.util;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Group;

public class GClipGroup extends Group {
   float clipH;
   float clipW;
   float clipX;
   float clipY;
   boolean isClipping;

   public void draw(Batch var1, float var2) {
      if(!this.isClipping || this.clipW >= 1.0F && this.clipH >= 1.0F) {
         var1.end();
         var1.begin();
         boolean var3;
         if(this.isClipping() && this.clipBegin(this.clipX + this.getX(), this.clipY + this.getY(), this.clipW, this.clipH)) {
            var3 = true;
         } else {
            var3 = false;
         }

         super.draw(var1, var2);
         if(var3) {
            var1.end();
            this.clipEnd();
            var1.begin();
            return;
         }
      }

   }

   public boolean isClipping() {
      return this.isClipping;
   }

   public void resetClipArea() {
      this.isClipping = false;
   }

   public void setClipArea(float var1, float var2, float var3, float var4) {
      this.clipX = var1;
      this.clipY = var2;
      this.clipW = var3;
      this.clipH = var4;
      this.isClipping = true;
   }
}
