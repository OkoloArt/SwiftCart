package com.example.swiftcart.ui.screens

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swiftcart.R
import com.example.swiftcart.data.model.LoginDto
import com.example.swiftcart.navigation.BottomNavigationScreens
import com.example.swiftcart.navigation.Screen
import com.example.swiftcart.utils.AuthResult
import com.example.swiftcart.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {
    val coroutineScope = rememberCoroutineScope()
    // UI elements like TextField for user input
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        // Login form UI elements...

        var emailSupportingText by remember { mutableStateOf(false) }
        var passwordSupportingText by remember { mutableStateOf(false) }
        var passwordVisible by remember { mutableStateOf(false) }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var loadingVisibility by remember { mutableStateOf(false) }
        var alpha by remember { mutableFloatStateOf(1f) }

        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(bottom = 50.dp)
                .alpha(alpha)
        ) {

            Heading()

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Welcome Back",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Please login to continue",
                fontWeight = FontWeight.W400
            )

            Spacer(modifier = Modifier.height(25.dp))

            LoginInputField(
                email = email,
                onUsernameChange = { value -> email = value },
                password = password,
                onPasswordChange = { value -> password = value },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                usernameSupportingText = emailSupportingText,
                passwordSupportingText = passwordSupportingText
            )

            Spacer(modifier = Modifier.height(50.dp))

            LoginButton(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                onClick = {
                    coroutineScope.launch {
                        if (password.isBlank() && email.isBlank()) {
                            passwordSupportingText = true
                            emailSupportingText = true
                        } else if (password.isBlank()) {
                            passwordSupportingText = true
                        } else if (email.isBlank()) {
                            emailSupportingText = true
                        } else {
                            loadingVisibility = true
                            alpha = 0.5f
                            val loginDto =
                                LoginDto(email = email, password = password)
                            authViewModel.loginUser(loginDto)
                        }
                    }
                },
                navigateToRegister = {
                    navController.popBackStack()
                    navController.navigate(Screen.Register.route)
                }
            )

            val context = LocalContext.current

            LaunchedEffect(key1 = "loginResult") {
                coroutineScope.launch {
                    authViewModel.loginResult.collect { authResult ->
                        when (authResult) {
                            is AuthResult.Error -> {
                                loadingVisibility = false
                                alpha = 1f
                                val data = authResult.error
                                Toast.makeText(context, data.message, Toast.LENGTH_SHORT).show()
                            }

                            AuthResult.Loading -> {

                            }

                            is AuthResult.Success -> {
                                loadingVisibility = false
                                alpha = 1f
                                navController.popBackStack()
                                navController.navigate(BottomNavigationScreens.Home.route)
                            }
                        }
                    }
                }
            }

        }

        LoadingIndicator(
            loadingVisible = loadingVisibility,
            modifier = Modifier.align(alignment = Alignment.Center)
        )
    }
}

@Composable
fun LoginInputField(
    modifier: Modifier = Modifier,
    email: String,
    onUsernameChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    usernameSupportingText: Boolean,
    passwordSupportingText: Boolean
) {
    Column() {
        InputFieldLabel(text = "Email")
        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            value = email,
            onValueChange = onUsernameChange,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            isError = email.isBlank() && usernameSupportingText
        )

        Spacer(modifier = Modifier.height(20.dp))

        InputFieldLabel(text = "Password")
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = onPasswordChange,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            singleLine = true,
            maxLines = 1,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = if (passwordVisible) KeyboardType.Text else KeyboardType.Password
            ),
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                val image = if (passwordVisible) {
                    painterResource(id = R.drawable.visibility)
                } else {
                    painterResource(id = R.drawable.visibility_off)
                }
                IconButton(onClick = onPasswordVisibilityChange) {
                    Icon(painter = image, contentDescription = null)
                }
            },
            isError = password.isBlank() && passwordSupportingText,
            shape = RoundedCornerShape(15.dp)
        )
    }
}

@Composable
fun LoginButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    navigateToRegister: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Don't have an account?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Register",
                color = Color.DarkGray,
                modifier = Modifier.clickable { navigateToRegister() })
        }
    }
}

@Composable
fun LoadingIndicator(loadingVisible: Boolean, modifier: Modifier = Modifier) {
    if (loadingVisible) {
        androidx.compose.material3.CircularProgressIndicator(
            modifier = modifier.width(64.dp),
            color = MaterialTheme.colors.secondary,
            trackColor = MaterialTheme.colors.primaryVariant,
        )
    }
}




