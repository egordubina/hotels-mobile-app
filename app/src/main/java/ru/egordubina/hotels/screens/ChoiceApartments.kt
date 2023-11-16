package ru.egordubina.hotels.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.hotels.R
import ru.egordubina.hotels.adapters.ApartmentsAdapter
import ru.egordubina.hotels.databinding.FragmentChoiceOfApartmentsBinding
import ru.egordubina.hotels.uistates.ApartmentsScreenUiState
import ru.egordubina.hotels.viewmodels.ApartmentsScreenViewModel

@AndroidEntryPoint
class ChoiceApartments : Fragment(R.layout.fragment__choice_of_apartments) {
    private var _binding: FragmentChoiceOfApartmentsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val vm: ApartmentsScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    when (uiState) {
                        is ApartmentsScreenUiState.Content -> {
                            binding.apply {
                                rvApartments.adapter = ApartmentsAdapter(uiState.apartments)
                            }
                        }

                        ApartmentsScreenUiState.Error -> Toast.makeText(requireContext(), "error", Toast.LENGTH_LONG).show()
                        ApartmentsScreenUiState.Loading -> Toast.makeText(requireContext(), "loading", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChoiceOfApartmentsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}