package com.ufkoku.daggernewway.domain.mapper

import com.ufkoku.daggernewway.domain.rest.entity.RestComment
import com.ufkoku.daggernewway.domain.ui.entity.Comment

interface CommentMapper {

    fun mapRestComment(comment: RestComment): Comment

}

class CommentMapperImpl : CommentMapper {

    override fun mapRestComment(comment: RestComment): Comment =
            Comment(comment.postId, comment.id, comment.name, comment.email, comment.body)

}