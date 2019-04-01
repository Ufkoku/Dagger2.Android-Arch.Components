package com.ufkoku.daggernewway.di.domain.mapper

import com.ufkoku.daggernewway.domain.mapper.post.IPostMapper
import com.ufkoku.daggernewway.domain.mapper.post.PostMapper
import com.ufkoku.daggernewway.domain.mapper.user.IUserMapper
import dagger.Module
import dagger.Provides

@Module(includes = [UserMapperModule::class])
class PostMapperModule {

    @Provides
    fun providePostMapper(userMapper: IUserMapper): IPostMapper =
            PostMapper(userMapper)

}