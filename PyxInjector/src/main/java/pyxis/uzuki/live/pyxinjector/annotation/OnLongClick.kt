package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: OnLongClick
 * Created by Pyxis on 2017-10-23.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class OnLongClick(val value: Int, val defaultReturn: Boolean =  false)