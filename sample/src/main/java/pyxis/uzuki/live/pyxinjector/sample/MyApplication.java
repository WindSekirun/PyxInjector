package pyxis.uzuki.live.pyxinjector.sample;

import android.app.Application;

import pyxis.uzuki.live.pyxinjector.PyxInjector;
import pyxis.uzuki.live.pyxinjector.config.BindViewPrefix;
import pyxis.uzuki.live.pyxinjector.config.Config;

/**
 * PyxInjector
 * Class: MyApplication
 * Created by Pyxis on 2017-10-23.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Config config = new Config(BindViewPrefix.PREFIX_M);

        PyxInjector.initializeApplication(config);
    }
}
