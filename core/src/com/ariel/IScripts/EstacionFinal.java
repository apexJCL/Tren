package com.ariel.IScripts;

import com.ariel.Stages.HUD;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by apex on 12/7/15.
 */
public class EstacionFinal implements IScript {

    private TransformComponent transformComponent;
    private boolean finished = false;
    private final Tren tren;
    private Entity entity;
    private Stage stage;

    public EstacionFinal(Tren tren, Stage stage){
        this.tren = tren;
        this.stage = stage;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
    }

    @Override
    public void act(float delta) {
        if (tren.getY() >= transformComponent.y && !tren.isAccelerating() && !finished) {
            tren.notifyEnd();
            ((HUD)stage).showFinishedMessage();
            finished = !finished;
        }
    }

    @Override
    public void dispose() {

    }

}
