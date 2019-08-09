package com.ss.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.commons.Tweens;
import com.ss.core.effects.EffectSlide;
import com.ss.core.effects.SoundEffect;
import com.ss.core.util.GUI;
import com.ss.gameLogic.scene.GGameMainScene;
import com.ss.gameLogic.scene.GGameStart;


public class Board {
    TextureAtlas gameMainAtlas;
    Group group;
    BoardConfig cfg;
    GGameMainScene game;
    Image startGameBtn;
    Image takeCardBtn;
    Image flipAllCardsBtn;
    Image newGameBtn;
    Array<Vector2> tiles;
    Array<Array<Card>> cards;
    Array<Array<Card>> cards_temp;
    Array<Group> cg;
    Array<Vector2> positionCardGroup;
    Array<Vector2> resultFinal;
    int turnAtTheMoment = 1;
    int turnInitCards = 0;
    boolean flagTurnLightOfTurn1 = true;
    EffectSlide slideButtonEffect;
    boolean[] idBotOverTurn;
    static int count = 0;
    int tics = 0;

    public Board(GGameMainScene game, TextureAtlas gameMainAtlas, Group group){
        this.game = game;
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        cfg = new BoardConfig();
        initIdBotOverTurn();
        initCardsGroup();
        initPositionCardGroup();
        initResutl();
        addListenerFlipCardBtn();
        startGame();
    }

    private void replay(){
        dispose();
        game.replay();
    }

    private void startGame(){
        startGameBtn = GUI.createImage(this.gameMainAtlas, "startGame");
        takeCardBtn = GUI.createImage(this.gameMainAtlas, "takeCard");
        flipAllCardsBtn = GUI.createImage(this.gameMainAtlas, "flipAll");
        newGameBtn = GUI.createImage(this.gameMainAtlas, "newGame");
        addlistenerButton();
        group.addActor(startGameBtn);
        group.addActor(takeCardBtn);
        group.addActor(flipAllCardsBtn);
        group.addActor(newGameBtn);

        newGameBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);
        newGameBtn.setVisible(false);
        flipAllCardsBtn.setPosition(850, GMain.screenHeight);

