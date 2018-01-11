package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: OnClicks
 * Created by Pyxis on 2017-10-23.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class OnClicks(vararg val value: Int, val preventDouble: Boolean = false)