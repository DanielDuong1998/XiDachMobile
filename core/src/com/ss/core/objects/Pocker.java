package com.ss.core.objects;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.ss.core.util.GUI;

public class Pocker {
    TextureAtlas gameMainAtlas;
    Group group;
    Image imagePocker;
    int value;

    public Pocker(TextureAtlas gameMainAtlas, Group group, int value){
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        this.value = value;
        imagePocker = GUI.createImage(this.gameMainAtlas, "chip" + value/1000);
        imagePocker.setWidth(imagePocker.getWidth()*0.2f);
        imagePocker.setHeight(imagePocker.getHeight()*0.2f);
        group.addActor(imagePocker);
    }
}
