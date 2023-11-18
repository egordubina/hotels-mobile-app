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
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.domain.utils.toRubInt
import ru.egordubina.hotels.R
import ru.egordubina.hotels.databinding.FragmentBookingBinding
import ru.egordubina.hotels.uistates.BookingScreenUiState
import ru.egordubina.hotels.utils.getStringNumberOfNights
import ru.egordubina.hotels.viewmodels.BookingViewModel

@AndroidEntryPoint
class BookingScreen : Fragment(R.layout.fragment__booking) {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val vm: BookingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    binding.loadingIndicator.isVisible = uiState == BookingScreenUiState.Loading
                    when (uiState) {
                        is BookingScreenUiState.Content -> {
                            binding.apply {
                                chipRating.text = getString(
                                    R.string.сhip_rating_text,
                                    uiState.hotelRating,
                                    uiState.ratingName
                                )
                                textViewHotelTitle.text = uiState.hotelName
                                buttonHotelAddress.text = uiState.hotelAddress
                                tetxtViewFrom.text = uiState.departure
                                tetxtViewTo.text = uiState.arrivalCountry
                                tetxtViewNumberOfNight.text =
                                    getStringNumberOfNights(requireContext(), uiState.countOfNight)
                                textViewHotelTitle2.text = uiState.hotelName
                                tetxtViewRoomName.text = uiState.roomName
                                tetxtViewEat.text = uiState.nutrition
                                textViewTourPrice.text = uiState.tourPrice.toRubInt()
                                textViewFuelPrice.text = uiState.fuelCharge.toRubInt()
                                textViewServicePrice.text = uiState.serviceCharge.toRubInt()
                                // fixme: перенести логику в юзкейс
                                textViewAllPrice.text = (uiState.tourPrice + uiState.fuelCharge + uiState.serviceCharge).toRubInt()
                                buttonToPay.text = getString(R.string.label__to_pay, (uiState.tourPrice + uiState.fuelCharge + uiState.serviceCharge).toRubInt())
                                buttonToPay.setOnClickListener { findNavController().navigate(R.id.action_bookingScreen_to_successPay) }
                                cardButtonPay.viewTreeObserver.addOnPreDrawListener(
                                    object : ViewTreeObserver.OnPreDrawListener {
                                        override fun onPreDraw(): Boolean {
                                            cardButtonPay.viewTreeObserver.removeOnPreDrawListener(this)
                                            val height = cardButtonPay.height + 12
                                            nestedScrollView.setPadding(0, 0, 0, height)
                                            return true
                                        }
                                    }
                                )
                            }
                        }

                        BookingScreenUiState.Error -> {}
                        BookingScreenUiState.Loading -> {}
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
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}