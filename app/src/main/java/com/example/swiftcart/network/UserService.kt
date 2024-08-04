package com.example.swiftcart.network

import com.example.swiftcart.data.model.CartResponse
import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.data.model.User
import com.example.swiftcart.utils.ApiException
import com.example.swiftcart.utils.Constants
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import javax.inject.Inject
import javax.inject.Named

class UserService @Inject constructor(@Named("MainClient")private val client: HttpClient) {

    suspend fun getCurrentUser(): User {
        val response: HttpResponse = client.get("${Constants.BASE_URL}user/current") {
            contentType(ContentType.Application.Json)
        }

        if (response.status.isSuccess()) {
            return response.body<User>()
        } else {
            throw ApiException(statusCode = response.status.value, "Error fetching user details")
        }
    }

    suspend fun getProductsInCart(): ProductResponse{
        val response : HttpResponse = client.get("${Constants.BASE_URL}user/getProductsInCart"){
            contentType(ContentType.Application.Json)
        }

        if (response.status.isSuccess()){
            return response.body<ProductResponse>()
        }else {
            throw ApiException(statusCode = response.status.value, "Error fetching products in cart")
        }
    }
}