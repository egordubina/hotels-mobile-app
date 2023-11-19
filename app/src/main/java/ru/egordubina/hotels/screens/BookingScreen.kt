package ru.egordubina.hotels.screens

import android.os.Bundle
import android.util.Log
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
        findNavController().addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.bookingScreen)
                vm.resetUiState()
        }
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    binding.apply {
                        loadingIndicator.isVisible =
                            uiState == BookingScreenUiState.Loading || uiState == BookingScreenUiState.LoadingPay
                        nestedScrollView.isVisible =
                            !(uiState == BookingScreenUiState.Loading || uiState == BookingScreenUiState.Error)
                    }
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
                                textViewTourPrice.text = uiState.tourPrice
                                textViewFuelPrice.text = uiState.fuelCharge
                                textViewServicePrice.text = uiState.serviceCharge
                                textViewAllPrice.text = uiState.totalPrice
                                buttonToPay.text =
                                    getString(R.string.label__to_pay, uiState.totalPrice)
                                buttonToPay.setOnClickListener { vm.bookingPay() }
                            }
                        }

                        BookingScreenUiState.Error -> {}
                        BookingScreenUiState.Loading -> {}
                        is BookingScreenUiState.SuccessfulPay -> {
                            val action = BookingScreenDirections.actionBookingScreenToSuccessPay(
                                bookingNumber = uiState.bookingNumber
                            )
                            findNavController().navigate(action)
                        }

                        BookingScreenUiState.UnsuccessfulPay -> {}
                        BookingScreenUiState.LoadingPay -> {}
                        BookingScreenUiState.Reload -> { }
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