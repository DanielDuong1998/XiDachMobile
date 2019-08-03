package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.utils.ActorGestureListener;
import com.ss.GMain;
import com.ss.core.transitions.GTransition;
import com.ss.core.util.GDirectedGame;
import com.ss.core.util.GSound;
import com.ss.core.util.GStage;

public abstract class GScreen implements Screen {
   public static int viewportH;
   public static int viewportW;
   private Color color = new Color(0.0F, 0.0F, 0.0F, 1.0F);
   public GDirectedGame game;
   public boolean isDebug = false;

   public static int getX() {
      return Gdx.input.getX();
   }

   public static int getX(int var0) {
      return Gdx.input.getX(var0);
   }

   public static int getY() {
      return Gdx.input.getY();
   }

   public static int getY(int var0) {
      return Gdx.input.getY(var0);
   }

   private void initGestureProcessor() {
      GStage.getStage().addCaptureListener(new ActorGestureListener() {
         public void fling(InputEvent var1, float var2, float var3, int var4) {
            GScreen.this.gFling(var2, var3, var4);
         }

         public boolean longPress(Actor var1, float var2, float var3) {
            return GScreen.this.gLongPress(var2, var3);
         }

         public void pan(InputEvent var1, float var2, float var3, float var4, float var5) {
            GScreen.this.gPan(var2, var3, var4, var5);
         }

         public void pinch(InputEvent var1, Vector2 var2, Vector2 var3, Vector2 var4, Vector2 var5) {
            GScreen.this.gPinch(var2, var3, var4, var5);
         }

         public void tap(InputEvent var1, float var2, float var3, int var4, int var5) {
            GScreen.this.gTap(var2, var3, var4, var5);
         }

         public void zoom(InputEvent var1, float var2, float var3) {
            GScreen.this.gZoom(var2, var3);
         }
      });
   }

   private void initInputProcessor() {
      GStage.getStage().addListener(new InputListener() {
         public void enter(InputEvent var1, float var2, float var3, int var4, Actor var5) {
            GScreen.this.gEnter(var1, var2, var3, var4, var5);
         }

         public void exit(InputEvent var1, float var2, float var3, int var4, Actor var5) {
            GScreen.this.gExit(var1, var2, var3, var4, var5);
         }

         public boolean keyDown(InputEvent var1, int var2) {
            return GScreen.this.gKeyDown(var2);
         }

         public boolean keyTyped(InputEvent var1, char var2) {
            return GScreen.this.gKeyTyped(var2);
         }

         public boolean keyUp(InputEvent var1, int var2) {
            return GScreen.this.gKeyUp(var2);
         }

         public boolean mouseMoved(InputEvent var1, float var2, float var3) {
            return GScreen.this.gMouseMoved(var1, var2, var3);
         }

         public boolean scrolled(InputEvent var1, float var2, float var3, int var4) {
            return GScreen.this.gScrolled(var1, var2, var3, var4);
         }

         public boolean touchDown(InputEvent var1, float var2, float var3, int var4, int var5) {
            return GScreen.this.gTouchDown((int)var2, (int)var3, var4, var5);
         }

         public void touchDragged(InputEvent var1, float var2, float var3, int var4) {
            GScreen.this.gTouchDragged((int)var2, (int)var3, var4);
         }

         public void touchUp(InputEvent var1, float var2, float var3, int var4, int var5) {
            GScreen.this.gTouchUp((int)var2, (int)var3, var4, var5);
         }
      });
   }

   public static boolean isJustTouched() {
      return Gdx.input.justTouched();
   }

   public static boolean isKeyPressed(int var0) {
      return Gdx.input.isKeyPressed(var0);
   }

   public static boolean isTouched() {
      return Gdx.input.isTouched();
   }

   public static boolean isTouched(int var0) {
      return Gdx.input.isTouched(var0);
   }

   public void debugPaint(String var1) {
      if(this.isDebug) {
         GMain.platform.log(var1);
      }

   }

   public abstract void dispose();

   public void gEnter(InputEvent var1, float var2, float var3, int var4, Actor var5) {
   }

   public void gExit(InputEvent var1, float var2, float var3, int var4, Actor var5) {
   }

   public boolean gFling(float var1, float var2, int var3) {
      this.debugPaint("fling 翻页动作 .............");
      return false;
   }

   public boolean gKeyDown(int var1) {
      return false;
   }

   public boolean gKeyTyped(char var1) {
      return false;
   }

   public boolean gKeyUp(int var1) {
      return false;
   }

   public boolean gLongPress(float var1, float var2) {
      this.debugPaint("longPress 长按 .............");
      return false;
   }

   public boolean gMouseMoved(InputEvent var1, float var2, float var3) {
      return false;
   }

   public boolean gPan(float var1, float var2, float var3, float var4) {
      this.debugPaint("pan 划屏动作.............");
      return false;
   }

   public boolean gPinch(Vector2 var1, Vector2 var2, Vector2 var3, Vector2 var4) {
      this.debugPaint("pan 手指捏的动作.............");
      return false;
   }

   public boolean gScrolled(InputEvent var1, float var2, float var3, int var4) {
      return false;
   }

   public boolean gTap(float var1, float var2, int var3, int var4) {
      this.debugPaint("tap 快速点击动作............." + var3);
      return false;
   }

   public boolean gTouchDown(int var1, int var2, int var3, int var4) {
      return false;
   }

   public void gTouchDragged(int var1, int var2, int var3) {
   }

   public void gTouchUp(int var1, int var2, int var3, int var4) {
   }

   public boolean gZoom(float var1, float var2) {
      this.debugPaint("zoom.............");
      return false;
   }

   public void hide() {
      this.dispose();
      GStage.clearAllLayers();
   }

   public abstract void init();

   public boolean isTransitionEnd() {
      return this.game.isTransitionEnd();
   }

   public void pause() {
      GSound.pause();
   }

   public void render(float var1) {
      this.run();
      GStage.render();
   }

   public void resize(int var1, int var2) {

      //GStage.updateViewport(var1, var2);
      //viewportW = var1;//480;//Gdx.graphics.getWidth();
      //viewportH =var2;//848;// Gdx.graphics.getHeight();
   }

   public void resume() {
      GSound.resume();
   }

   public abstract void run();

   public void setColor(Color var1) {
      this.color = var1;
   }

   protected void setGameInstance(GDirectedGame var1) {
      this.game = var1;
   }

   public void setScreen(GScreen var1) {
      this.game.setScreen(var1);
   }

   public void setScreen(GScreen var1, GTransition var2) {
      this.game.setScreen(var1, var2);
   }

   public void show() {
      GStage.setClearColor(this.color);
      GStage.clearListeners();
      this.initGestureProcessor();
      this.initInputProcessor();
      this.init();
   }
}
