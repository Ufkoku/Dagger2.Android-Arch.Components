package com.ufkoku.daggernewway.domain.mapper.comment

import com.ufkoku.daggernewway.domain.rest.entity.RestComment
import com.ufkoku.daggernewway.domain.ui.entity.Comment

class CommentMapper : ICommentMapper {

    override fun mapRestComment(comment: RestComment): Comment =
            Comment(comment.postId, comment.id, comment.name, comment.email, comment.body)

}