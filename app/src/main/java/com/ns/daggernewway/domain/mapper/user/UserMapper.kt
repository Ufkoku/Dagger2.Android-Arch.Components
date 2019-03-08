package com.ns.daggernewway.domain.mapper.user

import com.ns.daggernewway.domain.mapper.address.IAddressMapper
import com.ns.daggernewway.domain.mapper.company.ICompanyMapper
import com.ns.daggernewway.domain.rest.entity.RestUser
import com.ns.daggernewway.domain.ui.entity.User

class UserMapper(private val addressMapper: IAddressMapper,
                 private val companyMapper: ICompanyMapper) : IUserMapper {

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