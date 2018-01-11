package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: OnClick
 * Created by Pyxis on 2017-10-23.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class OnClick(val value: Int, val preventDouble: Boolean = false, val clickedTime: Int = 600)