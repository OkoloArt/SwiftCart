package com.example.swiftcart.data.model

import kotlinx.serialization.Serializable
@Serializable
data class User(
    val firstName: String,
    val lastName: String,
    val username: String? = null,
    val email: String,
    val password: String? = null,
    val profile: Profile? = null,
    val productResponses: List<ProductResponse>? = null,
    val userCart: List<String>? = null,
    val userRole: ROLE,
)

@Serializable
data class Profile(
    val country: String? = null,
    val address: String? = null,
    val mobileNo: Long? = null,
    val image: String? = null,
    val imageKey: String? = null,
    val gender: GENDER? = null
)


enum class ROLE(val role: String) {
    SELLER(role = "SELLER"),
    BUYER(role = "BUYER")
}

enum class GENDER(val gender :String){
    MALE(gender = "MALE"),
    FEMALE(gender = "FEMALE"),
    OTHER(gender = "OTHER")
}

@Serializable
data class CartResponse(
    val message : String
)
