package com.ns.daggernewway.domain.mapper.user

import com.ns.daggernewway.domain.rest.entity.RestUser
import com.ns.daggernewway.domain.ui.entity.User

interface IUserMapper {

    fun mapRestUser(restUser: RestUser): User

}