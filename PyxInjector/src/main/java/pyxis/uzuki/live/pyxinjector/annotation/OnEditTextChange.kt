package pyxis.uzuki.live.pyxinjector.annotation

/**
 * PyxInjector
 * Class: OnEditTextChange
 * Created by Pyxis on 2017-10-24.
 */

@Target(AnnotationTarget.FUNCTION)
annotation class OnEditTextChange(val value: Int, val trigger: EditTextChangeTrigger = EditTextChangeTrigger.TEXT)

enum class EditTextChangeTrigger {
    BEFORE, AFTER, TEXT
}