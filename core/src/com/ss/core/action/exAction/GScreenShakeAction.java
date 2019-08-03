package com.ss.core.action.exAction;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.ss.core.util.GLayer;

public class GScreenShakeAction extends Action {
   int curOffX;
   int curOffY;
   float duration;
   Group[] layers;
   float time;

   public static GScreenShakeAction screenShake(float var0, GLayer... var1) {
      GScreenShakeAction var3 = (GScreenShakeAction)Actions.action(GScreenShakeAction.class);
      var3.duration = var0;
      var3.layers = new Group[var1.length];

      for(int var2 = 0; var2 < var1.length; ++var2) {
         var3.layers[var2] = var1[var2].getGroup();
      }

      var3.time = 0.0F;
      return var3;
   }

   public boolean act(float var1) {
      if(this.time == 0.0F) {
         this.begin();
      }

      if(this.time >= this.duration) {
         this.translateLayer(-this.curOffY, -this.curOffY);
         return true;
      } else {
         int var2 = MathUtils.random(-3, 3);
         int var3 = MathUtils.random(-3, 3);
         this.translateLayer(var2 - this.curOffX, var3 - this.curOffY);
         this.curOffX = var2;
         this.curOffY = var3;
         this.time += var1;
         return false;
      }
   }

   public void begin() {
      this.curOffY = 0;
      this.curOffX = 0;
   }

   public void translateLayer(int var1, int var2) {
      Group[] var5 = this.layers;
      int var4 = var5.length;

      for(int var3 = 0; var3 < var4; ++var3) {
         var5[var3].moveBy((float)var1, (float)var2);
      }

   }
}
