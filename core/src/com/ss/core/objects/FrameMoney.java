package com.ss.core.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.core.util.GUI;

public class FrameMoney {
    TextureAtlas gameMainAtlas;
    Group group;
    Image image;

    public FrameMoney(TextureAtlas gameMainAtlas, Group group){
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        image = GUI.createImage(this.gameMainAtlas, "frameMoney");
        this.group.addActor(image);
    }

    public void setPosition(float x, float y){
        image.setPosition(x, y);
    }
}
