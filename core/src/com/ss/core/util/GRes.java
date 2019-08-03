package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.StreamUtils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.util.Iterator;

public abstract class GRes {
   public static final String FILE_PATH_ACTION = "action/";
   public static final String FILE_PATH_ANIMATION = "animation/";
   private static final String FILE_PATH_BONUS = "data/bonus/";
   public static final String FILE_PATH_DATA = "data/";
   private static final String FILE_PATH_DEC = "data/dec/";
   public static final String FILE_PATH_FONT = "font/";
   private static final String FILE_PATH_MAP = "data/map/";
   public static final String FILE_PATH_PARTICLE = "particle/";
   public static final String FILE_PATH_SCRIPT = "script/";
   private static final String FILE_PATH_SHOOTERS = "data/shooters/";
   public static final String FILE_PATH_SOUND = "sound/";
   public static final String FILE_PATH_STRING = "string/";
   public static final String FILE_PATH_TEXTURE = "texture/";
   public static final String FILE_PATH_TEXTURE_ATLAS = "textureAtlas/";
   private static BitmapFont defaultFont;
   public static Texture.TextureFilter magFilter;
   public static Texture.TextureFilter minFilter;

   static {
      minFilter = Texture.TextureFilter.Linear;
      magFilter = Texture.TextureFilter.Linear;
      defaultFont = null;
   }

   public static TextureAtlas.AtlasRegion createRegionFromAtlasRegion(TextureAtlas.AtlasRegion var0, int var1, int var2, int var3, int var4) {
      int var5;
      if(var0.rotate) {
         var5 = var0.getRegionWidth();
      } else {
         var5 = var0.getRegionHeight();
      }

      int var7 = var0.getRegionX();
      int var8 = var0.getRegionY();
      int var6;
      if(var0.rotate) {
         var6 = var4;
      } else {
         var6 = var3;
      }

      if(!var0.rotate) {
         var3 = var4;
      }

      TextureAtlas.AtlasRegion var9 = new TextureAtlas.AtlasRegion(var0.getTexture(), var7 + var1, var8 - var5 + var2, var6, var3);
      var9.rotate = var0.rotate;
      var9.flip(false, true);
      return var9;
   }

   public static TextureRegion createRegionFromTextureRegion(TextureRegion var0, int var1, int var2, int var3, int var4) {
      int var5 = var0.getRegionX();
      int var6 = var0.getRegionY();
      int var7 = var0.getRegionHeight();
      var0 = new TextureRegion(var0.getTexture(), var5 + var1, var6 - var7 + var2, var3, var4);
      var0.flip(false, true);
      return var0;
   }

   public static String getActionPath(String var0) {
      return "action/" + var0;
   }

   public static String getAnimationPath(String var0) {
      return "animation/" + var0;
   }

   public static String getBonusPath(String var0) {
      return "data/bonus/" + var0 + ".json";
   }

   public static String getDataPath(String var0) {
      return "data/" + var0;
   }

   public static String getDecPath(String var0) {
      return "data/dec/" + var0 + ".json";
   }

   public static String getFontPath(String var0) {
      return "font/" + var0;
   }

   public static String getMapPath(String var0) {
      return "data/map/" + var0;
   }

   public static String getParticlePath(String var0) {
      return "particle/" + var0;
   }

   public static String getScriptPath(String var0) {
      return "script/" + var0;
   }

   public static String getShootersPath(String var0) {
      return "data/shooters/" + var0;
   }

   public static String getSoundPath(String var0) {
      return "sound/" + var0;
   }

   public static String getStringPath(String var0) {
      return "string/" + var0;
   }

   public static String getTextureAtlasPath(String var0) {
      return "textureAtlas/" + var0;
   }

   public static String getTexturePath(String var0) {
      return "texture/" + var0;
   }

   public static void initTextureAtlas(TextureAtlas var0) {
      Iterator var1 = var0.getTextures().iterator();

      while(var1.hasNext()) {
         setTextureFilter((Texture)var1.next());
      }

   }

   public static BitmapFont loadDefaultFont() {
      if(defaultFont == null) {
         defaultFont = new BitmapFont(true);
         setTextureFilter(defaultFont.getRegion().getTexture());
      }

      return defaultFont;
   }

   public static BitmapFont loadFont(String var0) {
      BitmapFont var1 = new BitmapFont(openFileHandle(getFontPath(var0)), true);
      setTextureFilter(var1.getRegion().getTexture());
      return var1;
   }

   public static Pixmap loadPixmap(String var0) {
      return new Pixmap(openFileHandle(getTexturePath(var0)));
   }

   public static Music loadSBGMusic(String var0) {
      return Gdx.audio.newMusic(openFileHandle(getSoundPath(var0)));
   }

   public static Sound loadSoundEffect(String var0) {
      return Gdx.audio.newSound(openFileHandle(getSoundPath(var0)));
   }

   public static Texture loadTexture(String var0) {
      Texture var1 = new Texture(openFileHandle(getTexturePath(var0)));
      setTextureFilter(var1);
      return var1;
   }

   public static TextureAtlas loadTextureAtlas(String var0) {
      TextureAtlas var1 = new TextureAtlas(openFileHandle(getTextureAtlasPath(var0)), true);
      initTextureAtlas(var1);
      return var1;
   }

   public static TextureRegion loadTextureRegion(String var0) {
      TextureRegion var1 = new TextureRegion(loadTexture(var0));
      var1.flip(false, true);
      setTextureFilter(var1.getTexture());
      return var1;
   }

   public static TextureRegion loadTextureRegion(String var0, int var1, int var2, int var3, int var4) {
      TextureRegion var5 = new TextureRegion(loadTexture(var0), var1, var2, var3, var4);
      var5.flip(false, true);
      setTextureFilter(var5.getTexture());
      return var5;
   }

   public static FileHandle openFileHandle(String var0) {
      return Gdx.files.internal(var0);
   }

   public static DataInputStream openInputStream(String var0) {
      return new DataInputStream(openFileHandle(var0).read());
   }

   public static String readTextFile(FileHandle param0) {
	   StringBuffer v2 = new StringBuffer();
	   BufferedReader v1;
		try
		{
		    v1 = new BufferedReader(param0.reader("UTF-8") );    
		}catch(Exception e)
		{
			return "";
		}
		
		try
		{
		    while(true)
		    {
		        String v3 = v1.readLine();
		        if(v3==null)
		        {
		        	StreamUtils.closeQuietly(v1);
		            return v2.toString();
		        }
		
		        v2.append(v3);
		    }
		}
		catch(Exception e)
		{
			StreamUtils.closeQuietly(v1);
		    return v2.toString();
		}
   }

   public static String readTextFile(String var0) {
      return readTextFile(openFileHandle(var0));
   }

   public static void setTextureFilter(Texture var0) {
      var0.setFilter(minFilter, magFilter);
   }

   public static TextureRegion[][] textureSplit(Texture var0, int var1, int var2) {
      setTextureFilter(var0);
      TextureRegion[][] var3 = TextureRegion.split(var0, var1, var2);

      for(var1 = 0; var1 < var3.length; ++var1) {
         for(var2 = 0; var2 < var3[var1].length; ++var2) {
            var3[var1][var2].flip(false, true);
         }
      }

      return var3;
   }
}
