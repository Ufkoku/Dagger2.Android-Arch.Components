package com.ufkoku.daggernewway.di.domain.mapper

import com.ufkoku.daggernewway.domain.mapper.company.CompanyMapper
import com.ufkoku.daggernewway.domain.mapper.company.ICompanyMapper
import dagger.Module
import dagger.Provides

@Module
class CompanyMapperModule {

    @Provides
    fun provideCompanyMapper(): ICompanyMapper = CompanyMapper()

}