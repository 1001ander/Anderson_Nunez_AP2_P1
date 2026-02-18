package edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import javax.inject.Inject

class DeleteMetaUseCase @Inject constructor(
    private val repository: MetaRepository
) {
    suspend operator fun invoke(id: Int) {
        repository.delete(id)
    }
}