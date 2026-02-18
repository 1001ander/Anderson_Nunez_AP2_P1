package edu.ucne.anderson_nunez_ap2_p1.presentation.metas.edit

sealed interface EditMetaUiEvent {
    data class DescripcionChanged(val descripcion: String) : EditMetaUiEvent
    data class ObservacionesChanged(val observaciones: String) : EditMetaUiEvent
    data class MontoChanged(val monto: String) : EditMetaUiEvent
    data class LoadMeta(val idMeta: Int) : EditMetaUiEvent
    data object Save : EditMetaUiEvent
    data object Delete : EditMetaUiEvent
}