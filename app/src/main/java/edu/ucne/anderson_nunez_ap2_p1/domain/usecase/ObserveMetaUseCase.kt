package edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveMetasUseCase @Inject constructor(
    private val repository: MetaRepository
) {
    operator fun invoke(descripcion: String? = null, observaciones: String? = null): Flow<List<Meta>> {
        return if (descripcion.isNullOrBlank() && observaciones.isNullOrBlank()) {
            repository.observeAll()
        } else {
            repository.observeFiltered(descripcion, observaciones)
        }
    }
}