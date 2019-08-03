package com.ss.core.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Mesh;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.Sort;
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.FitViewport;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ss.core.util.GLayer;
import com.ss.core.util.GLayerGroup;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Vector;

public final class GStage {
   private static int avgFps;
   private static SpriteBatch batch;
   private static GStage.StageBorder border;
   private static StringBuilder buff;
   private static float cameraOffsetX;
   private static float cameraOffsetY;
   private static Color clearColor;
   private static float delta;
   private static BitmapFont font;
   private static int fps;
   private static int fpsCount;
   private static double gameTime;
   private static long javaHeap;
   private static long lastIndex;
   private static int maxSpritesInBatch;
   private static long nativeHeap;
   private static boolean pauseUpdateServices;
   private static int renderCalls;
   public static ShaderProgram shader;
   private static float sleepTime;
   private static Stage stage;
   private static int stageActorcount;
   private static int timeCount;
   private static int totalRenderCalls;
   private static ObjectMap updateServices = new ObjectMap();

   static {
      clearColor = Color.BLACK;
      sleepTime = 0.033333335F;
      lastIndex = 0L;
      fps = 60;
      avgFps = 60;
      buff = new StringBuilder();
      pauseUpdateServices = false;
   }

   private static String actorPath(Actor var0) {
      StringBuffer var1 = new StringBuffer();
      var1.append(var0);

      for(Group var2 = var0.getParent(); var2 != null; var2 = var2.getParent()) {
         var1.append("<---" + var2);
      }

      return var1.toString();
   }

   public static void addToLayer(GLayer var0, Actor var1) {
      getLayer(var0).addActor(var1);
   }

   public static void clearAllLayers() {
      GLayer[] var1 = GLayer.values();

      for(int var0 = 0; var0 < var1.length; ++var0) {
         clearLayer(var1[var0]);
      }

      //GPool.freeAll();
      System.gc();
   }

   public static void clearLayer(GLayer var0) {
      getLayer(var0).clear();
   }

   public static void clearListeners() {
      getStageLayer().clearListeners();
   }

   public static void clearStage() {
      clearAllLayers();
      stage.unfocusAll();
   }

   private static void createLayer(GLayer var0) {
      GLayerGroup var1 = new GLayerGroup();
      var0.init(var1);
      stage.addActor(var1);
   }

   public static void dispose() {
      stage.dispose();
      batch.dispose();
   }

   public static Actor getActorAtLayer(GLayer var0, String var1) {
      return getLayer(var0).findActor(var1);
   }


   public static OrthographicCamera getCamera() {
      return (OrthographicCamera)stage.getCamera();
   }

   public static float getCameraOffsetX() {
      return cameraOffsetX;
   }

   public static float getCameraOffsetY() {
      return cameraOffsetY;
   }

   public static float getDelta() {
      return delta;
   }

   public static int getHeight() {
      return (int)stage.getHeight();
   }

   public static int getIndex() {
      return (int)(gameTime / (double)sleepTime);
   }

   public static GLayerGroup getLayer(GLayer var0) {
      return var0.getGroup();
   }

   public static float getSleepTime() {
      return sleepTime;
   }

   public static Stage getStage() {
      return stage;
   }

   private static Vector getStageActor(Group var0) {
      Vector var2 = new Vector();
      SnapshotArray var4 = var0.getChildren();
      var4.begin();

      for(int var1 = 0; var1 < var4.size; ++var1) {
         Actor var3 = (Actor)var4.get(var1);
         if(var3 instanceof Group) {
            var2.addAll(getStageActor((Group)var3));
         } else if(var3.getName() == null || !var3.getName().equals("debugActor")) {
            var2.add(var3);
         }
      }

      var4.end();
      return var2;
   }

   public static float getStageHeight() {
      return stage.getHeight();
   }

   public static Group getStageLayer() {
      return stage.getRoot();
   }

   public static float getStageWidth() {
      return stage.getWidth();
   }

   public static int getWidth() {
      return (int)stage.getWidth();
   }


   static public ShaderProgram createDefaultShader () {
      String vertexShader = "attribute vec4 " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
              + "attribute vec4 " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
              + "attribute vec2 " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
              + "uniform mat4 u_projTrans;\n" //
              + "varying vec4 v_color;\n" //
              + "varying vec2 v_texCoords;\n" //
              + "\n" //
              + "void main()\n" //
              + "{\n" //
              + "   v_color = " + ShaderProgram.COLOR_ATTRIBUTE + ";\n" //
              + "   v_color.a = v_color.a * (255.0/254.0);\n" //
              + "   v_texCoords = " + ShaderProgram.TEXCOORD_ATTRIBUTE + "0;\n" //
              + "   gl_Position =  u_projTrans * " + ShaderProgram.POSITION_ATTRIBUTE + ";\n" //
              + "}\n";
      String fragmentShader = "#ifdef GL_ES\n" //
              + "#define LOWP lowp\n" //
              + "precision mediump float;\n" //
              + "#else\n" //
              + "#define LOWP \n" //
              + "#endif\n" //
              + "varying LOWP vec4 v_color;\n" //
              + "varying vec2 v_texCoords;\n" //
              + "uniform sampler2D u_texture;\n" //
              + "void main()\n"//
              + "{\n" //
              + "  gl_FragColor = v_color * texture2D(u_texture, v_texCoords);\n" //
              + "}";

      ShaderProgram shader = new ShaderProgram(vertexShader, fragmentShader);
      if (shader.isCompiled() == false) throw new IllegalArgumentException("Error compiling shader: " + shader.getLog());
      return shader;
   }

