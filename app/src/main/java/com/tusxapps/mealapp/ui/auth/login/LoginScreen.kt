package com.tusxapps.mealapp.ui.auth.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.ui.composables.LCE
import com.tusxapps.mealapp.ui.composables.LCEState
import com.tusxapps.mealapp.ui.navigation.Graph

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.lce) {
        if (state.lce == LCEState.Success) {
            navController.navigate(Graph.Main.route) {
                popUpTo(Graph.Root.route)
            }
        }
    }

    LoginScreen(
        state = state,
        onLoginChange = viewModel::onLoginChange,
        onPasswordChange = viewModel::onPasswordChange,
        onLoginClick = viewModel::onLoginClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LoginScreen(
    state: LoginScreenState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    LCE(lce = state.lce) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            TextButton(
                onClick = { },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Text(text = stringResource(R.string.register))
            }
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OutlinedTextField(
                    value = state.login,
                    label = { Text(text = stringResource(R.string.login)) },
                    supportingText = {
                        Text(
                            text = state.loginErrorMessage ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    onValueChange = onLoginChange
                )
                OutlinedTextField(
                    value = state.password,
                    label = { Text(text = stringResource(R.string.password)) },
                    supportingText = {
                        Text(
                            text = state.passwordErrorMessage ?: "",
                            color = MaterialTheme.colorScheme.error
                        )
                    },
                    onValueChange = onPasswordChange
                )
                OutlinedButton(onClick = onLoginClick) {
                    Text(text = stringResource(R.string.enter))
                }
            }
        }
    }
}