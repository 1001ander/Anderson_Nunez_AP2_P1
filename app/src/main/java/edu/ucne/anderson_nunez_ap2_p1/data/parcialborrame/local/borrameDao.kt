package edu.ucne.anderson_nunez_ap2_p1.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface BorrameDao {
    @Upsert
    suspend fun save(borrame: BorrameEntity)

    @Query("SELECT * FROM Borrames WHERE borrameId = :id")
    suspend fun find(id: Int): BorrameEntity?

    @Delete
    suspend fun delete(borrame: BorrameEntity)

    @Query("SELECT * FROM Borrames")
    fun getAll(): Flow<List<BorrameEntity>>
}