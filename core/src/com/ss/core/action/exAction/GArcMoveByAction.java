package com.ss.core.action.exAction;

import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class GArcMoveByAction extends MoveByAction {
   private float amountDegree;
   private float amountPX;
   private float amountPY;
   private float lastDegree;
   private float originX;
   private float originY;
   private float radius;
   private float startX;
   private float startY;

   public static GArcMoveByAction arcMoveBy(float var0, float var1, float var2, float var3, float var4, Interpolation var5) {
      GArcMoveByAction var6 = (GArcMoveByAction)Actions.action(GArcMoveByAction.class);
      var6.setAmount(var0, var1);
      var6.setAmountPass(var2, var3);
      var6.setDuration(var4);
      var6.setInterpolation(var5);
      return var6;
   }

   protected void begin() {
      super.begin();
      this.startX = this.actor.getX();
      this.startY = this.actor.getY();
      this.calculateOrigin();
      this.calculateRadius();
      this.calculateDegree();
   }

   public void calculateDegree() {
      float var3 = this.getAmountX();
      float var8 = this.getAmountY();
      float var10 = this.startX;
      float var9 = this.startY;
      float var4 = this.startX;
      float var7 = this.amountPX;
      float var5 = this.startY;
      float var6 = this.amountPY;
      float var2 = (float)(Math.acos((double)((this.startX - this.originX) / this.radius)) * 180.0D / 3.141592653589793D);
      float var1 = var2;
      if(this.startY > this.originY) {
         var1 = 360.0F - var2;
      }

      var3 = (float)(Math.acos((double)((var10 + var3 - this.originX) / this.radius)) * 180.0D / 3.141592653589793D);
      var2 = var3;
      if(var8 + var9 > this.originY) {
         var2 = 360.0F - var3;
      }

      var4 = (float)(Math.acos((double)((var4 + var7 - this.originX) / this.radius)) * 180.0D / 3.141592653589793D);
      var3 = var4;
      if(var5 + var6 > this.originY) {
         var3 = 360.0F - var4;
      }

      if(var2 >= var1) {
         if(var3 >= var1 && var3 <= var2) {
            this.amountDegree = var2 - var1;
         } else {
            this.amountDegree = -(360.0F - var2 + var1);
         }
      } else if(var3 >= var2 && var3 <= var1) {
         this.amountDegree = var2 - var1;
      } else {
         this.amountDegree = var2 + (360.0F - var1);
      }

      this.lastDegree = var1;
   }

   public void calculateOrigin() {
      float var1 = this.getAmountX();
      float var2 = this.getAmountY();
      float var3 = this.amountPX;
      float var4 = var1 - this.amountPX;
      float var5 = this.startX * 2.0F + this.amountPX;
      float var6 = this.startX;
      float var7 = this.amountPX;
      float var8 = this.amountPY;
      float var9 = var2 - this.amountPY;
      float var10 = this.startY * 2.0F + this.amountPY;
      this.originX = (((var1 + var6 * 2.0F + var7) * var4 + (var2 + this.startY * 2.0F + this.amountPY) * var9) * var8 - (var10 * var8 + var5 * var3) * var9) / ((var4 * var8 - var3 * var9) * 2.0F);
      this.originY = -var3 * this.originX / var8 + (var10 * var8 + var3 * var5) / (2.0F * var8);
   }

   public void calculateRadius() {
      this.radius = (float)Math.sqrt((double)((this.originX - this.startX) * (this.originX - this.startX) + (this.originY - this.startY) * (this.originY - this.startY)));
   }

   public void setAmountPass(float var1, float var2) {
      this.amountPX = var1;
      this.amountPY = var2;
   }

   protected void updateRelative(float var1) {
      var1 = this.amountDegree * var1;
      float var2 = this.radius;
      float var3 = MathUtils.cosDeg(this.lastDegree + var1);
      float var4 = MathUtils.cosDeg(this.lastDegree);
      float var5 = -this.radius;
      float var6 = MathUtils.sinDeg(this.lastDegree + var1);
      float var7 = MathUtils.sinDeg(this.lastDegree);
      this.lastDegree = (var1 + this.lastDegree + 360.0F) % 360.0F;
      this.actor.moveBy(var2 * (var3 - var4), var5 * (var6 - var7));
   }
}
