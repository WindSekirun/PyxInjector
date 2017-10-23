package pyxis.uzuki.live.pyxinjector

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.view.View
import pyxis.uzuki.live.pyxinjector.annotation.*
import pyxis.uzuki.live.pyxinjector.config.BindViewPrefix
import pyxis.uzuki.live.pyxinjector.config.Config
import java.lang.reflect.Field
import java.lang.reflect.Method


/**
 * PyxInjector
 * Class: PyxInjector
 * Created by Pyxis on 2017-10-23.
 */

typealias SupportFragment = android.support.v4.app.Fragment

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
            cls.declaredFields.forEach { field ->
                field.declaredAnnotations.forEach {
                    when (it) {
                        is BindView -> attachFindViewById(it, field)
                        is Extra -> attachExtra(it, field)
                        is Argument -> attachArgument(it, field)
                    }
                }
            }

            cls.declaredMethods.forEach { method ->
                method.declaredAnnotations.forEach {
                    when (it) {
                        is OnClick -> attachClickListener(it.resource, method)
                        is OnClicks -> for (i in 0 until it.resource.size) {
                            attachClickListener(it.resource[i], method)
                        }
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
        var id = bindView.resource
        if (id == 0) {
            var name = field.name

            if (config.bindViewPrefix == BindViewPrefix.PREFIX_M && name[0] == 'm') { // mTxtName 등에서 m 만 제외
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
            }
        }
    }

    companion object {
        var config: Config = Config(BindViewPrefix.NONE)

        @JvmStatic
        fun initializeApplication(config: Config) {
            this.config = config
        }
    }
}