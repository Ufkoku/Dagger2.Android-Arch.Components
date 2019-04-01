package com.ufkoku.daggernewway.domain.mapper.post

import com.ufkoku.daggernewway.domain.rest.entity.RestPost
import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import com.ufkoku.daggernewway.domain.ui.entity.Post

interface IPostMapper {

    fun mapRestPost(post: RestPost, user: RestUser): Post

}