        takeCardBtn.setVisible(false);
        flipAllCardsBtn.setVisible(false);
        startGameBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);
        slideButtonEffect = new EffectSlide(startGameBtn.getX() + 130, startGameBtn.getY() + 45, group);
        group.addActor(slideButtonEffect);
        startGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            startGameBtn.setVisible(false);
            slideButtonEffect.disposeEcffect();

            //am thanh
            Tweens.setTimeout(group, 0.5f, ()->{
                SoundEffect.Play(SoundEffect.renderCards);
                startRenderCards();
            });
            }
        });
        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                newGameBtn.setVisible(false);
                replay();
            }
        });
    }

    private void addListenerFlipCardBtn() {
        for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
            final int itemp = i;
            GGameMainScene.flipCards.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                count++;
                idBotOverTurn[itemp] = true;
                GGameMainScene.flipCards.get(itemp).setTouchable(Touchable.disabled);
                flipACards(itemp+1);
                return;
                }
            });
        }
    }

    private void initIdBotOverTurn(){
        idBotOverTurn = new boolean[GGameStart.member - 1];
        for(int i = 0; i < idBotOverTurn.length; i++) {
            idBotOverTurn[i] = false;
        }
    }

    private void flipACards(int index){
        GGameMainScene.flipCards.get(index-1).setOrigin(Align.center);
        int delta = checkCardsWidthPlayer(cards.get(index));
        GGameMainScene.flipCards.get(index-1).addAction(Actions.sequence(
            Actions.sequence(
                    scaleBy(-0.2f, -0.2f, 0.2f, Interpolation.circleIn),
                    scaleBy(0.2f, 0.2f, 0.2f, Interpolation.circleOut)
            ),
            GSimpleAction.simpleAction((d, a)->{
                GGameMainScene.flipCards.get(index-1).setVisible(false);
                for(int i = 0; i < cards.get(index).size; i++){
                     cards.get(index).get(i).flipCard(false);
                }
                checkFlipCardBtn(index - 1);
                return true;
            }))
        );
    }

    private void checkFlipCardBtn(int index){
        Gdx.app.log("debug", "count: " + count);
        if(count == GGameStart.member - 1){
            GGameMainScene.flipCards.get(index).addAction(
                Actions.sequence(
                    delay(0.5f),
                    GSimpleAction.simpleAction((d1, a1)->{
                        for(int i = 0; i < cards.get(0).size; i++ ){
                            cards.get(0).get(i).flipCard(true);
                        }
                        takeCardBtn.setVisible(false);
                        flipAllCardsBtn.setVisible(false);
                        newGameBtn.setVisible(true);
                        return true;
                    })
                )
            );
        }
    }

    private void startRenderCards(){
        renderPositionCard();
        initTiles();
        initCards();
    }

    private void addlistenerButton(){
        takeCardBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            takeCardBtn.setTouchable(Touchable.disabled);
            Gdx.app.log("debug", "click vao ne _ board_78!!");
            takeCardBtn.setOrigin(Align.center);
            takeCardBtn.addAction(Actions.sequence(
                Actions.sequence(
                    scaleBy(-0.2f, -0.2f, 0.2f, Interpolation.circleIn),
                    scaleBy(0.2f, 0.2f, 0.2f, Interpolation.circleOut)
                ),
                GSimpleAction.simpleAction((d, a)->{
                    getCardForPlayer();
                    return true;
                }))
            );
            }
        });

        flipAllCardsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            flipAllCardsBtn.setTouchable(Touchable.disabled);
            Gdx.app.log("debug", "board_106 _ click ne");
            flipAllCardsBtn.setOrigin(Align.center);
            flipAllCardsBtn.addAction(Actions.sequence(
                Actions.sequence(
                    scaleBy(-0.2f, -0.2f, 0.2f, Interpolation.circleIn),
                    scaleBy(0.2f, 0.2f, 0.2f, Interpolation.circleOut)
                ),
                GSimpleAction.simpleAction((d, a)->{
                    for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
                        GGameMainScene.flipCards.get(i).setVisible(false);
                    }
                    takeCardBtn.setVisible(false);
                    flipAllCardsBtn.setVisible(false);
                    flipAllCards();
                    newGameBtn.setVisible(true);
                    return true;
                }))
            );
            }
        });
    }

    private void flipAllCards(){
//        for(int index = 1; index < GGameStart.member; index++) {
//            cg.get(index).setPosition(cg.get(index).getX() - cfg.CW*Card.ratioScale/2, cg.get(index).getY() - cfg.CH*Card.ratioScale/2);
//        }
        for(int index = 0; index < GGameStart.member; index++) {
            for(int i = 0; i < cards.get(index).size; i++){
                if(index == 0)
                    cards.get(index).get(i).flipCard(true);
                else cards.get(index).get(i).flipCard(false);
            }
        }
    }

    private void getCardForPlayer(){
        Card card = new Card(gameMainAtlas, cg.get(0), (int)tiles.get(0).x, (int)tiles.get(0).y);
        cards.get(0).add(card);
        Vector2 result = checkPoint(cards.get(0));
        boolean flag = false, flag1 = false, flag2 = false;
        if(!(result.x == 0 && (result.y == -1 || result.y == 21))) {
            takeCardBtn.setTouchable(Touchable.enabled);
            if(result.y != 0){
                Gdx.app.log("debug", "come hiere!!!! board_197");
                flag1 = true;
                flag2 = true;
            }
        }
        else {
            flag = true;
            if(result.y == -1){
               // flipAllCardsBtn.setVisible(false);
                //goi ham chung tien::::!!!!
                flag1 = false;
            }
            else flag1 = true;
        }
        card.setPosition((cards.get(0).size - 1)*cfg.CW*2/3,0);
        card.setScale(2);
        card.setVisible(false);

        Card cardmove = new Card(gameMainAtlas, group, 0, 0);
        cardmove.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2);
        cardmove.tileDown.setVisible(false);
        final boolean flag1_temp = flag1;
        if(cards.get(0).size == 5 || flag || flag2) {
            if(flag2){
                for(int i = 0; i < GGameStart.member - 1; i++) {
                    if(!idBotOverTurn[i]){
                        GGameMainScene.flipCards.get(i).setVisible(true);
                        GGameMainScene.flipCards.get(i).setTouchable(Touchable.enabled);
                    }

                }
                moveFlipAllCards(flag1_temp);
            }
            else {
                takeCardBtn.setTouchable(Touchable.disabled);
                takeCardBtn.addAction(Actions.sequence(
                    delay(0.5f),
                    moveTo(takeCardBtn.getX(), GMain.screenHeight, 0.3f, Interpolation.elasticOut),
                    GSimpleAction.simpleAction((d, a)->{
                        for(int i = 0; i < GGameStart.member - 1; i++) {
                            if(!idBotOverTurn[i])
                                GGameMainScene.flipCards.get(i).setVisible(true);
                        }
                        moveFlipAllCards(flag1_temp);
                        return true;
                    })
                ));
            }
        }

        tiles.removeIndex(0);
        cardmove.image.addAction(sequence(
            Actions.parallel(
                Actions.scaleTo(2, 2, 0.3f, Interpolation.fastSlow),
                moveTo(cg.get(0).getX() + (cards.get(0).size - 1)*cfg.CW*2/3 - 20, cg.get(0).getY(), 0.3f, Interpolation.fastSlow),
                GSimpleAction.simpleAction((d, a)->{
                    moveGroupCards(0);
                    return true;
                })),
            GSimpleAction.simpleAction((d, a)-> {
                Gdx.app.log("debug", "x-y" + card.image.getX() + "-" + card.image.getY() + " x1-y1: " + cardmove.image.getX() + "-" + cardmove.image.getY());
                card.setVisible(true);
                cardmove.removeCard();
                cards.get(0).get(cards.get(0).size-1).image.setVisible(true);
                cards.get(0).get(cards.get(0).size-1).addListenerClick();
                return true;
            })
        ));
    }

    private void moveFlipAllCards(boolean isNotHidden){
        if(!isNotHidden){
            for(int index = 0; index < cards.size; index++) {
                for(int i = 0; i < cards.get(index).size; i++){
                    if(index == 0)
                        cards.get(index).get(i).flipCard(true);
                    else cards.get(index).get(i).flipCard(false);
                }
            }
        }
        for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
            if(isNotHidden){
                if(!idBotOverTurn[i])
                    GGameMainScene.flipCards.get(i).setVisible(isNotHidden);
            }
            else GGameMainScene.flipCards.get(i).setVisible(isNotHidden);
        }

        flipAllCardsBtn.setVisible(isNotHidden);
        flipAllCardsBtn.addAction(Actions.sequence(
            moveTo(850, cg.get(0).getY() + 50, 0.3f, Interpolation.fastSlow),
            GSimpleAction.simpleAction((d, a)->{
                flipAllCardsBtn.setTouchable(Touchable.enabled);
                if(!isNotHidden)
                    newGameBtn.setVisible(true);
                return true;
            })
        ));
    }

    private void moveGroupCards(int index){
        float ratioScale = Card.ratioScale;
        float padding = 25;
        float ratio = 2;
        if(index == 0){
            ratioScale = 1;
            padding = 30;
            ratio = 3;
        }
        cg.get(index).addAction(
            moveTo(cg.get(index).getX()-1*cfg.CW*ratioScale*1/ratio+ padding, cg.get(index).getY(), 0.2f, Interpolation.linear)
        );
    }

    private void sortCardsAtTheStart(){
        for(int index = 0; index < GGameStart.member; index++) {
            cards_temp.get(index).get(0).image.addAction(rotateTo(0, 0.4f));
            cards_temp.get(index).get(1).image.addAction(rotateTo(0, 0.4f));
        }
    }

    private void alignCardsAtTheStart(){
        for(int index = 0; index < GGameStart.member; index++){
            if(index == 0){
                cards_temp.get(index).get(1).image.addAction(moveBy(cfg.CW - cfg.CW/3, 0, 0.3f));
                cards_temp.get(index).get(1).tileDown.addAction(moveBy(cfg.CW - cfg.CW/3, 0, 0.3f));
            }
            else {
                cards_temp.get(index).get(1).image.addAction(moveBy(cfg.CW*Card.ratioScale - (cfg.CW*Card.ratioScale)/2, 0, 0.3f));
                cards_temp.get(index).get(1).tileDown.addAction(moveBy(cfg.CW*Card.ratioScale - (cfg.CW*Card.ratioScale)/2, 0, 0.3f));
            }
        }

        group.addAction(sequence(
            delay(0.3f),
            GSimpleAction.simpleAction((d, a)-> {
                showCardsAtTheStart();
                return true;
            })
        ));
    }

    private void removeCardTempAtTheStart(){
        for(int i = 0; i < GGameStart.member; i++) {
            cards_temp.get(i).get(0).image.remove();
            cards_temp.get(i).get(0).tileDown.remove();
            cards_temp.get(i).get(1).image.remove();
            cards_temp.get(i).get(1).tileDown.remove();
            cards_temp.get(i).removeIndex(0);
            cards_temp.get(i).removeIndex(0);
        }
    }

    private void showCardsAtTheStart(){
        for(int i = 0; i < GGameStart.member; i++) {
            cards.get(i).get(0).setVisible(true);
            cards.get(i).get(1).setVisible(true);
        }

        group.addAction(sequence(
            delay(0.3f),
            GSimpleAction.simpleAction((d, a)-> {
                removeCardTempAtTheStart();
                checkPointCardAtTheStart();
                //play();
                return true;
            })
        ));
    }

    private void checkPointCardAtTheStart(){
        Vector2 resutl = checkPoint(cards.get(0));
        if(resutl.x == 0 && (resutl.y == 1 || resutl.y == 2)){
            Gdx.app.log("debug_Board_143", "end Game roi ne, goi ham lat bai");
            cards.get(0).get(0).flipCard(true);
            cards.get(0).get(1).flipCard(true);
            for(int i = 1; i < cards.size; i++) {
                cards.get(i).get(0).flipCard(false);
                cards.get(i).get(1).flipCard(false);
            }
            newGameBtn.setVisible(true);
            return;
        }
        for(int i = 1; i < cards.size; i++) {
            resutl = checkPoint(cards.get(i));
            if(resutl.x == 0 && (resutl.y == 1 || resutl.y == 2)){
                count++;
                idBotOverTurn[i-1] = true;
                cards.get(i).get(0).flipCard(false);
                cards.get(i).get(1).flipCard(false);
            }
        }
        play();
    }

    private void initTiles() {
        tiles = new Array<Vector2>();
        for (int index = 0; index < 4; index++) {
            for (int i = 1; i < 14; i++) {
                tiles.add(new Vector2(index, i));
            }
        }
        tiles.shuffle();
        cards = new Array<Array<Card>>();
        cards_temp = new Array<Array<Card>>();
        for(int i = 0; i < GGameStart.member; i++) {
            Array<Card> cardPlayer = new Array<>();
            Array<Card> cardTemp = new Array<>();
            cards.add(cardPlayer);
            cards_temp.add(cardTemp);
        }

    }

    private void initCards(){
        if(turnInitCards == GGameStart.member*2){
            Gdx.app.log("debug", "out of turn");
            sortCardsAtTheStart();
            group.addAction(sequence(
                delay(0.6f),
                GSimpleAction.simpleAction((d, a)-> {
                    alignCardsAtTheStart();
                    return true;
                })
            ));
            Gdx.app.log("debug", "here!!");
            return;
        }
        else {
            Card card = new Card(gameMainAtlas, cg.get(turnInitCards%GGameStart.member),(int)tiles.get(0).x, (int)tiles.get(0).y);
            Card cardmove = new Card(gameMainAtlas, group, 0, 0);
            cardmove.hiddenTileDown();
            cards_temp.get(turnInitCards%GGameStart.member).add(cardmove);
            cardmove.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2);
            tiles.removeIndex(0);
            card.setVisible(false);

            float ratioScale = Card.ratioScale;
            float ratio_temp = 2;
            if(turnInitCards%GGameStart.member == 0){
                card.setScale(2);
                card.addListenerClick();
                cardmove.setScale(2);
                ratioScale = 1;
                ratio_temp = 3;
            }
            if(turnInitCards >= GGameStart.member) {
                card.setPosition(cfg.CW*ratioScale - (cfg.CW*ratioScale)/ratio_temp, card.image.getY());
            }
            cards.get(turnInitCards%GGameStart.member).add(card);
            cardmove.image.setOrigin(Align.center);
            cardmove.tileDown.setOrigin(Align.center);
            cardmove.image.addAction(sequence(
                parallel(
                    moveTo(cg.get(turnInitCards%GGameStart.member).getX(), cg.get(turnInitCards%GGameStart.member).getY(), 0.3f, Interpolation.fastSlow),
                    rotateBy(-1*((int)Math.floor(Math.random()*81 + 100) + 10), 0.3f),
                        GSimpleAction.simpleAction((d, a)->{
                            tics+=1;
                            if(tics == 17 ) {
                                SoundEffect.Play(SoundEffect.renderCards);
                                    tics = 0;
                            }
                            return true;
                        })
                ),
                GSimpleAction.simpleAction((d, a)-> {
                    turnInitCards++;
                    initCards();
                    return true;
                })
            ));
        }
    }

    private void renderPositionCard(){
        for(int index = 0; index < GGameStart.member; index++ ){
            cg.get(index).setPosition(positionCardGroup.get(index).x,positionCardGroup.get(index).y);
        }
    }

    private void playerTurn(){
        Gdx.app.log("debug", "het luot");
        Vector2 result = checkPoint(cards.get(0));
        if(result.x == 0 && result.y == 21){
            flipCards();
            return;
        }
        else if(result.x == 0 && result.y >= 16){
            flipAllCardsBtn.setVisible(true);
            moveFlipAllCards(true);
            for(int i = 0; i < GGameStart.member - 1; i++) {
                GGameMainScene.flipCards.get(i).setVisible(true);
                GGameMainScene.flipCards.get(i).setTouchable(Touchable.enabled);
            }
        }
        GGameMainScene.turnLight.setVisible(false);
        takeCardBtn.setPosition(cg.get(0).getX() - takeCardBtn.getWidth() - 90, GMain.screenHeight);
        takeCardBtn.setVisible(true);
        takeCardBtn.addAction(Actions.sequence(
            moveTo(cg.get(0).getX() - takeCardBtn.getWidth() - 90, cg.get(0).getY() + 50, 0.3f, Interpolation.fastSlow)
        ));
    }

    private void play(){
        if(turnAtTheMoment == 1 && flagTurnLightOfTurn1){
            flagTurnLightOfTurn1 = false;
           turnOnLight();
           return;
        }
        if(turnAtTheMoment == GGameStart.member){
           playerTurn();
            return;
        }

        Vector2 result = checkPoint(cards.get(turnAtTheMoment));
        Gdx.app.log("debug", "point: x-y: " + result.x + "-" + result.y);
        if(result.x == 0 && result.y == 0){
            Gdx.app.log("debug", "board_116: turn: " + turnAtTheMoment + " <16");
            getCard();
        }
        else if(result.x == 0 && result.y == -1){
            Gdx.app.log("debug", "board_121: turn: " + turnAtTheMoment + " >21");
            resultFinal.get(turnAtTheMoment).set(result);
            fitPositionGroupCards();
        }
        else {
            if(isGetCard(percentGetCard(cards.get(turnAtTheMoment)))){
                Gdx.app.log("debug", "board_128: turn: " + turnAtTheMoment + " 16<=x<=21 boc them");
                getCard();
            }
            else {
                Gdx.app.log("debug", "board_128: turn: " + turnAtTheMoment + " 16<=x<=21  ko boc them");
                fitPositionGroupCards();
            }
        }
    }

    private void fitPositionGroupCards(){
        if(cards.get(turnAtTheMoment).size > 2) {
            cg.get(turnAtTheMoment).addAction(sequence(
                moveBy(-1*((cards.get(turnAtTheMoment).size-2)*cfg.CW*Card.ratioScale)/2 + 20, 0, 0.3f),
                GSimpleAction.simpleAction((d, a)-> {
                    turnAtTheMoment++;
                    turnOnLight();
                    return true;
                })
            ));
        }
        else {
            turnAtTheMoment++;
            turnOnLight();
        }
    }

    private void turnOnLight(){
        if(turnAtTheMoment >= GGameStart.member){
            group.addAction(Actions.sequence(
                delay(1),
                GSimpleAction.simpleAction((d, a)->{
                    GGameMainScene.turnLight.setVisible(false);
                    Gdx.app.log("debug", "khong log");
                    play();
                    return true;
                })
            ));
        }
        else {
            GGameMainScene.turnLight.setPosition(cg.get(turnAtTheMoment).getX() - 150, cg.get(turnAtTheMoment).getY() - 200);
            GGameMainScene.turnLight.setScale(0.4f);
            GGameMainScene.turnLight.setOrigin(Align.center);

            GGameMainScene.turnLight.addAction(Actions.scaleTo(1,1,0.4f,Interpolation.fastSlow));
            GGameMainScene.turnLight.setVisible(true);

            group.addAction(Actions.sequence(
                delay(1f),
                GSimpleAction.simpleAction((d, a)->{
                    Gdx.app.log("debug", "co log");
                    play();
                    return true;
                })
            ));
        }
    }

    private void flipCards(){
        Gdx.app.log("debug_121", "flip cards");
        for(Card cardTemp : cards.get(turnAtTheMoment)){
            cardTemp.flipCard(false);
        }
    }

    private int percentGetCard(Array<Card> card){
        int percent = 0;
        Vector2 result = checkPoint(card);
        if((result.x == 0 && result.y == 1) ||(result.x == 0 && result.y == 2) || (result.x == 0 && result.y == -1) || result.x == 1){
            percent = 0; //todo: xì bàn, xì dách, ngũ linh,
        }
        else if(result.x == 0 && result.y == 0){
            percent = 100; // todo: diem duoi 16
        }
        else {
            if(result.y == 16) percent = 60;
            else if(result.y == 17) percent = 40;
            else if(result.y == 18) percent = 20;
            else if(result.y == 19) percent = 10;
            else if(result.y == 20) percent = 5;
            else percent = 0;
        }
        return percent;
    }

    private boolean isGetCard(int percent){
        boolean result = false;
        int percent_temp = (int) Math.floor(Math.random()* 100) + 1;
        if(percent_temp <= percent)
            result = true;
        return result;
    }

    private void getCard(){
        Card card = new Card(gameMainAtlas, cg.get(turnAtTheMoment), (int)tiles.get(0).x, (int)tiles.get(0).y);
        card.setVisible(false);
        card.setPosition((cards.get(turnAtTheMoment).size)*cfg.CW*Card.ratioScale*1/2,0);
        cards.get(turnAtTheMoment).add(card);
        Card cardmove = new Card(gameMainAtlas, group, 0, 0);
        cardmove.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight - cfg.CH*Card.ratioScale)/2);
        tiles.removeIndex(0);
        cardmove.image.addAction(sequence(
            delay(0.8f),
            Actions.parallel(
                    moveTo(cg.get(turnAtTheMoment).getX() + (cards.get(turnAtTheMoment).size - 1)*cfg.CW*Card.ratioScale*1/2 - 15, cg.get(turnAtTheMoment).getY(), 0.3f, Interpolation.fastSlow),
                    GSimpleAction.simpleAction((d, a)->{
                        moveGroupCards(turnAtTheMoment);
                        return true;
                    })
                    ),
            GSimpleAction.simpleAction((d, a)-> {
                card.setVisible(true);
                cardmove.removeCard();
                cards.get(turnAtTheMoment).get(cards.get(turnAtTheMoment).size-1).image.setVisible(true);
                play();
                return true;
            })
        ));
    }

    private void getCardComplete(){

    }

    private void initResutl(){
        resultFinal = new Array<>();
        for(int i = 0; i < GGameStart.member; i++) {
            Vector2 vt = new Vector2(0, 0);
            resultFinal.add(vt);
        }
    }

    private void initCardsGroup(){
        Gdx.app.log("debug", "inittttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttttt");
        cg = new Array<Group>();
        for(int index = 0; index < GGameStart.member; index++){
            Group groupCard = new Group();
            cg.add(groupCard);
            group.addActor(cg.get(index));
        }
        Gdx.app.log("debug", "size group: " + cg.size);
    }

    private void initPositionCardGroup(){
        positionCardGroup = new Array<>();
        switch (GGameStart.member){
            case 2: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 55);
                Vector2 position1 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105 + 10);
                positionCardGroup.add(position0, position1);
                break;
            }
            case 3: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 55);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, 155 + 10);
                Vector2 position2 = new Vector2( 150, 155 + 10);
                positionCardGroup.add(position0, position1, position2);
                break;
            }
            case 4: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 55);
                Vector2 position1 = new Vector2(GMain.screenWidth - 150, GMain.screenHeight/2 + 5 + 10);
                Vector2 position2 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105 + 10);
                Vector2 position3 = new Vector2(100, GMain.screenHeight/2 + 5 + 10);
                positionCardGroup.add(position0, position1, position2, position3);
                break;
            }
            case 5: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 55);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 245 + 10);
                Vector2 position2 = new Vector2(GMain.screenWidth - 250, 155 + 10);
                Vector2 position3 = new Vector2(150, 155 + 10);
                Vector2 position4 = new Vector2(150, GMain.screenHeight - 245 + 10);
                positionCardGroup.add(position0, position1, position2);
                positionCardGroup.add(position3, position4);
                break;
            }
            default: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 55);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 245 + 10);
                Vector2 position2 = new Vector2(GMain.screenWidth - 250, 155 + 10);
                Vector2 position3 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105 + 10);
                Vector2 position4 = new Vector2(150, 155 + 10);
                Vector2 position5 = new Vector2(150, GMain.screenHeight - 245 + 10);
                positionCardGroup.add(position0, position1, position2);
                positionCardGroup.add(position3, position4, position5);
                break;
            }
        }
    }

    private Vector2 checkPoint(Array<Card> card){
        Vector2 result = new Vector2(0, 0);
        if(card.size == 2){ //TODO: 2 LÁ BÀI
            if(card.get(0).values[1] == 1 && card.get(1).values[1] == 1){  //Todo: xì bàn
                result.set(0, 1);
            }
            else if((card.get(0).values[1] == 1 && card.get(1).values[1] >= 10 && card.get(1).values[1] <= 13) || (card.get(1).values[1] == 1 && card.get(0).values[1] >= 10 && card.get(1).values[1] <= 13) ){
                result.set(0, 2); //todo: xì dách
            }
            else if(card.get(0).values[1] == 1 ){ //todo: lá thứ 0 là xì, lá thứ 1 ko phải xì
                int value = getValueCard(card.get(1));
                int total = value + 1;
                if(total < 16){
                    if(total + 9 < 16) {
                        if(total + 10 < 16){
                            result.set(0, 0);
                        }
                        else if(total + 10 < 21)
                            result.set(0, total + 10);
                    }
                    else if(total + 9 < 21) {
                        result.set(0, total + 9);
                    }
                }
                else if(total < 21){
                    result.set(0, total);
                }
            }
            else if(card.get(1).values[1] == 1){ //Todo: lá thứ 0 ko phải là xì và lá thứ 1 là xì
                int value = getValueCard(card.get(0));
                int total = value + 1;
                if(total < 16){
                    if(total + 9 < 16) {
                        if(total + 10 < 16)
                            result.set(0, 0);
                        else if(total + 10 < 21)
                            result.set(0, total + 10);
                    }
                    else if(total + 9 < 21) {
                        result.set(0, total + 9);
                    }
                }
                else if(total < 21){
                    result.set(0, total);
                }
            }
            else { //Todo: 2 lá không phải là xì
                int dem = 0;
                for(int i = 0; i < card.size; i++) {
                    if(card.get(i).values[1] >= 10)
                        dem+=10;
                    else dem+=card.get(i).values[1];
                }
                if(dem > 21) result.set(0, -1);
                else if(dem < 16) result.set(0, 0);
                else result.set(0, dem);
            }
        }
        else if(card.size == 3 && (card.get(0).values[1] == 1 || card.get(1).values[1] == 1 || card.get(2).values[1] == 1)){ //todo: 3 lá và có ít nhất 1 lá xì
            int count1point = 0;
            for(Card cardtemp : card){
                if(cardtemp.values[1] == 1)
                    count1point++;
            }
            if(count1point == 1) {//todo: 1 la xi va 2 la khong phai la xi
                int value0 = getValueCard(card.get(0));
                int value1 = getValueCard(card.get(1));
                int value2 = getValueCard(card.get(2));
                int total = value0 + value1 + value2;
                if((total + 10) > 21){
                    if((total + 9) > 21) {
                        if(total > 21){
                            result.set(0, -1);
                        }
                        else if(total >= 16)
                            result.set(0, total);
                        else result.set(0, 0);
                    }
                    else if(total + 9 >= 16)
                        result.set(0, total + 9);
                    else result.set(0, 0);
                }
                else if(total + 10 >= 16)
                    result.set(0, total + 10);
                else result.set(0, 0);
            }
            else{ // todo: 2 la xi va 1 la khong phai
                int value0 = getValueCard(card.get(0));
                int value1 = getValueCard(card.get(1));
                int value2 = getValueCard(card.get(2));
                int total = value0 + value1 + value2;
                if(total > 21){
                    result.set(0, -1);
                }
                else if(total >= 16) {
                    result.set(0, total);
                }
                else result.set(0, 0);
            }
        }
        else if(card.size == 5){ // TODO: 5 LÁ BÀI
            int dem = 0;
            for(int i = 0; i < card.size; i++){
                if(card.get(i).values[1] >= 10)
                    dem+=10;
                else dem += card.get(i).values[1];
            }
            if(dem > 21) {
                result.set(0, -1);
            }
            else result.set(1, dem);
        }
        else { // TOdO: 3-4 LÁ BÀI
            int dem = 0;
            for(int i = 0; i < card.size; i++){
                if(card.get(i).values[1] >= 10)
                    dem += 10;
                else dem += card.get(i).values[1];
            }
            if(dem < 16){
                result.set(0, 0);
            }
            else if(dem > 21) {
                result.set(0, -1);
            }
            else result.set(0, dem);
        }

        return result;
    }

    private int getValueCard(Card card){//todo: truyen vao la bai, tra ve gia tri la bai
        int value = 0;
        value = card.values[1];
        if(value >= 10)
            value = 10;
        return value;
    }

    private int checkCardsWidthPlayer(Array<Card> cardsBot){//todo: so sanh bai cua bot voi bai cua nguoi choi
        int result = 0;
        Vector2 result1 = checkPoint(cardsBot);
        Vector2 result2 = checkPoint(cards.get(0));
        if(result2.x == 0 && result2.y == 1){
            if(result1.x == 0 && result1.y == 1){
                result = 0; // todo: hoa
            }
            else result = 1; // todo: cai thang
        }
        else if(result2.x == 0 && result2.y == 2) {
            if(result1.x == 0 && result1.y == 1)
                result = -1; //todo: con thang
            else if(result2.x == 0 && result2.y == 2)
                result = 0;
            else result = -1;
        }
        else if(result2.x == 1){
            if(result1.x == 1) {
                if(result1.y < result2.y)
                    result = -1;
                else result = 1;
            }
            else result = 1;
        }
        else if(result2.x == 0 && result2.y >= 16 && result2.y <= 21){
            if((result1.x == 0 && (result1.y == 1 || result1.y == 2)) || result1.x == 1)
                result = -1;
            else if(result1.x == 0 && result1.y >= 16 && result1.y <= 21){
                if(result1.y > result2.y)
                    result = -1;
                else if(result1.y == result2.y) result = 0;
                else result = 1;
            }
            else if(result1.x == 0 && result1.y == -1) {
                result = 1;
            }
        }
        else if(result2.x == 0 && result2.y == -1){
            if((result1.x == 0 && (result1.y == 1 || result1.y == 2 || result1.y >= 16)) || result1.x == 1 )
                result = -1;
            else result = 0;
        }
        else if(result2.x == 0 && result2.y == 0){
            if(result1.x == 0 && result1.y <= 0)
                result = 0;
            else result = -1;
        }
        return result;
    }

    private void dispose(){
        tiles.clear();
        count = 0;
        for(Group group : cg){
            group.clearChildren();
        }

        positionCardGroup.clear();
        resultFinal.clear();
    }
}
