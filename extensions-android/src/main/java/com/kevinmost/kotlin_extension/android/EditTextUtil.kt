package com.kevinmost.kotlin_extension.android

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText

fun EditText.addTextChangedListener(
        before: (s: CharSequence?, start: Int, count: Int, after: Int) -> Unit = {s, start, count, after -> },
        after: (s: Editable?) -> Unit = {},
        onChanged: (s: CharSequence?, start: Int, before: Int, after: Int) -> Unit = {s, start, before, after -> }
): TextWatcher {
    return object: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            before(s, start, count, after)
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onChanged(s, start, before, count)
        }

        override fun afterTextChanged(s: Editable?) {
            after(s)
        }

    }.apply {
        addTextChangedListener(this)
    }
}
