package com.irgiys.diabpred1.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.irgiys.diabpred1.databinding.FragmentDatasetBinding
import com.irgiys.diabpred1.model.FeatureModel
import com.irgiys.diabpred1.ui.adapter.TableViewAdapter

class DatasetFragment : Fragment() {

    private var _binding: FragmentDatasetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDatasetBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerViewMovieList = binding.recyclerViewFeatureList
        recyclerViewMovieList.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewMovieList.adapter = TableViewAdapter(featureList)
        binding.apply {
            linkDataset.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data =
                    Uri.parse("https://www.kaggle.com/datasets/iammustafatz/diabetes-prediction-dataset")
                startActivity(intent)
            }
            description1.setCollapsedText("Baca Selengkapnya")
            description1.setExpandedText("Lebih Sedikit")
            description1.setTrimLines(4)
            description1.setCollapsedTextColor(androidx.constraintlayout.widget.R.color.material_grey_600)
            description1.setExpandedTextColor(androidx.constraintlayout.widget.R.color.material_grey_600)
        }
    }

    private val featureList = ArrayList<FeatureModel>().apply {
        add(FeatureModel("Female", "80.0", "0", "1", "never", "25.19", "6.6", "140", "0"))
        add(FeatureModel("Female", "54.0", "0", "0", "No Info", "27.32", "6.6", "80", "0"))
        add(FeatureModel("Male", "28.0", "0", "0", "never", "27.32", "5.7", "158", "0"))
        add(FeatureModel("Female", "36.0", "0", "0", "current", "23.45", "5.0", "155", "0"))
        add(FeatureModel("Male", "76.0", "1", "1", "current", "20.14", "4.8", "155", "0"))
        add(FeatureModel("Female", "44.0", "0", "0", "never", "19.31", "6.5", "200", "1"))
        add(FeatureModel("Male", "67.0", "0", "1", "not current", "27.32", "6.5", "200", "1"))
        add(FeatureModel("Male", "50.0", "1", "0", "current", "27.32", "5.7", "260", "1"))
        add(FeatureModel("Male", "73.0", "0", "0", "former", "25.91", "9.0", "160", "1"))
        add(FeatureModel("Female", "53.0", "0", "0", "former", "27.32", "7.0", "159", "1"))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}