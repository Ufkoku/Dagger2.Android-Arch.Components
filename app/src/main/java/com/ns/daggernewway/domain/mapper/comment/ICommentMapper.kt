package com.ns.daggernewway.domain.mapper.comment

import com.ns.daggernewway.domain.rest.entity.RestComment
import com.ns.daggernewway.domain.ui.entity.Comment

interface ICommentMapper {

    fun mapRestComment(comment: RestComment): Comment
}