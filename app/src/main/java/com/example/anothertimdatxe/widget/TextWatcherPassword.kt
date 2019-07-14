package com.example.anothertimdatxe.widget

import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout

class TextWatcherPassword(var textInput: TextInputLayout) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        textInput.error = null
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        textInput.isPasswordVisibilityToggleEnabled = (s!!.length > 0)
    }
}