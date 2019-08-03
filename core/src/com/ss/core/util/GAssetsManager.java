package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.BitmapFontLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.ParticleEffectLoader;
import com.badlogic.gdx.assets.loaders.TextureAtlasLoader;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Gdx2DPixmap;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import com.badlogic.gdx.utils.StreamUtils;
import com.ss.GMain;
import com.ss.core.util.GRes;
import com.ss.gdx.NParticleEffect;
import com.ss.gdx.NParticleEffectLoader;
import com.ss.gdx.NSound;

import java.io.Closeable;
import java.io.InputStream;
import java.util.Iterator;

public class GAssetsManager {
   private static AssetManager assetManager = new AssetManager();
   private static boolean isFinished;
   public static Array tempResLog;

   static {
      assetManager.setLoader(Object.class, new GAssetsManager.GDataLoader(new InternalFileHandleResolver()));
      assetManager.setLoader(NParticleEffect.class, new NParticleEffectLoader(new InternalFileHandleResolver()));
      tempResLog = new Array();


//      DecodeTexture("textureAtlas/ui/360gift.png");


   }

   public static void addToLog(String var0) {
      GMain.platform.log("addToLog : " + var0);
      tempResLog.add(var0);
   }

   public static void clear() {
      assetManager.clear();
      isFinished = false;
   }

   public static void clearTempResLog() {
      tempResLog.clear();
   }

   public static boolean containsAsset(Object var0) {
      return assetManager.containsAsset(var0);
   }

   public static void finishLoading() {
      assetManager.finishLoading();
      isFinished = true;
   }

   public static String getAssetKey(Object var0) {
      return assetManager.getAssetFileName(var0);
   }

   public static AssetManager getAssetManager() {
      return assetManager;
   }

   public static Array getAssetNameList() {
      return assetManager.getAssetNames();
   }

   public static TextureAtlas.AtlasRegion getAtlasRegion(String var0, String var1) {

      TextureAtlas var2 = getTextureAtlas(var0);
      if(var2 == null) {
         GMain.platform.log("无法加载纹理图集(getTextureRegionFromAtlas) : " + var0);
         return null;
      } else {
         return var2.findRegion(var1);
      }
   }

   public static BitmapFont getBitmapFont(String var0) {
      String var3 = GRes.getFontPath(var0);
      BitmapFont var2 = (BitmapFont)getRes(var3, BitmapFont.class);
      BitmapFont var1 = var2;
      if(var2 == null) {
         loadBitmapFont(var0);
         finishLoading();
         BitmapFont var4 = (BitmapFont)getRes(var3, BitmapFont.class);
         if(var4 != null) {
            addToLog(var3 + "---------" + "BitmapFont.class");
            return var4;
         }

         GMain.platform.log("无法加载字体资源(getBitmapFont) : " + var3);
         var1 = null;
      }

      return var1;
   }

   public static Object getGameData(String var0, GAssetsManager.GDataAssetLoad var1) {
      Object var3 = getRes(var0, Object.class);
      Object var2 = var3;
      if(var3 == null) {
         if(var1 != null) {
            loadGameData(var0, var1);
            finishLoading();
            Object var4 = getRes(var0, Object.class);
            if(var4 == null) {
               GMain.platform.log("无法加载数据资源(getGameData) : " + var0);
               return null;
            }

            addToLog(var0 + "---------" + "Object.class");
            return var4;
         }

         GMain.platform.log("数据资源没有加载(getGameData) : " + var0);
         var2 = null;
      }

      return var2;
   }

   public static Music getMusic(String var0) {

      String var3 = GRes.getSoundPath(var0);
      Music var2 = (Music)getRes(var3, Music.class);
      Music var1 = var2;
      if(var2 == null) {
         loadMusic(var0);
         finishLoading();
         Music var4 = (Music)getRes(var3, Music.class);
         if(var4 != null) {
            addToLog(var3 + "---------" + "Music.class");
            return var4;
         }

         GMain.platform.log("getMusic : " + var3);
         var1 = null;
      }

      return var1;
   }

