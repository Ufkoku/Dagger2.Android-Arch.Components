package com.ufkoku.daggernewway.domain.mapper.user

import com.ufkoku.daggernewway.domain.mapper.address.IAddressMapper
import com.ufkoku.daggernewway.domain.mapper.company.ICompanyMapper
import com.ufkoku.daggernewway.domain.rest.entity.RestUser
import com.ufkoku.daggernewway.domain.ui.entity.User

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