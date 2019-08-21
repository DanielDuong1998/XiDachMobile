package com.ss.core.action.exAction;

import com.badlogic.gdx.math.CatmullRomSpline;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class GPathAction extends Action {
  private CatmullRomSpline<Vector2> path;
  private float speed;
  private float current;
  private Vector2 out;
  private Vector2 der;

  public static GPathAction init(Vector2[] controlPoints, float speed) {
    GPathAction instance = Actions.action(GPathAction.class);
    instance.path = new CatmullRomSpline<>(controlPoints,true);
    instance.speed = speed;
    instance.current = 0;
    instance.out = new Vector2(0,0);
    instance.der = new Vector2(0,0);
    return  instance;
  }

  @Override
  public boolean act(float delta) {
    current += delta*speed;
    if (current >= 1)
      return true;

    path.valueAt(out, current);
    path.derivativeAt(der, current);
    actor.setPosition(out.x, out.y);
    actor.setRotation(der.angle() + 90);

    return false;
  }
}