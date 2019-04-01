package com.ufkoku.daggernewway.domain.mapper.address

import com.ufkoku.daggernewway.domain.rest.entity.RestAddress
import com.ufkoku.daggernewway.domain.ui.entity.Address

class AddressMapper : IAddressMapper {

    override fun mapRestAddress(address: RestAddress): Address =
            Address(address.street, address.suite, address.city, address.zipcode)

}