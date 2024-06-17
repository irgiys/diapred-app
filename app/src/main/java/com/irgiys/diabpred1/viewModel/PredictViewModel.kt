package com.irgiys.diabpred1.viewModel

import android.content.res.AssetManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

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