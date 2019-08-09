package com.ss.core.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class EffectSlide extends Actor{
    private static FileHandle handle = Gdx.files.internal("particleSilde/side");
    private ParticleEffect effect;
    private Actor parent;
    private Group stage;

    public EffectSlide(float x, float y, Group stage) {
        this.parent = parent;
        this.stage = stage;

        effect = new ParticleEffect();
        setX(x);
        setY(y);
        effect.load(handle,Gdx.files.internal("particleSilde"));
        effect.scaleEffect(0.45f,0.4f);
        effect.setPosition(x,y);


    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.setPosition(getX(),getY());
        effect.update(delta);
        effect.start();




    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!effect.isComplete()) {
            effect.draw(batch);
        }
        else {
            effect.dispose();
//            stage.getActions().removeValue(this, true);
            stage.clear();
        }
    }
    public void disposeEcffect(){
        effect.dispose();
    }

    public void start(){

        effect.start();
    }
}