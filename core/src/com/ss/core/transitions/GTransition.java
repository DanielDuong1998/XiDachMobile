package com.ss.core.transitions;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public abstract class GTransition {
   public float duration;

   public abstract void render(Batch var1, Texture var2, Texture var3, float var4);
}
