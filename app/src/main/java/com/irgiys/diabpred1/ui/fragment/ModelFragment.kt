package com.irgiys.diabpred1.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irgiys.diabpred1.databinding.FragmentModelBinding

class ModelFragment : Fragment() {

    private var _binding: FragmentModelBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentModelBinding.inflate(inflater, container, false)
        val root: View = binding.root
        binding.apply{
            description.setCollapsedText("Baca Selengkapnya")
            description.setExpandedText("Lebih Sedikit")
            description.setTrimLines(5)
            description.setCollapsedTextColor(androidx.constraintlayout.widget.R.color.material_grey_600)
            description.setExpandedTextColor(androidx.constraintlayout.widget.R.color.material_grey_600)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}