package com.ss.core.effects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;

public class EffectSlide extends Actor{
    private static FileHandle handle = Gdx.files.internal("particleSilde/side");
    private static FileHandle handle1 = Gdx.files.internal("particleSilde/runAround");
    private static FileHandle handle2 = Gdx.files.internal("particleSilde/runAround2");
    private static FileHandle handlewin = Gdx.files.internal("particleSilde/Win");
    private static FileHandle handlelose = Gdx.files.internal("particleSilde/Lose");
    private static FileHandle handletie = Gdx.files.internal("particleSilde/p/Hoa");
    private static FileHandle handlebg = Gdx.files.internal("particleSilde/bg");

    private ParticleEffect effect;
    private Actor parent;
    private Group stage;

    public EffectSlide(String type, float x, float y, Group stage) {
        this.parent = parent;
        this.stage = stage;

        effect = new ParticleEffect();
        setX(x);
        setY(y);
        if(type == "slide"){
            effect.load(handle,Gdx.files.internal("particleSilde"));
            effect.scaleEffect(0.45f,0.4f);
            effect.setPosition(x,y);
        }
        else if(type == "runARound"){
            effect.load(handle1,Gdx.files.internal("particleSilde"));
            effect.scaleEffect(1.5f, 2f);
            effect.setPosition(x,y);
        }
        else if(type == "runARound2"){
            effect.load(handle2, Gdx.files.internal("particleSilde"));
            effect.scaleEffect(1.7f, 2f);
            effect.setPosition(x, y);
        }
        else if(type == "win"){
            effect.load(handlewin, Gdx.files.internal("particleSilde"));
            effect.scaleEffect(1.7f, 2f);
            effect.setPosition(x, y);
            for (int i= 0;i<effect.getEmitters().size;i++){
                effect.getEmitters().get(i).flipY();
            }
        }
        else if(type == "lose"){
            effect.load(handlelose, Gdx.files.internal("particleSilde"));
            effect.scaleEffect(1.7f, 2f);
            effect.setPosition(x, y);
            for (int i= 0;i<effect.getEmitters().size;i++){
                effect.getEmitters().get(i).flipY();
            }
        }
        else if(type == "tie"){
            effect.load(handletie, Gdx.files.internal("particleSilde/p"));
            effect.scaleEffect(1.5f, 2f);
            effect.setPosition(x, y);
            for (int i= 0;i<effect.getEmitters().size;i++){
                effect.getEmitters().get(i).flipY();
            }
        }
        else if(type == "bg"){
            effect.load(handlebg, Gdx.files.internal("particleSilde"));
            effect.scaleEffect(1.5f, 2f);
            effect.setPosition(x, y);
            for (int i= 0;i<effect.getEmitters().size;i++){
                effect.getEmitters().get(i).flipY();
            }
        }



    }

    @Override
    public void act(float delta) {
        super.act(delta);
        effect.setPosition(getX(),getY());
        effect.update(delta);
        // effect.start();




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