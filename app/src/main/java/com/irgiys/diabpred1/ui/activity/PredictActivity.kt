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
        predictViewModel = ViewModelProvider(this).get(PredictViewModel::class.java)
        initInterpreter(assets, "diabetes.tflite")

        setContentView(binding.root)
        setupAutoCompleteAdapters()
        setupAutoCompleteListeners()
        setupTextWatchers()
        supportActionBar?.title = "Predict Diabetes"

        binding.btnPredict.setOnClickListener {
            if (inputItem.any { it.isEmpty() }) {
                Toast.makeText(this, "Isi semua inputan terlebih dahulu", Toast.LENGTH_SHORT)
                    .show()
            } else {
                inputData = inputConversion(inputItem)
                predictViewModel.doInference(inputData, interpreter)
            }
        }

        predictViewModel.result.observe(this, Observer { result ->
            getResult(result)
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
            age.editText?.doOnTextChanged { text, _, _, _ -> inputItem[0] = text.toString() }
            bmi.editText?.doOnTextChanged { text, _, _, _ -> inputItem[1] = text.toString() }
            glucoseAverage.editText?.doOnTextChanged { text, _, _, _ ->
                inputItem[2] = text.toString()
            }
            bloodGlucose.editText?.doOnTextChanged { text, _, _, _ ->
                inputItem[3] = text.toString()
            }
        }
    }

    private fun getResult(result: Float) {
        lateinit var snackbar: Snackbar
        if (result > 0.5) {
            snackbar =
                Snackbar.make(binding.main, getString(R.string.predict_positif), Snackbar.LENGTH_LONG)
                    .setAction("Close") {
                        snackbar.dismiss()
                    }
            val snackbarView = snackbar.view
            val backgroundColor = ContextCompat.getColor(this, R.color.red_500)
            val textColor = ContextCompat.getColor(this, R.color.white)
            snackbar.setBackgroundTint(backgroundColor)
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .setTextColor(textColor)
            snackbar.show()

            binding.tvResultPrediction.text = getString(R.string.predict_positif)
            binding.tvResultPrediction.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.red_500
                )
            )
        } else {
            snackbar = Snackbar.make(
                binding.main,
                getString(R.string.predict_negatif),
                Snackbar.LENGTH_LONG
            )
                .setAction("OK") {
                    snackbar.dismiss()
                }
            val snackbarView = snackbar.view
            val backgroundColor = ContextCompat.getColor(this, R.color.teal_700)
            val textColor = ContextCompat.getColor(this, R.color.white)
            snackbar.setBackgroundTint(backgroundColor)
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
                .setTextColor(textColor)
            snackbar.show()

            binding.tvResultPrediction.text = getString(R.string.predict_negatif)
            binding.tvResultPrediction.setTextColor(
                ContextCompat.getColor(
                    this,
                    R.color.teal_200
                )
            )
        }
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

}