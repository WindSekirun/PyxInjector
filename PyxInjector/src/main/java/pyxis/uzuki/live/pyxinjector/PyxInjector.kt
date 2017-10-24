package pyxis.uzuki.live.pyxinjector

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.SeekBar
import pyxis.uzuki.live.pyxinjector.annotation.*
import pyxis.uzuki.live.pyxinjector.config.BindViewPrefix
import pyxis.uzuki.live.pyxinjector.config.Config
import pyxis.uzuki.live.pyxinjector.constants.SupportFragment
import pyxis.uzuki.live.pyxinjector.exception.*
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.lang.reflect.Modifier

/**
 * PyxInjector
 * Class: PyxInjector
 * Created by Pyxis on 2017-10-23.
 */

class PyxInjector {
    private lateinit var context: Context
    private lateinit var receiver: Any
    private lateinit var view: View

    fun execute(context: Context, receiver: Any, view: View) {
        this.receiver = receiver
        this.view = view
        this.context = context

        executeReflection()
    }

    @Suppress("SENSELESS_COMPARISON")
    private fun executeReflection() {
        var cls = receiver.javaClass

        do {
            cls.declaredFields.filter { it.declaredAnnotations.isNotEmpty() }.forEach { field ->
                field.declaredAnnotations.forEach {
                    when (it) {
                        is BindView -> attachFindViewById(it, field)
                        is Extra -> attachExtra(it, field)
                        is Argument -> attachArgument(it, field)
                    }
                }
            }

            cls.declaredMethods.filter { it.declaredAnnotations.isNotEmpty() }.forEach { method ->
                method.declaredAnnotations.forEach {
                    when (it) {
                        is OnClick -> attachClickListener(it.value, method)
                        is OnClicks -> for (i in 0 until it.value.size) {
                            attachClickListener(it.value[i], method)
                        }
                        is OnLongClick -> attachLongClickListener(it.value, method, it.defaultReturn)
                        is OnLongClicks -> for (i in 0 until it.value.size) {
                            attachLongClickListener(it.value[i], method, it.defaultReturn)
                        }
                        is OnSeekbarChange -> attachSeekbarChange(it, method)
                        is OnEditTextChange -> attachEditTextChange(it, method)
                    }
                }
            }

            try {
                cls = cls.getSuperclass()
            } catch (e: Exception) {
                break
            }
        } while (cls != null)
    }

    private fun attachFindViewById(bindView: BindView, field: Field) {
        var id = bindView.value
        if (id == 0) {
            var name = field.name

            if (config.bindViewPrefix == BindViewPrefix.PREFIX_M && name[0] == 'm') {
                name = name.substring(1)
                name = name[0].toLowerCase() + name.substring(1)
            }

            val packageName = this.context.packageName
            id = this.context.resources.getIdentifier(name, "id", packageName)
        }

        field.isAccessible = true
        field.set(receiver, view.findViewById(id))
    }

    private fun attachExtra(extra: Extra, field: Field) {
        if (receiver !is Activity)
            return

        var name = extra.value
        if (name.isEmpty()) {
            name = field.name
        }

        val activity = receiver as Activity
        val bundle = activity.intent.extras

        field.isAccessible = true
        field.set(receiver, bundle.get(name))
    }

    private fun attachArgument(argument: Argument, field: Field) {
        if (!(receiver is Fragment || receiver is SupportFragment))
            return

        var name = argument.value
        if (name.isEmpty()) {
            name = field.name
        }

        when (receiver) {
            is Fragment -> attachFragmentArgument(name, field)
            is SupportFragment -> attachSupportFragmentArgument(name, field)
        }
    }

    private fun attachSupportFragmentArgument(name: String, field: Field) {
        val fragment = receiver as SupportFragment
        val bundle = fragment.arguments

        field.isAccessible = true
        field.set(receiver, bundle.get(name))
    }

    private fun attachFragmentArgument(name: String, field: Field) {
        val fragment = receiver as Fragment
        val bundle = fragment.arguments

        field.isAccessible = true
        field.set(receiver, bundle.get(name))
    }

