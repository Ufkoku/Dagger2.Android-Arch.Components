package com.ns.daggernewway.di.domain.mapper

import com.ns.daggernewway.domain.mapper.address.AddressMapper
import com.ns.daggernewway.domain.mapper.address.IAddressMapper
import dagger.Module
import dagger.Provides

@Module
class AddressMapperModule {

    @Provides
    fun provideAddressMapper(): IAddressMapper = AddressMapper()

}