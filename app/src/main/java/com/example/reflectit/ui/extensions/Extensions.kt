package com.example.reflectit.ui.extensions

import androidx.lifecycle.MutableLiveData
import com.example.reflectit.ui.data.models.Mirror

fun <T>MutableLiveData<ArrayList<T>>.appendAsync(element: T) {
    val currentValue: ArrayList<T>
    if (this.value != null) {
        currentValue = this.value!!
        currentValue.add(element)
    }
    else
        currentValue = arrayListOf(element)
    this.postValue(currentValue)
}

fun <T>MutableLiveData<ArrayList<T>>.appendSync(element: T) {
    val currentValue: ArrayList<T>
    if (this.value != null) {
        currentValue = this.value!!
        currentValue.add(element)
    }
    else
        currentValue = arrayListOf(element)
    this.postValue(currentValue)
}