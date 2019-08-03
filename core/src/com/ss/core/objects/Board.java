package com.ss.core.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.utils.Array;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.gameLogic.scene.GGameStart;

public class Board {
    TextureAtlas gameMainAtlas;
    Group group;
    BoardConfig cfg;
    Array<Vector2> tiles;
    Array<Array<Card>> cards;
    Array<Array<Card>> cards_temp;
    Array<Group> cardsGroup;
    Array<Vector2> positionCardGroup;
    Array<Vector2> resultFinal;
    int turnAtTheMoment = 1;
    int turnInitCards = 0;

    public Board(TextureAtlas gameMainAtlas, Group group){
        this.gameMainAtlas = gameMainAtlas;
        this.group = group;
        cfg = new BoardConfig();
        initCardsGroup();
        initPositionCardGroup();
        initResutl();
        start();
    }

    private void start(){
        renderPositionCard();
        initTiles();
        initCards();
        //testPoint();
        //play();
    }

    private void testPoint(){
        Array<Card> cardtest = new Array<>();
        Card card1 = new Card(gameMainAtlas, group, 1, 9);
        Card card2 = new Card(gameMainAtlas, group,2 , 13);
        Card card3 = new Card(gameMainAtlas, group, 0, 3);
        Card card4 = new Card(gameMainAtlas, group, 0, 2);
        Card card5 = new Card(gameMainAtlas, group, 0, 2);
        cardtest.add(card1, card2)/*, card3, card4)*/;
        //cardtest.add(card5);

        int percent = percentGetCard(cardtest);
        Gdx.app.log("debug", "percent: " + percent);
    }

    private void sortCardsAtTheStart(){
        for(int index = 0; index < GGameStart.member; index++) {
            cards_temp.get(index).get(0).image.addAction(Actions.rotateTo(0, 0.2f));
            cards_temp.get(index).get(1).image.addAction(Actions.rotateTo(0, 0.2f));
        }
    }

