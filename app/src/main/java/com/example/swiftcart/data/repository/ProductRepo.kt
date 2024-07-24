package com.example.swiftcart.data.repository

import com.example.swiftcart.data.model.Product
import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.network.ProductService
import com.example.swiftcart.utils.AuthResult
import javax.inject.Inject

interface ProductRepo {
    suspend fun getAllProducts() : AuthResult<ProductResponse>

    suspend fun getCurrentProduct(productId: String): AuthResult<Product>
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

    override suspend fun getCurrentProduct(productId: String): AuthResult<Product> {
        return try {
            val product = productService.getCurrentProduct(productId)
            AuthResult.Success(product)
        }catch (e : Exception){
            AuthResult.Error(e)
        }
    }
}