package com.tusxapps.mealapp.ui.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.ui.composables.LCE
import com.tusxapps.mealapp.ui.composables.LCEState

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginViewModel) {
    val state by viewModel.state.collectAsState()
    LoginScreen(state)
}

@Composable
private fun LoginScreen(state: LoginScreenState) {
    Text(text = state.login)
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun LoginScreenPreview() {
    var state by remember {
        mutableStateOf(LoginScreenState(lce = LCEState.Loading))
    }
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
                    onValueChange = {
                        state = state.copy(login = it)
                    }
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
                    onValueChange = {
                        state = state.copy(password = it)
                    }
                )
                OutlinedButton(onClick = { }) {
                    Text(text = stringResource(R.string.enter))
                }
            }
        }
    }
}