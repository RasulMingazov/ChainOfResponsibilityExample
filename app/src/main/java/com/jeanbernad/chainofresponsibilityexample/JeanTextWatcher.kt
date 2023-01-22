package com.jeanbernad.chainofresponsibilityexample

import android.text.Editable
import android.text.TextWatcher

abstract class JeanTextWatcher(private val onTextChanged: (String) -> Unit) : TextWatcher {

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        onTextChanged.invoke(p0.toString())
    }

    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
}