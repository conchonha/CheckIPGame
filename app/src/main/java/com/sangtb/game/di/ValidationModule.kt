package com.sangtb.game.di;

import com.sangtb.androidlibrary.utils.Validations
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/*
    Copyright © 2022 UITS CO.,LTD
    Created by SangTB on 4/14/2022
*/

@Module
@InstallIn(SingletonComponent::class)
class ValidationModule {
    @Singleton
    @Provides
    fun provideValidation() = Validations()
}
