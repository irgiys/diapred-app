package com.irgiys.diabpred1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.irgiys.diabpred1.databinding.FragmentFeatureBinding
import com.irgiys.diabpred1.model.FeatureItemModel
import com.irgiys.diabpred1.ui.adapter.FeatureViewAdapter


class FeatureFragment : Fragment() {
    private var _binding: FragmentFeatureBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFeatureBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showFeatureItems()
    }

    private fun showFeatureItems() {
        val recyclerViewFeatureList = binding.recyclerViewFeatureItem
        recyclerViewFeatureList.setHasFixedSize(true)
        val featureViewAdapter = FeatureViewAdapter(featureItems)
        val featureDecoration =
            DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        recyclerViewFeatureList.layoutManager = LinearLayoutManager(context)
        recyclerViewFeatureList.addItemDecoration(featureDecoration)
        recyclerViewFeatureList.adapter = featureViewAdapter
    }

    private val featureItems = ArrayList<FeatureItemModel>().apply {
        add(FeatureItemModel("Gender", "object", "Female | Male | Other", "Gender mengacu pada seks biologis seseorang, yang dapat mempengaruhi kerentanan terhadap diabetes."))
        add(FeatureItemModel("Age", "float64", "0.08-80", "Age (umur) adalah faktor penting karena diabetes lebih sering didiagnosis pada orang dewasa yang lebih tua."))
        add(FeatureItemModel("hypertension", "int64", "0-1", "Hypertension adalah kondisi medis di mana tekanan darah di arteri terus-menerus tinggi. Nilainya 0 atau 1, di mana 0 menunjukkan tidak memiliki hipertensi dan 1 berarti memiliki hipertensi."))
        add(FeatureItemModel("heart_disease", "int64", "0-1", "Heart Disease (penyakit jantung) adalah kondisi medis lain yang terkait dengan peningkatan risiko diabetes. Nilainya 0 atau 1, di mana 0 menunjukkan tidak memiliki penyakit jantung dan 1 berarti memiliki penyakit jantung."))
        add(FeatureItemModel("smoking_history", "object", "No Info | Not Current | Never | Former | Current", "Smoking history (riwayat merokok) dianggap sebagai faktor risiko diabetes dan dapat memperburuk komplikasi yang terkait dengan diabetes."))
        add(FeatureItemModel("bmi", "float64", "10-95.7", "BMI (Indeks Massa Tubuh) adalah ukuran lemak tubuh berdasarkan berat dan tinggi. Nilai BMI yang lebih tinggi terkait dengan risiko diabetes yang lebih tinggi."))
        add(FeatureItemModel("HbA1c_level", "float64", "3.5-9", "HbA1c level (hemoglobin A1c) mengukur rata-rata kadar gula darah seseorang selama 2-3 bulan terakhir. Tingkat yang lebih tinggi menunjukkan risiko lebih besar untuk mengembangkan diabetes. Biasanya, HbA1c lebih dari 6.5% menunjukkan diabetes."))
        add(FeatureItemModel("blood_glucose_level", "int64", "80-300", "Blood glucose level (tingkat glukosa darah) mengacu pada jumlah glukosa dalam aliran darah pada suatu waktu tertentu. Tingkat glukosa darah yang tinggi adalah indikator utama diabetes."))
    }

}