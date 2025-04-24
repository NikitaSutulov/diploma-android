package com.nikitasutulov.macsro.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.nikitasutulov.macsro.data.dto.BaseResponse

fun <T> LiveData<BaseResponse<T>>.observeOnce(lifecycleOwner: LifecycleOwner, observer: (BaseResponse<T>) -> Unit) {
    val wrapper = object : Observer<BaseResponse<T>> {
        override fun onChanged(value: BaseResponse<T>) {
            observer(value)
            if (value !is BaseResponse.Loading) {
                removeObserver(this)
            }
        }
    }
    observe(lifecycleOwner, wrapper)
}
