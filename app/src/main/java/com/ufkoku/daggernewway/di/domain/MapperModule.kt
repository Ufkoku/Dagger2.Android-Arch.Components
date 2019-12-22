package com.ufkoku.daggernewway.di.domain

import com.ufkoku.daggernewway.domain.mapper.address.AddressMapper
import com.ufkoku.daggernewway.domain.mapper.address.IAddressMapper
import com.ufkoku.daggernewway.domain.mapper.comment.CommentMapper
import com.ufkoku.daggernewway.domain.mapper.comment.ICommentMapper
import com.ufkoku.daggernewway.domain.mapper.company.CompanyMapper
import com.ufkoku.daggernewway.domain.mapper.company.ICompanyMapper
import com.ufkoku.daggernewway.domain.mapper.post.IPostMapper
import com.ufkoku.daggernewway.domain.mapper.post.PostMapper
import com.ufkoku.daggernewway.domain.mapper.user.IUserMapper
import com.ufkoku.daggernewway.domain.mapper.user.UserMapper
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideUserMapper(addressMapper: IAddressMapper,
                          companyMapper: ICompanyMapper): IUserMapper =
            UserMapper(addressMapper, companyMapper)

    @Provides
    fun providePostMapper(userMapper: IUserMapper): IPostMapper =
            PostMapper(userMapper)

    @Provides
    fun provideCompanyMapper(): ICompanyMapper = CompanyMapper()

    @Provides
    fun provideCommentMapper(): ICommentMapper = CommentMapper()

    @Provides
    fun provideAddressMapper(): IAddressMapper = AddressMapper()

}