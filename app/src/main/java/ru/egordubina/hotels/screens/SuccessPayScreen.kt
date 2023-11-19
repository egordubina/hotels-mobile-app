package ru.egordubina.hotels.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialSharedAxis
import ru.egordubina.hotels.R
import ru.egordubina.hotels.databinding.FragmentSuccessPayBinding

class SuccessPayScreen : Fragment(R.layout.fragment__success_pay) {
    private var _binding: FragmentSuccessPayBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val args: SuccessPayScreenArgs by navArgs()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.Z, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.Z, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSuccessPayBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            buttonToHotelScreen.setOnClickListener {
                findNavController().navigate(R.id.action_successPay_to_hotelScreen)
            }
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            textViewTextSuccessfullyPayment.text = getString(R.string.text_view__success_pay_message, args.bookingNumber)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}