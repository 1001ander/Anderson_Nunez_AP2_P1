package edu.ucne.anderson_nunez_ap2_p1.data.mapper

import edu.ucne.anderson_nunez_ap2_p1.data.local.entities.MetaEntity
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta

fun MetaEntity.toDomain(): Meta {
    return Meta(
        idMeta = this.idMeta,
        descripcion = this.descripcion,
        observaciones = this.observaciones,
        monto = this.monto
    )
}

fun Meta.toEntity(): MetaEntity {
    return MetaEntity(
        idMeta = this.idMeta,
        descripcion = this.descripcion,
        observaciones = this.observaciones,
        monto = this.monto
    )
}
