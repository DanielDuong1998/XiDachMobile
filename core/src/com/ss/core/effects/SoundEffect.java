package com.ss.core.effects;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.ss.core.util.GAssetsManager;


public class SoundEffect {
    public static int MAX_COMMON = 19;
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
    public static int flipAllCards = 10;
    public static int rotateCards = 11;
    public static int turnBots = 12;
    public static int winSound = 13;
    public static int loseSound = 14;
    public static int xiDachSound = 15;
    public static int chipPockers = 16;
    public static int tick = 17;
    public static int shake = 18;



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
        commons[flipAllCards] = GAssetsManager.getSound("flipAllCards.mp3");
        commons[rotateCards] = GAssetsManager.getSound("rotateCards.mp3");
        commons[turnBots] = GAssetsManager.getSound("turnBots.mp3");
        commons[winSound] = GAssetsManager.getSound("winSound.mp3");
        commons[loseSound] = GAssetsManager.getSound("loseSound.mp3");
        commons[xiDachSound] = GAssetsManager.getSound("xiDach.mp3");
        commons[chipPockers] = GAssetsManager.getSound("chipPockers.mp3");
        commons[tick] = GAssetsManager.getSound("tick.mp3");
        commons[shake] = GAssetsManager.getSound("shake.mp3");
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