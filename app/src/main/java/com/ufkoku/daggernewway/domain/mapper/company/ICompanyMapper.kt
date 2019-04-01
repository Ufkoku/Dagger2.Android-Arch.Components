package com.ufkoku.daggernewway.domain.mapper.company

import com.ufkoku.daggernewway.domain.rest.entity.RestCompany
import com.ufkoku.daggernewway.domain.ui.entity.Company

interface ICompanyMapper {

    fun mapRestCompany(company: RestCompany): Company

}