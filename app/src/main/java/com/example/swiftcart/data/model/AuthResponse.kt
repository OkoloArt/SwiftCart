package com.example.swiftcart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LoginDto(
    val email: String,
    val password: String
)

@Serializable
data class LoginResponse(
    val status: Int,
    val message: String,
    @SerialName("access_token")
    val accessToken: String
)

@Serializable
data class CreateUserDto(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
)

@Serializable
data class CreateUserResponse(
    val status: Int,
    val message: String
)

@Serializable
data class ResetPasswordResponse(
    val message: String,
    @SerialName("access_token")
    val accessToken: String
)

@Serializable
data class ResetPasswordDto(
    val loginDto: LoginDto,
    val confirmPassword: String
)