package com.ufkoku.daggernewway.domain.mapper

import com.ufkoku.daggernewway.domain.rest.entity.RestCompany
import com.ufkoku.daggernewway.domain.ui.entity.Company

interface CompanyMapper {

    fun mapRestCompany(company: RestCompany): Company

}

class CompanyMapperImpl : CompanyMapper {

    override fun mapRestCompany(company: RestCompany): Company =
            Company(company.name, company.catchPhrase, company.bs)

}