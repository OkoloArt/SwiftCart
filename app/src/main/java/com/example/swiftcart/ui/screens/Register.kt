package com.example.swiftcart.ui.screens

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.swiftcart.R
import com.example.swiftcart.navigation.Screen
import com.example.swiftcart.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel = hiltViewModel(),
    navController: NavController
) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        var fullNameSupportingText by remember { mutableStateOf(false) }
        var emailSupportingText by remember { mutableStateOf(false) }
        var passwordSupportingText by remember { mutableStateOf(false) }
        var passwordVisible by remember { mutableStateOf(false) }
        var fullName by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val isInputValid = authViewModel.validateInput(fullName, email, password, "user190")
        val coroutineScope = rememberCoroutineScope()

        Column(
            modifier = Modifier
                .align(alignment = Alignment.Center)
                .padding(bottom = 50.dp)
        ) {

            Heading()

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                text = "Create an account",
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = "Fill in the details below to get started",
                fontWeight = FontWeight.W400
            )

            Spacer(modifier = Modifier.height(10.dp))

            InputField(
                fullName = fullName,
                onFullNameChange = { value -> fullName = value },
                email = email,
                onEmailChange = { value -> email = value },
                password = password,
                onPasswordChange = { value -> password = value },
                passwordVisible = passwordVisible,
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                fullNameSupportingText = fullNameSupportingText,
                emailSupportingText = emailSupportingText,
                passwordSupportingText = passwordSupportingText
            )
        }

        Spacer(modifier = Modifier.height(60.dp))

        CreateUserButton(
            modifier = Modifier.align(alignment = Alignment.BottomCenter),
            onClick = {
                fullNameSupportingText = !fullNameSupportingText
                emailSupportingText = !emailSupportingText
                passwordSupportingText = !passwordSupportingText
                coroutineScope.launch {
                    if (isInputValid) {
                        authViewModel.createUser(
                            authViewModel.createUserDto(
                                fullName,
                                email,
                                password,
                                "user190"
                            )
                        )
                    }
                }
            },
            navigateToLogin = {
                navController.popBackStack()
                navController.navigate(Screen.Login.route)
            }
        )

        val context = LocalContext.current
        var createUserResponse by remember { mutableStateOf("Waiting for Response") }

    }
}

@Composable
fun Heading(modifier: Modifier = Modifier) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "SwiftCart",
            fontSize = MaterialTheme.typography.h4.fontSize,
            fontFamily = FontFamily.Cursive,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "shop in an instant",
            fontFamily = FontFamily.Serif,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    fullName: String,
    onFullNameChange: (String) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    passwordVisible: Boolean,
    onPasswordVisibilityChange: () -> Unit,
    fullNameSupportingText: Boolean,
    emailSupportingText: Boolean,
    passwordSupportingText: Boolean
) {
    Column() {
        InputFieldLabel(text = "Full name")
        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            value = fullName,
            onValueChange = onFullNameChange,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next,
            isError = fullName.isBlank() && fullNameSupportingText
        )

        Spacer(modifier = Modifier.height(14.dp))

        InputFieldLabel(text = "Email")
        Spacer(modifier = Modifier.height(8.dp))
        InputTextField(
            value = email,
            onValueChange = onEmailChange,
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Next,
            isError = email.isBlank() && emailSupportingText
        )

        Spacer(modifier = Modifier.height(12.dp))

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

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "By creating an account, you agree with our terms & conditions",
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
fun InputFieldLabel(text: String) {
    Text(
        text = text,
        fontWeight = FontWeight.SemiBold,
        modifier = Modifier.fillMaxWidth(),
        fontSize = 20.sp
    )
}

@Composable
fun InputTextField(
    value: String,
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType,
    imeAction: ImeAction,
    isError: Boolean,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = KeyboardOptions(
            keyboardType = keyboardType,
            imeAction = imeAction
        ),
        isError = isError,
        shape = RoundedCornerShape(15.dp)
    )
}

@Composable
fun CreateUserButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    navigateToLogin: () -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Register")
        }
        Spacer(modifier = Modifier.height(6.dp))
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Already have an account?")
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Login",
                color = Color.DarkGray,
                modifier = Modifier.clickable { navigateToLogin() })
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewInputField() {
//    SwiftCartTheme {
//        RegisterScreen()
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewButton() {
//    SwiftCartTheme {
//        CreateUserButton{
//
//        }
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun PreviewHeading() {
//    SwiftCartTheme {
//        Heading()
//    }
//}