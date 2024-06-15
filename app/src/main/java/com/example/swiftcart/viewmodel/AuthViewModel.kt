package com.example.swiftcart.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcart.data.model.CreateUserDto
import com.example.swiftcart.data.model.LoginDto
import com.example.swiftcart.data.model.LoginResponse
import com.example.swiftcart.data.model.CreateUserResponse
import com.example.swiftcart.data.model.ResetPasswordDto
import com.example.swiftcart.data.model.ResetPasswordResponse
import com.example.swiftcart.data.model.UserResponse
import com.example.swiftcart.data.repository.AuthRepo
import com.example.swiftcart.data.repository.DataStoreRepo
import com.example.swiftcart.utils.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepo: AuthRepo,
    private val dataStoreRepo: DataStoreRepo
) : ViewModel() {

    private val _loginResult = MutableStateFlow<AuthResult<LoginResponse>>(AuthResult.Loading)
    val loginResult: StateFlow<AuthResult<LoginResponse>> = _loginResult.asStateFlow()

    private val _createUserResult =
        mutableStateOf<AuthResult<CreateUserResponse>>(AuthResult.Loading)
    val createUserResult: State<AuthResult<CreateUserResponse>> = _createUserResult

    private val _resetPassword =
        mutableStateOf<AuthResult<ResetPasswordResponse>>(AuthResult.Loading)
    val resetPassword: State<AuthResult<ResetPasswordResponse>> = _resetPassword

    fun loginUser(loginDto: LoginDto) {
        viewModelScope.launch {
            when (val result = authRepo.loginUser(loginDto)) {
                is AuthResult.Success -> {
                    if (result.data.status == 200) {
                        dataStoreRepo.apply {
                            this.saveJsonWebToken(result.data.accessToken)
                            this.setHasLoggedIn(true)
                        }
                    }
                    _loginResult.value = result
                }

                is AuthResult.Error -> {
                    _loginResult.value = result
                }

                is AuthResult.Loading -> {}

            }
        }
    }

    fun createUser(createUserDto: CreateUserDto) {
        viewModelScope.launch {
            when (val result = authRepo.registerUser(createUserDto)) {
                is AuthResult.Error -> {
                    _createUserResult.value = result
                }

                AuthResult.Loading -> {}
                is AuthResult.Success -> {
                    _createUserResult.value = result
                }
            }

        }
    }

    fun resetPassword(resetPasswordDto: ResetPasswordDto) {
        viewModelScope.launch {
            when (val result = authRepo.resetPassword(resetPasswordDto)) {
                is AuthResult.Error -> {
                    _resetPassword.value = result
                }

                AuthResult.Loading -> {}
                is AuthResult.Success -> {
                    _resetPassword.value = result
                }
            }
        }
    }

    fun validateInput(
        fullName: String,
        email: String,
        password: String,
        username: String
    ): Boolean {
        return !(fullName.isBlank() || email.isBlank() || password.isBlank() || username.isBlank())
    }

     fun createUserDto(fullName: String, email: String, password: String, username: String): CreateUserDto {
        val nameParts = fullName.split(" ")

        val firstName = if (nameParts.isNotEmpty()) nameParts[0] else ""
        val lastName = if (nameParts.size > 1) nameParts[1] else ""

        return CreateUserDto(
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = password,
            username = username
        )
    }


}
