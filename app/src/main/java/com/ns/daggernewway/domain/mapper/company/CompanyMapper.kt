package com.ns.daggernewway.domain.mapper.company

import com.ns.daggernewway.domain.rest.entity.RestCompany
import com.ns.daggernewway.domain.ui.entity.Company

class CompanyMapper : ICompanyMapper {

    override fun mapRestCompany(company: RestCompany): Company =
            Company(company.name, company.catchPhrase, company.bs)

}