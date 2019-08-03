package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GScreenFlashAction extends Action {
   private float a;
   private float b;
   private float flashTime;
   private float g;
   private int loop;
   private float r;
   private float time;
   private float waitTime;

   public static GScreenFlashAction screenFlash(float var0, float var1, int var2) {
      GScreenFlashAction var3 = (GScreenFlashAction)Actions.action(GScreenFlashAction.class);
      var3.time = 0.0F;
      var3.flashTime = var0;
      var3.waitTime = var1;
      var3.loop = var2;
      return var3;
   }

   public boolean act(float var1) {
      if(this.time < -this.flashTime) {
         this.actor.setVisible(false);
         if(this.loop > 0) {
            --this.loop;
            if(this.loop == 0) {
               return true;
            }
         }

         this.time += this.flashTime + this.waitTime;
      } else if(this.time <= 0.0F) {
         this.actor.setVisible(true);
      }

      this.time -= var1;
      return false;
   }
}
