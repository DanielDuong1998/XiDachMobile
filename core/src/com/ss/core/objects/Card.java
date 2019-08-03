package com.ss.core.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
    }

    public void addListenerClick(){
        tileDown.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                hiddenTileDown();
            }
        });
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
