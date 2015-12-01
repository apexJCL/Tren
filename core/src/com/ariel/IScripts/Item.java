package com.ariel.IScripts;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.uwsoft.editor.renderer.components.MainItemComponent;
import com.uwsoft.editor.renderer.scripts.IScript;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;
import com.uwsoft.editor.renderer.utils.CustomVariables;

/**
 * Esta clase se encarga de cargar las variables personalizadas que se le hayan agregado al objeto
 * Preferible extender de esta cuando se cuenten con ellas, sino, implementar simplemente IScript
 */
public class Item extends CustomVariables implements IScript {

    private Entity entity;
    protected Application.ApplicationType type;

    @Override
    public void init(Entity entity) {
        this.entity = entity;
        this.loadFromString(ComponentRetriever.get(entity, MainItemComponent.class).customVars);
        // Define the input type
        type = Gdx.app.getType();
    }

    @Override
    public void act(float delta) {

    }

    @Override
    public void dispose() {

    }
}
