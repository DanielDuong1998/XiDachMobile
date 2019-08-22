package com.ss.gameLogic.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GGameBegin extends GScreen {
    TextureAtlas menuAtlas;

    @Override
    public void dispose() {

    }

    @Override
    public void init() {
        Group menuGroup = new Group();
        GStage.addToLayer(GLayer.ui, menuGroup);

        menuAtlas = GAssetsManager.getTextureAtlas("gameStart/gameStart.atlas");
        Image bg = GUI.createImage(menuAtlas, "startScene");
        menuGroup.addActor(bg);

       Image startBtn = GUI.createImage(menuAtlas, "startBtn");
       menuGroup.addActor(startBtn);

       startBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight * (float) 4/5, Align.center);
       final GScreen gScreen = new GGameMainScene();
       menuGroup.setOrigin(Align.center);

        startBtn.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
               super.clicked(event, x, y);
               Gdx.app.log("debug", "click here!!!");
               menuGroup.addAction(Actions.sequence(
                   Actions.scaleBy(-1, -1, 0.5f, Interpolation.linear),
                   GSimpleAction.simpleAction((d, a)->{
                       return true;
                   })
               ));
               setScreen(gScreen);
           }
       });
    }

    @Override
    public void run() {

    }
}
