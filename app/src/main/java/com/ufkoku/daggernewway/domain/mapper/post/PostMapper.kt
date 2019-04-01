package com.ufkoku.daggernewway.domain.mapper.post

import com.ufkoku.daggernewway.domain.mapper.user.IUserMapper
import com.ufkoku.daggernewway.domain.rest.entity.RestPost
import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import com.ufkoku.daggernewway.domain.ui.entity.Post

class PostMapper(private val userMapper: IUserMapper) : IPostMapper {

    override fun mapRestPost(post: RestPost, user: RestUser): Post =
            Post(post.id,
                    userMapper.mapRestUser(user),
                    post.title,
                    post.body)

}