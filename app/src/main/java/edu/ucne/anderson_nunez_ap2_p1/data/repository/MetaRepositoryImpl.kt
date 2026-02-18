package edu.ucne.anderson_nunez_ap2_p1.data.metas.repository

import edu.ucne.anderson_nunez_ap2_p1.data.metas.local.MetaDao
import edu.ucne.anderson_nunez_ap2_p1.data.metas.mapper.toDomain
import edu.ucne.anderson_nunez_ap2_p1.data.metas.mapper.toEntity
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class MetaRepositoryImpl @Inject constructor(
    private val metaDao: MetaDao
) : MetaRepository {

    override suspend fun upsert(meta: Meta) {
        metaDao.upsert(meta.toEntity())
    }

    override suspend fun delete(id: Int) {
        getMeta(id)?.let { meta ->
            metaDao.delete(meta.toEntity())
        }
    }

    override suspend fun getMeta(id: Int): Meta? {
        return metaDao.getById(id)?.toDomain()
    }

    override fun observeAll(): Flow<List<Meta>> {
        return metaDao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun observeFiltered(descripcion: String?, observaciones: String?): Flow<List<Meta>> {
        return metaDao.observeFiltered(descripcion, observaciones).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun existsByDescripcion(descripcion: String, idMeta: Int?): Boolean {
        return metaDao.existsByDescripcion(descripcion, idMeta)
    }
}