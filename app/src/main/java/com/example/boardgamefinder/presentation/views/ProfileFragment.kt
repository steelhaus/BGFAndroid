package com.example.boardgamefinder.presentation.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.boardgamefinder.R
import com.example.boardgamefinder.databinding.FragmentProfileBinding
import com.example.boardgamefinder.presentation.viewModels.ProfileViewModel

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    lateinit var vm: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        vm = ViewModelProvider(this).get(ProfileViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.getBreedsButton.setOnClickListener {
            vm.getBreeds(requireActivity())
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