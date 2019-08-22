package com.ss.gameLogic.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
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
       startBtn.setOrigin(Align.center);

       startBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight * (float) 5/6, Align.center);
       final GScreen gScreen = new GGameMainScene();
       menuGroup.setOrigin(Align.center);

        startBtn.addListener(new ClickListener(){
           @Override
           public void clicked(InputEvent event, float x, float y) {
           super.clicked(event, x, y);
           startBtn.setTouchable(Touchable.disabled);
           startBtn.setScale(0.2f);
           startBtn.addAction(Actions.sequence(
                   Actions.scaleTo(1, 1, 0.5f, Interpolation.bounceOut),
                   GSimpleAction.simpleAction((d, a)->{
                       setScreen(gScreen);
                       return true;
                   })
           ));
           }
       });

        Image noneTick = GUI.createImage(menuAtlas, "noneTick");
        Image tick = GUI.createImage(menuAtlas, "ticked");
        menuGroup.addActor(noneTick);
        menuGroup.addActor(tick);

        noneTick.setSize(noneTick.getWidth()*0.5f, noneTick.getHeight()*0.5f);
        tick.setSize(tick.getWidth()*0.5f, tick.getHeight()*0.5f);

        noneTick.setPosition(GMain.screenWidth/2 + 180, GMain.screenHeight/2 + 180);
        tick.setPosition(GMain.screenWidth/2 + 180, GMain.screenHeight/2 + 180);

        noneTick.setOrigin(Align.center);
        tick.setOrigin(Align.center);
        tick.setVisible(false);

        noneTick.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                tick.setScale(0.5f);
                noneTick.setVisible(false);
                tick.setVisible(true);
                tick.addAction(Actions.sequence(
                    Actions.scaleTo(1, 1, 0.1f, Interpolation.bounceOut),
                    GSimpleAction.simpleAction((d,a)->{
                        GGameStart.mode = 1;
                        return true;
                    })
                ));
            }
        });

        tick.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                noneTick.setScale(0.5f);
                tick.setVisible(false);
                noneTick.setVisible(true);
                noneTick.addAction(Actions.sequence(
                        Actions.scaleTo(1, 1, 0.1f, Interpolation.bounceOut),
                        GSimpleAction.simpleAction((d,a)->{
                            GGameStart.mode = 0;
                            return true;
                        })
                ));
            }
        });


    }

    @Override
    public void run() {

    }
}
