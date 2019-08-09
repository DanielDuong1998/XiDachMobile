package com.ss.core.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GUI;
import com.ss.gameLogic.scene.GGameMainScene;

public class Player {
    TextureAtlas gameMainAtlas;
    Group group;
    Label name;
    int nameid;
    public Label moneyTxt;
    BitmapFont fontBitMap;
    long money;
    String unit;

    public Image avatar;
    public Image frameAvt;

    public Player(TextureAtlas gameMainAtlas, Group group, long money, int idAvatar, int nameid){
        initFont();
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        this.money = money;
        this.frameAvt = GUI.createImage(this.gameMainAtlas, "frameAvt");
        this.avatar = GUI.createImage(this.gameMainAtlas, "avt" + idAvatar);
        this.avatar.setScale(0.515f);
        this.frameAvt.setPosition(12, -44, Align.center);
        this.avatar.setPosition(0, -3.5f, Align.center);
        this.group.addActor(this.frameAvt);
        this.group.addActor(this.avatar);
        this.nameid = nameid;


        int div;
        if(money > 1000000000){
            div = (int) money/100000000;
            int nguyen, du;
            nguyen = div/10;
            du = div%10;
            unit = nguyen + "," + du + "B";

        }
        else if(money > 1000000){
            div = (int) money/100000;
            int nguyen, du;
            nguyen = div/10;
            du = div%10;
            unit = nguyen + "," + du + "M";
        }
        else if(money > 1000){
            div = (int) money/100;
            int nguyen, du;
            nguyen = div/10;
            du = div%10;
            unit = nguyen + "," + du + "K";
        }
        else unit = "";

        String firstName = GGameMainScene.firstName[nameid];
        this.name = new Label("" + firstName, new Label.LabelStyle(fontBitMap, null));
        this.name.setFontScale(0.7f);
        this.name.setPosition(15, -80);
        group.addActor(this.name);

        this.moneyTxt = new Label("" + unit, new Label.LabelStyle(fontBitMap, null));
        this.moneyTxt.setFontScale(0.7f);
        this.moneyTxt.setPosition(15, -30);
        group.addActor(this.moneyTxt);


    }
    private void initFont(){
        fontBitMap = GAssetsManager.getBitmapFont("font_money.fnt");
    }
}
