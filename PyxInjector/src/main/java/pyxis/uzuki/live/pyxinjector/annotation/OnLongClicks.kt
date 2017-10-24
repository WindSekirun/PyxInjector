package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: OnLongClicks
 * Created by Pyxis on 2017-10-23.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class OnLongClicks(val defaultReturn: Boolean = false, vararg val value: Int)