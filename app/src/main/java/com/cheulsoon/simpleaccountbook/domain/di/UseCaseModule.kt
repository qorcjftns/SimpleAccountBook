package com.cheulsoon.simpleaccountbook.domain.di

import com.cheulsoon.simpleaccountbook.data.respository.RepositoryImpl
import com.cheulsoon.simpleaccountbook.data.respository.TransactionGatewayImpl
import com.cheulsoon.simpleaccountbook.domain.usecase.GetProductDetailUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.GetProductListUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.GetTransactionByDateUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.GetTransactionByIdUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.InsertTransactionUseCase
import com.cheulsoon.simpleaccountbook.domain.usecase.UpdateTransactionUseCase
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
    fun getTransactionByDateUseCase(transactionGateway: TransactionGatewayImpl) : GetTransactionByDateUseCase{
        return GetTransactionByDateUseCase(transactionGateway)
    }

    @Provides
    @Singleton
    fun insertTransactionUseCase(transactionGateway: TransactionGatewayImpl) : InsertTransactionUseCase{
        return InsertTransactionUseCase(transactionGateway)
    }

    @Provides
    @Singleton
    fun getTransactionByIdUseCase(transactionGateway: TransactionGatewayImpl) : GetTransactionByIdUseCase {
        return GetTransactionByIdUseCase(transactionGateway)
    }

    @Provides
    @Singleton
    fun updateTransactionUseCase(transactionGateway: TransactionGatewayImpl) : UpdateTransactionUseCase {
        return UpdateTransactionUseCase(transactionGateway)
    }

}