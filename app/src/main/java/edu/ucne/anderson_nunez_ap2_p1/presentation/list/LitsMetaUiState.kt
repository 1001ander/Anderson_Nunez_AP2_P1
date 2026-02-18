package edu.ucne.anderson_nunez_ap2_p1.presentation.metas.list

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta

data class ListMetaUiState(
    val isLoading: Boolean = true,
    val metas: List<Meta> = emptyList(),
    val descripcionFilter: String = "",
    val observacionesFilter: String = "",
    val totalMetas: Int = 0,
    val sumaTotal: Double = 0.0,
    val errorMessage: String? = null
)