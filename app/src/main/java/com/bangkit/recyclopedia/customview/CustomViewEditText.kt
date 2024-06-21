package com.bangkit.recyclopedia.customview

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import com.google.android.material.textfield.TextInputEditText

class CustomViewEditText : TextInputEditText {
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

            }
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                removeError()
            }
            override fun afterTextChanged(s: Editable) {

            }
        })
    }

    private fun removeError() {
        this.error = null
    }
}