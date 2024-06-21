package com.bangkit.recyclopedia.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CustomViewPassword : TextInputEditText {
    constructor(context: Context) : super(context) {
        textInitiation()
    }
    constructor(context: Context, attributes: AttributeSet) : super(context, attributes) {
        textInitiation()
    }
    constructor(context: Context, attributes: AttributeSet, defStyleAttr: Int) : super(context,attributes, defStyleAttr) {
        textInitiation()
    }

    private fun textInitiation() {
        addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //Nothing
            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    setError("Password tidak boleh kurang dari 1 karakter!", null)
                } else {
                    error = null
                }
            }
            override fun afterTextChanged(s: Editable) {
                //Nothing
            }
        })
    }
}