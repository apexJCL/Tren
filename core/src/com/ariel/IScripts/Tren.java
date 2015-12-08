package com.ariel.IScripts;

import com.ariel.Config;
import com.ariel.Stages.HUD;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.uwsoft.editor.renderer.components.DimensionsComponent;
import com.uwsoft.editor.renderer.components.TransformComponent;
import com.uwsoft.editor.renderer.utils.ComponentRetriever;

/**
 * Esta clase controla el riel, que es el que simula el movimiento del tren
 */
public class Tren extends Item {

    private DimensionsComponent dimensionsComponent;
    private TransformComponent transformComponent;
    private final OrthographicCamera camera;
    private boolean arrived = false;
    private boolean isAccelerating = false;
    private boolean begin = false;
    private long startTime;
    private final HUD stage;
    private Actor joystick;
    // Variables personalizadas
    private float accel_sensibility;
    private float accel_deadzone;
    private float distance = 0;
    private float max_reverse;
    private float max_speed;
    private float zero_pos;
    private int vibration;
    private Config.Input controls;
    private Rectangle boundBox;

    public Tren(OrthographicCamera camera, HUD stage){
        this.camera = camera;
        this.stage = stage;
        // Cargamos los ajustes
        Config cfg = Config.Load();
        vibration = cfg.getVibration();
        controls = cfg.getInputType();
        switch (cfg.getInputType()){
            case ACCELEROMETER:
                accel_sensibility = cfg.getAccel_sensibility();
                accel_deadzone = cfg.getAccel_deadzone();
                break;
            case JOYSTICK:
                this.joystick = stage.getJoystick().getItem("joystick_ball"); // Aqui obtenemos la bola del joystick que es la importante
                // cargamos la posicion 0
                zero_pos = joystick.getY();
                break;
        }
    }

    @Override
    public void init(Entity entity) {
        super.init(entity); // Le decimos que llame a super, el cual se encarga de inicializar los objetos
        // Aqui se usa el Component Retriever para obtener la componente que controla la posicion del sprite en pantalla
        // En este caso, es TransformComponent
        transformComponent = ComponentRetriever.get(entity, TransformComponent.class);
        // Aqui obtenemos su componente de dimensiones
        dimensionsComponent = ComponentRetriever.get(entity, DimensionsComponent.class);
        boundBox = dimensionsComponent.boundBox;
        // Aqui cargamos las variables definidas, dependiendo de cual sea, sera el metodo que se mandara llamar
        max_speed = getFloatVariable("max_accel");
        max_reverse = getFloatVariable("max_reverse");
    }

    @Override
    public void act(float delta) {
        if(!begin) {
            begin = !begin;
            startTime = TimeUtils.millis();
        }
        else{
            if(!arrived)
                stage.setValorTiempo(TimeUtils.timeSinceMillis(startTime));
        }
        switch (type){
            case Android:
                AndroidControls(delta);
                break;
            case HeadlessDesktop:
            case Desktop:
                DesktopControls(delta);
                break;
        }
        stage.setValorDistancia((int)distance);
        camera.position.lerp(new Vector3(camera.position.x, transformComponent.y + dimensionsComponent.height/2f, 0), 1);
    }

    @Override
    public void dispose() {

    }

    private void DesktopControls(float delta) {
        switch (controls){
            case ACCELEROMETER: // Aqui el acelerometro funciona con las flechas
                if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                    transformComponent.y += delta * max_speed;
                    distance += delta * max_speed;
                    isAccelerating = true;
                }
                else if(Gdx.input.isKeyPressed(Input.Keys.DOWN) && transformComponent.y > 0) {
                    transformComponent.y -= delta * max_reverse;
                    distance -= delta * max_reverse;
                    isAccelerating = true;
                }
                else
                    isAccelerating = false;
                if(transformComponent.y <=0 )
                    distance = 0;
                break;
            case JOYSTICK:
                ControlJoystick(delta);
                break;
        }
    }

    private void AndroidControls(float delta){
        switch (controls){
            case ACCELEROMETER:
                if(Math.abs(Gdx.input.getAccelerometerY()) > accel_deadzone){
                    if(Gdx.input.getAccelerometerY() > 0 ){
                        transformComponent.y += delta * Gdx.input.getAccelerometerY() * accel_sensibility;
                        Gdx.input.vibrate((int) (vibration *delta));
                        isAccelerating = true;
                    }
                    else if(Gdx.input.getAccelerometerY() < 0 && transformComponent.y > 0) {
                        transformComponent.y += delta * Gdx.input.getAccelerometerY() * accel_sensibility;
                        Gdx.input.vibrate((int) (vibration *delta));
                        isAccelerating = true;
                    }
                    else
                        isAccelerating = false;

                    if (transformComponent.y <= 0)
                        distance = 0;
                }
                distance += delta * Gdx.input.getAccelerometerY() * accel_sensibility;
                break;
            case JOYSTICK:
                ControlJoystick(delta);
                break;
        }
    }

    /**
     * Se encarga de unificar el control del joystick, ya que en ambas plataformas es igual
     * @param delta Tiempo entre cada cuadro
     */
    private void ControlJoystick(float delta){
        if(transformComponent.y > 0 || (delta*(joystick.getY() - zero_pos)/max_speed) > 0) {
            transformComponent.y += delta * ((joystick.getY() - zero_pos) / max_speed);
            distance += delta * ((joystick.getY() - zero_pos) / max_speed);
            isAccelerating = ((joystick.getY() - zero_pos) / max_speed) != 0.0f;
        }
    }

    public float getY() { return  transformComponent.y; }

    public void notifyEnd(){
        arrived = true;
    }

    public boolean isAccelerating(){ return isAccelerating; }
}
