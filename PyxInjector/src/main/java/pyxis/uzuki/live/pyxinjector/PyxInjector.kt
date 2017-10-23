package pyxis.uzuki.live.pyxinjector

import android.app.Activity
import android.view.View
import pyxis.uzuki.live.pyxinjector.annotation.BindView
import pyxis.uzuki.live.pyxinjector.annotation.Extra
import pyxis.uzuki.live.pyxinjector.annotation.OnClick
import pyxis.uzuki.live.pyxinjector.annotation.OnClicks
import java.lang.reflect.Method

/**
 * PyxInjector
 * Class: PyxInjector
 * Created by Pyxis on 2017-10-23.
 */

class PyxInjector {
    private lateinit var receiver: Any
    private lateinit var view: View

    fun execute(receiver: Any, view: View) {
        this.receiver = receiver
        this.view = view

        executeReflection()
    }

    private fun executeReflection() {
        var cls = receiver.javaClass
        do {
            cls.declaredFields.forEach { field ->
                field.declaredAnnotations.forEach forEachAnnotations@ {
                    when (it) {
                        is BindView -> {
                            val id = it.resource
                            field.isAccessible = true
                            field.set(receiver, view.findViewById(id))
                        }

                        is Extra -> {
                            if ((receiver is Activity).not())
                                return@forEachAnnotations

                            val name = it.value
                            val activity = receiver as Activity
                            val bundle =  activity.intent.extras

                            field.isAccessible = true
                            field.set(receiver, bundle.get(name))
                        }
                    }
                }
            }

            cls.declaredMethods.forEach { method ->
                method.declaredAnnotations.forEach {
                    when (it) {
                        is OnClick -> {
                            val id = it.resource
                            attachClickListener(id, method)
                        }

                        is OnClicks -> {
                            val ids = it.resources
                            for (i in 0 until ids.size) {
                                attachClickListener(ids[i], method)
                            }
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

    private fun attachClickListener(id: Int, method: Method) {
        view.findViewById<View>(id).setOnClickListener {
            val types = method.parameterTypes
            if (types.size == 1 && types[0] == View::class.java) {
                method.isAccessible = true
                method.invoke(receiver, it)
            }
        }
    }
}