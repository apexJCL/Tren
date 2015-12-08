package com.ariel.IScripts;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.math.Rectangle;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Created by apex on 12/7/15.
 */
public class EstacionFinal implements IScript {

    private TransformComponent transformComponent;
    private final Tren tren;
    private Entity entity;

    public EstacionFinal(Tren tren){
        this.tren = tren;
    }

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
    }

    @Override
    public void act(float delta) {
        //if (tren.getY() >= transformComponent.y && !tren.isAccelerating())
    }

    @Override
    public void dispose() {

    }

}
