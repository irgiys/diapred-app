package com.irgiys.diabpred1.ui.activity

import android.content.res.AssetManager
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputLayout
import com.irgiys.diabpred1.R
import com.irgiys.diabpred1.databinding.ActivityPredictBinding
import com.irgiys.diabpred1.utils.inputConversion
import com.irgiys.diabpred1.viewModel.PredictViewModel
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class PredictActivity : AppCompatActivity() {

    private var _binding: ActivityPredictBinding? = null
    private val binding get() = _binding!!

    private lateinit var interpreter: Interpreter
    private lateinit var predictViewModel: PredictViewModel

    private lateinit var inputData: Array<Float>
    private var inputItem = Array(8) { "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityPredictBinding.inflate(layoutInflater)
        setContentView(binding.root)


        predictViewModel = ViewModelProvider(this).get(PredictViewModel::class.java)


        supportActionBar?.title = "Predict Diabetes"
        initInterpreter(assets, "diabetes_model.tflite")
        setupAutoCompleteAdapters()
        setupAutoCompleteListeners()
        setupTextWatchers()
        binding.btnPredict.setOnClickListener {
            if (inputItem.any { it.isEmpty() }) {
                displayEmptyFieldsError()
            } else {
                inputData = inputConversion(inputItem)
                predictViewModel.doInference(inputData, interpreter)
            }
            
        }

        predictViewModel.result.observe(this, Observer { result ->
            displayResult(result)
        })

    }

    fun initInterpreter(assetManager: AssetManager, modelPath: String) {
        val options = Interpreter.Options()
        options.setNumThreads(4)
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assetManager, modelPath), options)
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }

    private fun setupAutoCompleteAdapters() {
        val genderOptions = resources.getStringArray(R.array.options_gender)
        val yesNoOptions = resources.getStringArray(R.array.options_yes_no)
        val smokingOptions = resources.getStringArray(R.array.options_smoking)

        binding.apply {
            genderAutoComplete.setAdapter(createArrayAdapter(genderOptions))
            hypertensionAutoComplete.setAdapter(createArrayAdapter(yesNoOptions))
            heartAutoComplete.setAdapter(createArrayAdapter(yesNoOptions))
            smokeAutoComplete.setAdapter(createArrayAdapter(smokingOptions))
        }
    }

    private fun createArrayAdapter(options: Array<String>): ArrayAdapter<String> {
        return ArrayAdapter(this, R.layout.item_dropdown, options)
    }

    private fun setupAutoCompleteListeners() {
        setupAutoCompleteListener(binding.genderAutoComplete, 4)
        setupAutoCompleteListener(binding.hypertensionAutoComplete, 5)
        setupAutoCompleteListener(binding.heartAutoComplete, 6)
        setupAutoCompleteListener(binding.smokeAutoComplete, 7)
    }

    private fun setupAutoCompleteListener(autoComplete: AutoCompleteTextView, index: Int) {
        autoComplete.setOnItemClickListener { _, _, position, _ ->
            inputItem[index] = autoComplete.adapter.getItem(position).toString()

        }
    }

    private fun setupTextWatchers() {
        binding.apply {
            age.editText?.doOnTextChanged { text, _, _, _ ->
                inputItem[0] = text.toString()
                if (text.isNullOrEmpty().not()) age.error = null
            }
            bmi.editText?.doOnTextChanged { text, _, _, _ ->
                inputItem[1] = text.toString()
                if (text.isNullOrEmpty().not()) bmi.error = null
            }
            glucoseAverage.editText?.doOnTextChanged { text, _, _, _ ->
                inputItem[2] = text.toString()
                if (text.isNullOrEmpty().not()) glucoseAverage.error = null
            }
            bloodGlucose.editText?.doOnTextChanged { text, _, _, _ ->
                inputItem[3] = text.toString()
                if (text.isNullOrEmpty().not()) bloodGlucose.error = null
            }
            gender.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) gender.setError(null)
            }
            hypertension.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) hypertension.setError(null)
            }
            heart.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) heart.setError(null)
            }
            smoke.editText?.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty().not()) smoke.setError(null)
            }
        }
    }

    private fun displayEmptyFieldsError() {
        binding.apply {
            if (age.editText?.text.isNullOrEmpty()) age.error = getString(R.string.empty_filed)
            if (bmi.editText?.text.isNullOrEmpty()) bmi.error = getString(R.string.empty_filed)
            if (glucoseAverage.editText?.text.isNullOrEmpty()) glucoseAverage.error = getString(R.string.empty_filed)
            if (bloodGlucose.editText?.text.isNullOrEmpty()) bloodGlucose.error = getString(R.string.empty_filed)
            if (genderAutoComplete.text.isNullOrEmpty()) {
                gender.setError(getString(R.string.empty_filed))
            }
            if (hypertensionAutoComplete.text.isNullOrEmpty()) {
                hypertension.setError(getString(R.string.empty_filed))
            }
            if (heartAutoComplete.text.isNullOrEmpty()) {
                heart.setError(getString(R.string.empty_filed))
            }
            if (smokeAutoComplete.text.isNullOrEmpty()) {
                smoke.setError(getString(R.string.empty_filed))
            }
        }
    }

    private fun displayResult(result: Float) {
        lateinit var snackbar: Snackbar
        val isPositive = result > .5
        val messageResId = if (isPositive) R.string.predict_positif else R.string.predict_negatif
        val colorResId = if (isPositive) R.color.red_500 else R.color.teal_700
        val textColorResId = R.color.white

        snackbar = Snackbar.make(binding.main, getString(messageResId), Snackbar.LENGTH_LONG)
            .setAction("Close") { snackbar.dismiss() }
        snackbar.setBackgroundTint(ContextCompat.getColor(this, colorResId))
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(this, textColorResId))
        snackbar.show()

        binding.tvResultPrediction.apply {
            text = getString(messageResId)
//            text = result.toString()
            setTextColor(ContextCompat.getColor(this@PredictActivity, colorResId))
        }
    }

    override fun onDestroy() {
        _binding = null
        interpreter.close()
        super.onDestroy()
    }

}