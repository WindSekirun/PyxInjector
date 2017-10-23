package pyxis.uzuki.live.pyxinjector.base

import android.support.v7.app.AppCompatActivity
import pyxis.uzuki.live.pyxinjector.PyxInjector

/**
 * PyxInjector
 * Class: InjectorActivity
 * Created by Pyxis on 2017-10-23.
 */
open class InjectorActivity : AppCompatActivity() {

    protected val injector = PyxInjector()

    override fun onContentChanged() {
        super.onContentChanged()
        injector.execute(this, findViewById(android.R.id.content))
    }
}