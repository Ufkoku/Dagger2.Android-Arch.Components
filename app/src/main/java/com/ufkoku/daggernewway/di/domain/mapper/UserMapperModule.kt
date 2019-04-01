package com.ufkoku.daggernewway.di.domain.mapper

import com.ufkoku.daggernewway.domain.mapper.address.IAddressMapper
import com.ufkoku.daggernewway.domain.mapper.company.ICompanyMapper
import com.ufkoku.daggernewway.domain.mapper.user.IUserMapper
import com.ufkoku.daggernewway.domain.mapper.user.UserMapper
import dagger.Module
import dagger.Provides

@Module(includes = [AddressMapperModule::class, CompanyMapperModule::class])
class UserMapperModule {

    @Provides
    fun provideUserMapper(addressMapper: IAddressMapper,
                          companyMapper: ICompanyMapper): IUserMapper =
            UserMapper(addressMapper, companyMapper)

}