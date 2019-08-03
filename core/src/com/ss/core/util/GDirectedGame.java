package com.ss.core.util;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.ss.core.transitions.GTransition;
import com.ss.core.util.GScreen;
import com.ss.core.util.GStage;

public abstract class GDirectedGame implements ApplicationListener {
   private static boolean isUseTransition = true;
   private SpriteBatch batch;
   private FrameBuffer currFbo;
   private GScreen currScreen;
   private boolean isTransitionEnd;
   private FrameBuffer nextFbo;
   private GTransition screenTransition;
   private float time;

   public void dispose() {
      if(this.currScreen != null) {
         this.currScreen.hide();
      }

      if(this.batch != null) {
         this.currFbo.dispose();
         this.currScreen = null;
         this.nextFbo.dispose();
         this.batch.dispose();
         this.batch = null;
      }

   }

   public boolean isTransitionEnd() {
      return this.isTransitionEnd;
   }

   public void pause() {
      if(this.currScreen != null) {
         this.currScreen.pause();
      }

   }

   public void render() {
      if(!this.isTransitionEnd && isUseTransition && this.screenTransition != null) {
         float var1 = Math.min(GStage.getDelta(), GStage.getSleepTime());
         float var2 = this.screenTransition.duration;
         this.time = Math.min(this.time + var1, var2);
         if(this.time >= var2) {
            this.screenTransition = null;
            this.isTransitionEnd = true;
            GStage.updateViewport(480, 848);
         } else {
            this.nextFbo.begin();
            this.currScreen.render(var1);
            this.nextFbo.end();
            var1 = this.time / var2;
            GStage.updateViewport(480, 848);
            this.screenTransition.render(this.batch, this.currFbo.getColorBufferTexture(), this.nextFbo.getColorBufferTexture(), var1);
         }
      } else {
         this.currScreen.render(0.0F);
      }
   }

   public void resize(int var1, int var2) {
      GStage.updateViewport(var1, var2);
      if(this.currScreen != null) {
         this.currScreen.resize(var1, var2);
      }
   }

   public void resume() {
      if(this.currScreen != null) {
         this.currScreen.resume();
      }

   }

   public void setScreen(GScreen var1) {
      this.setScreen(var1, (GTransition)null);
   }

   public void setScreen(GScreen var1, GTransition var2) {
      System.gc();
      var1.setGameInstance(this);
      if(isUseTransition && var2 != null) {
         int var3 = Gdx.graphics.getWidth();
         int var4 =  Gdx.graphics.getHeight();
         this.isTransitionEnd = false;
         if(this.batch == null) {
            this.batch = new SpriteBatch(2);
            this.currFbo = new FrameBuffer(Pixmap.Format.RGB565, var3, var4, false);
            this.nextFbo = new FrameBuffer(Pixmap.Format.RGB565, var3, var4, false);
          //  this.currFbo =  FrameBuffer.createFrameBuffer(Pixmap.Format.RGB565, var3, var4, false);
           // this.nextFbo =  FrameBuffer.createFrameBuffer(Pixmap.Format.RGB565, var3, var4, false);
         }

         if(this.currScreen != null) {
            this.currFbo.begin();
            this.currScreen.render(0.0F);
            this.currFbo.end();
            this.currScreen.hide();
            GStage.updateViewport(480, 848);
         }

         this.currScreen = var1;
         this.currScreen.show();
         this.currScreen.resize(var3, var4);
         this.screenTransition = var2;
         this.time = 0.0F;
      } else {
         if(this.currScreen != null) {
            this.currScreen.hide();
         }

         this.currScreen = var1;
         this.currScreen.show();
         this.isTransitionEnd = true;
      }
   }

   public void setUseTransition(boolean var1) {
      isUseTransition = var1;
   }
}
