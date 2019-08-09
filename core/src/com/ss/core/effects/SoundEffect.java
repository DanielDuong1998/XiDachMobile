package com.ss.core.effects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;


public class SoundEffect {
    public static int MAX_COMMON = 1;
    public static int renderCards = 0;
    public static boolean mute = false;

    public static Sound[] commons;
    private static Music bgSound;
    public static void initSound(){
        commons = new Sound[MAX_COMMON];
        commons[renderCards] = GAssetsManager.getSound("throwCard.mp3");
    }
    public static void Play(int soundCode){
        if(!mute)
        {
            commons[soundCode].play();
        }
    }
    public static  void Playmusic(){
        bgSound.setLooping(true);
        bgSound.setVolume(0.5f);
        bgSound.play();
    } public static  void Stopmusic(){
        bgSound.pause();
    }


}