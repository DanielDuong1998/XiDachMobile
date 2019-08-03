package com.ss.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.ss.core.util.GUI;

public class Player {
    TextureAtlas gameMainAtlas;
    Group group;
    String name;
    long money;

    public Image avatar;
    public Image frameAvt;

    public Player(TextureAtlas gameMainAtlas, Group group, long money, int idAvatar){
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        this.money = money;
        this.frameAvt = GUI.createImage(this.gameMainAtlas, "frameAvt");
        this.avatar = GUI.createImage(this.gameMainAtlas, "avt" + idAvatar);
        this.avatar.setScale(0.5f);
        this.frameAvt.setPosition(12, -44, Align.center);
        this.avatar.setPosition(0, 0, Align.center);
        this.group.addActor(this.frameAvt);
        this.group.addActor(this.avatar);
    }
}
