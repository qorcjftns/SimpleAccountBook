package com.cheulsoon.simpleaccountbook.domain.di

import com.cheulsoon.simpleaccountbook.data.respository.RepositoryImpl
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import com.cheulsoon.simpleaccountbook.domain.usecase.GetProductDetailUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.GetProductListUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.GetTransactionByDateUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class UseCaseModule {

    @Provides
    @Singleton
    fun productListUseCaseProvider(repositoryImpl: RepositoryImpl) : GetProductListUseCase{
        return GetProductListUseCase(repositoryImpl)
    }

    @Provides
    @Singleton
    fun productDetailUseCaseProvider(repositoryImpl: RepositoryImpl) : GetProductDetailUseCase{
        return GetProductDetailUseCase(repositoryImpl)
    }

    @Provides
    @Singleton
    fun getTransactionByDateUseCase(transactionByDateUseCase: TransactionGatewayImpl) : GetTransactionByDateUseCase{
        return GetTransactionByDateUseCase(transactionByDateUseCase)
    }

}