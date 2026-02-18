package edu.ucne.anderson_nunez_ap2_p1.data.metas.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface MetaDao {
    @Upsert
    suspend fun upsert(meta: MetaEntity)

    @Query("SELECT * FROM Metas WHERE idMeta = :id LIMIT 1")
    suspend fun getById(id: Int): MetaEntity?

    @Delete
    suspend fun delete(meta: MetaEntity)

    @Query("SELECT * FROM Metas ORDER BY descripcion ASC")
    fun observeAll(): Flow<List<MetaEntity>>

    @Query("""
        SELECT * FROM Metas 
        WHERE (:descripcion IS NULL OR LOWER(descripcion) LIKE '%' || LOWER(:descripcion) || '%')
        AND (:observaciones IS NULL OR LOWER(observaciones) LIKE '%' || LOWER(:observaciones) || '%')
        ORDER BY descripcion ASC
    """)
    fun observeFiltered(descripcion: String?, observaciones: String?): Flow<List<MetaEntity>>

    @Query("""
        SELECT EXISTS(
            SELECT 1 FROM Metas 
            WHERE LOWER(TRIM(descripcion)) = LOWER(TRIM(:descripcion))
            AND (:idMeta IS NULL OR idMeta != :idMeta)
            LIMIT 1
        )
    """)
    suspend fun existsByDescripcion(descripcion: String, idMeta: Int?): Boolean
}