package com.example.swiftcart.utils

class ApiException(val statusCode: Int, message: String) : Exception(message)
