package com.example.swiftcart.network

import com.example.swiftcart.data.model.CreateUserDto
import com.example.swiftcart.data.model.LoginDto
import com.example.swiftcart.data.model.LoginResponse
import com.example.swiftcart.data.model.CreateUserResponse
import com.example.swiftcart.data.model.ResetPasswordDto
import com.example.swiftcart.data.model.ResetPasswordResponse
import com.example.swiftcart.data.model.UserResponse
import com.example.swiftcart.utils.ApiException
import com.example.swiftcart.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject
import javax.inject.Named

class AuthService @Inject constructor(@Named("AuthClient")private val client: HttpClient) {

    suspend fun loginUser(loginDto: LoginDto): LoginResponse {
        val response: HttpResponse = client.post("${Constants.BASE_URL}auth/login-user") {
            contentType(ContentType.Application.Json)
            setBody(loginDto)
        }

        if (response.status.isSuccess()) {
            return response.body<LoginResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "Failed to login: Wrong credentials")
        }
    }

    suspend fun createUser(createUserDto: CreateUserDto): CreateUserResponse {
        val response = client.post("${Constants.BASE_URL}auth/register-user") {
            contentType(ContentType.Application.Json)
            setBody(createUserDto)
        }

        if (response.status.isSuccess()) {
            return response.body<CreateUserResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "An error occurred while creating an account")
        }

    }

    suspend fun resetPassword(resetPasswordDto: ResetPasswordDto): ResetPasswordResponse {
        val response = client.post("${Constants.BASE_URL}auth/reset-password") {
            contentType(ContentType.Application.Json)
            setBody(resetPasswordDto)
        }

        if (response.status.isSuccess()) {
            return response.body<ResetPasswordResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "Failed to reset user password")
        }
    }
}