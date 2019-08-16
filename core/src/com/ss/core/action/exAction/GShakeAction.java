package com.ss.core.action.exAction;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class GShakeAction extends TemporalAction {
  private float fluctuation;
  private float originX;
  private float originY;
  private Actor actor;

  public static GShakeAction shakeAction(float duration, float fluctuation, Interpolation easing) {
    GShakeAction instance = Actions.action(GShakeAction.class);
    instance.reset();
    instance.fluctuation = fluctuation;
    instance.setDuration(duration);
    instance.setInterpolation(easing);
    return instance;
  }

  @Override
  protected void begin() {
    super.begin();
    originX = getActor().getX();
    originY = getActor().getY();
    actor = getActor();
  }

  @Override
  protected void update(float percent) {
    float f = MathUtils.random(-fluctuation, fluctuation);
    actor.setPosition(originX + f, originY + f);
    if (percent == 1)
      actor.setPosition(originX, originY);
  }
}
