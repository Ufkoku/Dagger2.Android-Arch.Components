package com.ns.daggernewway.domain.mapper.company

import com.ns.daggernewway.domain.rest.entity.RestCompany
import com.ns.daggernewway.domain.ui.entity.Company

interface ICompanyMapper {

    fun mapRestCompany(company: RestCompany): Company

}