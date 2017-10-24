package pyxis.uzuki.live.pyxinjector.exception

/**
 * PyxInjector
 * Class: InvalidAnnotationException
 * Created by Pyxis on 2017-10-24.
 */
class InvalidAnnotationException(override var message: String) : Exception()

fun throwException(message: String) {
    throw InvalidAnnotationException(message)
}

val EXCEPT_TWO_PARAMETER = "Invalid Annotation Methods. Except 2 Parameter, Valid format is %s"
val CASTING_FAILED_VIEW_ID = "Auto cast is failed. Are you sure type of View ID is %s ?"