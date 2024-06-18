package com.irgiys.diabpred1.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irgiys.diabpred1.R
import com.irgiys.diabpred1.databinding.FragmentHomeBinding
import com.irgiys.diabpred1.ui.activity.PredictActivity

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            predictButton.setOnClickListener {
                val intent = Intent(requireContext(), PredictActivity::class.java)
                startActivity(intent)
            }
            linkRepo.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse("https://github.com/irgiys/diapred-app")
                startActivity(intent)
            }
            description.setCollapsedText("Baca Selengkapnya")
            description.setExpandedText("Lebih Sedikit")
            description.setTrimLines(4)
            description.setCollapsedTextColor(androidx.constraintlayout.widget.R.color.material_grey_600)
            description.setExpandedTextColor(androidx.constraintlayout.widget.R.color.material_grey_600)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}