package com.me.deepblue;

import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.swarmconnect.Swarm;

public class MainActivity extends AndroidApplication {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useGL20 = true;
        
        initialize(new DeepBlue(), cfg);
        
        Swarm.setActive(this);
    }
    
    public void onResume() {
        super.onResume();
        Swarm.setActive(this);
        Swarm.init(this, 10448, "b13bb9dc6f4b4523737fae7593723675");
    }

    public void onPause() {
        super.onPause();
        Swarm.setInactive(this);
    }
}