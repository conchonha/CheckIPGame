package com.sangtb.game.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainCoroutineScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IOCoroutineScope

//Retrofit
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitAddress

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class RetrofitVietNam

//ipAddress
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiIPAddress

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiIPVietNam

