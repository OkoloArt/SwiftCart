package com.example.swiftcart.di

import android.content.Context
import com.example.swiftcart.data.repository.AuthRepo
import com.example.swiftcart.data.repository.AuthRepoImpl
import com.example.swiftcart.data.repository.DataStoreRepo
import com.example.swiftcart.data.repository.ProductRepo
import com.example.swiftcart.data.repository.ProductRepoImpl
import com.example.swiftcart.data.repository.UserRepo
import com.example.swiftcart.data.repository.UserRepoImpl
import com.example.swiftcart.network.AuthService
import com.example.swiftcart.network.MainHttpClient
import com.example.swiftcart.network.ProductService
import com.example.swiftcart.network.UserService
import com.example.swiftcart.network.authHttpClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepo(context = context)

}

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepo(authService: AuthService): AuthRepo {
        return AuthRepoImpl(authService)
    }

    @Provides
    @Singleton
    fun provideUserRepo(userService: UserService): UserRepo = UserRepoImpl(userService)

    @Provides
    @Singleton
    fun provideProductRepo(productService: ProductService): ProductRepo = ProductRepoImpl(productService)
}

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("AuthClient")
    fun provideAuthClient(): HttpClient {
        return authHttpClient
    }

    @Provides
    @Singleton
    @Named("MainClient")
    fun provideMainClient(dataStoreRepo: DataStoreRepo): HttpClient {
        return MainHttpClient(dataStoreRepo).httpClient
    }

    @Provides
    @Singleton
    fun provideAuthApiInterface(@Named("AuthClient") httpClient: HttpClient): AuthService {
        return AuthService(httpClient)
    }

    @Provides
    @Singleton
    fun provideUserApiInterface(@Named("MainClient") httpClient: HttpClient): UserService {
        return UserService(httpClient)
    }

    @Provides
    @Singleton
    fun provideProductApiInterface(@Named("MainClient") httpClient: HttpClient): ProductService =
        ProductService(httpClient)
}
