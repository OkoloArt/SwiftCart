package com.example.swiftcart.network

import io.ktor.client.HttpClient
import javax.inject.Inject
import javax.inject.Named

class MainService @Inject constructor(@Named("MainClient")private val client: HttpClient) {

}