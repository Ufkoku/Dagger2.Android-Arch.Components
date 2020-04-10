package com.ufkoku.daggernewway.domain.mapper

import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import com.ufkoku.daggernewway.domain.ui.entity.User

interface IUserMapper {

    fun mapRestUser(restUser: RestUser): User

}

class UserMapperImpl(private val addressMapper: AddressMapper,
                     private val companyMapper: CompanyMapper) : IUserMapper {

    override fun mapRestUser(restUser: RestUser): User =
            User(restUser.id,
                    restUser.name,
                    restUser.username,
                    restUser.email,
                    addressMapper.mapRestAddress(restUser.address),
                    restUser.phone,
                    restUser.website,
                    companyMapper.mapRestCompany(restUser.company))

}