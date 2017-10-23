package pyxis.uzuki.live.pyxinjector.base

import android.app.Fragment
import android.os.Bundle
import android.view.View
import pyxis.uzuki.live.pyxinjector.PyxInjector


/**
 * PyxInjector
 * Class: InjectFragment
 * Created by Pyxis on 2017-10-23.
 */

abstract class InjectFragment : Fragment() {
    protected val injector = PyxInjector()

    abstract fun getCreatedView(): View

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        injector.execute(activity, this, getCreatedView());
    }
}