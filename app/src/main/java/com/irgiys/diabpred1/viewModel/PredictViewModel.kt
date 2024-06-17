package com.irgiys.diabpred1.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.tensorflow.lite.Interpreter

class PredictViewModel : ViewModel() {

    private val _result = MutableLiveData<Float>()
    val result: LiveData<Float> get() = _result

    fun doInference(input: Array<Float>, interpreter: Interpreter) {
        val floatArray = input.map { it }.toFloatArray()
        val output = Array(1) { FloatArray(1) }
        interpreter.run(arrayOf(floatArray), output)
        _result.value = output[0][0]
    }

}