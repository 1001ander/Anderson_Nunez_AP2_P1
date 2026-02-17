package edu.ucne.anderson_nunez_ap2_p1.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import edu.ucne.anderson_nunez_ap2_p1.data.local.entities.MetaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MetaDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(meta: MetaEntity)

    @Delete
    suspend fun delete(meta: MetaEntity)

    @Query("SELECT * FROM Metas WHERE idMeta = :id LIMIT 1")
    suspend fun getById(id: Int): MetaEntity?

    @Query("SELECT * FROM Metas ORDER BY descripcion ASC")
    fun observeAll(): Flow<List<MetaEntity>>

    @Query("SELECT * FROM Metas WHERE (:descripcion IS NULL OR descripcion LIKE '%' || :descripcion || '%') AND (:observaciones IS NULL OR observaciones LIKE '%' || :observaciones || '%') ORDER BY descripcion ASC")
    fun observeFiltered(descripcion: String?, observaciones: String?): Flow<List<MetaEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM Metas WHERE descripcion = :descripcion AND idMeta != :id LIMIT 1)")
    suspend fun existsByDescripcion(descripcion: String, id: Int): Boolean

    @Query("SELECT SUM(monto) FROM Metas WHERE (:descripcion IS NULL OR descripcion LIKE '%' || :descripcion || '%') AND (:observaciones IS NULL OR observaciones LIKE '%' || :observaciones || '%')")
    suspend fun getTotalMonto(descripcion: String?, observaciones: String?): Double?
}
