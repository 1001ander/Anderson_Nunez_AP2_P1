package edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta
import kotlinx.coroutines.flow.Flow

interface MetaRepository {
    suspend fun upsert(meta: Meta)
    suspend fun delete(id: Int)
    suspend fun getMeta(id: Int): Meta?
    fun observeAll(): Flow<List<Meta>>
    fun observeFiltered(descripcion: String?, observaciones: String?): Flow<List<Meta>>
    suspend fun existsByDescripcion(descripcion: String, idMeta: Int?): Boolean
}