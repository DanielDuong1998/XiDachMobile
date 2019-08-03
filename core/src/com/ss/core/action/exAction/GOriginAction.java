package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GOriginAction extends Action {
   private static final int ANCHOR_BC = 8;
   private static final int ANCHOR_CENTER = 0;
   private static final int ANCHOR_LB = 4;
   private static final int ANCHOR_LC = 5;
   private static final int ANCHOR_LT = 1;
   private static final int ANCHOR_RB = 3;
   private static final int ANCHOR_RC = 7;
   private static final int ANCHOR_RT = 2;
   private static final int ANCHOR_TC = 6;
   private int anchor = -1;
   private float ox;
   private float oy;

   public static Action originAtAnchor(int var0) {
      GOriginAction var1 = (GOriginAction)Actions.action(GOriginAction.class);
      var1.anchor = var0;
      return var1;
   }

   public static Action originAtPoint(float var0, float var1) {
      GOriginAction var2 = (GOriginAction)Actions.action(GOriginAction.class);
      var2.ox = var0;
      var2.oy = var1;
      var2.anchor = -1;
      return var2;
   }

   private void setOrigin(int var1) {
      float var3 = 0.0F;
      float var2 = this.actor.getOriginX();
      float var4 = this.actor.getOriginY();
      switch(var1) {
      case 0:
         var2 = this.actor.getWidth() / 2.0F;
         var3 = this.actor.getHeight() / 2.0F;
         break;
      case 1:
         var2 = 0.0F;
         break;
      case 2:
         var2 = this.actor.getWidth();
         break;
      case 3:
         var2 = this.actor.getWidth();
         var3 = this.actor.getHeight();
         break;
      case 4:
         var3 = this.actor.getHeight();
         var2 = 0.0F;
         break;
      case 5:
         var3 = this.actor.getHeight() / 2.0F;
         var2 = 0.0F;
         break;
      case 6:
         var2 = this.actor.getWidth() / 2.0F;
         break;
      case 7:
         var2 = this.actor.getWidth();
         var3 = this.actor.getHeight() / 2.0F;
         break;
      case 8:
         var2 = this.actor.getWidth() / 2.0F;
         var3 = this.actor.getHeight();
         break;
      default:
         var3 = var4;
      }

      this.actor.setOrigin(var2, var3);
   }

   public boolean act(float var1) {
      if(this.anchor < 0) {
         this.actor.setOrigin(this.ox, this.oy);
      } else {
         this.setOrigin(this.anchor);
      }

      return true;
   }
}
