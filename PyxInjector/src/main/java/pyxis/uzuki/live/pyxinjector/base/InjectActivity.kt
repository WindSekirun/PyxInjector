package pyxis.uzuki.live.pyxinjector.base

import android.support.v7.app.AppCompatActivity
import pyxis.uzuki.live.pyxinjector.PyxInjector

/**
 * PyxInjector
 * Class: InjectActivity
 * Created by Pyxis on 2017-10-23.
 */
open class InjectActivity : AppCompatActivity() {

    protected val injector = PyxInjector()

    override fun onContentChanged() {
        super.onContentChanged()
        injector.execute(this, this, findViewById(android.R.id.content))
    }
}