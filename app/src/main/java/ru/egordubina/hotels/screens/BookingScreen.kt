package ru.egordubina.hotels.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
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
import ru.egordubina.hotels.databinding.FragmentBookingBinding
import ru.egordubina.hotels.models.TouristUi
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
                        is BookingScreenUiState.Content -> showContent(uiState)

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

    private fun showContent(uiState: BookingScreenUiState.Content) {
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
            composeList.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    MaterialTheme {
                        var touristsList by remember { mutableStateOf(listOf<TouristUi>()) }
                        Column(
                            modifier = Modifier.animateContentSize()
                        ) {
                            touristsList.forEach {
                                TouristCard(id = it.id, expanded = it.isVisible)
                            }
                            ButtonAddTourist(touristsList.size) {
                                touristsList = touristsList + TouristUi(
                                    id = touristsList.size, isVisible = touristsList.isEmpty()
                                )
                            }
                        }
                    }
                }
            }
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

@Composable
fun TouristCard(id: Int, expanded: Boolean) {
    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var country by remember { mutableStateOf("") }
    var birthday by remember { mutableStateOf("") }
    var pasNumber by remember { mutableStateOf("") }
    var pasDate by remember { mutableStateOf("") }
    var expand by remember { mutableStateOf(expanded) }
    val touristNumber = getTouristNumber(id = id + 1)
    var cardName by remember { mutableStateOf(touristNumber) }
    Card(
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_corner_size)),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(id = R.color.white)
        ),
        modifier = Modifier
            .padding(top = dimensionResource(id = R.dimen.small_indent))
            .animateContentSize()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.medium_indent))
        ) {
            Text(
                text = cardName,
                fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
                fontSize = 22.sp,
                style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.weight(.1f)
            )
            IconButton(onClick = { expand = !expand }) {
                Icon(
                    painter = if (expand)
                        painterResource(id = R.drawable.show_less)
                    else
                        painterResource(id = R.drawable.show_more),
                    contentDescription = null,
                    modifier = Modifier.weight(1f)
                )
            }
        }
        if (expand)
            Column(
                verticalArrangement = Arrangement.spacedBy(
                    dimensionResource(id = R.dimen.small_indent)
                ),
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.medium_indent))
                    .padding(bottom = dimensionResource(id = R.dimen.medium_indent))
            ) {
                AppEditText(
                    value = name,
                    hintText = stringResource(id = R.string.hint_tourist_name)
                ) {
                    name = it
                    cardName = if (it.isNotEmpty()) "$touristNumber ($it)" else touristNumber
                }
                AppEditText(
                    value = surname,
                    hintText = stringResource(id = R.string.hint_tourist_surname)
                ) { surname = it }
                AppEditText(
                    value = birthday,
                    hintText = stringResource(id = R.string.hint_tourist_birthday)
                ) { birthday = it }
                AppEditText(
                    value = country,
                    hintText = stringResource(id = R.string.hint_tourist_country)
                ) { country = it }
                AppEditText(
                    value = pasNumber,
                    hintText = stringResource(id = R.string.hint_tourist_pas_number)
                ) { pasNumber = it }
                AppEditText(
                    value = pasDate,
                    hintText = stringResource(id = R.string.hint_tourist_pas_date)
                ) { pasDate = it }
            }
    }
}

@Composable
fun getTouristNumber(id: Int): String {
    return when (id) {
        1 -> stringResource(id = R.string.first_tourist)
        2 -> stringResource(id = R.string.second_tourist)
        3 -> stringResource(id = R.string.third_tourist)
        4 -> stringResource(id = R.string.fourth_tourist)
        5 -> stringResource(id = R.string.fifth_tourist)
        6 -> stringResource(id = R.string.sixth_tourist)
        7 -> stringResource(id = R.string.seventh_tourist)
        8 -> stringResource(id = R.string.eighth_tourist)
        9 -> stringResource(id = R.string.nineth_tourist)
        10 -> stringResource(id = R.string.tenth_tourist)
        else -> stringResource(id = R.string.tourist)
    }
}

@Composable
fun AppEditText(
    value: String,
    hintText: String,
    onChangeValue: (String) -> Unit
) {
    OutlinedTextField(
        maxLines = 1,
        singleLine = true,
        value = value,
        onValueChange = { onChangeValue(it) },
        placeholder = { Text(text = hintText) },
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = colorResource(id = R.color.gray),
            unfocusedIndicatorColor = colorResource(id = R.color.gray),
            focusedContainerColor = colorResource(id = R.color.blue10),
            focusedIndicatorColor = colorResource(id = R.color.blue),
        ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.medium_corner_size)),
    )
}

@Composable
fun ButtonAddTourist(touristSize: Int, onClick: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.white)),
        modifier = Modifier.padding(top = dimensionResource(id = R.dimen.small_indent))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(id = R.dimen.medium_indent))
        ) {
            Text(
                text = stringResource(id = R.string.add_tourist_title),
                fontFamily = FontFamily(Font(R.font.sf_pro_display_medium)),
                fontSize = 22.sp,
                style = TextStyle(
                    platformStyle = PlatformTextStyle(includeFontPadding = false)
                )
            )
            FilledIconButton(
                onClick = onClick,
                colors = IconButtonDefaults.filledIconButtonColors(
                    containerColor = colorResource(id = R.color.blue)
                ),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.small_corner_size)),
                enabled = touristSize < 10
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.add),
                    contentDescription = null,
                    tint = colorResource(id = R.color.white)
                )
            }
        }
    }
}

@Preview
@Composable
fun ButtonAddTouristPreview() {
    MaterialTheme {
        ButtonAddTourist(0) {}
    }
}