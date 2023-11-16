package ru.egordubina.hotels.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.hotels.R
import ru.egordubina.hotels.databinding.FragmentHotelScreenBinding
import ru.egordubina.hotels.uistates.HotelScreenUiState
import ru.egordubina.hotels.viewmodels.HotelScreenViewModel

@AndroidEntryPoint
class HotelScreen : Fragment(R.layout.fragment__hotel_screen) {
    private var _binding: FragmentHotelScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val vm: HotelScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    binding.apply {
                        buttonToChoiceNumber.isVisible = uiState is HotelScreenUiState.Content
                        chipRaiting.isVisible = uiState is HotelScreenUiState.Content
                        textViewAboutHotel.isVisible = uiState is HotelScreenUiState.Content
                    }
                    when (uiState) {
                        is HotelScreenUiState.Content -> {
                            binding.apply {
                                textViewHotelTitle.text = uiState.name
                                buttonHotelAddress.text = uiState.address
                                textViewPrice.text = "от ${uiState.price} ₽"
                                textViewPriceLabel.text = uiState.priceLabel
                                chipRaiting.text = "${uiState.rating} ${uiState.ratingName}"
                                textViewHotelDescription.text = uiState.hotelDescription
                                chipGroupPeculiarities.removeAllViews()
                                uiState.peculiarities.forEach(::addChipToPeculiarities)
                            }
                        }

                        HotelScreenUiState.Error -> {
                        }

                        HotelScreenUiState.Loading -> {
                        }
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
        _binding = FragmentHotelScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addChipToPeculiarities(feature: String) {
        val chip = Chip(requireContext())
        chip.text = feature
        chip.isEnabled = false
        binding.chipGroupPeculiarities.addView(chip)
    }
}