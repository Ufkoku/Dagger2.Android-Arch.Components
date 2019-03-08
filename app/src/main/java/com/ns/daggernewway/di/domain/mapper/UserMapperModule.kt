package com.ns.daggernewway.di.domain.mapper

import com.ns.daggernewway.domain.mapper.address.IAddressMapper
import com.ns.daggernewway.domain.mapper.company.ICompanyMapper
import com.ns.daggernewway.domain.mapper.user.IUserMapper
import com.ns.daggernewway.domain.mapper.user.UserMapper
import dagger.Module
import dagger.Provides

@Module(includes = [AddressMapperModule::class, CompanyMapperModule::class])
class UserMapperModule {

    @Provides
    fun provideUserMapper(addressMapper: IAddressMapper,
                          companyMapper: ICompanyMapper): IUserMapper =
            UserMapper(addressMapper, companyMapper)

}