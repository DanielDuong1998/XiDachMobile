package com.ss.core.util;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.ss.core.util.GLayerGroup;
import java.util.Comparator;

public enum GLayer {
	bottom("bottom", Touchable.childrenOnly),
	map("map", Touchable.childrenOnly),
	sprite("sprite", Touchable.childrenOnly),
	effect( "effect", Touchable.childrenOnly),
	ui( "ui", Touchable.childrenOnly),
	top("top", Touchable.childrenOnly);
		 
	
	
   // $FF: synthetic field
   private static int[] var_unk;//
  /* public enum ENUM{
	   bottom,
	   effect,
	   map,
	   sprite,
	   top,
	   ui};*/

   private Comparator comparator;
   private GLayerGroup group;
   private String name;
   private Touchable touchable;
   
   // $FF: synthetic method
   static int[] var_unk() {
	   var_unk = new int[6];
	   var_unk[bottom.ordinal()] = 1;
	   var_unk[effect.ordinal()] = 4;
	   var_unk[map.ordinal()] = 2;
	   var_unk[sprite.ordinal()] = 3;
	   var_unk[top.ordinal()] = 6;
	   var_unk[ui.ordinal()] = 5;
       return var_unk;
      
   }
   
  
   
  // static {
    // }

   private GLayer(String var3, Touchable var4) {
      this.name = var3;
      this.touchable = var4;
   }

   private void createComparator() {
      Comparator<Actor> var1 = new Comparator<Actor>() {
         public int compare(Actor var1, Actor var2) {
            return var1.getY() < var2.getY()?-1:(var1.getY() > var2.getY()?1:0);
         }
      };
      switch(this) {
      case bottom:
      case map:
      case sprite:
      case effect:
      case ui:
      case top:
         var1 = null;
      default:
         this.comparator = var1;
      }
   }

   public Comparator getComparator() {
      return this.comparator;
   }

   public GLayerGroup getGroup() {
      return this.group;
   }

   public String getName() {
      return this.name;
   }

   public Touchable getTouchable() {
      return this.touchable;
   }

   public void init(GLayerGroup var1) {
      this.group = var1;
      var1.setName(this.name);
      var1.setTouchable(this.touchable);
      this.createComparator();
   }
}
