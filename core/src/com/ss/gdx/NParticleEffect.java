package com.ss.gdx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.StreamUtils;
import com.ss.GMain;
import com.ss.core.util.GAssetsManager;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class NParticleEffect extends ParticleEffect {

    public NParticleEffect(){
        super();
    }
    public NParticleEffect (ParticleEffect ef){
        super(ef);
        /*if(ef instanceof  NParticleEffect)
            this.test = ((NParticleEffect)ef).test;*/
    }
    protected ParticleEmitter newEmitter (BufferedReader reader) throws IOException {
        return new NParticleEmitter(reader);
    }

    public void loadEmitters(FileHandle fileHandle) {
        try {
            InputStream inputStream = fileHandle.read();
            this.getEmitters().clear();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream), 512);
            do {
                NParticleEmitter particleEmitter = new NParticleEmitter(bufferedReader);
                bufferedReader.readLine();
                particleEmitter.setImagePath(bufferedReader.readLine());
                this.getEmitters().add(particleEmitter);

            } while (bufferedReader.readLine() != null && bufferedReader.readLine() != null);
            StreamUtils.closeQuietly((Closeable) bufferedReader);
        }
        catch (Exception e){
            GMain.platform.log("load Emitter failed " + fileHandle.name());
        }

    }

    public void loadEmitterImages (TextureAtlas atlas, String atlasPrefix) {
       // super.loadEmitterImages(atlas, atlasPrefix);

        for (int i = 0, n = getEmitters().size; i < n; i++) {
            NParticleEmitter emitter = (NParticleEmitter)getEmitters().get(i);
            String imagePath = emitter.getImagePath();
            Array<Sprite> sprites = new Array<Sprite>();

            String imageName = new File(imagePath.replace('\\', '/')).getName();
            int lastDotIndex = imageName.lastIndexOf('.');
            if (lastDotIndex != -1) imageName = imageName.substring(0, lastDotIndex);
            if (atlasPrefix != null) imageName = atlasPrefix + imageName;
            Sprite sprite = atlas.createSprite(imageName);
            if (sprite == null) throw new IllegalArgumentException("SpriteSheet missing image: " + imageName);
            sprites.add(sprite);

            emitter.setSprites(sprites);
        }
    }




    public void loadEmitterImages (FileHandle imagesDir) {

        super.loadEmitterImages(imagesDir);

        HashMap<String, Sprite> loadedSprites = new HashMap<String, Sprite>(getEmitters().size);
        for (int i = 0, n = getEmitters().size; i < n; i++) {
            NParticleEmitter emitter = (NParticleEmitter)getEmitters().get(i);
           // if (emitter.getImagePaths().size == 0) continue;
            Array<Sprite> sprites = new Array<Sprite>();

            String imagePath = emitter.getImagePath();

            String string = emitter.getImagePath();
            if (string != null) {
                String fileName = string.replace('\\', '/');
                GAssetsManager.DecodeTexture("particle/" + imagesDir.name() + "/"+fileName);
                sprites.add(new Sprite(this.loadTexture(imagesDir.child(new File(fileName).getName()))));
            }
            emitter.setSprites(sprites);
        }
    }


}
