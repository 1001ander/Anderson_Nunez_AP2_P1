package edu.ucne.anderson_nunez_ap2_p1.domain.metas.usecase

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import javax.inject.Inject

class GetMetaUseCase @Inject constructor(
    private val repository: MetaRepository
) {
    suspend operator fun invoke(id: Int) = repository.getMeta(id)
}