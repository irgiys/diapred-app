package com.irgiys.diabpred1.ui.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.irgiys.diabpred1.databinding.FragmentAboutMeBinding


class AboutMeFragment : Fragment() {
    private var _binding: FragmentAboutMeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAboutMeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            val intent = Intent(Intent.ACTION_VIEW)
            emailLink.setOnClickListener {
                intent.data = Uri.parse("mailto:irgiyansy@gmail.com")
                startActivity(intent)
            }
            githubLink.setOnClickListener {
                intent.data =
                    Uri.parse("https://www.github.com/irgiys")
                startActivity(intent)
            }
            linkedinLink.setOnClickListener {
                intent.data =
                    Uri.parse("https://www.linkedin.com/in/irgiyansyah-2b2a23209/")
                startActivity(intent)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

