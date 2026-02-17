package edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import javax.inject.Inject

class ObserveMetasUseCase @Inject constructor(
    private val repository: MetaRepository
) {
    operator fun invoke(descripcion: String? = null, observaciones: String? = null) =
        if (descripcion.isNullOrBlank() && observaciones.isNullOrBlank()) {
            repository.observeAll()
        } else {
            repository.observeFiltered(descripcion, observaciones)
        }
}