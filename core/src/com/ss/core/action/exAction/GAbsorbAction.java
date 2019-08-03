package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GAbsorbAction extends Action {
   private float endTime;
   private float speed;
   private Actor target;
   private float time;
   private float x;
   private float y;

   public static GAbsorbAction absorb(Actor var0, float var1, float var2, float var3) {
      GAbsorbAction var4 = (GAbsorbAction)Actions.action(GAbsorbAction.class);
      var4.target = var0;
      var4.x = var1;
      var4.y = var2;
      var4.speed = var3;
      var4.time = 0.0F;
      return var4;
   }

   public boolean act(float var1) {
      if(this.time == 0.0F) {
         this.begin();
      }

      float var3 = this.endTime - this.time;
      if(var3 <= 0.0F) {
         this.actor.setPosition(this.target.getX(), this.target.getY());
         return true;
      } else {
         float var2 = (this.target.getX() - this.actor.getX()) * var1 / var3;
         var3 = (this.target.getY() - this.actor.getY()) * var1 / var3;
         this.actor.moveBy(var2, var3);
         this.time += var1;
         return false;
      }
   }

   public void begin() {
      float var1 = this.actor.getX() - this.target.getX();
      float var2 = this.actor.getY() - this.target.getY();
      this.endTime = (float)Math.abs(Math.sqrt((double)(var1 * var1 + var2 * var2)) / (double)this.speed);
   }
}
