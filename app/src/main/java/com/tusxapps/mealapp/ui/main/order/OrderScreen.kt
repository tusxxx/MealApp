package com.tusxapps.mealapp.ui.main.order

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.ui.navigation.Graph
import com.tusxapps.mealapp.ui.navigation.Screen


@Composable
fun OrderScreen(navController: NavController, viewModel: OrderViewModel) {
    val state by viewModel.state.collectAsState()
    OrderScreen(
        state = state,
        onAddressChange = viewModel::onAddressChange,
        onSBPClick = viewModel::onSBPClick,
        onCardClick = viewModel::onCardClick,
        onAddCardClick = {},
        onManualBuyClick = viewModel::onManualBuyClick,
        onOrderCLick = viewModel::onOrderCLick,
        onDialogClosed = {
            navController.navigate(Screen.Home.route) {
                popUpTo(Graph.Root.route)
            }
        },
        onBackClick = navController::popBackStack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderScreen(
    state: OrderScreenState,
    onAddressChange: (String) -> Unit,
    onSBPClick: () -> Unit,
    onCardClick: () -> Unit,
    onAddCardClick: () -> Unit,
    onManualBuyClick: () -> Unit,
    onOrderCLick: () -> Unit,
    onDialogClosed: () -> Unit,
    onBackClick: () -> Unit,
) {
    Scaffold(topBar = { OrderAppBar(onBackClick) }) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                AddressCard(state, onAddressChange)
                PaymentCard(onManualBuyClick, state, onSBPClick, onCardClick, onAddCardClick)
                Spacer(Modifier.height(32.dp))
            }
            Button(
                onClick = onOrderCLick,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
            ) {
                Text(text = "Заказать")
            }
        }
        if (state.isDialogShowed) {
            AlertDialog(
                onDismissRequest = onDialogClosed,
                confirmButton = {
                    OutlinedButton(onClick = onDialogClosed) {
                        Text(text = "Жду ;)")
                    }
                },
                text = {
                    Text(text = "Курьер вам перезвонит!\nОжидайте")
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderAppBar(onBackClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Оформление заказа", color = MaterialTheme.colorScheme.onPrimary) },
        colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    )
}

@Composable
private fun PaymentCard(
    onManualBuyClick: () -> Unit,
    state: OrderScreenState,
    onSBPClick: () -> Unit,
    onCardClick: () -> Unit,
    onAddCardClick: () -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Text(
            text = "Способ оплаты",
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Black,
            modifier = Modifier.padding(8.dp)
        )
        Divider()
        Column(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    1.dp,
                    MaterialTheme.colorScheme.onSurface.copy(0.4f),
                    RoundedCornerShape(16.dp)
                )
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onManualBuyClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = state.isManualBuySelected,
                    onClick = onManualBuyClick
                )
                Text(
                    text = "Оплата наличными курьеру",
                    fontWeight = FontWeight.SemiBold
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onSBPClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = state.isSBPSelected, onClick = onSBPClick)
                Text(text = "Оплата через СПБ", fontWeight = FontWeight.SemiBold)
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .clickable { onCardClick() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(selected = state.isCardSelected, onClick = onCardClick)
                Text(text = "Картой ** 2869", fontWeight = FontWeight.SemiBold)
                Spacer(Modifier.width(4.dp))
                Icon(painter = painterResource(id = R.drawable.ic_card), null)
            }
            TextButton(onClick = onAddCardClick, modifier = Modifier.padding(8.dp)) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_card),
                    contentDescription = null
                )
                Spacer(Modifier.width(4.dp))
                Text(text = "Добавить карту")
            }
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
private fun AddressCard(
    state: OrderScreenState,
    onAddressChange: (String) -> Unit,
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_house),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.delivery_adress),
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Divider()
        OutlinedTextField(value = state.address,
            onValueChange = onAddressChange,
            shape = RoundedCornerShape(32.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            singleLine = true,
            label = { Text(text = "Введите адрес") })
    }
}