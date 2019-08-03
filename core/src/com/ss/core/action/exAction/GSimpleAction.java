package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GSimpleAction extends Action {
   private GSimpleAction.ActInterface actInterface;

   public static GSimpleAction simpleAction(GSimpleAction.ActInterface var0) {
      GSimpleAction var1 = (GSimpleAction)Actions.action(GSimpleAction.class);
      var1.actInterface = var0;
      return var1;
   }

   public boolean act(float var1) {
      return this.actInterface.act(var1, this.actor);
   }

   public interface ActInterface {
      boolean act(float var1, Actor var2);
   }
}
