package com.example.swiftcart.data.repository

import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.network.ProductService
import com.example.swiftcart.utils.AuthResult
import javax.inject.Inject

interface ProductRepo {
    suspend fun getAllProducts() : AuthResult<ProductResponse>
}

class ProductRepoImpl @Inject constructor(private val productService: ProductService): ProductRepo {
    override suspend fun getAllProducts(): AuthResult<ProductResponse> {
       return try {
           val productResponse = productService.getAllProducts()
           AuthResult.Success(productResponse)
       }catch (e : Exception){
           AuthResult.Error(e)
       }
    }
}