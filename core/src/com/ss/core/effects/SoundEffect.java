package com.ss.core.effects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;


public class SoundEffect {
    public static int MAX_COMMON = 10;
    public static int renderCards = 0;
    public static int flipACard = 1;
    public static int takeCard = 2;
    public static int button = 3;
    public static int buttonFlipAll = 4;
    public static int playerTurn = 5;
    public static int buttonStartGame = 6;
    public static int buttonNewGame = 7;
    public static int buttonFlipCards = 8;
    public static int flipCards = 9;

    public static boolean mute = false;

    public static Sound[] commons;
    private static Music bgSound;
    public static void initSound(){
        commons = new Sound[MAX_COMMON];
        commons[renderCards] = GAssetsManager.getSound("throwCard.mp3");
        commons[flipACard] = GAssetsManager.getSound("flipACard.mp3");
        commons[takeCard] = GAssetsManager.getSound("takeCard.mp3");
        commons[button] = GAssetsManager.getSound("button.mp3");
        commons[buttonFlipAll] = GAssetsManager.getSound("buttonFlipAll.mp3");
        commons[playerTurn] = GAssetsManager.getSound("playerTurn.mp3");
        commons[buttonStartGame] = GAssetsManager.getSound("buttonStartGame.mp3");
        commons[buttonNewGame] = GAssetsManager.getSound("buttonNewGame.mp3");
        commons[buttonFlipCards] = GAssetsManager.getSound("buttonFlipCards.mp3");
        commons[flipCards] = GAssetsManager.getSound("flipCards.mp3");
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