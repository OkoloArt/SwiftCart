package com.example.swiftcart.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ProductResponse(
    val itemCount: Int,
    @SerialName("products")
    val productDto: List<ProductDto>
)

@Serializable
data class ProductDto(
    val id: String,
    val name: String,
    val price: Int,
    val ratings: Int,
    val image: String
)
