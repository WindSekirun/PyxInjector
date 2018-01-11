@file:JvmName("PyxUtils")
@file:JvmMultifileClass

package pyxis.uzuki.live.pyxinjector.utils

import android.app.Activity
import android.util.SparseLongArray
import android.view.View
import java.util.concurrent.atomic.AtomicInteger

/**
 * PyxInjector
 * Class: Utils
 * Created by Pyxis on 2017-10-27.
 *
 * Description:
 */

fun <T : View?> Activity.content(): T = findViewById<T>(android.R.id.content)