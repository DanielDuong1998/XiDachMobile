package com.ss.gdx;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.SynchronousAssetLoader;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class NParticleEffectLoader extends SynchronousAssetLoader<NParticleEffect, NParticleEffectLoader.NParticleEffectParameter> {
    public NParticleEffectLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    @Override
    public NParticleEffect load(AssetManager am, String fileName, FileHandle file, NParticleEffectParameter param) {
        NParticleEffect effect = new NParticleEffect();
        if (param != null && param.atlasFile != null)
            effect.load(file, am.get(param.atlasFile, TextureAtlas.class), param.atlasPrefix);
        else if (param != null && param.imagesDir != null)
            effect.load(file, param.imagesDir);
        else
            effect.load(file, file.parent());
        return effect;
    }

    @Override
    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, NParticleEffectParameter param) {
        Array<AssetDescriptor> deps = null;
        if (param != null && param.atlasFile != null) {
            deps = new Array();
            deps.add(new AssetDescriptor<TextureAtlas>(param.atlasFile, TextureAtlas.class));
        }
        return deps;
    }

    public static class NParticleEffectParameter extends AssetLoaderParameters<NParticleEffect> {
        /**
         * Atlas file name.
         */
        public String atlasFile;
        /**
         * Optional prefix to image names
         **/
        public String atlasPrefix;
        /**
         * Image directory.
         */
        public FileHandle imagesDir;
    }
}
