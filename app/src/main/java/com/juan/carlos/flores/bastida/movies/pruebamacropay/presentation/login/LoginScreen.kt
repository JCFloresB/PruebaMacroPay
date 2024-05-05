package com.juan.carlos.flores.bastida.movies.pruebamacropay.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import com.juan.carlos.flores.bastida.movies.pruebamacropay.domain.data.Result
import com.juan.carlos.flores.bastida.movies.pruebamacropay.ui.theme.PruebaMacroPayTheme
import timber.log.Timber

@Composable
fun LoginScreen(
    navigateToMovies: (String) -> Unit,
    snackbarHostState: SnackbarHostState,
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isUserError by rememberSaveable { mutableStateOf(false) }
    var isPasswordError by rememberSaveable { mutableStateOf(false) }

    val viewModel = hiltViewModel<LoginViewModel>()
    val isLoged by viewModel.isLoginSuccess.collectAsStateWithLifecycle()

    when (isLoged) {
        is Result.Error -> {
            Timber.e("Error en logueo: ${(isLoged as Result.Error<Boolean>).error}")
            LaunchedEffect(key1 = snackbarHostState) {
                snackbarHostState.showSnackbar(
                    (isLoged as Result.Error<Boolean>).error ?: "Error en logueo"
                )
            }
        }
        is Result.Loading -> Timber.d("Comienza proceso de login")
        is Result.Success -> {
            Timber.d("Se ha logueado correctamente")
            navigateToMovies("Juan Carlos Flores Bastida")
        }
        null -> Timber.i("Inicializado en view model")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text(text = "Usuario") },
            singleLine = true,
            isError = isUserError,
            trailingIcon = {
                if (isUserError) {
                    Icon(Icons.Filled.Error, "Empty field", tint = MaterialTheme.colorScheme.error)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        if (isUserError) {
            Text(
                text = "El usuario no debe estar vacío",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        PasswordField(
            password = password,
            onPasswordChange = { password = it },
            isPasswordError = isPasswordError,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                isUserError = validate(username)
                isPasswordError = validate(password)
                if (!isUserError && !isPasswordError) {
                    viewModel.loginHandled(username, password)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Iniciar sesión")
        }
    }
}

@Composable
fun PasswordField(
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordError: Boolean,
    modifier: Modifier = Modifier
) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = password,
        onValueChange = { onPasswordChange(it) },
        label = { Text(text = "Contraseña") },
        singleLine = true,
        modifier = modifier,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = { /* Acción al presionar "Listo" en el teclado */ }),
        isError = isPasswordError,
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Icon(
                    imageVector = if (passwordVisibility) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = "Toggle password visibility"
                )
            }
        }
    )
    if (isPasswordError) {
        Text(
            text = "La contraseña no debe estar vacía",
            color = MaterialTheme.colorScheme.error,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

private fun validate(text: String?): Boolean {
    return text.isNullOrBlank()
}

