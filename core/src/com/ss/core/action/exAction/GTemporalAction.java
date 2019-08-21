package com.ss.core.action.exAction;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class GTemporalAction extends TemporalAction {
  private ActInterface at;
  private Actor actor;
  public static GTemporalAction init(float duration, ActInterface at) {
    GTemporalAction instance = Actions.action(GTemporalAction.class);
    instance.setDuration(duration);
    instance.at = at;
    return  instance;
  }

  @Override
  protected void begin() {
    super.begin();
    actor = super.getActor();
  }

  @Override
  protected void update(float percent) {
    at.act(percent, actor);
  }

  @FunctionalInterface
  public interface ActInterface {
    void act(float percent, Actor actor);
  }
}
