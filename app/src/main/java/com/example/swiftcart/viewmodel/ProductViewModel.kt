package com.example.swiftcart.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcart.data.model.Product
import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.data.repository.ProductRepo
import com.example.swiftcart.utils.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(private val productRepo: ProductRepo) : ViewModel(){

    private val _products = MutableStateFlow<AuthResult<ProductResponse>>(AuthResult.Loading)
    val products : StateFlow<AuthResult<ProductResponse>> = _products.asStateFlow()

    private val _productDetail = MutableStateFlow<AuthResult<Product>>(AuthResult.Loading)
    val productDetail : StateFlow<AuthResult<Product>> = _productDetail.asStateFlow()

    private val _productId = MutableLiveData<String>()
    val productId: LiveData<String> = _productId

     var isRefreshing by mutableStateOf(false)
        private set

    private var isDataLoaded = false

    init {
        //getAllProducts()
       // startPolling()
    }

    fun getAllProducts(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            if (!isDataLoaded || forceRefresh) {
                _products.value = AuthResult.Loading
                delay(5000) // Add a delay of 5 seconds
                val result = productRepo.getAllProducts()
                _products.value = result
                if (result is AuthResult.Success) {
                    isDataLoaded = true
                }
            }
        }
    }

    fun setProductId(productId: String){
        _productId.value = productId
    }

    fun getSwipeChanges(productId: String) = viewModelScope.launch {
        isRefreshing = true
        delay(3000L)
        getCurrentProduct(productId)
        isRefreshing = false
    }

    fun getCurrentProduct(productId: String){
        viewModelScope.launch {
            val response = productRepo.getCurrentProduct(productId)
            _productDetail.value = response
        }
    }


    private fun startPolling() {
        viewModelScope.launch {
            while (isActive) {
                delay(3 * 60 * 60 * 1000) // Poll every 3 hours
                getAllProducts(forceRefresh = true)
            }
        }
    }
}
