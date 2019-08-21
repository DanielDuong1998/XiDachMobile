package com.ss.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GUI;

public class FrameMoney {
    TextureAtlas gameMainAtlas;
    Group group;
    Image image;
    public long money = 0;
    public Label moneyTxt;
    BitmapFont bitmapFont;

    public FrameMoney(TextureAtlas gameMainAtlas, Group group){
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        image = GUI.createImage(this.gameMainAtlas, "frameMoney");
        this.group.addActor(image);
        bitmapFont = GAssetsManager.getBitmapFont("font_money.fnt");
        moneyTxt = new Label("" + 0, new Label.LabelStyle(bitmapFont, null));
        moneyTxt.setAlignment(Align.center);
        group.addActor(moneyTxt);
        //initBitmapFont();
    }

    public void setPosition(float x, float y){
        image.setPosition(x, y);
    }

    private void initBitmapFont(){
        long div;
        String unit;
        long moneyTemp = Math.abs(money);

        if(moneyTemp >= 1000000000){
            div = (long) moneyTemp/100000000;
            long nguyen, du;
            nguyen =  div/10;
            du =  div%10;
            unit = nguyen + "," + du + "B";
            if(money >= 0)
                money = (long) nguyen * 1000000000 + du * 100000000;
            else money =(long) -1*nguyen*1000000000 + du * 100000000;
        }
        else if(moneyTemp >= 1000000){
            div = (long) moneyTemp/100000;
            long nguyen, du;
            nguyen =  div/10;
            du =  div%10;
            unit = nguyen + "," + du + "M";
            if(money>=0)
                money = (long) nguyen * 1000000 + du * 100000;
            else money = (long) -1*nguyen*1000000 + du * 100000;
        }
        else if(moneyTemp >= 1000){
            div = (long) moneyTemp/100;
            long nguyen, du;
            nguyen =  div/10;
            du =  0;
            unit = nguyen + "," + du + "K";
            if(money>=0)
                money = (long) nguyen*1000;
            else money = (long) -1*nguyen*1000;
        }
        else unit = "" + money;

        if(money >= 0)
            moneyTxt.setText("" + unit);
        else moneyTxt.setText("-" + unit);
        moneyTxt.setX(image.getX() + 50);
    }

    public void setMoney(long money){
        this.money = money;
        initBitmapFont();
    }


    public void addMoney(long money1){
        this.money = this.money + (long)money1;
        initBitmapFont();
    }

    public void subMoney(long money){
        this.money -= money;
        initBitmapFont();
    }


}
