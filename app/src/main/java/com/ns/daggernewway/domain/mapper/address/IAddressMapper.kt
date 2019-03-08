package com.ns.daggernewway.domain.mapper.address

import com.ns.daggernewway.domain.rest.entity.RestAddress
import com.ns.daggernewway.domain.ui.entity.Address

interface IAddressMapper {

    fun mapRestAddress(address: RestAddress): Address

}