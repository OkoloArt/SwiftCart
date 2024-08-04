package com.example.swiftcart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.swiftcart.data.model.CartResponse
import com.example.swiftcart.data.model.ProductResponse
import com.example.swiftcart.data.model.User
import com.example.swiftcart.data.repository.UserRepo
import com.example.swiftcart.utils.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepo: UserRepo) : ViewModel() {

    private val _user = MutableStateFlow<AuthResult<User>>(AuthResult.Loading)
    val user : StateFlow<AuthResult<User>> = _user.asStateFlow()

    private val _productsInCart = MutableStateFlow<AuthResult<ProductResponse>>(AuthResult.Loading)
    val productsInCart : StateFlow<AuthResult<ProductResponse>> = _productsInCart.asStateFlow()

    init {
        getCurrentUser()
    }

    private fun getCurrentUser(){
        viewModelScope.launch {
            val response = userRepo.getCurrentUser()
            _user.value = response
        }
    }

    fun getProductsInCart(){
        viewModelScope.launch {
            delay(2000)
            val response = userRepo.getProductsInCart()
            _productsInCart.value = response
        }
    }
}
