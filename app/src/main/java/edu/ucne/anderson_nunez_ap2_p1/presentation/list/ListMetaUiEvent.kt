package edu.ucne.anderson_nunez_ap2_p1.presentation.metas.list

sealed interface ListMetaUiEvent {
    data class DescripcionFilterChanged(val descripcion: String) : ListMetaUiEvent
    data class ObservacionesFilterChanged(val observaciones: String) : ListMetaUiEvent
    data object Refresh : ListMetaUiEvent
    data object ClearFilters : ListMetaUiEvent
    data class DeleteMeta(val id: Int) : ListMetaUiEvent
}