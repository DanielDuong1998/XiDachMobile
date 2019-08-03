package com.ss.core.util;

import com.badlogic.gdx.scenes.scene2d.Group;

public class GLayerGroup extends Group {
   private boolean pause;

   public void act(float var1) {
      if(!this.pause) {
         super.act(var1);
      }
   }

   public boolean isPause() {
      return this.pause;
   }

   public void setPause(boolean var1) {
      this.pause = var1;
   }
}
