package com.ariel.android;

import android.os.Bundle;
import com.ariel.MainGame;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {

    ActionResolverAndroid resolverAndroid;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        resolverAndroid = new ActionResolverAndroid(this);
		initialize(new MainGame(resolverAndroid), config);
	}
}
