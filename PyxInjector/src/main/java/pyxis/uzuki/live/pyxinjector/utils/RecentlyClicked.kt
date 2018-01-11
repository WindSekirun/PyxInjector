package pyxis.uzuki.live.pyxinjector.utils

import android.os.SystemClock
import android.util.SparseLongArray
import android.view.View
import pyxis.uzuki.live.pyxinjector.R
import java.util.concurrent.atomic.AtomicInteger


/**
 * PyxInjector
 * Class: RecentlyClicked
 * Created by Pyxis on 2018-01-11.
 *
 * Description:
 */

object RecentlyClicked {
    private val mLastClickedTimes = SparseLongArray()
    private val sNextGeneratedTagId = AtomicInteger(1)
    private var mClickedDelay = 600
    private var mPreventDouble = false

    fun getPreventDoubleFeature() = mPreventDouble

    @JvmStatic @JvmOverloads fun isRecentlyClicked(view: View, time: Int = mClickedDelay): Boolean {
        val viewTagId = view.getTagId()
        val lastClickedTime = mLastClickedTimes.get(viewTagId, -1)
        val currentTime = SystemClock.elapsedRealtime()
        return if (lastClickedTime == -1L || lastClickedTime + time < currentTime) {
            mLastClickedTimes.put(viewTagId, currentTime)
            return false
        } else {
            true
        }
    }

    @JvmStatic fun setRecentlyClickedDelay(time: Int) {
        this.mClickedDelay = time
    }

    @JvmStatic fun setPreventDoubleFeature(flag: Boolean) {
        mPreventDouble = flag
    }

    private fun View.getTagId(): Int = (this.getTag(R.id.PYXINJECTOR_LAST_CLICKED_TAG) as Int)
            .getOrDefault(sNextGeneratedTagId.incrementAndGet())

    private fun <T> T.getOrDefault(default: T) : T = this ?: default
}