    private void alignCardsAtTheStart(){
        for(int index = 0; index < GGameStart.member; index++){
            if(index == 0){
                cards_temp.get(index).get(1).image.addAction(Actions.moveBy(cfg.CW*Card.ratioScale - 2, 0, 0.3f));
                Gdx.app.log("debug", "cfg.cw: " + cfg.CW);
            }
            cards_temp.get(index).get(1).image.addAction(Actions.moveBy(cfg.CW*Card.ratioScale-2, 0, 0.3f));
        }

        group.addAction(Actions.sequence(
                Actions.delay(0.3f),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        showCardsAtTheStart();
                        return true;
                    }
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

        group.addAction(Actions.sequence(
                Actions.delay(0.3f),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        removeCardTempAtTheStart();
                        play();
                        return true;
                    }
                })
        ));
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
            group.addAction(Actions.sequence(
                    Actions.delay(0.3f),
                    GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                        @Override
                        public boolean act(float var1, Actor var2) {
                            alignCardsAtTheStart();
                            return true;
                        }
                    })
            ));
            Gdx.app.log("debug", "here!!");
            return;
        }
        else {
            Card card = new Card(gameMainAtlas, cardsGroup.get(turnInitCards%GGameStart.member),(int)tiles.get(0).x, (int)tiles.get(0).y);
            Card cardmove = new Card(gameMainAtlas, group, 0, 0);
            cards_temp.get(turnInitCards%GGameStart.member).add(cardmove);
            cardmove.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2);
            tiles.removeIndex(0);
            card.setVisible(false);

            float ratioScale = Card.ratioScale;
            if(turnInitCards%GGameStart.member == 0){
                card.setScale(1);
                card.addListenerClick();
                cardmove.setScale(1);
                ratioScale = 1;
            }
            if(turnInitCards >= GGameStart.member) {
                card.setPosition(cfg.CW*ratioScale, card.image.getY());
            }
            cards.get(turnInitCards%GGameStart.member).add(card);
            cardmove.image.addAction(Actions.sequence(
                Actions.parallel(
                        Actions.moveTo(cardsGroup.get(turnInitCards%GGameStart.member).getX(), cardsGroup.get(turnInitCards%GGameStart.member).getY(), 0.3f, Interpolation.fastSlow),
                        Actions.rotateBy(-1*((int)Math.floor(Math.random()*81) + 10), 0.3f)
                ),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        turnInitCards++;
                        initCards();
                        return true;
                    }
                })
            ));
        }
    }


    private void renderPositionCard(){
        for(int index = 0; index < GGameStart.member; index++ ){
            cardsGroup.get(index).setPosition(positionCardGroup.get(index).x,positionCardGroup.get(index).y);
        }
    }

    private void play(){
        if(turnAtTheMoment == GGameStart.member){
            Gdx.app.log("debug", "het luot");
            return;
        }
        for(Card card : cards.get(turnAtTheMoment)){
            Gdx.app.log("card turn" + turnAtTheMoment, "" + card.values[1]);
        }

        Vector2 result = checkPoint(cards.get(turnAtTheMoment));
        Gdx.app.log("debug", "point: x-y: " + result.x + "-" + result.y);
        if(result.x == 0 && result.y == 1){
            Gdx.app.log("debug", "board_103: turn: " + turnAtTheMoment + " xi ban");
            resultFinal.get(turnAtTheMoment).set(result);
            flipCards();
            fitPositionGroupCards();
        }
        else if(result.x == 0 && result.y == 2) {
            Gdx.app.log("debug", "board_109: turn: " + turnAtTheMoment + " xi dach");
            resultFinal.get(turnAtTheMoment).set(result);
            flipCards();
            fitPositionGroupCards();
        }
        else if(result.x == 0 && result.y == 0){
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
            cardsGroup.get(turnAtTheMoment).addAction(Actions.sequence(
                Actions.moveBy(-1*((cards.get(turnAtTheMoment).size-2)*cfg.CW*Card.ratioScale)/2, 0, 0.3f),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        turnAtTheMoment++;
                        play();
                        return true;
                    }
                })
            ));
        }
        else {
            turnAtTheMoment++;
            play();
        }
    }

    private void flipCards(){
        Gdx.app.log("debug_121", "flip cards");
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
        Card card = new Card(gameMainAtlas, cardsGroup.get(turnAtTheMoment), (int)tiles.get(0).x, (int)tiles.get(0).y);
        cards.get(turnAtTheMoment).addAll(card);
        cards.get(turnAtTheMoment).get(cards.get(turnAtTheMoment).size-1).image.setVisible(false);
        Gdx.app.log("debug", "size: " + cards.get(turnAtTheMoment).size + " cfg: " + cfg.CW);

        Card cardmove = new Card(gameMainAtlas, group, 0, 0);
        cardmove.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2);
        cardmove.image.setPosition((GMain.screenWidth - cfg.CW* Card.ratioScale)/2, (GMain.screenHeight- cfg.CH*Card.ratioScale)/2);

        tiles.removeIndex(0);
        cardmove.image.addAction(Actions.sequence(
                Actions.delay(0.8f),
                Actions.moveTo(cardsGroup.get(turnAtTheMoment).getX(), cardsGroup.get(turnAtTheMoment).getY(), 0.3f, Interpolation.fastSlow),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        cardmove.removeCard();
                        cards.get(turnAtTheMoment).get(cards.get(turnAtTheMoment).size-1).image.setVisible(true);
                        moveCardInAGroup(cards.get(turnAtTheMoment).get(cards.get(turnAtTheMoment).size-1));
                        return true;
                    }
                })
        ));
    }

    private void moveCardInAGroup(Card card){
        card.tileDown.addAction(Actions.moveTo((cards.get(turnAtTheMoment).size-1)*cfg.CW*Card.ratioScale,0, 0.2f));
        card.image.addAction(Actions.sequence(
                Actions.moveTo((cards.get(turnAtTheMoment).size-1)*cfg.CW*Card.ratioScale,0, 0.2f),
                GSimpleAction.simpleAction(new GSimpleAction.ActInterface() {
                    @Override
                    public boolean act(float var1, Actor var2) {
                        play();
                        return true;
                    }
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
        cardsGroup = new Array<Group>();
        for(int index = 0; index < GGameStart.member; index++){
            Group groupCard = new Group();
            cardsGroup.add(groupCard);
            group.addActor(cardsGroup.get(index));
        }
    }

    private void initPositionCardGroup(){
        positionCardGroup = new Array<>();
        switch (GGameStart.member){
            case 2: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH);
                Vector2 position1 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105);
                positionCardGroup.add(position0, position1);
                break;
            }
            case 3: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, 155);
                Vector2 position2 = new Vector2( 150, 155);
                positionCardGroup.add(position0, position1, position2);
                break;
            }
            case 4: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH);
                Vector2 position1 = new Vector2(GMain.screenWidth - 150, GMain.screenHeight/2 + 5);
                Vector2 position2 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105);
                Vector2 position3 = new Vector2(100, GMain.screenHeight/2 + 5);
                positionCardGroup.add(position0, position1, position2, position3);
                break;
            }
            case 5: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 245);
                Vector2 position2 = new Vector2(GMain.screenWidth - 250, 155);
                Vector2 position3 = new Vector2(150, 155);
                Vector2 position4 = new Vector2(150, GMain.screenHeight - 245);
                positionCardGroup.add(position0, position1, position2);
                positionCardGroup.add(position3, position4);
                break;
            }
            default: {
                Vector2 position0 = new Vector2((GMain.screenWidth - cfg.CW*2)/2, GMain.screenHeight - cfg.CH);
                Vector2 position1 = new Vector2(GMain.screenWidth - 250, GMain.screenHeight - 245);
                Vector2 position2 = new Vector2(GMain.screenWidth - 250, 155);
                Vector2 position3 = new Vector2((GMain.screenWidth - cfg.CW*2*Card.ratioScale)/2, 105);
                Vector2 position4 = new Vector2(150, 155);
                Vector2 position5 = new Vector2(150, GMain.screenHeight - 245);
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
                int dem = 0;
                int valuesTemp = 0;
                if(card.get(1).values[1] >= 10) valuesTemp = 10;
                else valuesTemp = card.get(1).values[1];
                dem = valuesTemp + 11;
                if(dem < 16){
                    result.set(0, 0);
                }
                else result.set(0, dem);
            }
            else if(card.get(1).values[1] == 1){ //Todo: lá thứ 0 ko phải là xì và lá thứ 1 là xì
                int dem = 0;
                int valuesTemp = 0;
                if(card.get(0).values[1] >= 10) valuesTemp = 10;
                else valuesTemp = card.get(0).values[1];
                dem = valuesTemp + 11;
                if(dem > 21) {
                    dem = valuesTemp + 10;
                    if(dem < 16) {
                        result.set(0, 0);
                    }
                    else result.set(0, dem);
                }
                else result.set(0, dem);
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
}
