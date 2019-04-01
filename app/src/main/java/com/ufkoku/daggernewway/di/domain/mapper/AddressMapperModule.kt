package com.ufkoku.daggernewway.di.domain.mapper

import com.ufkoku.daggernewway.domain.mapper.address.AddressMapper
import com.ufkoku.daggernewway.domain.mapper.address.IAddressMapper
import dagger.Module
import dagger.Provides

@Module
class AddressMapperModule {

    @Provides
    fun provideAddressMapper(): IAddressMapper = AddressMapper()

}