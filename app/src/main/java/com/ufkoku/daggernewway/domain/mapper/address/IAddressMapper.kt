package com.ufkoku.daggernewway.domain.mapper.address

import com.ufkoku.daggernewway.domain.rest.entity.RestAddress
import com.ufkoku.daggernewway.domain.ui.entity.Address

interface IAddressMapper {

    fun mapRestAddress(address: RestAddress): Address

}