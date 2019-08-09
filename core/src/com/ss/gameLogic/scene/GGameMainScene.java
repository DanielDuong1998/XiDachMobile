package com.ss.gameLogic.scene;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.core.effects.SoundEffect;
import com.ss.core.objects.Board;
import com.ss.core.objects.BoardConfig;
import com.ss.core.objects.Card;
import com.ss.core.objects.FrameMoney;
import com.ss.core.objects.Player;
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
    Array<Player> bots;
    Board board;
    Array<Vector2> positionGroup;
    Array<Vector2> positionFrameMoney;
    Array<Vector2> positionFlipCards;
    Array<Integer> idAvatar;
    Array<FrameMoney> frameMoney;
    public static Array<Image> flipCards;
    public static String[] firstName;
    public static String[] lastName;
    public static Image turnLight;
    Array<Integer> nameids;

    @Override
    public void dispose() {
        gameMainAtlas.dispose();
    }

    @Override
    public void init() {
        gameMainAtlas = GAssetsManager.getTextureAtlas("gameMain/gameMain.atlas");
        uiGroup = new Group();
        cfg = new BoardConfig();
        initUI();
        SoundEffect.initSound();
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
        board = new Board(this, gameMainAtlas, uiGroup);
    }

    @Override
    public void run() {

    }

    public void replay(){
        flipCards.removeRange(0, flipCards.size-1);
        initFlipsCards();
        renderFlipCards();
        this.board = new Board(this, gameMainAtlas, uiGroup);
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
        Image cardDown = GUI.createImage(gameMainAtlas, "noc");

        table.setWidth(table.getWidth()*0.958f);

        cardDown.setScale(0.5f);
        uiGroup.addActor(bg);
        uiGroup.addActor(table);
        uiGroup.addActor(cardDown);
        table.setPosition(table.getX(), 0);
        cardDown.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2 - 5);
        turnLight = GUI.createImage(this.gameMainAtlas, "turnLight");
        uiGroup.addActor(turnLight);
        turnLight.setVisible(false);
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
        }
    }

    private void initIdAvatarRandom(){
        idAvatar = new Array<>();
        for(int index = 0; index < 12; index++){
            idAvatar.add(index);
        }
        idAvatar.shuffle();
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

    private void renderFrameMoney(){
        for(int i = 0; i < GGameStart.member; i++){
            frameMoney.get(i).setPosition(positionFrameMoney.get(i).x,positionFrameMoney.get(i).y);
        }
    }

    private void renderFlipCards(){
        for(int i = 0; i < GGameStart.member - 1; i++) {
            flipCards.get(i).setPosition(positionFlipCards.get(i).x, positionFlipCards.get(i).y);
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

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 55);
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

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 55);
                Vector2 positionM1 = new Vector2(GMain.screenWidth - 420 - 50, 150);
                Vector2 positionM2 = new Vector2(320, 150);
                positionFrameMoney.add(positionM0, positionM1, positionM2);

                Vector2 positionF0 = new Vector2(GMain.screenWidth - 250, 250);
                Vector2 positionF1 = new Vector2(120, 250);
                positionFlipCards.add(positionF0, positionF1);
                break;
            }
            case 4: {
                Vector2 position0 = new Vector2(GMain.screenWidth-100, GMain.screenHeight/2 );
                Vector2 position1 = new Vector2(GMain.screenWidth/2, 100);
                Vector2 position2 = new Vector2(150, GMain.screenHeight/2 );
                positionGroup.add(position0, position1, position2);

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 55);
                Vector2 positionM1 = new Vector2(GMain.screenWidth-300, GMain.screenHeight/2);
                Vector2 positionM2 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, 200 + 10);
                Vector2 positionM3 = new Vector2(250, GMain.screenHeight/2);
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


                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, GMain.screenHeight - 220 - 55);
                Vector2 positionM1 = new Vector2(GMain.screenWidth - 420 - 50, GMain.screenHeight - 250);
                Vector2 positionM2 = new Vector2(GMain.screenWidth - 420 - 50, 150);
                Vector2 positionM3 = new Vector2(320, 150);
                Vector2 positionM4 = new Vector2(320, GMain.screenHeight - 250);
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

                Vector2 positionM0 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2 , GMain.screenHeight - 220 - 55);
                Vector2 positionM1 = new Vector2(GMain.screenWidth - 420 - 50, GMain.screenHeight - 250);
                Vector2 positionM2 = new Vector2(GMain.screenWidth - 420 - 50, 150);
                Vector2 positionM3 = new Vector2((GMain.screenWidth - cfg.frameMoney)/2, 200 + 10);
                Vector2 positionM4 = new Vector2(320, 150);
                Vector2 positionM5 = new Vector2(320, GMain.screenHeight - 250);
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



}
