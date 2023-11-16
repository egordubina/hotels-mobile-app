package ru.egordubina.hotels.screens

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.chip.Chip
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.hotels.R
import ru.egordubina.hotels.adapters.ImageSliderAdapter
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
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    binding.apply {
                        loadingIndicator.isVisible = uiState == HotelScreenUiState.Loading
                        buttonToChoiceNumber.isVisible = uiState is HotelScreenUiState.Content
                        chipRaiting.isVisible = uiState is HotelScreenUiState.Content
                        textViewAboutHotel.isVisible = uiState is HotelScreenUiState.Content
                    }
                    when (uiState) {
                        is HotelScreenUiState.Content -> {
                            binding.apply {
                                textViewHotelTitle.text = uiState.name
                                buttonHotelAddress.text = uiState.address
                                textViewPrice.text =
                                    requireContext().getString(R.string.price_from, uiState.price)
                                textViewPriceLabel.text = uiState.priceLabel
                                chipRaiting.text = "${uiState.rating} ${uiState.ratingName}"
                                textViewHotelDescription.text = uiState.hotelDescription
                                viewPagerImagesSlider.adapter =
                                    ImageSliderAdapter(uiState.imagesUrls)
                                chipGroupPeculiarities.removeAllViews()
                                uiState.peculiarities.forEach(::addChipToPeculiarities)
                                buttonToChoiceNumber.setOnClickListener {
                                    findNavController().navigate(
                                        R.id.action_hotelScreen_to_successPay
                                    )
                                }
                            }
                        }

                        HotelScreenUiState.Error -> {}

                        HotelScreenUiState.Loading -> {}
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
        chip.setEnsureMinTouchTargetSize(false)
        chip.typeface = Typeface.create(
            ResourcesCompat.getFont(requireContext(), R.font.sf_pro_display_medium),
            Typeface.NORMAL
        )
        chip.setTextColor(Color.parseColor("#FF828796"))
        chip.chipStrokeWidth = 0f
        chip.setBackgroundColor(Color.parseColor("#FFFBFBFC"))
        binding.chipGroupPeculiarities.addView(chip)
    }
}