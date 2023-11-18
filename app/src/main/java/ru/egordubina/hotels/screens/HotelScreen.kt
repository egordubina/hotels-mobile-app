package ru.egordubina.hotels.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.domain.utils.toRubInt
import ru.egordubina.hotels.R
import ru.egordubina.hotels.adapters.ImageSliderAdapter
import ru.egordubina.hotels.databinding.FragmentHotelScreenBinding
import ru.egordubina.hotels.uistates.HotelScreenUiState
import ru.egordubina.hotels.utils.addChipToPeculiarities
import ru.egordubina.hotels.viewmodels.HotelScreenViewModel

@AndroidEntryPoint
class HotelScreen : Fragment(R.layout.fragment__hotel_screen) {
    private var _binding: FragmentHotelScreenBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val vm: HotelScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    binding.apply {
                        loadingIndicator.isVisible = uiState == HotelScreenUiState.Loading
                        cardBottomInfoHotel.isVisible = uiState is HotelScreenUiState.Content
                        cardButtonHotel.isVisible = uiState is HotelScreenUiState.Content
                        cardTopInfoHotel.isVisible = uiState is HotelScreenUiState.Content
                    }
                    when (uiState) {
                        is HotelScreenUiState.Content -> showContent(uiState)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showContent(uiState: HotelScreenUiState.Content) {
        binding.apply {
            textViewHotelTitle.text = uiState.name
            buttonHotelAddress.text = uiState.address
            textViewPrice.text = getString(R.string.price_from, uiState.price.toRubInt())
            textViewPriceLabel.text = uiState.priceLabel
            chipRating.text = getString(
                R.string.сhip_rating_text,
                uiState.rating,
                uiState.ratingName
            )
            textViewHotelDescription.text = uiState.hotelDescription
            viewPagerImagesSlider.adapter = ImageSliderAdapter(uiState.imagesUrls)
            chipGroupPeculiarities.removeAllViews()
            uiState.peculiarities.forEach {
                chipGroupPeculiarities.addView(
                    addChipToPeculiarities(
                        context = requireContext(),
                        feature = it
                    )
                )
            }
            buttonToChoiceNumber.setOnClickListener {
                val action = HotelScreenDirections
                    .actionHotelScreenToChoiceApartments(hotelName = uiState.name)
                findNavController().navigate(action)
            }
            cardButtonHotel.viewTreeObserver.addOnPreDrawListener(
                object : ViewTreeObserver.OnPreDrawListener {
                    override fun onPreDraw(): Boolean {
                        cardButtonHotel.viewTreeObserver.removeOnPreDrawListener(this)
                        val height = cardButtonHotel.height + 12
                        nestedScrollView.setPadding(0, 0, 0, height)
                        return true
                    }
                }
            )
        }
    }
}