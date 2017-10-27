@file:JvmName("PyxUtils")
@file:JvmMultifileClass

package pyxis.uzuki.live.pyxinjector.utils

import android.app.Activity
import android.view.View

/**
 * PyxInjector
 * Class: Utils
 * Created by Pyxis on 2017-10-27.
 *
 * Description:
 */

fun <T : View?> Activity.content(): T = findViewById<T>(android.R.id.content)