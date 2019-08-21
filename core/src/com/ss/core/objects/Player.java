package com.ss.core.objects;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.core.effects.SoundEffect;
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
    public long money;
    String unit;
    public Image avatar;
    public Image frameAvt;
    public Image win;
    public Image lose;
    public Image tie;

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

        win = GUI.createImage(gameMainAtlas, "win");
        win.setSize(win.getWidth()*0.5f, win.getHeight()*0.5f);
        win.setPosition(- 100, - 20);

        lose = GUI.createImage(gameMainAtlas, "lose");
        lose.setSize(lose.getWidth()*0.5f, lose.getHeight()*0.5f);
        lose.setPosition( - 100,  - 20);

        tie = GUI.createImage(gameMainAtlas, "tie");
        tie.setSize(tie.getWidth()*0.5f, tie.getHeight()*0.5f);
        tie.setPosition(- 100, - 20);

        lose.setVisible(false);
        win.setVisible(false);
        tie.setVisible(false);
        group.addActor(win);
        group.addActor(lose);
        group.addActor(tie);


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
        else unit = "" + money;


        String firstName = GGameMainScene.firstName[nameid];
        this.name = new Label("" + firstName, new Label.LabelStyle(fontBitMap, null));
        this.name.setFontScale(0.7f);
        this.name.setPosition(15, -80);
        group.addActor(this.name);

        this.moneyTxt = new Label("" + unit, new Label.LabelStyle(fontBitMap, null));
        this.moneyTxt.setFontScale(0.7f);
        this.moneyTxt.setPosition(18, -50);
        group.addActor(this.moneyTxt);

    }

    public void addMoney(long money){
        this.money += money;
        showMoneyTxt();
    }

    public void subMoney(long money){
        this.money -= money;
        showMoneyTxt();
    }

    private void showMoneyTxt(){
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
        else unit = "" + money;

        this.moneyTxt.setText("" + unit);

    }
    private void initFont(){
        fontBitMap = GAssetsManager.getBitmapFont("font_money.fnt");
    }

    public void newBots(int idAvatar, int nameid, long money){
        SoundEffect.Play(SoundEffect.doorBell);
        this.avatar.remove();
        this.moneyTxt.remove();
        this.name.remove();

        this.avatar = GUI.createImage(this.gameMainAtlas, "avt" + idAvatar);
        this.avatar.setScale(0.515f);
        this.avatar.setPosition(0, -3.5f, Align.center);
        this.group.addActor(this.avatar);

        this.nameid = nameid;
        String firstName = GGameMainScene.firstName[nameid];
        this.name = new Label("" + firstName, new Label.LabelStyle(fontBitMap, null));
        this.name.setFontScale(0.7f);
        this.name.setPosition(15, -80);
        group.addActor(this.name);

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
        else unit = "" + money;

        this.money = money;
        this.moneyTxt = new Label("" + unit, new Label.LabelStyle(fontBitMap, null));
        this.moneyTxt.setFontScale(0.7f);
        this.moneyTxt.setPosition(15, -50);
        group.addActor(this.moneyTxt);

        win.remove();
        lose.remove();
        tie.remove();

        win = GUI.createImage(gameMainAtlas, "win");
        win.setSize(win.getWidth()*0.5f, win.getHeight()*0.5f);
        win.setPosition(- 100, - 20);

        lose = GUI.createImage(gameMainAtlas, "lose");
        lose.setSize(lose.getWidth()*0.5f, lose.getHeight()*0.5f);
        lose.setPosition( - 100,  - 20);

        tie = GUI.createImage(gameMainAtlas, "tie");
        tie.setSize(tie.getWidth()*0.5f, tie.getHeight()*0.5f);
        tie.setPosition(- 100, - 20);

        lose.setVisible(false);
        win.setVisible(false);
        tie.setVisible(false);
        group.addActor(win);
        group.addActor(lose);
        group.addActor(tie);

    }
}
