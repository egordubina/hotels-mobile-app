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
import androidx.navigation.fragment.navArgs
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import ru.egordubina.hotels.R
import ru.egordubina.hotels.adapters.RoomsAdapterItemDecoration
import ru.egordubina.hotels.adapters.RoomsAdapter
import ru.egordubina.hotels.adapters.ImageSliderAdapter
import ru.egordubina.hotels.databinding.FragmentChoiceOfApartmentsBinding
import ru.egordubina.hotels.models.RoomUi
import ru.egordubina.hotels.uistates.RoomsScreenUiState
import ru.egordubina.hotels.utils.addChipToPeculiarities
import ru.egordubina.hotels.utils.toPx
import ru.egordubina.hotels.viewmodels.RoomsScreenViewModel

@AndroidEntryPoint
class ChoiceRoomScreen : Fragment(R.layout.fragment__choice_of_apartments) {
    private var _binding: FragmentChoiceOfApartmentsBinding? = null
    private val binding get() = checkNotNull(_binding)
    private val vm: RoomsScreenViewModel by viewModels()
    private val args: ChoiceRoomScreenArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { uiState ->
                    binding.apply {
                        loadingIndicator.isVisible = uiState == RoomsScreenUiState.Loading
                    }
                    when (uiState) {
                        is RoomsScreenUiState.Content -> showContent(rooms = uiState.apartments)

                        RoomsScreenUiState.Error -> {}
                        RoomsScreenUiState.Loading -> {}
                    }
                }
            }
        }
    }

    private fun showContent(rooms: List<RoomUi>) {
        binding.apply {
            rvApartments.adapter = RoomsAdapter(
                apartmentsItems = rooms,
                setImagesSliderAdapter = { urls -> ImageSliderAdapter(urls) },
                setChips = {
                    it.map { chipText -> addChipToPeculiarities(requireContext(), chipText) }
                },
                onButtonClick = { findNavController().navigate(R.id.action_choiceApartments_to_bookingScreen) }
            )
            rvApartments.addItemDecoration(
                RoomsAdapterItemDecoration(top = requireContext().toPx(8).toInt())
            )
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
        binding.apply {
            toolbar.setNavigationOnClickListener { findNavController().navigateUp() }
            toolbar.title = args.hotelName
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
}