package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.LongMap;
import com.badlogic.gdx.utils.ObjectMap;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GStage;
import com.ss.gdx.NSound;

import java.util.Iterator;

public class GSound {
   private static final float SOUND_INTERVAL = 0.1F;
   private static LongMap loopSound = new LongMap();
   static Music music;
   static String musicFile = "";
   static ObjectMap musicPool = new ObjectMap();
   private static boolean silence_music;
   private static boolean silence_sound;
   private static ObjectMap soundPlayList = new ObjectMap();
   private static ObjectMap soundPool = new ObjectMap();
   private static float volume_music = 1.0F;
   private static float volume_sound = 1.0F;

   public static Preferences prefs;
   static {
      registerSoundListUpdate();
      prefs = Gdx.app.getPreferences("SoundPref");
      boolean silence = prefs.getBoolean("silence", false);
      //GPlayData.setIsSilence(silence);
      GSound.setSoundSilence(silence);
      GSound.setMusicSilence(silence);
   }

   public static void clear() {
      stopMusic();
      stopSound();

      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){

      }
      else {
         ObjectMap.Values var0 = musicPool.values();
         Iterator var1 = var0.iterator();

         while (var1.hasNext()) {
            Music var2 = (Music) var1.next();
            GAssetsManager.unload((Object) var0);
         }

         Iterator var3 = soundPool.values().iterator();

         while (var3.hasNext()) {
            GAssetsManager.unload((Object) ((Sound) var3.next()));
         }
         musicPool.clear();
         soundPool.clear();
         loopSound.clear();
         soundPlayList.clear();
      }
   }

   private static Sound getSound(String var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL)
         return null;
      else {
         if (!soundPool.containsKey(var0)) {
            initSound(var0);
         }

         return (Sound) soundPool.get(var0);
      }
   }

   public static void initMusic(String var0) {
      stopMusic();
      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){
         musicFile = var0;
      }
      else {
         if (!musicPool.containsKey(var0)) {
            music = GAssetsManager.getMusic(var0);
            musicPool.put(var0, music);
         }

         music = (Music) musicPool.get(var0);
      }
   }

   public static void initSound(String var0) {
      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){

      }
      else {
         if (!soundPool.containsKey(var0)) {
            Sound var1 = GAssetsManager.getSound(var0);
            soundPool.put(var0, var1);
         }

         soundPool.put(var0, GAssetsManager.getSound(var0));
      }
   }

   /*public static boolean isMusicPlaying() {
      return music != null && music.isPlaying();
   }*/

   public static void pause() {
      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){
         NSound.pauseAllSound();
      }
      else {

         if (music != null) {
            music.pause();
         }
         pauseSound();
      }


   }

   public static void pauseMusic() {
      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){
         if(!musicFile.equals(""))
            NSound.pauseSound(musicFile);
      }
      else {
         if (music != null) {
            music.pause();
         }
      }

   }

   public static void pauseSound() {
      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){
         NSound.pauseAllSound();
      }
      else {
         Iterator var0 = soundPool.values().iterator();

         while (var0.hasNext()) {
            ((Sound) var0.next()).pause();
         }
      }
   }

   /*public static void pauseSound(String var0) {
      getSound(var0).pause();
   }*/

   public static long playLoopSound(String var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         if(!silence_sound)
            NSound.playLoopSound(var0);
         return -1L;
      }
      else {
         if (!silence_sound && !soundPlayList.containsKey(var0)) {
            long var1 = getSound(var0).loop(volume_sound);
            loopSound.put(var1, var0);
            soundPlayList.put(var0, Float.valueOf(0.0F));
            return var1;
         } else {
            return -1L;
         }
      }
   }

  /* public static void playLoopSound() {

      Iterator var0 = loopSound.values().iterator();

      while(var0.hasNext()) {
         playLoopSound((String)var0.next());
      }

   }*/

   public static void playMusic() {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         if(!silence_sound && !musicFile.equals(""))
            NSound.playSound(musicFile);
      }
      else {
         if (music != null && !silence_music && !music.isPlaying()) {
            music.setVolume(volume_music);
            music.setLooping(true);
            music.play();
         }
      }
   }

   public static long playSound(String var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         if(!silence_sound)
            NSound.playSound(var0);
      }
      else {
         if (!silence_sound && !soundPlayList.containsKey(var0)) {
            soundPlayList.put(var0, Float.valueOf(0.0F));
            Sound var1 = getSound(var0);
            if (var1 != null) {
               return var1.play(volume_sound);
            }
         }
      }

      return -1L;
   }

   private static void registerSoundListUpdate() {
      if(Gdx.graphics.getType() != Graphics.GraphicsType.WebGL) {
         GStage.registerUpdateService("soundUpdate", new GStage.GUpdateService() {
            public boolean update(float var1) {
               Iterator var3 = GSound.soundPlayList.keys().iterator();

               while (var3.hasNext()) {
                  String var4 = (String) var3.next();
                  float var2 = ((Float) GSound.soundPlayList.get(var4)).floatValue() + var1;
                  if (var2 >= 0.1F) {
                     GSound.soundPlayList.remove(var4);
                  } else {
                     GSound.soundPlayList.put(var4, Float.valueOf(var2));
                  }
               }

               return false;
            }
         });
      }
   }

   public static void resume() {
      playMusic();
      resumeSound();
   }

   public static void resumeSound() {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         GMain.platform.log("resume sound not implement");
      }
      else {
         Iterator var0 = soundPool.values().iterator();

         while (var0.hasNext()) {
            ((Sound) var0.next()).resume();
         }
      }

   }

   public static void resumeSound(String var0) {
      getSound(var0).resume();
   }

   private static void setLoopSoundVolume(float var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         GMain.platform.log("loop sound volume not implement");
      }
      else {
         Iterator var1 = loopSound.values().iterator();

         while (var1.hasNext()) {
            String var2 = (String) var1.next();
            ((Sound) GAssetsManager.getRes(var2)).setVolume(loopSound.findKey(var2, true, -1L), var0);
         }
      }

   }

   public static void setMusicSilence(boolean var0) {
      silence_music = var0;



      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         if(!musicFile.equals(""))
         {
            if (silence_music) {
               NSound.stopSound(musicFile);
            } else {
               playMusic();
            }
         }
      }
      else {
         if (music != null) {
            if (silence_music) {
               music.stop();
            } else {
               playMusic();
            }
         }
      }
   }

   /*public static float setMusicVolume(float var0) {
      volume_music = Math.max(0.0F, var0);
      volume_music = Math.min(volume_music, 1.0F);
      if(music != null) {
         music.setVolume(volume_music);
      }

      return volume_music;
   }
*/
   public static void setSilence(boolean var0) {
      setMusicSilence(var0);
      setSoundSilence(var0);
   }

   public static void setSoundSilence(boolean var0) {
      silence_sound = var0;
      prefs.putBoolean("soundSilence", var0);
      prefs.flush();
      if(silence_music) {
         stopSound();
      }

   }

   public static float setSoundVolume(float var0) {
      volume_sound = Math.max(0.0F, var0);
      volume_sound = Math.min(volume_sound, 1.0F);
      setLoopSoundVolume(volume_sound);
      return volume_music;
   }

   public static void stopMusic() {
      if(Gdx.app.getGraphics().getType() == Graphics.GraphicsType.WebGL){
         if(!musicFile.equals(""))
            NSound.stopSound(musicFile);
      }else {
         if (music != null) {
            music.stop();
         }
      }

   }

   public static void stopSound() {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         NSound.stopAllSound();
      }
      else {
         Iterator var0 = soundPool.keys().iterator();
         while (var0.hasNext()) {
            stopSound((String) var0.next());
         }
      }

   }

   public static void stopSound(String var0) {
      if(Gdx.graphics.getType() == Graphics.GraphicsType.WebGL){
         NSound.stopSound(var0);
      }
      else {
         getSound(var0).stop();
         soundPlayList.remove(var0);
         loopSound.remove(loopSound.findKey(var0, true, -1L));
      }
   }

  /* public static void stopSound(String var0, long var1) {
      getSound(var0).stop(var1);
      soundPlayList.remove(var0);
      loopSound.remove(var1);
   }*/

  /* public static void unloadMusic(String var0) {
      Music var1 = (Music)musicPool.get(var0);
      if(var1.isPlaying()) {
         var1.stop();
      }

      musicPool.remove(var0);
      GAssetsManager.unload((Object)var1);
   }

   public static void unloadSound(String var0) {
      stopSound(var0);
      GAssetsManager.unload(var0);
      soundPool.remove(var0);
      soundPlayList.remove(var0);
      loopSound.remove(loopSound.findKey(var0, true, -1L));
   }

   public float getMusicVolume() {
      return volume_music;
   }

   public float getSoundVolume() {
      return volume_sound;
   }*/
}