    private fun attachClickListener(id: Int, method: Method) {
        view.findViewById<View>(id).setOnClickListener {
            val types = method.parameterTypes
            if (types.size == 1 && types[0] == View::class.java) {
                method.isAccessible = true
                method.invoke(receiver, it)
            } else if (types.isEmpty()) {
                method.isAccessible = true
                method.invoke(receiver)
            }
        }
    }

    private fun attachLongClickListener(id: Int, method: Method, returnValue: Boolean) {
        view.findViewById<View>(id).setOnLongClickListener {
            val types = method.parameterTypes
            if (types.size == 1 && types[0] == View::class.java) {
                method.isAccessible = true
                method.invoke(receiver, it)
            } else if (types.isEmpty()) {
                method.isAccessible = true
                method.invoke(receiver)
            }

            returnValue
        }
    }

    private fun attachSeekbarChange(seekbarChange: OnSeekbarChange, method: Method) {
        val seekbar: SeekBar

        try {
            seekbar = view.findViewById(seekbarChange.value)
        } catch (e: Exception) {
            throwException(CASTING_FAILED_VIEW_ID.format("SeekBar"))
            return
        }

        seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onStopTrackingTouch(p0: SeekBar?) {
                // not implemented
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
                // not implemented
            }

            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val types = method.parameterTypes
                if (types.size == 2 && types[0] == Integer::class.javaPrimitiveType && types[1] == Boolean::class.javaPrimitiveType) {
                    method.isAccessible = true
                    method.invoke(receiver, p1, p2)
                } else {
                    val modifier = Modifier.toString(method.modifiers)
                    val name = method.name
                    throwException(EXCEPT_TWO_PARAMETER.format("$modifier void $name(int progress, boolean fromUser)"))
                    return
                }
            }
        })
    }

    private fun attachEditTextChange(editTextChange: OnEditTextChange, method: Method) {
        val editText: EditText

        try {
            editText = view.findViewById(editTextChange.value)
        } catch (e: Exception) {
            throwException(CASTING_FAILED_VIEW_ID.format("EditText"))
            return
        }

        val trigger = editTextChange.trigger
        val types = method.parameterTypes

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                if (trigger != EditTextChangeTrigger.AFTER)
                    return

                if (types.size == 1 && types[0] == EditText::class.java) {
                    method.isAccessible = true
                    method.invoke(receiver, editText)
                } else {
                    val modifier = Modifier.toString(method.modifiers)
                    val name = method.name
                    throwException(EXCEPT_ONE_PARAMETER.format("$modifier void $name(EditText editText)"))
                    return
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (trigger != EditTextChangeTrigger.BEFORE)
                    return

                if (types.size == 5 && types[0] == EditText::class.java && types[1] == CharSequence::class.java &&
                        types[2] == Integer::class.javaPrimitiveType && types[3] == Integer::class.javaPrimitiveType &&
                        types[4] == Integer::class.javaPrimitiveType) {
                    method.isAccessible = true
                    method.invoke(receiver, editText, p0, p1, p2, p3)
                } else {
                    val modifier = Modifier.toString(method.modifiers)
                    val name = method.name
                    throwException(EXCEPT_FIVE_PARAMETER.format(
                            "$modifier void $name(EditText editText, CharSequence s, int start, int count, int after)"))
                    return
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (trigger != EditTextChangeTrigger.TEXT)
                    return

                if (types.size == 5 && types[0] == EditText::class.java && types[1] == CharSequence::class.java &&
                        types[2] == Integer::class.javaPrimitiveType && types[3] == Integer::class.javaPrimitiveType &&
                        types[4] == Integer::class.javaPrimitiveType) {
                    method.isAccessible = true
                    method.invoke(receiver, editText, p0, p1, p2, p3)
                } else {
                    val modifier = Modifier.toString(method.modifiers)
                    val name = method.name
                    throwException(EXCEPT_FIVE_PARAMETER.format(
                            "$modifier void $name(EditText editText, CharSequence s, int start, int before, int count)"))
                    return
                }
            }
        })
    }

    companion object {
        var config: Config = Config(BindViewPrefix.NONE)

        @SuppressLint("StaticFieldLeak")
        @JvmStatic
        val instance: PyxInjector = PyxInjector()

        @JvmStatic
        fun initializeApplication(config: Config) {
            this.config = config
        }
    }
}