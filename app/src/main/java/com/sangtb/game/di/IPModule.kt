//package com.sangtb.game.di
//
//import com.sangtb.game.data.ApiIpList
//import com.sangtb.game.data.repository.IpRepository
//import com.sangtb.game.data.repository.IpRepositoryImpl
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import retrofit2.Retrofit
//import javax.inject.Singleton
//
//@Module(includes = [NetworkModule::class])
//@InstallIn(SingletonComponent::class)
//class AuthModule {
//    @Singleton
//    @Provides
//    fun provideAuthenticationAPI(retrofit: Retrofit):ApiIpList{
//        return retrofit.create(ApiIpList::class.java)
//    }
//
//    @Singleton
//    @Provides
//    fun provideIpListRepository(apiIpList: ApiIpList) : IpRepository {
//        return IpRepositoryImpl(apiIpList)
//    }
//}