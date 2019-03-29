package com.example.reflectit.ui.extensions

import androidx.lifecycle.MutableLiveData
import com.example.reflectit.ui.data.models.Mirror

fun MutableLiveData<ArrayList<Mirror>>.append(element: Mirror) {
    val currentValue: ArrayList<Mirror>
    if (this.value != null) {
        currentValue = this.value!!
        currentValue.add(element)
    }
    else
        currentValue = arrayListOf(element)
    this.postValue(currentValue)
}