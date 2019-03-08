package com.ns.daggernewway.domain.mapper.address

import com.ns.daggernewway.domain.rest.entity.RestAddress
import com.ns.daggernewway.domain.ui.entity.Address

class AddressMapper : IAddressMapper {

    override fun mapRestAddress(address: RestAddress): Address =
            Address(address.street, address.suite, address.city, address.zipcode)

}