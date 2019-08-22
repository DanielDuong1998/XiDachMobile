package com.ss.gameLogic.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GScreen;

public class GGameStart extends GScreen {
    TextureAtlas gameStartAtlas;
    Group uiGroup;
    public static int member = 6;
    public static int mode = 1;
    public static int idBoss = -1;
    public static long money = 0;
    public static Preferences prefs = Gdx.app.getPreferences("My Preferences");

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        gameStartAtlas = GAssetsManager.getTextureAtlas("gameMain/gameStart.atlas");
        uiGroup = new Group();

    }

    @Override
    public void run() {

    }
}