   public static ParticleEffect getParticleEffect(String var0) {
      String var3 = GRes.getParticlePath(var0);
      ParticleEffect var2 = (ParticleEffect)getRes(var3, NParticleEffect.class);
      ParticleEffect var1 = var2;
      if(var2 == null) {
         loadParticleEffect(var0);
         finishLoading();
         ParticleEffect var4 = (ParticleEffect)getRes(var3, NParticleEffect.class);
         if(var4 != null) {
            addToLog(var3 + "---------" + "NParticleEffect.class");
            initParticle(var4);
            return var4;
         }

         GMain.platform.log("无法加载粒子效果(getNParticleEffect) : " + var3);
         var1 = null;
      }

      return var1;
   }

   public static Pixmap getPixmap(String var0) {
      String var3 = GRes.getTexturePath(var0);
      Pixmap var2 = (Pixmap)getRes(var3, Pixmap.class);
      Pixmap var1 = var2;
      if(var2 == null) {
         loadPixmap(var0);
         finishLoading();
         Pixmap var4 = (Pixmap)getRes(var3, Pixmap.class);
         if(var4 != null) {
            addToLog(var3 + "---------" + "Pixmap.class");
            return var4;
         }

         GMain.platform.log("无法加载纹理Pixmap(getPixmap) : " + var3);
         var1 = null;
      }

      return var1;
   }


   public static float getProgress() {
      return 100.0F * assetManager.getProgress();
   }

   public static int getReferenceCount(Object var0) {
      return assetManager.getReferenceCount(getAssetKey(var0));
   }

   public static int getReferenceCount(String var0) {
      return assetManager.getReferenceCount(var0);
   }

   public static Object getRes(String var0) {
      return assetManager.get(var0);
   }

   public static Object getRes(String var0, Class var1) {
      return !assetManager.isLoaded(var0, var1)?null:assetManager.get(var0, var1);
   }

   public static Sound getSound(String var0) {
      String var3 = GRes.getSoundPath(var0);
      Sound var2 = (Sound)getRes(var3, Sound.class);
      Sound var1 = var2;
      if(var2 == null) {
         loadSound(var0);
         finishLoading();
         Sound var4 = (Sound)getRes(var3, Sound.class);
         if(var4 != null) {
            addToLog(var3 + "---------" + "Sound.class");
            return var4;
         }

         GMain.platform.log("无法加载音效文件(getSound) : " + var3);
         var1 = null;
      }

      return var1;
   }

   public static Array getTempResLoadLog() {
      return tempResLog;
   }

   public static TextureAtlas getTextureAtlas(String var0) {
      String var3 = GRes.getTextureAtlasPath(var0);
      TextureAtlas var2 = (TextureAtlas)getRes(var3, TextureAtlas.class);
      TextureAtlas var1 = var2;
      if(var2 == null) {
         loadTextureAtlas(var0);
         finishLoading();
         TextureAtlas var4 = (TextureAtlas)getRes(var3, TextureAtlas.class);
         if(var4 != null) {
            Iterator var5 = var4.getTextures().iterator();

            while(var5.hasNext()) {
               ((Texture)var5.next()).setFilter(GRes.minFilter, GRes.magFilter);
            }

            addToLog(var3 + "---------" + "TextureAtlas.class");
            return var4;
         }

         GMain.platform.log("无法加载纹理图集(getTextureAtlas) : " + var3);
         var1 = null;
      }

      return var1;
   }

   public static TextureRegion getTextureRegion(String var0) {
      String var3 = GRes.getTexturePath(var0);
      Texture var2 = (Texture)getRes(var3, Texture.class);
      Texture var1 = var2;
      if(var2 == null) {
         loadTexture(var0);
         finishLoading();
         var1 = (Texture)getRes(var3, Texture.class);
         if(var1 == null) {
            GMain.platform.log("无法加载纹理(getTexture) : " + var3);
            return null;
         }

         addToLog(var3 + "---------" + "Texture.class");
      }

      GMain.platform.log(var3 + " ******** " + var1.getTextureData().getFormat());
      TextureRegion var4 = new TextureRegion(var1);
      var4.flip(false, true);
      return var4;
   }

   public static void initParticle(ParticleEffect var0) {
      Iterator var2 = var0.getEmitters().iterator();

      /*while(var2.hasNext()) {
         Sprite var1 = ((ParticleEmitter)var2.next()).getSprite();
         GRes.setTextureFilter(var1.getTexture());
         if(!var1.isFlipY()) {
            var1.flip(false, true);
         }
      }*/ //xxxxxxx



      while(var2.hasNext()) {
         Sprite var1 = ((ParticleEmitter)var2.next()).getSprites().get(0);  //xxx
         GRes.setTextureFilter(var1.getTexture());
         if(!var1.isFlipY()) {
            var1.flip(false, true);
         }
      }

   }

