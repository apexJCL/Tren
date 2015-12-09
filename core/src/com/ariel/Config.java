package com.ariel;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Json;

/**
 * Esta clase administra la configuracion por defecto/modificada del juego
 */
public class Config {

    // Estos no deben tocarse
    protected static String prefName = "trenes";
    protected static String prefId = "config";
    // ************** Variables del Juego *************** //
    private int vibration = 150; // Fuerza de vibraci;n
    private float accel_sensibility = 1;
    private float accel_deadzone = 1;
    private Input input_type = Input.JOYSTICK; // Tipo de control

    public static Config Load(){
        return new Json().fromJson(Config.class, Gdx.app.getPreferences(prefName).getString(prefId));
    }

    public static String getName() {
        return prefName;
    }

    public enum Input{
        ACCELEROMETER, JOYSTICK
    }

    public Input getInputType() { return  input_type; }
    public void setInputType(Input input_type) { this.input_type = input_type; }

    public int getVibration() { return vibration; }
    public void setVibration(int vibration) { this.vibration = vibration; }

    public float getAccel_sensibility() { return accel_sensibility; }
    public void setAccel_sensibility(float accel_sensibility){ this.accel_sensibility = accel_sensibility; }

    public float getAccel_deadzone() { return accel_deadzone; }
    public void setAccel_deadzone(float accel_deadzone) { this.accel_deadzone = accel_deadzone; }

    public String getInputTypeText(){
        switch (input_type){
            case ACCELEROMETER:
                switch (Gdx.app.getType()){
                    case Android:
                        return "Acelerómetro";
                    default:
                    case Desktop:
                    case HeadlessDesktop:
                        return "Flechas";
                }
            case JOYSTICK:
                return "JoyStick OS";
            default:
                return "Ni puta idea";
        }
    }

    public void switchInput(){
        switch (input_type){
            case ACCELEROMETER:
                input_type = Input.JOYSTICK;
                break;
            case JOYSTICK:
                input_type = Input.ACCELEROMETER;
                break;
        }
    }

    public static void Save(Config config){
        Preferences preferences = Gdx.app.getPreferences(Config.prefName).putString(Config.prefId, new Json().prettyPrint(config));
        preferences.flush();
    }
}
