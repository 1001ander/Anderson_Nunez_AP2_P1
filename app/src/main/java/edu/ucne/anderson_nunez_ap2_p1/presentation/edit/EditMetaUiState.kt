package edu.ucne.anderson_nunez_ap2_p1.presentation.metas.edit

data class EditMetaUiState(
    val idMeta: Int? = null,
    val descripcion: String = "",
    val observaciones: String = "",
    val monto: String = "",
    val descripcionError: String? = null,
    val observacionesError: String? = null,
    val montoError: String? = null,
    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val success: Boolean = false
)