   public static boolean isFinished() {
      return isFinished;
   }

   public static boolean isLoaded(String var0) {
      return assetManager.isLoaded(var0);
   }

   private static void load(String var0, Class var1, AssetLoaderParameters var2) {


      //GMain.platform.log( "load " + var1.toString() + " " + var0 );
     // consolelog("load " + var1.toString() + " " + var0);
	 if(var1.toString().contains("ParticleEffect"))
	 {

	 }
       assetManager.load(var0, var1, var2);

   }

   public static String loadBitmapFont(String var0) {
      var0 = GRes.getFontPath(var0);
      BitmapFontLoader.BitmapFontParameter var1 = new BitmapFontLoader.BitmapFontParameter();
      var1.flip = true;
      var1.minFilter = GRes.minFilter;
      var1.magFilter = GRes.magFilter;
      load(var0, BitmapFont.class, var1);
      return var0;
   }

   public static String loadGameData(String var0, GAssetsManager.GDataAssetLoad var1) {
      GAssetsManager.GameDataParameter var2 = new GAssetsManager.GameDataParameter((GAssetsManager.GameDataParameter)null);
      var2.dataLoad = var1;
      load(var0, Object.class, var2);
      return var0;
   }

   public static String loadMusic(String var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL)
      {
         NSound.loadSound(var0);
         return "";
      }
      else {
         var0 = GRes.getSoundPath(var0);
         load(var0, Music.class, (AssetLoaderParameters) null);
         return var0;
      }
   }

	public static String loadParticleEffect(String var0) {
      var0 = GRes.getParticlePath(var0);
      load(var0, NParticleEffect.class, (AssetLoaderParameters)null);
      return var0;
   }

   public static String loadParticleEffectAsImageDir(String var0, FileHandle var1) {
      var0 = GRes.getParticlePath(var0);
      ParticleEffectLoader.ParticleEffectParameter var2 = new ParticleEffectLoader.ParticleEffectParameter();
      var2.imagesDir = var1;
      load(var0, ParticleEffect.class, var2);
      return var0;
   }

   public static String loadParticleEffectAsTextureAtlas(String var0) {
      var0 = GRes.getParticlePath(var0);
      NParticleEffectLoader.NParticleEffectParameter var1 = new NParticleEffectLoader.NParticleEffectParameter();
      var1.atlasFile = var0 + "ack";
      load(var0, NParticleEffect.class, var1);
      return var0;
   }

   public static String loadParticleEffectAsTextureAtlas(String var0, String var1) {
      var0 = GRes.getParticlePath(var0);
      NParticleEffectLoader.NParticleEffectParameter var2 = new NParticleEffectLoader.NParticleEffectParameter();
      var2.atlasFile = GRes.getTextureAtlasPath(var1);
      load(var0, NParticleEffect.class, var2);
      return var0;
   }

   public static String loadPixmap(String var0) {
      var0 = GRes.getTexturePath(var0);
      load(var0, Pixmap.class, (AssetLoaderParameters)null);
      return var0;
   }

   public static String loadSound(String var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL)
      {
         NSound.loadSound(var0);
         return "";
      }
      else {
         var0 = GRes.getSoundPath(var0);
         load(var0, Sound.class, (AssetLoaderParameters) null);
         return var0;
      }
   }

   public static String loadTexture(String var0) {
      var0 = GRes.getTexturePath(var0);

      DecodeTexture(var0);
      //var0 = "dec/" + var0;
      TextureLoader.TextureParameter var1 = new TextureLoader.TextureParameter();
      var1.minFilter = GRes.minFilter;
      var1.magFilter = GRes.magFilter;
      load(var0, Texture.class, var1);
      return var0;
   }

   public static String loadTextureAtlas(String var0) {


      var0 = GRes.getTextureAtlasPath(var0);
      String v1 = var0.replace(".pack", ".png");
      if(var0.contains("noticebg")) {
         v1 = var0.replace(".pack", ".jpg");
      }
      if(var0.contains("particleAtlas1")) {
        int t = 0;
        t++;
      }
      DecodeTexture(v1);

      load(var0, TextureAtlas.class, new TextureAtlasLoader.TextureAtlasParameter(true));
      return var0;
   }

   public static void paintAssetReferenceList() {
      Iterator var0 = assetManager.getAssetNames().iterator();

      while(var0.hasNext()) {
         String var1 = (String)var0.next();
         System.err.println(var1 + " ____ : " + getReferenceCount(var1));
      }

   }

   public static void paintAssetReferenceList(String var0) {
      Iterator var1 = assetManager.getAssetNames().iterator();

      while(var1.hasNext()) {
         String var2 = (String)var1.next();
         if(var2.toLowerCase().contains(var0.toLowerCase())) {
            System.err.println(var2 + " ____ : " + getReferenceCount(var2));
         }
      }

   }

   public static void unload(Object var0) {
      String var1 = getAssetKey(var0);
      if(var1 != null) {
         unload(var1);
      }
   }

   public static void unload(String var0) {
      try {
         assetManager.unload(var0);
      }
      catch(Exception e){

      }
   }

   public static void unloadFont(String var0) {
      unload(GRes.getFontPath(var0));
   }

   public static void unloadParticleEffect(String var0) {
      unload(GRes.getParticlePath(var0));
   }

   public static void unloadPixmap(String var0) {
      unload(GRes.getTexturePath(var0));
   }

   public static void unloadSound(String var0) {
      if(Gdx.graphics.getType() != Graphics.GraphicsType.WebGL)
         unload(GRes.getSoundPath(var0));
   }

   public static void unloadTexture(String var0) {
      unload(GRes.getTexturePath(var0));
   }

   public static void unloadTextureAtlas(String var0) {
      unload(GRes.getTextureAtlasPath(var0));
   }

   public static void update() {
      isFinished = assetManager.update();
   }

   public interface GDataAssetLoad {
      Object load(String var1, FileHandle var2);
   }

   public static class GDataLoader extends AsynchronousAssetLoader<Object, GAssetsManager.GameDataParameter> {
      private Object dat;

      public GDataLoader(FileHandleResolver var1) {
         super(var1);
      }

      public Array getDependencies(String var1, FileHandle var2, GAssetsManager.GameDataParameter var3) {
         return null;
      }

      public void loadAsync(AssetManager var1, String var2, FileHandle var3, GAssetsManager.GameDataParameter var4) {
         this.dat = var4.dataLoad.load(var2, var3);
      }

      public Object loadSync(AssetManager var1, String var2, FileHandle var3, GAssetsManager.GameDataParameter var4) {
         return this.dat;
      }
   }

   private static class GameDataParameter extends AssetLoaderParameters<Object> {
      GAssetsManager.GDataAssetLoad dataLoad;

      private GameDataParameter() {
      }

      // $FF: synthetic method
      GameDataParameter(GAssetsManager.GameDataParameter var1) {
        
      }
   }


   public static void DecodeTexture(String fname) {
//      try {
//
//         Gdx.app.log("DECODE", fname);
//
//         FileHandle fileHandle = new FileHandle( fname);
//
//         int[] arrn = new int[5];
//         arrn[0] = 2;
//         arrn[2] = 4;
//         arrn[3] = 1;
//         arrn[4] = 3;
//        // this.readOrder = arrn;
//         byte[] arrby = new byte[4];
//
//
//         InputStream inputStream = fileHandle.read();
//         inputStream.read(arrby);
//         byte[] arrby2 = new String(arrby).equals(".tex") ? readBytes((InputStream) inputStream, (int[]) arrn) : fileHandle.readBytes();
//         Pixmap pixmap = new Pixmap(new Gdx2DPixmap(arrby2, 0, arrby2.length, 0));
//         StreamUtils.closeQuietly((Closeable) inputStream);
//
//
//         FileHandle fh;
//
//         fh = new FileHandle(fname);
//
//
//
//         PixmapIO.writePNG(fh, pixmap);
//      }
//      catch(Exception e ){
//
//      }
   }


   private static byte[] readBytes(InputStream inputStream, int[] arrn) throws Exception {
      int n = inputStream.available();
      byte[] arrby = new byte[n];
      int n2 = n / arrn.length;
      int n3 = 0;
      do {
         if (n3 >= arrn.length) {
            int n4 = n % arrn.length;
            if (n4 <= 0) return arrby;
            inputStream.read(arrby, n2 * arrn.length, n4);
            return arrby;
         }
         inputStream.read(arrby, n2 * arrn[n3], n2);
         ++n3;
      } while (true);
   }

}


