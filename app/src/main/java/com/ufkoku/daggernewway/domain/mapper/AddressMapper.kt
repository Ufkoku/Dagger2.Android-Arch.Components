package com.ufkoku.daggernewway.domain.mapper

import com.ufkoku.daggernewway.domain.rest.entity.RestAddress
import com.ufkoku.daggernewway.domain.ui.entity.Address

interface AddressMapper {

    fun mapRestAddress(address: RestAddress): Address

}

class AddressMapperImpl : AddressMapper {

    override fun mapRestAddress(address: RestAddress): Address =
            Address(address.street, address.suite, address.city, address.zipcode)

}