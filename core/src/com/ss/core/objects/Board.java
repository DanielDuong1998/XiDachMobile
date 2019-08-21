package com.ss.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Group;
import static com.badlogic.gdx.math.Interpolation.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.core.action.exAction.GShakeAction;
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
    int dem1 = 0;

    //TEST
    int dem = 0;

    //bOT LAM CAI
    public int idBoss = 0;
    Image imageBoss;
    Array<Image> chipPocker;
    Group groupPocker;
    Array<Pocker> pockersPlayer;
    Array<Pocker> pockersBetPlayer;
    Array<String> nameChips;
    Image doneBtn;
    Image blockCardBtn;
    boolean isClickedChip = false;
    boolean isPassTurnPlayer = false;
    int turnBossCheck = 0;
    int turnBotModeOutside = 0;
    boolean[] idBotOverTurn1;
    boolean isTurnBoss = false;
    Array<Image> statusPlayer;
    boolean isPlayerWin = false;

    Group groupPockerPlayer;
    Group groupPockerPlayerTemp;

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

        if(GGameStart.mode == 0){ //todo: nguoi choi lam cai
            startGame0();
        }
        else {//todo: bot lam cai
            startGame1();
        }
    }

    private void replay(){
        if(GGameStart.mode == 1)
            GGameStart.idBoss = idBoss;
        dispose();
        game.replay();
    }

    private void startGame0(){
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
        slideButtonEffect = new EffectSlide("slide", startGameBtn.getX() + 130, startGameBtn.getY() + 45, group);
        group.addActor(slideButtonEffect);
        startGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            GGameMainScene.cardDown.setVisible(true);
            SoundEffect.Play(SoundEffect.buttonStartGame);
            startGameBtn.setVisible(false);
            slideButtonEffect.disposeEcffect();

            Tweens.setTimeout(group, 0.3f, ()->{
                SoundEffect.Play(SoundEffect.chipPockers);
                bet();
            });

            //am thanh
            Tweens.setTimeout(group, 0.6f, ()->{
                startRenderCards();
            });
            }
        });

        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            newGameBtn.setTouchable(Touchable.disabled);
            SoundEffect.Play(SoundEffect.buttonNewGame);
            newGameBtn.setVisible(false);
            replay();
            }
        });
    }

    private void startGame1(){
        findBoss();
        initChipPocker();
    }

    private void addListenerBtnMode1(){
        takeCardBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            takeCardBtn.setTouchable(Touchable.disabled);
            if(GGameMainScene.effect.size == 2){
                GGameMainScene.disposeParticleCardsPlayer();
            }
            if(cards.get(0).size == 5){
                return;
            }
            SoundEffect.Play(SoundEffect.button);
            takeCardBtn.setOrigin(Align.center);
            takeCardBtn.setScale(0.8f);
            Tweens.setTimeout(group,0.05f,()->{
                takeCardBtn.addAction(Actions.scaleTo(1,1,0.05f,linear));
                getCardForPlayer();
            });
            }
        });

        blockCardBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            SoundEffect.Play(SoundEffect.buttonFlipAll);
            blockCardBtn.setTouchable(Touchable.disabled);
            takeCardBtn.setTouchable(Touchable.disabled);
            blockCardBtn.setOrigin(Align.center);
            blockCardBtn.setScale(0.8f);
            blockCardBtn.addAction(sequence(
                scaleTo(1, 1, 0.05f, linear),
                delay(0.2f),
                moveTo(blockCardBtn.getX(), blockCardBtn.getY() + 200, 0.3f, linear),
                GSimpleAction.simpleAction((d, a)->{
                    moveBtn();
                    return true;
                })

            ));
            }
        });
    }

    private void moveBtn(){
        takeCardBtn.addAction(sequence(
            moveTo(takeCardBtn.getX(), GMain.screenHeight, 0.3f, linear),
            GSimpleAction.simpleAction((d, a)->{
                blockBtnClicked();
                return true;
            })
        ));
    }

    private void blockBtnClicked(){
        turnAtTheMoment++;
        turnAtTheMoment = turnAtTheMoment%GGameStart.member;
        Gdx.app.log("debug", "turn: 200: " + turnAtTheMoment);
        isPassTurnPlayer = true;
        play();
        return;
    }

    private void initChipPocker(){
        chipPocker = new Array<>();
        nameChips  = new Array<>();
        pockersPlayer = new Array<>();
        groupPocker = new Group();
        group.addActor(groupPocker);
        doneBtn = GUI.createImage(gameMainAtlas, "done");
        blockCardBtn = GUI.createImage(gameMainAtlas, "blockCard");
        groupPocker.addActor(doneBtn);
        group.addActor(blockCardBtn);
        blockCardBtn.setPosition(850, GMain.screenHeight);
        doneBtn.setSize(doneBtn.getWidth()*1.3f, doneBtn.getHeight()*1.3f);
        doneBtn.setPosition(375, -250);
        newGameBtn = GUI.createImage(gameMainAtlas, "newGame");
        group.addActor(newGameBtn);
        newGameBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);
        newGameBtn.setOrigin(Align.center);
        newGameBtn.setVisible(false);

        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            SoundEffect.Play(SoundEffect.buttonNewGame);
            newGameBtn.setTouchable(Touchable.disabled);
            newGameBtn.setAlign(Align.center);
            newGameBtn.addAction(sequence(
                scaleBy(-0.5f, -0.5f, 0.1f, circleIn),
                scaleBy(0.5f, 0.5f, 0.1f, circleOut),
                GSimpleAction.simpleAction((d, a)->{
                    newGameBtn.setVisible(false);
                    replay();
                    return true;
                })

            ));
            }
        });

        statusPlayer = new Array<>();
        Image win = GUI.createImage(gameMainAtlas, "win");
        Image lose = GUI.createImage(gameMainAtlas, "lose");
        Image tie = GUI.createImage(gameMainAtlas, "tie");
        group.addActor(win);
        group.addActor(lose);
        group.addActor(tie);
        statusPlayer.add(win, lose, tie);
        for(Image image : statusPlayer){
            image.setPosition(GMain.screenWidth/2 - 400, GMain.screenHeight - 100);
            image.setVisible(false);
        }

        Pocker pocker0 = new Pocker(gameMainAtlas, groupPocker, 10000);
        Pocker pocker1 = new Pocker(gameMainAtlas, groupPocker, 20000);
        Pocker pocker2 = new Pocker(gameMainAtlas, groupPocker, 50000);
        Pocker pocker3 = new Pocker(gameMainAtlas, groupPocker, 100000);
        Pocker pocker4 = new Pocker(gameMainAtlas, groupPocker, 200000);
        Pocker pocker5 = new Pocker(gameMainAtlas, groupPocker, 500000);
        pockersPlayer.add(pocker0, pocker1, pocker2, pocker3);
        pockersPlayer.add(pocker4, pocker5);

        idBotOverTurn1 = new boolean[GGameStart.member];
        for(int i = 0; i < GGameStart.member; i++) {
            if(i == idBoss + 1){
                idBotOverTurn1[i] = true;
            }
            else idBotOverTurn1[i] = false;
        }

        doneBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            doneBtn.setTouchable(Touchable.disabled);
            if(game.frameMoney.get(0).money <= 0){
                doneBtn.setTouchable(Touchable.enabled);
                return;
            }
            SoundEffect.Play(SoundEffect.button);
            Tweens.setTimeout(group, 0.85f, ()->{
                SoundEffect.Play(SoundEffect.chipPockers);
            });

            doneBtn.setOrigin(Align.center);
            doneBtn.addAction(sequence(
                scaleBy(-0.5f, -0.5f, 0.1f),
                scaleBy(0.5f, 0.5f, 0.1f),
                    delay(0.1f),
                    GSimpleAction.simpleAction((d, a)->{
                    hiddenGroupPocker();
                    return true;
                })
            ));
            }
        });

        groupPockerPlayer = new Group();
        group.addActor(groupPockerPlayer);
        groupPockerPlayerTemp = new Group();
        group.addActor(groupPockerPlayerTemp);

        for(int i = 0; i < 6; i++) {
            pockersPlayer.get(i).imagePocker.setSize(pockersPlayer.get(i).imagePocker.getWidth()*4.3f,pockersPlayer.get(i).imagePocker.getHeight()*4.3f );
            if(i != 0){
                pockersPlayer.get(i).imagePocker.setPosition((pockersPlayer.get(0).imagePocker.getWidth()-10)*i, 0);
            }
            final int itemp = i;
            pockersPlayer.get(i).imagePocker.addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                pockersPlayer.get(itemp).imagePocker.setTouchable(Touchable.disabled);
                if(GGameMainScene.moneyPlayer < pockersPlayer.get(itemp).value){
                    pockersPlayer.get(itemp).imagePocker.addAction(GShakeAction.shakeAction(0.3f, 5f, linear));
                    Tweens.setTimeout(group, 0.4f, ()->{
                        pockersPlayer.get(itemp).imagePocker.setTouchable(Touchable.enabled);
                    });
                    SoundEffect.Play(SoundEffect.shake);
                    return;
                }
                SoundEffect.Play(SoundEffect.tick);
                pockersPlayer.get(itemp).imagePocker.setOrigin(Align.center);
                pockersPlayer.get(itemp).imagePocker.addAction(sequence(
                    scaleBy(-0.5f, -0.5f, 0.1f),
                    scaleBy(0.5f, 0.5f, 0.1f),
                    GSimpleAction.simpleAction((d, a)->{
                        chipClick(itemp);
                        return true;
                    })
                ));
                }
            });
        }
        groupPocker.setPosition((GMain.screenWidth-(pockersPlayer.get(0).imagePocker.getWidth()-10)*6)/2,GMain.screenHeight - pockersPlayer.get(0).imagePocker.getHeight() - 70, Align.center);
        groupPockerPlayer.setPosition(game.frameMoney.get(0).image.getX(), game.frameMoney.get(0).image.getY(), Align.center);
        groupPockerPlayerTemp.setPosition(game.positionGroup.get(idBoss).x, game.positionGroup.get(idBoss).y);
        groupPockerPlayerTemp.setVisible(false);
    }

    private void hiddenGroupPocker(){
        groupPocker.addAction(sequence(
            moveBy(0, 500, 0.5f, fastSlow),
            GSimpleAction.simpleAction((d, a)->{
                Gdx.app.log("debug", "done");
                groupPocker.clearChildren();
                bet();
                return true;
            })
        ));
    }

    private void findBoss(){
        if(GGameStart.idBoss == -1)
            idBoss = (int) Math.floor(Math.random()*(GGameStart.member-1));
        else idBoss = GGameStart.idBoss;

        imageBoss = GUI.createImage(gameMainAtlas, "boss");
        GGameMainScene.groupsBot.get(idBoss).addActor(imageBoss);
        imageBoss.setPosition(imageBoss.getX(), imageBoss.getY() - 25);
        pockersBetPlayer = new Array<>();
        //
    }

    private void chipClick(int index){
        Pocker pocker = new Pocker(gameMainAtlas, groupPockerPlayer, pockersPlayer.get(index).value);
        Pocker pocker_temp = new Pocker(gameMainAtlas, groupPockerPlayerTemp, pockersPlayer.get(index).value);
        Gdx.app.log("debug", "x-y pokcer: " + groupPockerPlayerTemp.getX() + "-" + groupPockerPlayerTemp.getY());

        pockersBetPlayer.add(pocker);
        groupPockerPlayerTemp.addActor(pocker_temp.imagePocker);
        pocker.imagePocker.setPosition(pockersPlayer.get(0).imagePocker.getWidth()*index - 500, 50);
        pocker_temp.imagePocker.setPosition((float) Math.floor(Math.random()*70), (float)Math.floor(Math.random()*30));

        game.subMoneyPlayer(pockersPlayer.get(index).value);
        game.frameMoney.get(0).addMoney(pockersPlayer.get(index).value);
        Gdx.app.log("debug", "herer!!!: " + pockersPlayer.get(index).value);
        pocker.imagePocker.addAction(sequence(
            moveTo(game.frameMoney.get(0).image.getX() + (float)Math.floor(Math.random()*70) - 580,game.frameMoney.get(0).image.getY() - 470 - (float)Math.floor(Math.random()*30), 0.2f, fastSlow),
            GSimpleAction.simpleAction((d, a)->{
                pockersPlayer.get(index).imagePocker.setTouchable(Touchable.enabled);
                return true;
            })
        ));
    }

    private void addListenerFlipCardBtn() {
        for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
            final int itemp = i;
            GGameMainScene.flipCards.get(i).addListener(new ClickListener(){
                @Override
                public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                SoundEffect.Play(SoundEffect.buttonFlipCards);
                count++;
                idBotOverTurn[itemp] = true;
                GGameMainScene.flipCards.get(itemp).setTouchable(Touchable.disabled);
                int temp = checkCardsWidthPlayer(cards.get(itemp + 1), -1);
                Gdx.app.log("debug", "so: " + temp);
                if(temp == 1){
                    game.addMoneyPlayer(game.frameMoney.get(itemp+1).money);
                    game.frameMoney.get(0).addMoney(game.frameMoney.get(itemp+1).money);
                    Tweens.setTimeout(group, 0.5f, ()->{
                        game.groupPocker.get(itemp).addAction(moveTo(game.frameMoney.get(0).image.getX(),game.frameMoney.get(0).image.getY(), 0.4f, fastSlow ));
                    });

                }
                else if(temp == -1) {
                    game.subMoneyPlayer(game.frameMoney.get(itemp+1).money);
                    game.bots.get(itemp).addMoney(game.frameMoney.get(itemp+1).money*2);
                    game.frameMoney.get(0).subMoney(game.frameMoney.get(itemp+1).money);
                    Tweens.setTimeout(group, 0.5f, ()->{
                        game.groupPockerTemp.get(itemp).setVisible(true);
                        game.groupPockerTemp.get(itemp).addAction(moveTo(game.groupPocker.get(itemp).getX() + 50,game.groupPocker.get(itemp).getY(), 0.5f, fastSlow));
                    });
                }
                else if(temp == 0){
                    game.bots.get(itemp).addMoney(game.frameMoney.get(itemp+1).money);
                }
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

        Tweens.setTimeout(group, 0.4f, ()->{
            SoundEffect.Play(SoundEffect.flipCards);
            showPointTxt(index);
        });
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

    private void showPointTxt(int index){
        String str = checkPointTxt(index);
        GGameMainScene.pointsTxt.get(index).setText(str);
        GGameMainScene.pointsTxt.get(index).setVisible(true);
    }

    private void checkFlipCardBtn(int index){
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
                        //check tien bot va tien player
                        checkMoneyBotPlayer();
                        int soundTemp = game.frameMoney.get(0).money > 0 ? SoundEffect.winSound : SoundEffect.loseSound;
                        Tweens.setTimeout(group, 0.3f,()->{
                            newGameBtn.setVisible(true);
                            SoundEffect.Play(soundTemp);
                        });
                        return true;
                    })
                )
            );
        }
    }

    private void checkMoneyBotPlayer(){
        if(GGameMainScene.moneyPlayer <= 0){
            Gdx.app.log("debug", "GameOver, watch video to get more money!!!");
        }
        else {
            for(int i = 0; i < GGameStart.member - 1; i++){
               final int itemp = i;
                if(game.bots.get(i).money <= 100000){
                    game.groupsBot.get(i).addAction(Actions.sequence(
                        moveBy(1000, 0, 0.5f, fastSlow),
                        GSimpleAction.simpleAction((d, a)->{
                            Gdx.app.log("debug", "hehe");
                            game.newBots(itemp);
                            moveBots(itemp);
                            return true;
                        })
                    ));
                }
            }
        }
    }

    private void moveBots(int index){
        game.groupsBot.get(index).addAction(sequence(
            moveBy(-1000, 0, 0.5f, fastSlow),
            GSimpleAction.simpleAction((d, a)->{
                return true;
            })
        ));
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

            if(GGameMainScene.effect.size == 2){
                GGameMainScene.disposeParticleCardsPlayer();
            }

            if(cards.get(0).size == 5){
                return;
            }
            SoundEffect.Play(SoundEffect.button);
            takeCardBtn.setOrigin(Align.center);
            takeCardBtn.setScale(0.8f);
            Tweens.setTimeout(group,0.05f,()->{
                takeCardBtn.addAction(Actions.scaleTo(1,1,0.05f,Interpolation.linear));
                getCardForPlayer();
            });
            }
        });

        flipAllCardsBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            if(GGameMainScene.effect.size == 2){
                GGameMainScene.disposeParticleCardsPlayer();
            }
            SoundEffect.Play(SoundEffect.buttonFlipAll);
            flipAllCardsBtn.setTouchable(Touchable.disabled);
            flipAllCardsBtn.setOrigin(Align.center);
            Tweens.setTimeout(group, 0.4f, ()->{
                SoundEffect.Play(SoundEffect.flipAllCards);
                for(int i = 0; i < GGameStart.member; i++) {
                    showPointTxt(i);
                }
            });
            flipAllCardsBtn.addAction(Actions.sequence(
                Actions.sequence(
                    scaleBy(-0.2f, -0.2f, 0.2f, Interpolation.circleIn),
                    scaleBy(0.2f, 0.2f, 0.2f, Interpolation.circleOut)
                ),
                GSimpleAction.simpleAction((d, a)->{
                    for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
                        GGameMainScene.flipCards.get(i).setVisible(false);
                        if(!idBotOverTurn[i]) {
                            int temp = checkCardsWidthPlayer(cards.get(i + 1), -1);
                            if(temp == 1){
                                game.addMoneyPlayer(game.frameMoney.get(i + 1).money);
                                game.frameMoney.get(0).addMoney(game.frameMoney.get(i+1).money);
                                final int itemp = i;
                                Tweens.setTimeout(group, 0.5f, ()->{
                                    game.groupPocker.get(itemp).addAction(moveTo(game.frameMoney.get(0).image.getX(),game.frameMoney.get(0).image.getY(), 0.4f, fastSlow ));
                                });
                            }
                            else if(temp == -1){
                                game.bots.get(i).addMoney(game.frameMoney.get(i + 1).money*2);
                                game.subMoneyPlayer(game.frameMoney.get(i + 1).money);
                                game.frameMoney.get(0).subMoney(game.frameMoney.get(i+1).money);

                                final int itemp = i;
                                Tweens.setTimeout(group, 0.5f, ()->{
                                    game.groupPockerTemp.get(itemp).setVisible(true);
                                    game.groupPockerTemp.get(itemp).addAction(moveTo(game.groupPocker.get(itemp).getX() + 50,game.groupPocker.get(itemp).getY(), 0.5f, fastSlow));
                                });

                            }
                            else if(temp == 0){
                                game.bots.get(i).addMoney(game.frameMoney.get(i + 1).money);
                                Gdx.app.log("debug", "vao roi ne:))");
                            }
                        }
                    }
                    takeCardBtn.setVisible(false);
                    flipAllCardsBtn.setVisible(false);
                    flipAllCards();
                    //check tien bot va player
                    checkMoneyBotPlayer();
                    Tweens.setTimeout(group, 0.3f, ()->{
                        newGameBtn.setVisible(true);
                    });
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
        int soundTemp = game.frameMoney.get(0).money > 0 ? SoundEffect.winSound : SoundEffect.loseSound;
        Tweens.setTimeout(group, 0.5f,()->{
            SoundEffect.Play(soundTemp);
        });
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
        if(!((result.x == 0 && (result.y == -1 || result.y == 21)) || cards.get(0).size == 5)) {
            if(result.y != 0){
                flag1 = true;
                flag2 = true;
            }
        }
        else {
            takeCardBtn.setTouchable(Touchable.disabled);
            flag = true;
            if(result.y == -1){
                if(GGameStart.mode == 0){
                    for(int i = 1; i < GGameStart.member; i++) {
                        if(!idBotOverTurn[i-1]){
                            Vector2 vt = checkPoint(cards.get(i));
                            if(vt.y != -1){
                                game.bots.get(i-1).addMoney(game.frameMoney.get(i).money*2);
                                game.subMoneyPlayer(game.frameMoney.get(i).money);
                                game.frameMoney.get(0).subMoney(game.frameMoney.get(i).money);

                                final int itemp = i - 1;
                                Tweens.setTimeout(group, 0.5f, ()->{
                                    game.groupPockerTemp.get(itemp).setVisible(true);
                                    game.groupPockerTemp.get(itemp).addAction(moveTo(game.groupPocker.get(itemp).getX() + 50,game.groupPocker.get(itemp).getY(), 0.5f, fastSlow));
                                });

                            }
                            else {
                                game.bots.get(i-1).addMoney(game.frameMoney.get(i).money);
                            }
                        }
                    }
                    flag1 = false;
                }
                else{

                }
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
        SoundEffect.Play(SoundEffect.takeCard);
        if(cards.get(0).size == 5 || flag || flag2) {
            if(flag2){//todo: bai du diem
                if(GGameStart.mode == 0){
                    for(int i = 0; i < GGameStart.member - 1; i++) {
                        if(!idBotOverTurn[i]){
                            GGameMainScene.flipCards.get(i).setVisible(true);
                            GGameMainScene.flipCards.get(i).setTouchable(Touchable.enabled);
                        }

                    }
                }
                moveFlipAllCards(flag1_temp);
            }
            else {
                takeCardBtn.setTouchable(Touchable.disabled);
                if(GGameStart.mode == 0){ //todo: bai bi quat va 21 diem
                    takeCardBtn.addAction(Actions.sequence(
                        delay(0.5f),
                        moveTo(takeCardBtn.getX(), GMain.screenHeight, 0.3f, Interpolation.elasticOut),
                        GSimpleAction.simpleAction((d, a)->{
                            for(int i = 0; i < GGameStart.member - 1; i++) {
                                if(!idBotOverTurn[i])
                                    GGameMainScene.flipCards.get(i).setVisible(true);
                            }
                            int soundTemp = game.frameMoney.get(0).money > 0 ? SoundEffect.winSound : SoundEffect.loseSound;
                            Tweens.setTimeout(group, 0.5f,()->{
                                SoundEffect.Play(soundTemp);
                            });
                            moveFlipAllCards(flag1_temp);
                            return true;
                        })
                    ));
                }
                else {
                    takeCardBtn.addAction(sequence(
                        delay(0.5f),
                        parallel(
                            moveTo(takeCardBtn.getX(), GMain.screenHeight, 0.3f, fastSlow),
                            GSimpleAction.simpleAction((d, a)->{
                                blockCardBtn.addAction(sequence(
                                    moveTo(blockCardBtn.getX(), cg.get(0).getY() + 50, 0.3f),
                                    GSimpleAction.simpleAction((d1, a1)->{
                                        return true;
                                    })
                                ));
                                return true;
                            })
                        )
                    ));
                }
            }
        }

        tiles.removeIndex(0);
        cardmove.image.addAction(sequence(
            Actions.parallel(
                Actions.scaleTo(2, 2, 0.3f, fastSlow),
                moveTo(cg.get(0).getX() + (cards.get(0).size - 1)*cfg.CW*2/3 - 20, cg.get(0).getY(), 0.3f, fastSlow),
                GSimpleAction.simpleAction((d, a)->{
                    moveGroupCards(0);
                    return true;
                })),
            GSimpleAction.simpleAction((d, a)-> {
                Vector2 vt = checkPoint(cards.get(0));
                if(vt.y != -1 && vt.y != 21 && vt.x != 1)
                    takeCardBtn.setTouchable(Touchable.enabled);
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
            if(GGameStart.mode == 0){
                SoundEffect.Play(SoundEffect.flipAllCards);
                for(int index = 0; index < cards.size; index++) {
                    showPointTxt(index);
                    for(int i = 0; i < cards.get(index).size; i++){
                        if(index == 0)
                            cards.get(index).get(i).flipCard(true);
                        else cards.get(index).get(i).flipCard(false);
                    }
                }
            }
           else {

            }
        }

        if(GGameStart.mode == 0){
            for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
                if(isNotHidden){
                    if(!idBotOverTurn[i])
                        GGameMainScene.flipCards.get(i).setVisible(isNotHidden);
                }
                else GGameMainScene.flipCards.get(i).setVisible(isNotHidden);
            }
        }

        if(GGameStart.mode == 0){
            flipAllCardsBtn.setVisible(isNotHidden);
            flipAllCardsBtn.addAction(Actions.sequence(
                moveTo(850, cg.get(0).getY() + 50, 0.3f, fastSlow),
                GSimpleAction.simpleAction((d, a)->{
                    flipAllCardsBtn.setTouchable(Touchable.enabled);
                    if(!isNotHidden) {
                        //check tien bot va player
                        checkMoneyBotPlayer();
                        Tweens.setTimeout(group, 0.3f, ()->{
                            newGameBtn.setVisible(true);
                        });
                    }
                    return true;
                })
            ));
        }
        else {
            blockCardBtn.setVisible(true);
            blockCardBtn.addAction(sequence(
                moveTo(850, cg.get(0).getY() + 50, 0.3f, fastSlow),
                GSimpleAction.simpleAction((d, a)->{
                    return true;
                })
            ));
        }
    }

    private void moveGroupCards(int index){
        float ratioScale = Card.ratioScale;
        float padding = 25;
        float ratio = 2;
        if(index == 0){
            ratioScale = 1;
            padding = 35;
            ratio = 3;
        }
        cg.get(index).addAction(
            moveTo(cg.get(index).getX()-1*cfg.CW*ratioScale*1/ratio+ padding, cg.get(index).getY(), 0.1f, Interpolation.linear)
        );
    }

    private void sortCardsAtTheStart(){
        SoundEffect.Play(SoundEffect.rotateCards);
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
                game.particleCardsPlayer();
                checkPointCardAtTheStart();
                //play();
                return true;
            })
        ));
    }

    private void checkPointCards1(){ //todo: mode bot lam cai, nguoi choi duoc xi lat hoac xi ban
        SoundEffect.Play(SoundEffect.flipAllCards);
        for(int index = 0; index < cards.size; index++) {
            for(Card card : cards.get(index)){
                if(index == 0){
                    card.flipCard(true);
                    showPointTxt(0);
                }
                else {
                    card.flipCard(false);
                    showPointTxt(index);
                }
            }
        }

        int temp;

        for(int i = 0; i < cards.size; i++) {
            if(i != idBoss + 1){
                temp = checkCardsWidthPlayer(cards.get(i), idBoss);
                Gdx.app.log("debug", "temp : " + temp + " i: " + i);
                if(temp == 1){
                    if(i != 0){
                        final int itemp = i;
                        game.groupPocker.get(i - 1).addAction(sequence(
                            moveTo(game.positionGroupPocker.get(idBoss).x,game.positionGroupPocker.get(idBoss).y, 0.5f, fastSlow),
                            GSimpleAction.simpleAction((d, a)->{
                                game.bots.get(itemp - 1).lose.setVisible(true);
                                game.bots.get(idBoss).addMoney(game.frameMoney.get(itemp).money);
                                game.frameMoney.get(idBoss + 1).addMoney(game.frameMoney.get(itemp).money);
                                return true;
                            })
                        ));
                    }
                    else {
                        groupPockerPlayer.addAction(sequence(
                            moveTo(game.positionGroupPocker.get(idBoss).x,game.positionGroupPocker.get(idBoss).y, 0.5f, fastSlow),
                            GSimpleAction.simpleAction((d, a)->{
                                statusPlayer.get(1).setVisible(true);
                                isPlayerWin = false;
                                return true;
                            })
                        ));
                    }
                }
                else if(temp == -1){
                    if(i!=0){
                        final int itemp = i;
                        game.groupPockerTemp.get(itemp - 1).setVisible(true);
                        game.groupPockerTemp.get(itemp - 1).addAction(sequence(
                            moveTo(game.positionGroupPocker.get(itemp-1).x + 50,game.positionGroupPocker.get(itemp-1).y, 0.5f, fastSlow ),
                            GSimpleAction.simpleAction((d, a)->{
                                game.bots.get(itemp - 1).win.setVisible(true);
                                game.bots.get(idBoss).subMoney(game.frameMoney.get(itemp).money);
                                game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(itemp).money);
                                game.bots.get(itemp - 1).addMoney(game.frameMoney.get(itemp).money*2);
                                return true;
                            })
                        ));
                    }
                    else {
                        final int itemp = i;
                        groupPockerPlayerTemp.addAction(sequence(
                            moveTo(groupPockerPlayer.getX(),groupPockerPlayer.getY(), 0.5f, fastSlow ),
                            GSimpleAction.simpleAction((d, a)->{
                                statusPlayer.get(0).setVisible(true);
                                isPlayerWin = true;
                                game.bots.get(idBoss).subMoney(game.frameMoney.get(itemp).money);
                                game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(itemp).money);
                                game.addMoneyPlayer(game.frameMoney.get(itemp).money*2);
                                return true;
                            })
                        ));
                    }
                }
                else {
                    if(i!= 0){
                        game.bots.get(i-1).tie.setVisible(true);
                        game.bots.get(i - 1).addMoney(game.frameMoney.get(i).money);
                    }
                    else {
                        statusPlayer.get(2).setVisible(true);
                        isPlayerWin = false;
                        game.addMoneyPlayer(game.frameMoney.get(i).money);
                    }
                }
            }
        }
    }

    private void checkPointCards2(){ //todo: mode bot lam cai, bot duoc xi lat hoac xi ban ma khong phai cai
        for(int i = 0; i < cards.size; i++) {
            if(i != idBoss + 1){
                Vector2 result = checkPoint(cards.get(i));
                if(result.y == 1 || result.y == 2){
                    if(i == 0){
                        if(GGameMainScene.effect.size == 2){
                            GGameMainScene.disposeParticleCardsPlayer();
                        }
                    }
                    showPointTxt(i);
                    SoundEffect.Play(SoundEffect.xiDachSound);
                    idBotOverTurn1[i] = true;
                    for(Card card : cards.get(i)){
                        if(i == 0)
                            card.flipCard(true);
                        else card.flipCard(false);
                    }
                    if(i != 0){
                        final int itemp = i;
                        game.groupPockerTemp.get(i - 1).setVisible(true);
                        game.groupPockerTemp.get(i - 1).addAction(sequence(
                            moveTo(game.positionGroupPocker.get(itemp - 1).x, game.positionGroupPocker.get(itemp - 1).y, 0.5f, fastSlow),
                            GSimpleAction.simpleAction((d, a)->{
                                game.bots.get(itemp - 1).win.setVisible(true);
                                game.bots.get(itemp - 1).addMoney(game.frameMoney.get(itemp).money*2);
                                game.bots.get(idBoss).subMoney(game.frameMoney.get(itemp).money);
                                game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(itemp).money);
                                return true;
                            })
                        ));
                    }
                    else {
                        final int itemp = i;
                        isPlayerWin = true;
                        groupPockerPlayerTemp.setVisible(true);
                        groupPockerPlayerTemp.addAction(sequence(
                            moveTo(groupPockerPlayer.getX(), groupPockerPlayer.getY(), 0.5f, fastSlow),
                            GSimpleAction.simpleAction((d, a)->{
                                statusPlayer.get(0).setVisible(true);
                                isPlayerWin = true;
                                game.addMoneyPlayer(game.frameMoney.get(itemp).money*2);
                                game.bots.get(idBoss).subMoney(game.frameMoney.get(itemp).money);
                                game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(itemp).money);
                                return true;
                            })
                        ));
                    }
                }
            }
        }
    }

    private void checkPointCardAtTheStart(){
        int indexTemp = GGameStart.mode == 0 ? 0 : idBoss + 1;
        Vector2 resutl = checkPoint(cards.get(indexTemp));
        if(resutl.x == 0 && (resutl.y == 1 || resutl.y == 2)){
            if(GGameMainScene.effect.size == 2){
                GGameMainScene.disposeParticleCardsPlayer();
            }
            if(GGameStart.mode == 1){
                checkPointCards1();
                checkMoneyBotPlayer();
                if(isPlayerWin){
                    SoundEffect.Play(SoundEffect.winSound);
                }
                else {
                    SoundEffect.Play(SoundEffect.loseSound);
                }
                Tweens.setTimeout(group, 0.8f, ()->{
                    newGameBtn.setVisible(true);
                });
                return;
            }
            SoundEffect.Play(SoundEffect.flipAllCards);
            cards.get(0).get(0).flipCard(true);
            cards.get(0).get(1).flipCard(true);
            showPointTxt(0);
            for(int i = 1; i < cards.size; i++) {
                cards.get(i).get(0).flipCard(false);
                cards.get(i).get(1).flipCard(false);
                showPointTxt(i);
            }
            //check tien bot va player
            checkMoneyBotPlayer();
            for(int i = 0; i < GGameMainScene.flipCards.size; i++) {
                GGameMainScene.flipCards.get(i).setVisible(false);
                int temp = checkCardsWidthPlayer(cards.get(i+1), -1);
                if(temp == 1){
                   // game.bots.get(i).subMoney(game.frameMoney.get(i+1).money);
                    game.addMoneyPlayer(game.frameMoney.get(i+1).money);
                    game.frameMoney.get(0).addMoney(game.frameMoney.get(i+1).money);

                    final int itemp = i;
                    Tweens.setTimeout(group, 0.5f, ()->{
                        game.groupPocker.get(itemp).addAction(moveTo(game.frameMoney.get(0).image.getX(),game.frameMoney.get(0).image.getY(), 0.4f, fastSlow ));
                    });
                }
                else if(temp == -1){
                    game.bots.get(i).addMoney(game.frameMoney.get(i+1).money*2);
                    game.subMoneyPlayer(game.frameMoney.get(i+1).money);
                    game.frameMoney.get(0).subMoney(game.frameMoney.get(i+1).money);

                    final int itemp = i;
                    Tweens.setTimeout(group, 0.5f, ()->{
                        game.groupPockerTemp.get(itemp).setVisible(true);
                        game.groupPockerTemp.get(itemp).addAction(moveTo(game.groupPocker.get(itemp).getX() + 50,game.groupPocker.get(itemp).getY(), 0.5f, fastSlow));
                    });

                }
                else if(temp == 0){
                    game.bots.get(i).addMoney(game.frameMoney.get(i+1).money);
                }
            }
            int soundTemp = game.frameMoney.get(0).money > 0 ? SoundEffect.winSound : SoundEffect.loseSound;
            Tweens.setTimeout(group, 0.5f,()->{
                newGameBtn.setVisible(true);
                SoundEffect.Play(soundTemp);
            });
            return;
        }
        if(GGameStart.mode == 1){
            checkPointCards2();
            turnAtTheMoment = idBoss + 2;
            turnOnLight();
            return;
        }
        for(int i = 1; i < cards.size; i++) {
            resutl = checkPoint(cards.get(i));
            if(resutl.x == 0 && (resutl.y == 1 || resutl.y == 2)){
                count++;
                SoundEffect.Play(SoundEffect.xiDachSound);
                idBotOverTurn[i-1] = true;
                showPointTxt(i);
                cards.get(i).get(0).flipCard(false);
                cards.get(i).get(1).flipCard(false);

                game.bots.get(i-1).addMoney(game.frameMoney.get(i).money*2);
                game.subMoneyPlayer(game.frameMoney.get(i).money);
                game.frameMoney.get(0).subMoney(game.frameMoney.get(i).money);

                final int itemp = i - 1;
                Tweens.setTimeout(group, 0.5f, ()->{
                    game.groupPockerTemp.get(itemp).setVisible(true);
                    game.groupPockerTemp.get(itemp).addAction(moveTo(game.groupPocker.get(itemp).getX() + 50,game.groupPocker.get(itemp).getY(), 0.5f, fastSlow));
                });
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
        if(GGameStart.mode == 1 && turnInitCards%GGameStart.member == (idBoss + 2) && cards.get(turnInitCards%GGameStart.member).size == 2) {
            Gdx.app.log("debug", "turn: " + turnInitCards%GGameStart.member);
            sortCardsAtTheStart();
            group.addAction(sequence(
                delay(0.6f),
                GSimpleAction.simpleAction((d, a)-> {
                    alignCardsAtTheStart();
                    return true;
                })
            ));
            return;
        }

        if(turnInitCards == GGameStart.member*2 && cards.get(turnInitCards%GGameStart.member).size == 2 ){
            sortCardsAtTheStart();
            group.addAction(sequence(
                delay(0.6f),
                GSimpleAction.simpleAction((d, a)-> {
                    alignCardsAtTheStart();
                    return true;
                })
            ));
            return;
        }
        else {
            Card card;
//            if(turnInitCards%6 == 0 && dem == 0){
//                card = new Card(gameMainAtlas, cg.get(turnInitCards%GGameStart.member),3, 10);
//                dem++;
//            } else if (turnInitCards%6 == 0 && dem == 1) {
//                card = new Card(gameMainAtlas, cg.get(turnInitCards%GGameStart.member),2, 1);
//            }
//            else
//             if(turnInitCards%6 == idBoss + 1 && dem1 == 0){
//                card = new Card(gameMainAtlas, cg.get(turnInitCards%GGameStart.member),2, 10);
//                dem1++;
//            }
//            else if(turnInitCards%6 == idBoss + 1 && dem1 == 1){
//                card = new Card(gameMainAtlas, cg.get(turnInitCards%GGameStart.member),1, 1);
//            }
//            else
            card = new Card(gameMainAtlas, cg.get(turnInitCards%GGameStart.member),(int)tiles.get(0).x, (int)tiles.get(0).y);
            Gdx.app.log("debug" , "GroupCard: " + turnInitCards%GGameStart.member);
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
            if((turnInitCards >= GGameStart.member && GGameStart.mode == 0) || (GGameStart.mode == 1 && cards.get(turnInitCards%GGameStart.member).size == 1)) {
                card.setPosition(cfg.CW*ratioScale - (cfg.CW*ratioScale)/ratio_temp, card.image.getY());
            }
            cards.get(turnInitCards%GGameStart.member).add(card);
            cardmove.image.setOrigin(Align.center);
            cardmove.tileDown.setOrigin(Align.center);
            SoundEffect.Play(SoundEffect.renderCards);
            cardmove.image.addAction(sequence(
                parallel(
                    moveTo(cg.get(turnInitCards%GGameStart.member).getX(), cg.get(turnInitCards%GGameStart.member).getY(), 0.3f, fastSlow),
                    rotateBy(-1*((int)Math.floor(Math.random()*81 + 100) + 10), 0.3f)
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
            Gdx.app.log("debug", "x-y: " + positionCardGroup.get(index).x + "-" + positionCardGroup.get(index).y);
        }
    }

    private void playerTurn(){
        Vector2 result = checkPoint(cards.get(0));
        if(result.x == 0 && result.y == 21){
            flipCards();
            return;
        }
        else if(result.x == 0 && result.y >= 16){
            if(GGameStart.mode == 0){
                flipAllCardsBtn.setVisible(true);
                moveFlipAllCards(true);
                for(int i = 0; i < GGameStart.member - 1; i++) {
                    if(!idBotOverTurn[i]){
                        GGameMainScene.flipCards.get(i).setVisible(true);
                        GGameMainScene.flipCards.get(i).setTouchable(Touchable.enabled);
                    }
                }
            }
            else {
                blockCardBtn.setVisible(true);
                blockCardBtn.addAction(sequence(
                    moveTo(850, cg.get(0).getY() + 50, 0.3f, fastSlow),
                    GSimpleAction.simpleAction((d, a)->{
                        return true;
                    })
                ));
            }

        }
        GGameMainScene.turnLight.setVisible(false);
        Gdx.app.log("debug", "here!!!");
        takeCardBtn.setPosition(cg.get(0).getX() - takeCardBtn.getWidth() - 90, GMain.screenHeight);
        takeCardBtn.setVisible(true);
        takeCardBtn.addAction(Actions.sequence(
            moveTo(cg.get(0).getX() - takeCardBtn.getWidth() - 90, cg.get(0).getY() + 50, 0.3f, fastSlow)
        ));
    }

    private void bossBotAction(){
        if(!isTurnBoss){
            isTurnBoss = true;
            for(Card card : cards.get(idBoss + 1)){
                card.flipCard(false);
            }
        }

        Vector2 result = checkPoint(cards.get(idBoss + 1));
        Gdx.app.log("debug-----", "x-y: " + result.x + "-" + result.y);
        if(count == GGameStart.member - 1){
            showPointTxt(idBoss + 1);
            for(Card card : cards.get(idBoss + 1)){
                card.flipCard(false);
            }
        }
        if(result.x == 0 && result.y == 0){ // todo: chua du diem
            getCard();
            return;
        }
        else if(result.y >= 16 && result.y <= 21) {
            if(turnBossCheck >= GGameStart.member){
                turnBotModeOutside++;
                if(turnBotModeOutside == 3) {
                    for(Card card : cards.get(idBoss + 1)){
                        card.flipCard(false);
                    }
                    Gdx.app.log("debug", "Game Over");
                    checkMoneyBotPlayer();

                    if(isPlayerWin){
                        SoundEffect.Play(SoundEffect.winSound);
                    }
                    else {
                        SoundEffect.Play(SoundEffect.loseSound);
                    }
                    Tweens.setTimeout(group, 0.8f, ()->{
                        newGameBtn.setVisible(true);
                    });
                    return;
                }
                turnBossCheck = 0;
                bossBotAction();
                return;
                //todo: het van, choi lai
            }

            if(cards.get(turnBossCheck).size >= 4 && turnBossCheck != idBoss + 1 && turnBotModeOutside == 0 && !idBotOverTurn1[turnBossCheck]){
                idBotOverTurn1[turnBossCheck] = true;
                for(Card card : cards.get(turnBossCheck)){
                    if(turnBossCheck == 0)
                        card.flipCard(true);
                    else card.flipCard(false);
                }
                showPointTxt(turnBossCheck);
                count++;
                Tweens.setTimeout(group, 0.9f, ()->{
                    Gdx.app.log("debug", "flip Cards");
                    caculateMoneyBoss();
                });
                return;
            }
            else if(turnBotModeOutside == 0) {
                turnBossCheck++;
                bossBotAction();
                return;
            }

            if(((cards.get(turnBossCheck).size == 3 && turnBotModeOutside == 1) || turnBotModeOutside == 2) && turnBossCheck != idBoss + 1 && !idBotOverTurn1[turnBossCheck]){
                int percent = 0;
                if(cards.get(idBoss + 1).size == 5){
                    percent = 100;
                }
                else if(result.y <= 17){
                    percent = 40;
                    Gdx.app.log("debug", "vao roi: percent: " + percent);
                }
                else if(result.y == 18){
                    percent = 50;
                    Gdx.app.log("debug", "vao roi: percent: " + percent);
                }
                else if(result.y == 19){
                    percent = 60;
                    Gdx.app.log("debug", "vao roi: percent: " + percent);
                }
                else if(result.y == 20){
                    percent = 90;
                    Gdx.app.log("debug", "vao roi: percent: " + percent);
                }
                else {
                    percent = 100;
                    Gdx.app.log("debug", "vao roi: percent: " + percent);
                }

                if(isGetCard(percent)){
                    Gdx.app.log("debug", "herere!!! " + percent);
                    idBotOverTurn1[turnBossCheck] = true;
                    for(Card card : cards.get(turnBossCheck)){
                        if(turnBossCheck == 0)
                            card.flipCard(true);
                        else card.flipCard(false);
                    }
                    showPointTxt(turnBossCheck);
                    count++;
                    Tweens.setTimeout(group, 0.9f, ()->{
                        Gdx.app.log("debug", "flip Cards");
                        caculateMoneyBoss();
                    });
                    return;
                }
                else if(turnBotModeOutside == 1){
                    turnBossCheck++;
                    bossBotAction();
                    return;
                }
                else {
                    getCard();
                    return;
                }
            }
            else {
                turnBossCheck++;
                bossBotAction();
                return;
            }

        }
        else if(result.y == -1){
            Gdx.app.log("GAme over!!", "bot cai da quat!!!!!!!!!!!!!1");
            caculateMoneyBoss1();
            for(int i = 0; i < cards.size; i++) {
                if(!idBotOverTurn1[i] || i == idBoss + 1){
                    showPointTxt(i);
                    Gdx.app.log("debug", "count : " + i);
                }
                for(Card card : cards.get(i)){
                    if(i == 0){
                        card.flipCard(true);
                    }
                    else card.flipCard(false);
                }
            }

            Tweens.setTimeout(group, 0.8f, ()->{
                checkMoneyBotPlayer();
            });
            if(isPlayerWin){
                SoundEffect.Play(SoundEffect.winSound);
            }
            else {
                SoundEffect.Play(SoundEffect.loseSound);
            }
            Tweens.setTimeout(group, 1f, ()->{
                newGameBtn.setVisible(true);
            });
        }
    }

    private void caculateMoneyBoss1(){ // xu ly khi cai quat
        for(int i = 0; i < cards.size; i++) {
            if(!idBotOverTurn1[i]){
                int resultTemp = checkCardsWidthPlayer(cards.get(i), idBoss);
                Gdx.app.log("debug", "resultTemp + " + resultTemp + " i: " + i);
                if(resultTemp == -1) {
                    if(i != 0){
                        game.groupPockerTemp.get(i - 1).setVisible(true);
                        final int itemp = i;
                        SoundEffect.Play(SoundEffect.chipPockers);
                        game.groupPockerTemp.get(i - 1).addAction(sequence(
                            moveTo(game.groupPocker.get(i - 1).getX() + 50, game.groupPocker.get(i - 1).getY(), 0.5f),
                            GSimpleAction.simpleAction((d, a)->{
                                game.bots.get(idBoss).subMoney(game.frameMoney.get(itemp).money);
                                game.bots.get(itemp - 1).win.setVisible(true);
                                game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(itemp).money);
                                game.bots.get(itemp - 1).addMoney(game.frameMoney.get(itemp).money*2);
                                return true;
                            })
                        ));
                    }
                    else {
                        groupPockerPlayerTemp.setVisible(true);
                        final int itemp = i;
                        SoundEffect.Play(SoundEffect.chipPockers);
                        groupPockerPlayerTemp.addAction(sequence(
                            moveTo(groupPockerPlayer.getX() + 50, groupPockerPlayer.getY() - 10, 0.5f),
                            GSimpleAction.simpleAction((d, a)->{
                                statusPlayer.get(0).setVisible(true);
                                isPlayerWin = true;
                                game.bots.get(idBoss).subMoney(game.frameMoney.get(itemp).money);
                                game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(itemp).money);
                                game.addMoneyPlayer(game.frameMoney.get(itemp).money*2);
                                return true;
                            })
                        ));
                    }
                }
                else if(resultTemp == 0){
                    if(i != 0){
                        game.bots.get(i - 1).addMoney(game.frameMoney.get(i).money);
                        game.bots.get(i - 1).tie.setVisible(true);
                    }
                    else {
                        statusPlayer.get(2).setVisible(true);
                        isPlayerWin = false;
                        game.addMoneyPlayer(game.frameMoney.get(i).money);
                    }
                }
            }
        }
    }

    private void caculateMoneyBoss(){
        int result = checkCardsWidthPlayer(cards.get(turnBossCheck), idBoss);
        if(result == 1){
            Gdx.app.log("debug", "result: " + result);
            if(turnBossCheck != 0){
                SoundEffect.Play(SoundEffect.chipPockers);
                game.groupPocker.get(turnBossCheck - 1).addAction(sequence(
                    moveTo(game.positionGroupPocker.get(idBoss).x, game.positionGroupPocker.get(idBoss).y, 0.5f),
                    delay(0.5f),
                    GSimpleAction.simpleAction((d, a)->{
                        game.bots.get(idBoss).addMoney(game.frameMoney.get(turnBossCheck).money);
                        game.bots.get(turnBossCheck - 1).lose.setVisible(true);
                        game.frameMoney.get(idBoss + 1).addMoney(game.frameMoney.get(turnBossCheck).money);
                        turnBossCheck++;
                        bossBotAction();
                        return true;
                    })
                ));
            }
            else {
                SoundEffect.Play(SoundEffect.chipPockers);
                groupPockerPlayer.addAction(sequence(
                    moveTo(game.positionGroupPocker.get(idBoss).x, game.positionGroupPocker.get(idBoss).y - 10, 0.5f),
                    delay(0.5f),
                    GSimpleAction.simpleAction((d, a)->{
                        statusPlayer.get(1).setVisible(true);
                        isPlayerWin = false;
                        game.bots.get(idBoss).addMoney(game.frameMoney.get(turnBossCheck).money);
                        game.frameMoney.get(idBoss + 1).addMoney(game.frameMoney.get(turnBossCheck).money);
                        turnBossCheck++;
                        bossBotAction();
                        return true;
                    })

                ));
            }
        }
        else if(result == -1){
            Gdx.app.log("debug-1", "result: " + result);
            if(turnBossCheck != 0){
                game.groupPockerTemp.get(turnBossCheck - 1).setVisible(true);
                SoundEffect.Play(SoundEffect.chipPockers);
                game.groupPockerTemp.get(turnBossCheck - 1).addAction(sequence(
                    moveTo(game.groupPocker.get(turnBossCheck - 1).getX() + 50, game.groupPocker.get(turnBossCheck - 1).getY(), 0.5f),
                        delay(0.5f),
                        GSimpleAction.simpleAction((d, a)->{
                        game.bots.get(idBoss).subMoney(game.frameMoney.get(turnBossCheck).money);
                        game.bots.get(turnBossCheck - 1).win.setVisible(true);
                        game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(turnBossCheck).money);
                        game.bots.get(turnBossCheck - 1).addMoney(game.frameMoney.get(turnBossCheck).money*2);
                        turnBossCheck++;
                        bossBotAction();
                        return true;
                    })
                ));
            }
            else {
                groupPockerPlayerTemp.setVisible(true);
                SoundEffect.Play(SoundEffect.chipPockers);
                groupPockerPlayerTemp.addAction(sequence(
                    moveTo(groupPockerPlayer.getX() + 50, groupPockerPlayer.getY() - 10, 0.5f),
                        delay(0.5f),
                        GSimpleAction.simpleAction((d, a)->{
                            statusPlayer.get(0).setVisible(true);
                            isPlayerWin = true;
                        game.bots.get(idBoss).subMoney(game.frameMoney.get(turnBossCheck).money);
                        game.frameMoney.get(idBoss + 1).subMoney(game.frameMoney.get(turnBossCheck).money);
                        game.addMoneyPlayer(game.frameMoney.get(turnBossCheck).money*2);
                        turnBossCheck++;
                        bossBotAction();
                        return true;
                    })
                ));

            }
        }
        else{
            if(turnBossCheck != 0){
                game.bots.get(turnBossCheck - 1).addMoney(game.frameMoney.get(turnBossCheck).money);
                game.bots.get(turnBossCheck - 1).tie.setVisible(true);
                turnBossCheck++;
                bossBotAction();
            }
            else {
                statusPlayer.get(2).setVisible(true);
                isPlayerWin = false;
                game.addMoneyPlayer(game.frameMoney.get(turnBossCheck).money);
                turnBossCheck++;
                bossBotAction();
            }
        }

    }

    private void play(){

        if(GGameStart.mode == 1){
            if(turnAtTheMoment%GGameStart.member == idBoss + 1){ // todo: đến lượt bot lam cai
                Gdx.app.log("debug", "1039 hoan tat bot");
                bossBotAction();
                return;
            }
        }

        if(turnAtTheMoment == 1 && flagTurnLightOfTurn1){
            flagTurnLightOfTurn1 = false;
            turnOnLight();
            return;
        }
        if(turnAtTheMoment == GGameStart.member){
            if(GGameStart.mode == 1){
                if(!idBotOverTurn1[0]){
                    SoundEffect.Play(SoundEffect.playerTurn);
                    playerTurn();
                    return;
                }
                else {
                    blockBtnClicked();
                    return;
                }
            }
            else {
                SoundEffect.Play(SoundEffect.playerTurn);
                playerTurn();
                return;
            }

        }

        Vector2 result = checkPoint(cards.get(turnAtTheMoment));
        if(result.x == 0 && result.y == 0){
            getCard();
        }
        else if(result.x == 0 && result.y == -1){
            resultFinal.get(turnAtTheMoment).set(result);
            fitPositionGroupCards();
        }
        else {
            if(isGetCard(percentGetCard(cards.get(turnAtTheMoment)))){
                getCard();
            }
            else {
                fitPositionGroupCards();
            }
        }
    }

    private void fitPositionGroupCards(){
        if(cards.get(turnAtTheMoment).size > 2) {
            cg.get(turnAtTheMoment).addAction(sequence(
                //moveTo(-1*((cards.get(turnAtTheMoment).size-2)*cfg.CW*Card.ratioScale)/2 + 20, 0, 0.3f),
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

    private String checkPointTxt(int index){
        String pointTxt = "";
        Vector2 result = checkPoint(cards.get(index));
        if(result.x == 0 && result.y == -1){
            pointTxt = "QUẮC";
        }
        else if(result.x == 0 && result.y == 1) {
            pointTxt = "XÌ BÀN";
        }
        else if(result.x == 0 && result.y == 2){
            pointTxt = "XÌ DÁCH";
        }
        else if(result.x == 0 && result.y >= 16 && result.y <= 21){
            pointTxt = (int)result.y + " ĐIỂM";
        }
        else if(result.x == 1){
            pointTxt = "NGŨ LINH";
        }
        else if(result.x == 0 && result.y == 0){
            pointTxt = "THIẾU ĐIỂM";
        }
        return pointTxt;
    }

    private void turnOnLight(){
        if(turnAtTheMoment >= GGameStart.member){
            group.addAction(Actions.sequence(
                delay(1),
                GSimpleAction.simpleAction((d, a)->{
                    GGameMainScene.turnLight.setVisible(false);
                    play();
                    return true;
                })
            ));
        }
        else {
            GGameMainScene.turnLight.setPosition(cg.get(turnAtTheMoment).getX() - 150, cg.get(turnAtTheMoment).getY() - 200);
            GGameMainScene.turnLight.setScale(0.4f);
            GGameMainScene.turnLight.setOrigin(Align.center);
            GGameMainScene.turnLight.addAction(Actions.scaleTo(1,1,0.4f, fastSlow));
            SoundEffect.Play(SoundEffect.turnBots);
            GGameMainScene.turnLight.setVisible(true);
            Gdx.app.log("debug", "1135");
            group.addAction(Actions.sequence(
                delay(1f),
                GSimpleAction.simpleAction((d, a)->{
                    play();
                    return true;
                })
            ));
        }
    }

    private void flipCards(){
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
        Gdx.app.log("debug", "percent Temp: " + percent_temp + " percent: " + percent);
        if(percent_temp <= percent)
            result = true;
        return result;
    }

    private void getCard(){
        Card card = new Card(gameMainAtlas, cg.get(turnAtTheMoment), (int)tiles.get(0).x, (int)tiles.get(0).y);
        card.setVisible(false);
        card.setPosition((cards.get(turnAtTheMoment).size)*cfg.CW*Card.ratioScale/2,0);
        cards.get(turnAtTheMoment).add(card);
        Card cardmove = new Card(gameMainAtlas, group, 0, 0);
        cardmove.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight - cfg.CH*Card.ratioScale)/2);
        tiles.removeIndex(0);
        Tweens.setTimeout(group, 0.8f, ()->{
            SoundEffect.Play(SoundEffect.takeCard);
        });
        cardmove.image.addAction(sequence(
            delay(0.8f),
            Actions.parallel(
                moveTo(cg.get(turnAtTheMoment).getX() + (cards.get(turnAtTheMoment).size - 1)*cfg.CW*Card.ratioScale*1/2 - 28.75f, cg.get(turnAtTheMoment).getY(), 0.3f, fastSlow),
                GSimpleAction.simpleAction((d, a)->{
                    moveGroupCards(turnAtTheMoment);
                    return true;
                })
            ),
            delay(0.3f),
            GSimpleAction.simpleAction((d, a)-> {

                card.setVisible(true);
                cardmove.removeCard();
                cards.get(turnAtTheMoment).get(cards.get(turnAtTheMoment).size-1).image.setVisible(true);
                if(GGameStart.mode == 1 && turnAtTheMoment == idBoss + 1){
                    if(isTurnBoss){
                        card.flipCard(false);
                    }
                    bossBotAction();
                }
                else {
                    play();
                }
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
        cg = new Array<Group>();
        for(int index = 0; index < GGameStart.member; index++){
            Group groupCard = new Group();
            cg.add(groupCard);
            group.addActor(cg.get(index));
        }
    }

    private void initPositionCardGroup(){
        positionCardGroup = new Array<>();
        switch (GGameStart.member){
            case 2: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 70);
                Vector2 position1 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105 + 10);
                positionCardGroup.add(position0, position1);
                break;
            }
            case 3: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 70);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, 155 + 10);
                Vector2 position2 = new Vector2( 150, 155 + 10);
                positionCardGroup.add(position0, position1, position2);
                break;
            }
            case 4: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 70);
                Vector2 position1 = new Vector2(GMain.screenWidth - 185, GMain.screenHeight/2 + 5 + 10);
                Vector2 position2 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105 + 10);
                Vector2 position3 = new Vector2(100, GMain.screenHeight/2 + 5 + 10);
                positionCardGroup.add(position0, position1, position2, position3);
                break;
            }
            case 5: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 70);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 245 + 10);
                Vector2 position2 = new Vector2(GMain.screenWidth - 250, 155 + 10);
                Vector2 position3 = new Vector2(150, 155 + 10);
                Vector2 position4 = new Vector2(150, GMain.screenHeight - 245 + 10);
                positionCardGroup.add(position0, position1, position2);
                positionCardGroup.add(position3, position4);
                break;
            }
            default: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH - 70);
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

                if(total + 10 >= 16 && total + 10 <= 21)
                    result.set(0, total + 10);
                else if(total + 9 >= 16 && total + 9 <= 21) {
                    result.set(0, total + 9);
                }
                else result.set(0, 0);
            }
            else if(card.get(1).values[1] == 1){ //Todo: lá thứ 0 ko phải là xì và lá thứ 1 là xì
                int value = getValueCard(card.get(0));
                int total = value + 1;

                if(total + 10 >= 16 && total + 10 <= 21)
                    result.set(0, total + 10);
                else if(total + 9 >= 16 && total + 9 <= 21) {
                    result.set(0, total + 9);
                }
                else result.set(0, 0);
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

    private int checkCardsWidthPlayer(Array<Card> cardsBot, int id){//todo: so sanh bai cua bot voi bai cua nguoi choi
        int index = 0;
        if(id == -1){
            index = 0;
        }
        else {
            index = id+1;
        }
        int result = 0;
        Vector2 result1 = checkPoint(cardsBot);
        Vector2 result2 = checkPoint(cards.get(index));
        if(result2.x == 0 && result2.y == 1){
            if(result1.x == 0 && result1.y == 1){
                result = 0; // todo: hoa
            }
            else result = 1; // todo: cai thang
        }
        else if(result2.x == 0 && result2.y == 2) {
            if(result1.x == 0 && result1.y == 1)
                result = -1; //todo: con thang
            else if(result1.x == 0 && result1.y == 2)
                result = 0;
            else result = 1;


        }
        else if(result2.x == 1){
            if(result1.x == 1) {
                if(result1.y < result2.y)
                    result = -1;
                else if(result1.y == result2.y) result = 0;
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

    private void bet(){
        for(int i = 0; i < GGameStart.member - 1; i++) {
            if(GGameStart.mode == 1 && i == idBoss)
                continue;
            int percent = (int) Math.floor(Math.random()*30 + 10);
            long moneyPet = (percent*game.bots.get(i).money)/100;
            if(moneyPet < 50000){
                moneyPet = 50000;
            }
            Gdx.app.log("debug", "moneypet1: " + moneyPet);
            //long moneyPet = 2000000;
            if(moneyPet < 1000000){
                moneyPet = (moneyPet /10000)*10000;
            }
            Gdx.app.log("debug", "moneypet2: " + moneyPet);
            game.frameMoney.get(i + 1).setMoney(moneyPet);
            game.bots.get(i).subMoney(moneyPet);
            showPocker(moneyPet, i);
        }
        if(GGameStart.mode == 1){
            Tweens.setTimeout(group, 0.5f, ()->{
                if(GGameStart.mode == 1){
                    GGameMainScene.cardDown.setVisible(true);
                    startRenderCards1();
                }
            });
        }
    }

    private void showPocker(long moneyPet, int index){
        int ratio = 0;

        while(moneyPet > 0){
            if(moneyPet >= 500000) {
                ratio = 500000;
                Gdx.app.log("debug", "vao ratio: " + ratio);

            }
            else if(moneyPet >= 200000){
                ratio = 200000;
                Gdx.app.log("debug", "vao ratio: " + ratio);

            }
            else if(moneyPet >= 100000){
                ratio = 100000;
                Gdx.app.log("debug", "vao ratio: " + ratio);

            }
            else if(moneyPet >= 50000){
                ratio = 50000;
                Gdx.app.log("debug", "vao ratio: " + ratio);

            }
            else if(moneyPet >= 20000){
                ratio = 20000;
                Gdx.app.log("debug", "vao ratio: " + ratio);

            }
            else {
                ratio = 10000;
                Gdx.app.log("debug", "vao ratio: " + ratio);

            }

            long nguyen = moneyPet/ratio;
            long du = moneyPet - nguyen*ratio;

            for(int i = 0; i < nguyen; i++) {
                float x_temp = (float)Math.floor(Math.random() * 50);
                float y_temp = (float)Math.floor(Math.random() * 40) - 50;
                Pocker pocker = new Pocker(gameMainAtlas, game.groupPocker.get(index), ratio);
                pocker.imagePocker.setPosition(x_temp, y_temp);

                Pocker pocker1 = new Pocker(gameMainAtlas, game.groupPockerTemp.get(index), ratio);
                pocker1.imagePocker.setPosition(x_temp, y_temp);
            }

            game.groupPocker.get(index).addAction(sequence(
                moveTo(game.positionGroupPocker.get(index).x,game.positionGroupPocker.get(index).y, 0.5f),
                GSimpleAction.simpleAction((d, a)->{
                    return true;
                })
            ));

            moneyPet = du;
        }

        if(GGameStart.mode == 1){
            game.groupPockerTemp.get(index).setPosition(game.positionGroup.get(idBoss).x, game.positionGroup.get(idBoss).y);
            Gdx.app.log("debug-positionGroup", "x-y: " + game.positionGroup.get(idBoss).x + "-" + game.positionGroup.get(idBoss).y + " idBos: " + idBoss);
        }


    }

    private void startRenderCards1(){
        takeCardBtn = GUI.createImage(this.gameMainAtlas, "takeCard");
        newGameBtn = GUI.createImage(this.gameMainAtlas, "newGame");
        group.addActor(takeCardBtn);
        group.addActor(newGameBtn);
        newGameBtn.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);
        newGameBtn.setVisible(false);
        takeCardBtn.setVisible(false);
        newGameBtn.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
            super.clicked(event, x, y);
            SoundEffect.Play(SoundEffect.buttonNewGame);
            newGameBtn.setVisible(false);
            replay();
            }
        });

        addListenerBtnMode1();

        turnInitCards = (idBoss + 2);
        turnInitCards = turnInitCards%GGameStart.member;
        startRenderCards();
    }

    private void dispose(){
        tiles.clear();
        count = 0;
        for(Group group : cg){
            group.clearChildren();
        }

        positionCardGroup.clear();

        if(GGameStart.mode == 0){
            resultFinal.clear();
        }
        else {
            groupPocker.clearChildren();
            groupPockerPlayer.clearChildren();
            groupPockerPlayerTemp.clearChildren();
            groupPocker.clear();
            groupPockerPlayer.clear();
            groupPockerPlayerTemp.clear();
            imageBoss.remove();
            for(Image image : statusPlayer){
                image.remove();
            }
        }

    }
}
