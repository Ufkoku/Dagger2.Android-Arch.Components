package com.ns.daggernewway.di.domain.mapper

import com.ns.daggernewway.domain.mapper.company.CompanyMapper
import com.ns.daggernewway.domain.mapper.company.ICompanyMapper
import dagger.Module
import dagger.Provides

@Module
class CompanyMapperModule {

    @Provides
    fun provideCompanyMapper(): ICompanyMapper = CompanyMapper()

}