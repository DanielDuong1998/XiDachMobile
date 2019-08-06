package com.ss.core.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.util.GUI;

public class Card extends Image {
    TextureAtlas gameMainAtlas;
    Group group;
    public Image image;
    public Image tileDown;
    int[] values;
    public static float ratioScale = 0.5f;

    public Card(TextureAtlas gameMainAtlas, Group group, int element, int value){
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        this.values = new int[]{element, value};
        image = GUI.createImage(this.gameMainAtlas, "" + this.values[0] + this.values[1]);
        tileDown = GUI.createImage(this.gameMainAtlas, "00");
        setScale(ratioScale);
        this.group.addActor(image);
        this.group.addActor(tileDown);
        this.setDebug(true);
        this.debug();
    }

    public void addListenerClick(){
        tileDown.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                flipCard(true);
            }
        });
    }

    public void flipCard(boolean isNotBot){
        if(!isNotBot){
            tileDown.setVisible(false);
            return;
        }
        float ratio = isNotBot ? 1 : 0.5f;
        tileDown.setTouchable(Touchable.disabled);
        tileDown.setOrigin(Align.center);
        image.setOrigin(Align.center);
        image.setScale(0, ratio);
        tileDown.addAction(Actions.sequence(
                Actions.scaleBy(-1, 0, 0.15f),
                GSimpleAction.simpleAction((d, a)-> {
                    scaleImageCard(isNotBot);
                    return true;
                })
        ));
    }

    private void scaleImageCard(boolean isNotBot){
        float ratio = isNotBot ? 1 : 0.5f;
        image.addAction(Actions.scaleTo(ratio, ratio, 0.15f));
    }

    public void setPosition(float x, float y){
        image.setPosition(x, y);
        tileDown.setPosition(x, y);
    }

    public void setScale(float ratioScale){
        image.setScale(ratioScale);
        tileDown.setScale(ratioScale);
    }

    public void hiddenTileDown(){
        tileDown.setVisible(false);
        //tileDown.remove();
    }

    public void removeCard(){
        image.remove();
        tileDown.remove();
    }

    public void setVisible(boolean isVisible){
        tileDown.setVisible(isVisible);
        image.setVisible(isVisible);
    }

}
