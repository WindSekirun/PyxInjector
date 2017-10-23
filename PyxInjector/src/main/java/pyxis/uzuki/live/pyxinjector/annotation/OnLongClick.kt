package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: OnClick
 * Created by Pyxis on 2017-10-23.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class OnLongClick(val resource: Int, val defaultReturn: Boolean =  false)