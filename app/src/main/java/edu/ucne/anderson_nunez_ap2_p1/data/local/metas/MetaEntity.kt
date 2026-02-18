package edu.ucne.anderson_nunez_ap2_p1.data.metas.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Metas")
data class MetaEntity(
    @PrimaryKey(autoGenerate = true)
    val idMeta: Int? = null,
    val descripcion: String = "",
    val observaciones: String = "",
    val monto: Double = 0.0
)