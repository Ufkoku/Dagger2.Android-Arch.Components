package com.ns.daggernewway.domain.mapper.post

import com.ns.daggernewway.domain.rest.entity.RestPost
import com.ns.daggernewway.domain.rest.entity.RestUser
import com.ns.daggernewway.domain.ui.entity.Post

interface IPostMapper {

    fun mapRestPost(post: RestPost, user: RestUser): Post

}