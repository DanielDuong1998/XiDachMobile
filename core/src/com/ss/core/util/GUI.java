package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.ss.GMain;

public class GUI {
   public static Button creatButton(TextureRegion... var0) {
      TextureRegionDrawable[] var2 = new TextureRegionDrawable[4];
      if(var0 != null) {
         for(int var1 = 0; var1 < Math.min(4, var0.length); ++var1) {
            var2[var1] = new TextureRegionDrawable(var0[var1]);
            if(var1 == 0) {
               TextureRegionDrawable var3 = var2[0];
               var2[3] = var3;
               var2[2] = var3;
               var2[1] = var3;
            }
         }
      }

      Button.ButtonStyle var4 = new Button.ButtonStyle();
      var4.up = var2[0];
      var4.down = var2[1];
      var4.checked = var2[2];
      var4.disabled = var2[3];
      return new Button(var4);
   }

   public static Button creatButton(TextureAtlas atlas, String regionname) {
      return creatButton( atlas.findRegion(regionname));
   }

   public static Label creatLabel(CharSequence var0, Color var1) {
      return new Label(var0, new Label.LabelStyle(new BitmapFont(true), var1));
   }

   public static TextureAtlas.AtlasRegion FindRegionLocalize(TextureAtlas atlas, String regionName){
      String lang = GMain.platform.GetDefaultLanguage();
      String localRegionName = regionName;
      if(!lang.equals("en")){
         localRegionName=regionName+"_"+lang;
      }

      TextureAtlas.AtlasRegion region = atlas.findRegion(localRegionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region localize " + regionName + " not found");
         region = atlas.findRegion(regionName);
         return region;
      }

      return region;
   }
   public static Button createButtonLocalize(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = FindRegionLocalize(atlas, regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return GUI.creatButton(new TextureRegion[]{region});
   }
   public static Image createImageLocalize(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = FindRegionLocalize(atlas, regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return new Image(region);

   }
   public static Image createImage(TextureAtlas atlas, String regionName){
      TextureAtlas.AtlasRegion region = atlas.findRegion(regionName);
      if(region == null){
         GMain.platform.log("atlas " + atlas.toString() + "find region " + regionName + " not found");
         return null;
      }
      return new Image(region);
   }
}
