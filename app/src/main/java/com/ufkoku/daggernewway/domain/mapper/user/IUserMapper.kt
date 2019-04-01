package com.ufkoku.daggernewway.domain.mapper.user

import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import com.ufkoku.daggernewway.domain.ui.entity.User

interface IUserMapper {

    fun mapRestUser(restUser: RestUser): User

}