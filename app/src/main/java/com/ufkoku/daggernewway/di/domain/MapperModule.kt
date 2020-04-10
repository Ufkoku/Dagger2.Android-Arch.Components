package com.ufkoku.daggernewway.di.domain

import com.ufkoku.daggernewway.domain.mapper.*
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideUserMapper(addressMapper: AddressMapper,
                          companyMapper: CompanyMapper): IUserMapper =
            UserMapperImpl(addressMapper, companyMapper)

    @Provides
    fun providePostMapper(userMapper: IUserMapper): PostMapper =
            PostMapperImpl(userMapper)

    @Provides
    fun provideCompanyMapper(): CompanyMapper = CompanyMapperImpl()

    @Provides
    fun provideCommentMapper(): CommentMapper = CommentMapperImpl()

    @Provides
    fun provideAddressMapper(): AddressMapper = AddressMapperImpl()

}