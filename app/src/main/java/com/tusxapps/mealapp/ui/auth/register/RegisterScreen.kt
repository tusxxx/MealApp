package com.tusxapps.mealapp.ui.auth.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.ui.composables.LCE
import com.tusxapps.mealapp.ui.composables.LCEState
import com.tusxapps.mealapp.ui.navigation.Graph

@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.lce) {
        if (state.lce is LCEState.Success) {
            navController.navigate(Graph.Main.route) {
                popUpTo(Graph.Root.route)
            }
        }
    }

    RegisterScreen(
        state = state,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onPasswordVarificationChange = viewModel::onPasswordVerificationChange,
        onRegisterClick = viewModel::onRegisterClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RegisterScreen(
    state: RegisterScreenState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPasswordVarificationChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
) {
    LCE(lce = state.lce) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = state.login,
                onValueChange = onLoginChange,
                singleLine = true,
                label = { Text(text = "Почта / номер телефона") }
            )
            OutlinedTextField(
                value = state.password,
                onValueChange = onPasswordChange,
                singleLine = true,
                label = { Text(text = "Пароль") }
            )
            OutlinedTextField(
                value = state.passwordVerification,
                onValueChange = onPasswordVarificationChange,
                singleLine = true,
                label = { Text(text = "Повторение пароля") }
            )
            OutlinedButton(onClick = onRegisterClick) {
                Text(text = stringResource(id = R.string.register))
            }
            Text(
                text = (state.lce as? LCEState.Failure)?.message ?: "",
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}