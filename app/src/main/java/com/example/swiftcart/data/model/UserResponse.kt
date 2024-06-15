package com.example.swiftcart.data.model

import kotlinx.serialization.Serializable
@Serializable
data class UserResponse(
    val firstName: String,
    val lastName: String,
    val username: String,
    val email: String,
    val password: String,
    val profile: Profile,
    val products: List<Product>,
    val userCart: List<String>,
    val userRole: ROLE,
)

@Serializable
data class Profile(
    val country: String? = null,
    val address: String? = null,
    val mobileNo: Int? = null,
    val image: String?,
    val imageKey: String?,
    val gender: GENDER?
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

