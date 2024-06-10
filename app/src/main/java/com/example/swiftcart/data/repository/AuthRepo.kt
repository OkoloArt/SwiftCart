package com.example.swiftcart.data.repository

import com.example.swiftcart.data.model.CreateUserDto
import com.example.swiftcart.data.model.LoginDto
import com.example.swiftcart.data.model.LoginResponse
import com.example.swiftcart.data.model.CreateUserResponse
import com.example.swiftcart.data.model.ResetPasswordDto
import com.example.swiftcart.data.model.ResetPasswordResponse
import com.example.swiftcart.data.model.UserResponse
import com.example.swiftcart.network.AuthService
import com.example.swiftcart.utils.AuthResult
import javax.inject.Inject

interface AuthRepo {
    suspend fun loginUser(loginDto: LoginDto): AuthResult<LoginResponse>
    suspend fun registerUser(createUserDto: CreateUserDto): AuthResult<CreateUserResponse>
    suspend fun resetPassword(resetPasswordDto: ResetPasswordDto): AuthResult<ResetPasswordResponse>
}

class AuthRepoImpl @Inject constructor(private val authService: AuthService) : AuthRepo {
    override suspend fun loginUser(loginDto: LoginDto): AuthResult<LoginResponse> =
        try {
            val response = authService.loginUser(loginDto)
            AuthResult.Success(response)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }

    override suspend fun registerUser(createUserDto: CreateUserDto): AuthResult<CreateUserResponse> =
        try {
            val response = authService.createUser(createUserDto)
            AuthResult.Success(response)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }

    override suspend fun resetPassword(resetPasswordDto: ResetPasswordDto): AuthResult<ResetPasswordResponse> =
        try {
            val response = authService.resetPassword(resetPasswordDto)
            AuthResult.Success(response)
        } catch (e: Exception) {
            AuthResult.Error(e)
        }
}
