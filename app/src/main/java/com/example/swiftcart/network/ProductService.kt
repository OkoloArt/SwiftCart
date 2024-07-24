package com.example.swiftcart.network

import com.example.swiftcart.data.model.Product
import com.example.swiftcart.data.model.ProductResponse
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

class ProductService @Inject constructor(@Named("MainClient")private val client: HttpClient) {

    suspend fun getAllProducts() : ProductResponse {
        val response: HttpResponse = client.get("${Constants.BASE_URL}product/all-products") {
            contentType(ContentType.Application.Json)
        }

        if (response.status.isSuccess()) {
            return response.body<ProductResponse>()
        } else {
            throw ApiException(statusCode = response.status.value, "Error fetching products list")
        }
    }

    suspend fun getCurrentProduct(productId : String) : Product {
        val response: HttpResponse = client.get("${Constants.BASE_URL}product/current/${productId}") {
            contentType(ContentType.Application.Json)
        }

        if (response.status.isSuccess()) {
            return response.body<Product>()
        } else {
            throw ApiException(statusCode = response.status.value, "Error getting product")
        }
    }

}