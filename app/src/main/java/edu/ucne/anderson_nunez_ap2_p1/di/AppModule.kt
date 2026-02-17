package edu.ucne.anderson_nunez_ap2_p1.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import edu.ucne.anderson_nunez_ap2_p1.data.local.database.MetaDb
import edu.ucne.anderson_nunez_ap2_p1.data.repository.MetaRepositoryImpl
import edu.ucne.anderson_nunez_ap2_p1.domain.repository.MetaRepository
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.DeleteMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.GetMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.ObserveMetasUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.UpsertMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.MetaValidations
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.GetTotalMetasUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.GetTotalMontoUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMetaDb(@ApplicationContext context: Context): MetaDb {
        return Room.databaseBuilder(context, MetaDb::class.java, "Meta.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideMetaRepository(db: MetaDb): MetaRepository {
        return MetaRepositoryImpl(db.metaDao())
    }

    @Provides
    @Singleton
    fun provideUpsertMetaUseCase(repository: MetaRepository): UpsertMetaUseCase {
        return UpsertMetaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetMetaUseCase(repository: MetaRepository): GetMetaUseCase {
        return GetMetaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideDeleteMetaUseCase(repository: MetaRepository): DeleteMetaUseCase {
        return DeleteMetaUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideObserveMetasUseCase(repository: MetaRepository): ObserveMetasUseCase {
        return ObserveMetasUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideMetaValidations(repository: MetaRepository): MetaValidations {
        return MetaValidations(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalMetasUseCase(repository: MetaRepository): GetTotalMetasUseCase {
        return GetTotalMetasUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetTotalMontoUseCase(repository: MetaRepository): GetTotalMontoUseCase {
        return GetTotalMontoUseCase(repository)
    }
}
