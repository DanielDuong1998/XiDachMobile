package com.ss.gameLogic.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GMenuScene extends GScreen {
    TextureAtlas menuAtlas;

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        Group menuGroup = new Group();
        GStage.addToLayer(GLayer.ui, menuGroup);

        menuAtlas = GAssetsManager.getTextureAtlas("menu.atlas");
        Image bg = GUI.createImage(menuAtlas, "bg");
        menuGroup.addActor(bg);

        Button btn = GUI.creatButton(menuAtlas.findRegion("newgame"));
        menuGroup.addActor(btn);
        btn.addListener(new ClickListener() {
            public void clicked(InputEvent event, float x, float y) {
                GMain.platform.ShowVideoReward((finish) -> {
                    if (finish) {
                        Gdx.app.log("video reward", "view complete");
                    }
                    else {
                        Gdx.app.log("video reward", "view not complete");
                    }
                });
            }
        });
    }

    @Override
    public void run() {

    }
}
