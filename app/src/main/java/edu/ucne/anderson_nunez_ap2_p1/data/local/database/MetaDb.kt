package edu.ucne.anderson_nunez_ap2_p1.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import edu.ucne.anderson_nunez_ap2_p1.data.local.dao.MetaDao
import edu.ucne.anderson_nunez_ap2_p1.data.local.entities.MetaEntity

@Database(
    entities = [MetaEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MetaDb : RoomDatabase() {
    abstract fun metaDao(): MetaDao
}
