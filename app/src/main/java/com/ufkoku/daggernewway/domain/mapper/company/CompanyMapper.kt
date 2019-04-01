package com.ufkoku.daggernewway.domain.mapper.company

import com.ufkoku.daggernewway.domain.rest.entity.RestCompany
import com.ufkoku.daggernewway.domain.ui.entity.Company

class CompanyMapper : ICompanyMapper {

    override fun mapRestCompany(company: RestCompany): Company =
            Company(company.name, company.catchPhrase, company.bs)

}