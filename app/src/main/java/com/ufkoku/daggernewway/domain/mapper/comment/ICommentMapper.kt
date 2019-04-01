package com.ufkoku.daggernewway.domain.mapper.comment

import com.ufkoku.daggernewway.domain.rest.entity.RestComment
import com.ufkoku.daggernewway.domain.ui.entity.Comment

interface ICommentMapper {

    fun mapRestComment(comment: RestComment): Comment
}