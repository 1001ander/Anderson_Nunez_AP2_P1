package edu.ucne.anderson_nunez_ap2_p1.data.metas.mapper

import edu.ucne.anderson_nunez_ap2_p1.data.metas.local.MetaEntity
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta

fun MetaEntity.toDomain() = Meta(
    idMeta = idMeta ?: 0,
    descripcion = descripcion,
    observaciones = observaciones,
    monto = monto
)

fun Meta.toEntity() = MetaEntity(
    idMeta = if (idMeta == 0) null else idMeta,
    descripcion = descripcion,
    observaciones = observaciones,
    monto = monto
)