   public static void init(float width, float height, float camera_offset_x, float camera_offset_y, GStage.StageBorder var4) {
      //Mesh.forceVBO = true;
     // if(Gdx.graphics.isGL20Available()) {
      shader = createDefaultShader();//SpriteBatch.createDefaultShader();
      //}

      cameraOffsetX = camera_offset_x;
      cameraOffsetY = camera_offset_y;
      border = var4;
      batch = new SpriteBatch(3000, shader);
      Viewport viewport = new FitViewport(width, height);
      //viewport = new ExtendViewport(width, height);
      //viewport = new View(width, height);
      stage = new Stage( viewport, batch);
      getCamera().setToOrtho(true, width, height);
      getCamera().translate(cameraOffsetX, cameraOffsetY);
      Gdx.input.setInputProcessor(stage);
      Gdx.input.setCatchBackKey(true);
      initLayers();
      initDebug();
   }

   private static void initDebug() {
   }

   private static void initLayers() {
      GLayer[] var1 = GLayer.values();

      for(int var0 = 0; var0 < var1.length; ++var0) {
         createLayer(var1[var0]);
      }

   }

   public static boolean isUpdate() {
      boolean var0;
      if((long)getIndex() - lastIndex >= 1L) {
         var0 = true;
      } else {
         var0 = false;
      }

      if(var0) {
         lastIndex = (long)getIndex();
      }

      return var0;
   }

   public static boolean isUpdateServicesPause() {
      return pauseUpdateServices;
   }

   public static void registerUpdateService(String var0, GStage.GUpdateService var1) {
      if(var1 != null) {
         updateServices.put(var0, var1);
      } else {
         updateServices.remove(var0);
      }
   }

   public static void removeActor(GLayer var0, Actor var1) {
      getLayer(var0).removeActor(var1);
   }

   public static void render() {
     // stage.setDebugAll(true);

      float var0 = clearColor.r;
      float var1 = clearColor.g;
      float var2 = clearColor.b;
      float var3 = clearColor.a;
      Gdx.gl.glClearColor(var0, var1, var2, var3);
      Gdx.gl.glClear(16384);
      delta = Gdx.graphics.getDeltaTime();
      sortLayers();
      updateServices(delta);
      stage.act(delta);

      stage.draw();
      if(border != null) {
         batch.begin();
         border.drawVerticalBorder(batch, getCamera().viewportHeight, cameraOffsetX);
         border.drawHorizontalBorder(batch, getCamera().viewportWidth, cameraOffsetY);
         batch.end();
      }

      gameTime += (double)delta;
   }

   public static void setClearColor(Color var0) {
      clearColor = var0;
   }

   public static void setSleepTime(long var0) {
      sleepTime = (float)var0;
   }

   public static void setUpdateServicesPause(boolean var0) {
      pauseUpdateServices = var0;
   }

   /*protected static void setViewport(float var0, float var1) {
      stage.setViewport(var0, var1);
   }*/

   public static void updateViewport(int width, int height){
      //stage.getViewport().update(width, height, true);
      stage.getViewport().update(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), true);

   }

   public static void test(int width, int height){
      //getCamera().setToOrtho(true, width, height);
      stage.getViewport().getCamera().translate(-100, 0, 0);
     // getCamera().translate(-(Gdx.graphics.getWidth()- 484), 0);
   }
   private static void sleep(long var0) {
   }

   public static void sort(Group var0, Comparator var1) {
      Sort.instance().sort((Array)var0.getChildren(), var1);
   }

   public static void sort(GLayer var0) {
      sort(getLayer(var0), var0.getComparator());
   }

   private static void sortLayers() {
      GLayer[] var1 = GLayer.values();

      for(int var0 = 0; var0 < var1.length; ++var0) {
         if(var1[var0].getComparator() != null) {
            sort(var1[var0]);
         }
      }

   }

   private static void updateServices(float var0) {
      if(!pauseUpdateServices) {
         Iterator var1 = updateServices.values().iterator();

         while(var1.hasNext()) {
            GStage.GUpdateService var2 = (GStage.GUpdateService)var1.next();
            if(var2.update(var0)) {
               updateServices.remove((String)updateServices.findKey(var2, true));
            }
         }
      }

   }

   public TextureRegion getScreenSnapshot() {
      return ScreenUtils.getFrameBufferTexture();
   }

   public interface GUpdateService {
      boolean update(float var1);
   }

   public interface StageBorder {
      void drawHorizontalBorder(Batch var1, float var2, float var3);

      void drawVerticalBorder(Batch var1, float var2, float var3);
   }
}
