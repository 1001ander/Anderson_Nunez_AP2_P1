package edu.ucne.anderson_nunez_ap2_p1.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Borrames")
data class BorrameEntity(
    @PrimaryKey(autoGenerate = true)
    val borrameId: Int? = null,
    val nombre: String = "",
    val descripcion: String = ""
)