package edu.ucne.anderson_nunez_ap2_p1.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import edu.ucne.anderson_nunez_ap2_p1.data.metas.repository.MetaRepositoryImpl
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindMetaRepository(
        impl: MetaRepositoryImpl
    ): MetaRepository
}