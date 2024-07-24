package com.example.swiftcart.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val attributes: List<Attribute>,
    val category: String,
    val createdAt: String,
    val description: String,
    val id: String,
    val images: List<String>,
    val name: String,
    val price: Int,
    val quantity: Int,
    val ratings: Ratings,
    val reviews: List<Review>,
    val specifications: Specifications,
    val updatedAt: String,
    val user: SimpleUser
)

@Serializable
data class SimpleUser(
    val username: String,
    val id: String
)

@Serializable
data class Attribute(
    val name: String,
    val value: String
)

@Serializable
data class Review(
    val user: String,
    val rating: Int,
    val comment: String,
)

@Serializable
data class Dimensions(
    val height: Int,
    val length: Int,
    val width: Int
)

@Serializable
data class Ratings(
    val average: Double,
    val count: Int
)

@Serializable
data class Specifications(
    val dimensions: Dimensions,
    val weight: Double
)