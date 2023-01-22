package com.jeanbernad.chainofresponsibilityexample

import android.app.Activity
import com.jeanbernad.chainofresponsibilityexample.keyboard.KeyboardState
import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar

interface ListenKeyboard {
    fun init(activity: Activity)

    fun unregister()
    class Base(
        private val keyboardState: KeyboardState
    ) : ListenKeyboard {

        private lateinit var unregistrar: Unregistrar

        override fun init(activity: Activity) {
            unregistrar = KeyboardVisibilityEvent.registerEventListener(activity) {
                if (it)
                    keyboardState.up()
                else
                    keyboardState.down()
            }

        }

        override fun unregister() {
            unregistrar.unregister()
        }
    }
}
