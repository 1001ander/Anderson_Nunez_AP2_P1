package edu.ucne.anderson_nunez_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.anderson_nunez_ap2_p1.data.local.database.MetaDb
import edu.ucne.anderson_nunez_ap2_p1.data.metas.repository.MetaRepositoryImpl
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase.DeleteMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase.GetMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase.ObserveMetasUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase.UpsertMetaUseCase
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideMetaDb(@ApplicationContext appContext: Context) =
        Room.databaseBuilder(
            appContext,
            MetaDb::class.java,
            "Meta.db"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideMetaDao(metaDb: MetaDb) = metaDb.metaDao()

    @Provides
    @Singleton
    fun provideMetaRepository(impl: MetaRepositoryImpl): MetaRepository = impl


    @Provides
    @Singleton
    fun provideUpsertMetaUseCase(repository: MetaRepository) =
        UpsertMetaUseCase(repository)

    @Provides
    @Singleton
    fun provideGetMetaUseCase(repository: MetaRepository) =
        GetMetaUseCase(repository)

    @Provides
    @Singleton
    fun provideDeleteMetaUseCase(repository: MetaRepository) =
        DeleteMetaUseCase(repository)

    @Provides
    @Singleton
    fun provideObserveMetasUseCase(repository: MetaRepository) =
        ObserveMetasUseCase(repository)
}