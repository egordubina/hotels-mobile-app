package ru.egordubina.hotels.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.hotels.R
import ru.egordubina.hotels.adapters.DefaultItemDecorator
import ru.egordubina.hotels.adapters.TouristsAdapter
import ru.egordubina.hotels.databinding.FragmentBookingBinding
import ru.egordubina.hotels.uistates.BookingScreenUiState
import ru.egordubina.hotels.utils.getStringNumberOfNights
import ru.egordubina.hotels.utils.toPx
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
                vm.loadData()
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
                        is BookingScreenUiState.Content -> showConnent(uiState)

                        BookingScreenUiState.Error -> showError()

                        is BookingScreenUiState.SuccessfulPay -> {
                            val action = BookingScreenDirections.actionBookingScreenToSuccessPay(
                                bookingNumber = uiState.bookingNumber
                            )
                            findNavController().navigate(action)
                        }

                        BookingScreenUiState.Loading -> {}
                        BookingScreenUiState.UnsuccessfulPay -> {
                            binding.buttonToPay.isEnabled = true
                            Snackbar.make(
                                binding.buttonToPay,
                                R.string.pay_error,
                                Snackbar.LENGTH_LONG
                            ).show()
                        }

                        BookingScreenUiState.LoadingPay -> {
                            binding.buttonToPay.isEnabled = false
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
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            swipeRefreshLayout.setOnRefreshListener {
                vm.loadData()
                swipeRefreshLayout.isRefreshing = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showConnent(uiState: BookingScreenUiState.Content) {
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
            rvTourists.adapter = TouristsAdapter(uiState.touristsList)
            rvTourists.addItemDecoration(
                DefaultItemDecorator(top = requireContext().toPx(8).toInt())
            )
        }
    }

    private fun showError() {
        Snackbar.make(
            binding.swipeRefreshLayout,
            getString(R.string.loading_error),
            Snackbar.LENGTH_INDEFINITE
        ).setAction(R.string.try_retry_load_data) { vm.loadData() }.show()
    }
}