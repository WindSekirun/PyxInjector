package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: BindView
 * Created by Pyxis on 2017-10-23.
 */

@Target(AnnotationTarget.FIELD)
annotation class BindView(val value: Int = 0)