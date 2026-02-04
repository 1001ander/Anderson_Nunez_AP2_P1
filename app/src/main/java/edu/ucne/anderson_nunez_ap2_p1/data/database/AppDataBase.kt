package edu.ucne.anderson_nunez_ap2_p1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BorrameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun borrameDao(): BorrameDao
}