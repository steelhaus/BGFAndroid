package com.example.boardgamefinder.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.boardgamefinder.R
import com.example.boardgamefinder.databinding.FragmentProfileBinding
import com.example.boardgamefinder.presentation.viewModels.ProfileViewModel

internal class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val vm: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getBreedsButton.setOnClickListener {
            vm.getBreeds()
        }

        vm.countBreeds.observe(viewLifecycleOwner) {
            binding.countBreedsTextView.text = it
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}