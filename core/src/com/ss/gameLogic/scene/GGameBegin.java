package com.ss.gameLogic.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.ss.GMain;
import com.ss.core.action.exAction.GSimpleAction;
import com.ss.core.effects.EffectSlide;
import com.ss.core.effects.SoundEffect;
import com.ss.core.util.GAssetsManager;
import com.ss.core.util.GLayer;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;
import com.ss.core.util.GUI;

public class GGameBegin extends GScreen {
  TextureAtlas menuAtlas;
  BitmapFont font;
  Image startBtn;

  @Override
  public void dispose() {

  }

  @Override
  public void init() {

    GGameStart.mode = 1;
    SoundEffect.initSound();
    Group menuGroup = new Group();
    GStage.addToLayer(GLayer.ui, menuGroup);

    menuAtlas = GAssetsManager.getTextureAtlas("gameStart/gameStart.atlas");
    Image bg = GUI.createImage(menuAtlas, "startScene");
    menuGroup.addActor(bg);

    EffectSlide effectSlideBg = new EffectSlide("bg", GMain.screenWidth/2, GMain.screenHeight/2, menuGroup);
    menuGroup.addActor(effectSlideBg);
    effectSlideBg.start();

    startBtn = GUI.createImage(menuAtlas, "startBtn");
    menuGroup.addActor(startBtn);
    startBtn.setOrigin(Align.center);

    startBtn.setPosition(GMain.screenWidth/2 - 50, GMain.screenHeight * (float) 4/5, Align.center);
    menuGroup.setOrigin(Align.center);

    int theFirstTime = 0;
    theFirstTime = GGameStart.prefs.getInteger("theFirstTime");
    if(theFirstTime == 0){
      GGameStart.prefs.putLong("money", 5000000l);
      GGameStart.prefs.putInteger("theFirstTime", 1);
      GGameStart.prefs.flush();
    }

    long money = GGameStart.prefs.getLong("money");
    if(money <= 0){
      showPanelAddMoney();
    }

    startBtn.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        startBtn.setTouchable(Touchable.disabled);
        SoundEffect.Play(SoundEffect.button);
        startBtn.addAction(Actions.sequence(
          Actions.scaleTo(0.8f, 0.8f, 0.02f, Interpolation.linear),
          Actions.scaleTo(1f, 1f, 0.02f, Interpolation.linear),
          GSimpleAction.simpleAction((d, a)->{
            showPanelStart();
            return true;
          })
        ));
      }
    });

  }

  private void showPanelAddMoney(){
    Group addMoneyGroup = new Group();
    GStage.addToLayer(GLayer.ui, addMoneyGroup);
    Image panel = GUI.createImage(menuAtlas, "panel");
    addMoneyGroup.addActor(panel);
    addMoneyGroup.setOrigin(Align.center);
    addMoneyGroup.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);
    panel.setOrigin(Align.center);
    panel.setPosition(0, 0, Align.center);
    panel.addAction(Actions.sequence(
            Actions.scaleTo(2, 2, 0.5f, Interpolation.bounceOut),
            GSimpleAction.simpleAction((d, a)->{
              showTxtAddMoney(addMoneyGroup);
              return true;
            })
    ));
  }

  private void showTxtAddMoney(Group group){
    if(GMain.platform.isVideoRewardReady()){
      Image txt = GUI.createImage(menuAtlas, "3000000");
      group.addActor(txt);
      txt.setSize(txt.getWidth()*2, txt.getHeight()*2);
      txt.setPosition(0, -50, Align.center);

      Image confirm = GUI.createImage(menuAtlas, "confirm");
      Image cancel = GUI.createImage(menuAtlas, "cancel");

      group.addActor(confirm);
      group.addActor(cancel);

      confirm.setPosition(150, 150, Align.center);
      cancel.setPosition(-150, 150, Align.center);

      confirm.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        SoundEffect.Play(SoundEffect.button);
        confirm.setTouchable(Touchable.disabled);
        GMain.platform.ShowVideoReward(success -> {
          if (success) {
            Gdx.app.log("debug", "done");
            GGameStart.prefs.putLong("money", 3000000);
            GGameStart.prefs.flush();
            group.addAction(Actions.sequence(
                    Actions.scaleTo(0, 0, 0.4f, Interpolation.swingIn)
            ));
          } else {
            Gdx.app.log("debug", "not done");
            group.addAction(Actions.sequence(
              Actions.scaleTo(0, 0, 0.4f, Interpolation.swingIn),
              GSimpleAction.simpleAction((d, a)->{
                confirm.remove();
                cancel.remove();
                txt.remove();
                showPanelSubsidize(group);
                return true;
              })
            ));
          }
        });
        }
      });

      cancel.addListener(new ClickListener(){
        @Override
        public void clicked(InputEvent event, float x, float y) {
          super.clicked(event, x, y);
          SoundEffect.Play(SoundEffect.button);
          cancel.setTouchable(Touchable.disabled);
          group.addAction(Actions.sequence(
            Actions.scaleTo(0, 0, 0.4f, Interpolation.slowFast),
            GSimpleAction.simpleAction((d, a)->{
              confirm.remove();
              cancel.remove();
              txt.remove();
              showPanelSubsidize(group);
              return true;
            })
          ));
        }
      });

    }
    else {
      showPanelSubsidize(group);
    }

  }

  private void showPanelSubsidize(Group group){
    group.setScale(1);
    Image txt = GUI.createImage(menuAtlas, "500000");
    group.addActor(txt);
    txt.setSize(txt.getWidth()*2, txt.getHeight()*2);
    txt.setPosition(0, -50, Align.center);
    GGameStart.prefs.putLong("money", 500000);
    GGameStart.prefs.flush();

    Image confirm = GUI.createImage(menuAtlas, "confirm");
    group.addActor(confirm);
    confirm.setPosition(0, 150, Align.center);

    confirm.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        SoundEffect.Play(SoundEffect.button);
        confirm.setTouchable(Touchable.disabled);
        group.addAction(Actions.sequence(
                Actions.scaleTo(0, 0, 0.4f, Interpolation.elasticIn)
        ));
      }
    });
  }

  private void showPanelStart(){
    Group panelGroup = new Group();
    GStage.addToLayer(GLayer.ui, panelGroup);

    Image panel = GUI.createImage(menuAtlas, "panel");
    panelGroup.addActor(panel);
    panelGroup.setOrigin(Align.center);
    panelGroup.setPosition(GMain.screenWidth/2, GMain.screenHeight/2, Align.center);
    panel.setOrigin(Align.center);
    panel.setPosition(0, 0, Align.center);
    panel.addAction(Actions.sequence(
            Actions.scaleTo(2, 2, 0.5f, Interpolation.elasticOut),
            GSimpleAction.simpleAction((d, a)->{
              showTextPanelStart(panelGroup);
              return true;
            })
    ));

  }

  private void showTextPanelStart(Group panelGroup){
    Image modeStart = GUI.createImage(menuAtlas, "modeStart");
    panelGroup.addActor(modeStart);
    modeStart.setScale(2f);
    modeStart.setPosition( - 190, - 90, Align.center);

    Image confirm = GUI.createImage(menuAtlas, "confirm");
    Image cancel = GUI.createImage(menuAtlas, "cancel");
    Image up = GUI.createImage(menuAtlas, "up");
    Image down = GUI.createImage(menuAtlas, "down");
    font = GAssetsManager.getBitmapFont("font_white.fnt");

    Label countTxt = new Label("" + GGameStart.member, new Label.LabelStyle(font, null));

    panelGroup.addActor(confirm);
    panelGroup.addActor(cancel);
    panelGroup.addActor(up);
    panelGroup.addActor(down);
    panelGroup.addActor(countTxt);

    up.setScale(2);
    down.setScale(2);

    confirm.setPosition(150,  modeStart.getHeight() + 80, Align.center);
    cancel.setPosition( -150,  modeStart.getHeight() + 80, Align.center);
    up.setPosition( 300,- 132, Align.center);
    down.setPosition( 0, - 132, Align.center);
    countTxt.setPosition( 90, -115, Align.center);

    AddtickBox(panelGroup);
    upDownClick(up, down, countTxt);
    confirmCancelClick(confirm, cancel, panelGroup);
  }

  private void confirmCancelClick(Image confirm, Image cancel, Group group){
    confirm.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        SoundEffect.Play(SoundEffect.button);
        confirm.setOrigin(Align.center);
        confirm.addAction(Actions.sequence(
                Actions.scaleTo(0.8f, 0.8f, 0.02f, Interpolation.linear),
                Actions.scaleTo(1f, 1f, 0.02f, Interpolation.linear),
                GSimpleAction.simpleAction((d, a)->{
                  setScreen(new GGameMainScene());
                  return true;
                })
        ));
      }
    });

    cancel.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        SoundEffect.Play(SoundEffect.button);
        group.addAction(Actions.sequence(
                Actions.scaleTo(0, 0, 0.2f, Interpolation.slowFast),
                GSimpleAction.simpleAction((d, a)->{
                  group.clearChildren();
                  group.clear();
                  startBtn.setTouchable(Touchable.enabled);
                  return true;
                })
        ));
      }
    });
  }

  private void upDownClick(Image up, Image down, Label label){
    up.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
        super.clicked(event, x, y);
        SoundEffect.Play(SoundEffect.tick);
        GGameStart.member++;
        if(GGameStart.member == 7)
          GGameStart.member = 2;
        label.setText("" + GGameStart.member);
      }
    });

    down.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
      super.clicked(event, x, y);
      SoundEffect.Play(SoundEffect.tick);
      GGameStart.member--;
      if(GGameStart.member == 1)
        GGameStart.member = 6;
      label.setText("" + GGameStart.member);
      }
    });
  }

  private void AddtickBox(Group group){
    Image noneTick = GUI.createImage(menuAtlas, "noneTick");
    Image tick = GUI.createImage(menuAtlas, "ticked");
    group.addActor(noneTick);
    group.addActor(tick);

    noneTick.setSize(noneTick.getWidth()*0.7f, noneTick.getHeight()*0.7f);
    tick.setSize(tick.getWidth()*0.7f, tick.getHeight()*0.7f);

    noneTick.setPosition( 220,  - 33);
    tick.setPosition( 220,  - 33);

    noneTick.setOrigin(Align.center);
    tick.setOrigin(Align.center);
    tick.setVisible(false);

    noneTick.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
      super.clicked(event, x, y);
      SoundEffect.Play(SoundEffect.tick);
      noneTick.setTouchable(Touchable.disabled);
      tick.setTouchable(Touchable.enabled);
      tick.setScale(0.5f);
      noneTick.setVisible(false);
      tick.setVisible(true);
      tick.addAction(Actions.sequence(
        Actions.scaleTo(1, 1, 0.1f, Interpolation.bounceOut),
        GSimpleAction.simpleAction((d,a)->{
          GGameStart.mode = 0;
          return true;
        })
      ));
      }
    });

    tick.addListener(new ClickListener(){
      @Override
      public void clicked(InputEvent event, float x, float y) {
      super.clicked(event, x, y);
      SoundEffect.Play(SoundEffect.tick);
      tick.setTouchable(Touchable.disabled);
      noneTick.setTouchable(Touchable.enabled);
      noneTick.setScale(0.5f);
      tick.setVisible(false);
      noneTick.setVisible(true);
      noneTick.addAction(Actions.sequence(
        Actions.scaleTo(1, 1, 0.1f, Interpolation.bounceOut),
        GSimpleAction.simpleAction((d,a)->{
          GGameStart.mode = 1;
          return true;
        })
      ));
      }
    });
  }

  @Override
  public void run() {

  }
}
