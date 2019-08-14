package com.ss.gameLogic.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.effects.SoundEffect;
import com.ss.core.objects.Board;
import com.ss.core.objects.BoardConfig;
import com.ss.core.objects.Card;
import com.ss.core.objects.FrameMoney;
import com.ss.core.objects.Player;
import com.ss.core.objects.Pocker;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GGameMainScene extends GScreen {
    TextureAtlas gameMainAtlas;
    Group uiGroup;
    BoardConfig cfg;
    public static Array<Group> groupsBot;
    Array<Group> groupFrameMoney;
    public Array<Group> groupPocker;
    public Array<Player> bots;
    Board board;
    Array<Vector2> positionGroup;
    Array<Vector2> positionFrameMoney;
    Array<Vector2> positionFlipCards;
    Array<Vector2> positionGroupPocker;
    Array<Integer> idAvatar;
    int countIdAvatar = 0;
    public Array<FrameMoney> frameMoney;
    public static Array<Image> flipCards;
    public static Array<Label> pointsTxt;
    public static String[] firstName;
    public static Image turnLight;
    Array<Integer> nameids;
    int countNameId = 0;
    BitmapFont fontBitMap;
    BitmapFont fontBitMap1;
    Group fontGroup;
    public static long moneyPlayer = 2000000l;
    Label moneyPlayerTxt;
    public static Image cardDown;



    @Override
    public void dispose() {
        gameMainAtlas.dispose();
    }

    @Override
    public void init() {
        gameMainAtlas = GAssetsManager.getTextureAtlas("gameMain/gameMain.atlas");
        uiGroup = new Group();
        cfg = new BoardConfig();
        initFont();
        initUI();
        SoundEffect.initSound();
        //pokc eer
        initGroupPockers();
        initPositionGroup();
        initGroupBot();
        initGroupFrameMoney();
        initIdAvatarRandom();
        initBot();
        renderBot();
        initFrameMoney();
        renderFrameMoney();
        initFlipsCards();
        renderFlipCards();
        initPointTxt();
        renderPointTxt();
        renderGroupPocker();
        board = new Board(this, gameMainAtlas, uiGroup);
    }

    @Override
    public void run() {

    }

    public void replay(){
        flipCards.removeRange(0, flipCards.size-1);
        fontGroup.clearChildren();
        fontGroup.clear();
        for(Group group : groupPocker){
            group.clearChildren();
        }
        //initGroupPockers();
        renderGroupPocker();

        initFlipsCards();
        renderFlipCards();
        initPointTxt();
        renderPointTxt();
        frameMoney.get(0).setMoney(0);
        this.board = new Board(this, gameMainAtlas, uiGroup);
    }

    private void initPointTxt(){
        pointsTxt = new Array<>();
        fontGroup = new Group();
        uiGroup.addActor(fontGroup);
        for(int index = 0; index < GGameStart.member; index++){
            Label point = new Label("", new Label.LabelStyle(fontBitMap, null));
            fontGroup.addActor(point);
            pointsTxt.add(point);
            point.setVisible(false);
        }
    }

    private void initFont(){
        fontBitMap = GAssetsManager.getBitmapFont("font_money.fnt");
        fontBitMap1 = GAssetsManager.getBitmapFont("font_lieng.fnt");
    }


    private void initName(){
        firstName = new String[]{"Vu", "Tuyen", "Calverley", "Alan", "Eggleston", "Ferryman", "Gail", "Daniel", "Josey", "Kim",
        "Oliver", "Jack", "Harry", "Jacob", "Charlie", "Thomas", "George", "Oscar", "James", "William", "Jake", "Connor", "Callum",
        "Kyle", "Joe", "Reece", "Rhys", "Charlie", "Damian", "Thor"};
        nameids = new Array<>();

        for(int i = 0; i < 30; i++) {
            int id = i;
           nameids.add(id);
        }
        nameids.shuffle();
    }

    private void initUI(){
        GStage.addToLayer(GLayer.ui, uiGroup);
        initName();
        Image bg = GUI.createImage(gameMainAtlas, "bg");
        Image table = GUI.createImage(gameMainAtlas, "table");
        cardDown = GUI.createImage(gameMainAtlas, "noc");
        cardDown.setVisible(false);
        Image frameMoneyPlayer = GUI.createImage(gameMainAtlas, "frameMoneyPlayer");
        table.setWidth(table.getWidth()*0.958f);
        cardDown.setScale(0.5f);
        uiGroup.addActor(bg);
        uiGroup.addActor(table);
        uiGroup.addActor(cardDown);
        uiGroup.addActor(frameMoneyPlayer);
        frameMoneyPlayer.setWidth(frameMoneyPlayer.getWidth()*0.5f);
        frameMoneyPlayer.setHeight(frameMoneyPlayer.getHeight()*0.5f);
        frameMoneyPlayer.setPosition(GMain.screenWidth/2 - frameMoneyPlayer.getWidth()/2, GMain.screenHeight - frameMoneyPlayer.getHeight() + 5);
        table.setPosition(table.getX(), 0);
        cardDown.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2 - 10);
        turnLight = GUI.createImage(this.gameMainAtlas, "turnLight");
        uiGroup.addActor(turnLight);
        turnLight.setVisible(false);

        //Pocker pocker = new Pocker(gameMainAtlas, uiGroup, 10000);
    }

    private void initFlipsCards(){
        flipCards = new Array<>();
        for(int i = 0; i < GGameStart.member - 1; i++){
            Image image = GUI.createImage(this.gameMainAtlas, "flipCards");
            uiGroup.addActor(image);
            flipCards.add(image);
            image.setVisible(false);
        }
    }

    private void initGroupBot(){
        groupsBot = new Array<>();
        for(int index = 0; index < GGameStart.member - 1; index++) {
            Group groupBot = new Group();
            uiGroup.addActor(groupBot);
            groupsBot.add(groupBot);
        }
    }

    private void initGroupFrameMoney(){
        groupFrameMoney = new Array<>();
        for(int index = 0; index < GGameStart.member; index++) {
            Group group = new Group();
            uiGroup.addActor(group);
            groupFrameMoney.add(group);
        }
    }

    private void initBot(){
        bots = new Array<>();
        for(int index = 0; index < GGameStart.member - 1; index++){
            Player bot = new Player(gameMainAtlas, groupsBot.get(index), 2000000, idAvatar.get(index), nameids.get(index));
            countIdAvatar++;
            countNameId++;
            bots.add(bot);
        }

        long div;
        String unit;
        if(moneyPlayer >= 1000000000){
            div = (long) moneyPlayer/100000000;
            long nguyen, du;
            nguyen = div/10;
            du = div%10;
            unit = nguyen + "," + du + "B";

        }
        else if(moneyPlayer >= 1000000){
            div = (long) moneyPlayer/100000;
            long nguyen, du;
            nguyen = (long) div/10;
            du = div%10;
            unit = nguyen + "," + du + "M";
        }
        else if(moneyPlayer >= 1000){
            div = (long) moneyPlayer/100;
            long nguyen, du;
            nguyen = (long) div/10;
            du = div%10;
            unit = nguyen + "," + du + "K";
        }
        else unit = "";

        moneyPlayerTxt = new Label("" + unit, new Label.LabelStyle(fontBitMap, null));
        moneyPlayerTxt.setFontScale(1.9f);
        moneyPlayerTxt.setAlignment(Align.center);
        moneyPlayerTxt.setPosition(GMain.screenWidth/2, GMain.screenHeight - 35, Align.center);
        uiGroup.addActor(moneyPlayerTxt);
    }

    private void initIdAvatarRandom(){
        idAvatar = new Array<>();
        for(int index = 0; index < 12; index++){
            idAvatar.add(index);
        }
        idAvatar.shuffle();
    }

    private void initGroupPockers(){
        groupPocker = new Array<>();
        for(int i = 0; i < GGameStart.member - 1; i++) {
            Group group = new Group();
            groupPocker.add(group);
            uiGroup.addActor(group);
        }

    }

    private void initFrameMoney(){
        frameMoney = new Array<>();
        for(int i = 0; i < GGameStart.member; i++) {
            FrameMoney fm = new FrameMoney(gameMainAtlas, uiGroup);
            frameMoney.add(fm);
        }
    }

    private void renderBot(){
        for(int index = 0; index < GGameStart.member - 1; index++){
            groupsBot.get(index).setPosition(positionGroup.get(index).x,positionGroup.get(index).y, Align.center);
        }
    }

    private void renderGroupPocker(){
        for(int index = 0; index < GGameStart.member - 1; index++){
            groupPocker.get(index).setPosition(positionGroup.get(index).x,positionGroup.get(index).y);
        }
    }

    private void renderFrameMoney(){
        for(int i = 0; i < GGameStart.member; i++){
            frameMoney.get(i).setPosition(positionFrameMoney.get(i).x,positionFrameMoney.get(i).y);
            frameMoney.get(i).moneyTxt.setPosition(positionFrameMoney.get(i).x + 50,positionFrameMoney.get(i).y + 8);
        }
    }

    private void renderFlipCards(){
        for(int i = 0; i < GGameStart.member - 1; i++) {
            flipCards.get(i).setPosition(positionFlipCards.get(i).x, positionFlipCards.get(i).y);
        }
    }

    private void renderPointTxt(){
        for(int i = 0; i < GGameStart.member; i++) {
            if(i == 0) {
                pointsTxt.get(i).setPosition(GMain.screenWidth/2 + 200, GMain.screenHeight - 100);
                pointsTxt.get(i).setFontScale(2);
            }
            else {
                pointsTxt.get(i).setPosition(positionFlipCards.get(i-1).x + 5, positionFlipCards.get(i-1).y + 20);
            }
        }
    }

    private void initPositionGroup(){
        positionGroup = new Array<>();
        positionFrameMoney = new Array<>();
        positionFlipCards = new Array<>();
        switch (GGameStart.member){
            case 2: {
                Vector2 position = new Vector2(GMain.screenWidth/2, 100);
                positionGroup.add(position);

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 60);
                Vector2 positionM1 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, 200 + 10);
                positionFrameMoney.add(positionM0, positionM1);

                Vector2 positionF = new Vector2(GMain.screenWidth/2 - 225, 60);
                positionFlipCards.add(positionF);

                break;
            }
            case 3: {
                Vector2 position0 = new Vector2(GMain.screenWidth - 200, 150);
                Vector2 position1 = new Vector2(200, 150);
                positionGroup.add(position0, position1);

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 60);
                Vector2 positionM1 = new Vector2(GMain.screenWidth - 420 - 50, 170);
                Vector2 positionM2 = new Vector2(360, 170);
                positionFrameMoney.add(positionM0, positionM1, positionM2);

                Vector2 positionF0 = new Vector2(GMain.screenWidth - 250, 250);
                Vector2 positionF1 = new Vector2(120, 250);
                positionFlipCards.add(positionF0, positionF1);
                break;
            }
            case 4: {
                Vector2 position0 = new Vector2(GMain.screenWidth-170, GMain.screenHeight/2);
                Vector2 position1 = new Vector2(GMain.screenWidth/2, 100);
                Vector2 position2 = new Vector2(150, GMain.screenHeight/2 );
                positionGroup.add(position0, position1, position2);

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 60);
                Vector2 positionM1 = new Vector2(GMain.screenWidth-400, GMain.screenHeight/2 - 40);
                Vector2 positionM2 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, 200 + 10);
                Vector2 positionM3 = new Vector2(290, GMain.screenHeight/2 - 40);
                positionFrameMoney.add(positionM0, positionM1, positionM2, positionM3);

                Vector2 positionF0 = new Vector2(GMain.screenWidth-150, GMain.screenHeight/2 + 100 );
                Vector2 positionF1 = new Vector2(GMain.screenWidth/2 - 225, 60);
                Vector2 positionF2 = new Vector2(100, GMain.screenHeight/2  + 100);
                positionFlipCards.add(positionF0, positionF1, positionF2);
                break;
            }
            case 5: {
                Vector2 position0 = new Vector2(GMain.screenWidth - 200, GMain.screenHeight - 250);
                Vector2 position1 = new Vector2(GMain.screenWidth - 200, 150);
                Vector2 position2 = new Vector2(200, 150);
                Vector2 position3 = new Vector2(200, GMain.screenHeight - 250);
                positionGroup.add(position0, position1, position2, position3);


                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 60);
                Vector2 positionM1 = new Vector2(GMain.screenWidth - 420 - 50, GMain.screenHeight - 280);
                Vector2 positionM2 = new Vector2(GMain.screenWidth - 420 - 50, 210);
                Vector2 positionM3 = new Vector2(340, 210);
                Vector2 positionM4 = new Vector2(340, GMain.screenHeight - 280);
                positionFrameMoney.add(positionM0, positionM1, positionM2, positionM3);
                positionFrameMoney.add(positionM4);

                Vector2 positionF0 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 150);
                Vector2 positionF1 = new Vector2(GMain.screenWidth - 250, 250);
                Vector2 positionF2 = new Vector2(120, 250);
                Vector2 positionF3 = new Vector2(120, GMain.screenHeight - 150);
                positionFlipCards.add(positionF0, positionF1, positionF2, positionF3);
                break;
            }
            default: {
                Vector2 position0 = new Vector2(GMain.screenWidth - 200, GMain.screenHeight - 250);
                Vector2 position1 = new Vector2(GMain.screenWidth - 200, 150);
                Vector2 position2 = new Vector2(GMain.screenWidth/2, 100);
                Vector2 position3 = new Vector2(200, 150);
                Vector2 position4 = new Vector2(200, GMain.screenHeight - 250);
                positionGroup.add(position0, position1, position2, position3);
                positionGroup.add(position4);

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2 , GMain.screenHeight - 220 - 60);
                Vector2 positionM1 = new Vector2(GMain.screenWidth - 420 - 50, GMain.screenHeight - 280);
                Vector2 positionM2 = new Vector2(GMain.screenWidth - 420 - 50, 210);
                Vector2 positionM3 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, 200 + 10);
                Vector2 positionM4 = new Vector2(340, 210);
                Vector2 positionM5 = new Vector2(340, GMain.screenHeight - 280);
                positionFrameMoney.add(positionM0, positionM1, positionM2, positionM3);
                positionFrameMoney.add(positionM4, positionM5);

                Vector2 positionF0 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 150);
                Vector2 positionF1 = new Vector2(GMain.screenWidth - 250, 250);
                Vector2 positionF2 = new Vector2(GMain.screenWidth/2 - 225, 60);
                Vector2 positionF3 = new Vector2(120, 250);
                Vector2 positionF4 = new Vector2(120, GMain.screenHeight - 150);
                positionFlipCards.add(positionF0, positionF1, positionF2, positionF3);
                positionFlipCards.add(positionF4);
                break;
            }
        }
    }

    public void addMoneyPlayer(long money){
        moneyPlayer += money;
        showMoneyPlayerTxt();
    }

    public void subMoneyPlayer(long money){
        moneyPlayer -= money;
        showMoneyPlayerTxt();
    }


    private void showMoneyPlayerTxt(){
        long moneyTemp = moneyPlayer;
        if(moneyPlayer <= 0){
            moneyTemp = 0;
        }
        long div;
        String unit;
        if(moneyPlayer >= 1000000000){
            div = (long) moneyPlayer/100000000;
            long nguyen, du;
            nguyen = div/10;
            du = div%10;
            unit = nguyen + "," + du + "B";

        }
        else if(moneyPlayer >= 1000000){
            div = (long) moneyPlayer/100000;
            long nguyen, du;
            nguyen = (long) div/10;
            du = div%10;
            unit = nguyen + "," + du + "M";
        }
        else if(moneyPlayer >= 1000){
            div = (long) moneyPlayer/100;
            long nguyen, du;
            nguyen = (long) div/10;
            du = div%10;
            unit = nguyen + "," + du + "K";
        }
        else unit = "" + moneyTemp;
        moneyPlayerTxt.setText("" + unit);
    }

    public void newBots(int index){
        bots.get(index).newBots(countIdAvatar, countNameId, moneyPlayer*2);
        countNameId++;
        countNameId++;
    }
}
