package com.ufkoku.daggernewway.domain.mapper

import com.ufkoku.daggernewway.domain.rest.entity.RestPost
import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import com.ufkoku.daggernewway.domain.ui.entity.Post

interface PostMapper {

    fun mapRestPost(post: RestPost, user: RestUser): Post

}

class PostMapperImpl(private val userMapper: IUserMapper) : PostMapper {

    override fun mapRestPost(post: RestPost, user: RestUser): Post =
            Post(post.id,
                    userMapper.mapRestUser(user),
                    post.title,
                    post.body)

}