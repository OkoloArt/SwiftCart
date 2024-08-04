package com.example.swiftcart.data.repository

import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.data.model.User
import com.example.swiftcart.network.UserService
import com.example.swiftcart.utils.AuthResult

interface UserRepo {
    suspend fun getCurrentUser() : AuthResult<User>

    suspend fun getProductsInCart() : AuthResult<ProductResponse>
}

class UserRepoImpl (private val userService: UserService): UserRepo{
    override suspend fun getCurrentUser(): AuthResult<User> {
        return try {
           val user = userService.getCurrentUser()
            AuthResult.Success(user)
        }catch (e: Exception){
            AuthResult.Error(e)
        }
    }

    override suspend fun getProductsInCart(): AuthResult<ProductResponse> {
      return try {
          val response = userService.getProductsInCart()
          AuthResult.Success(response)
      }catch (e: Exception){
          AuthResult.Error(e)
      }
    }

}
