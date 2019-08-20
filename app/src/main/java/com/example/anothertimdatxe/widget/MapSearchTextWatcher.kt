package com.example.anothertimdatxe.widget

import android.text.Editable
import android.text.TextWatcher
import com.example.anothertimdatxe.adapter.BaseMapSearch

class MapSearchTextWatcher(var mBaseMapSearch: BaseMapSearch, var role: String) : TextWatcher {
    override fun afterTextChanged(s: Editable?) {

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        mBaseMapSearch.findLocationPredictionAutoComplte(s.toString(), role)
    }
}