package edu.ucne.anderson_nunez_ap2_p1.presentation.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.anderson_nunez_ap2_p1.data.mapper.toEntity
import edu.ucne.anderson_nunez_ap2_p1.data.mapper.toDomain
import edu.ucne.anderson_nunez_ap2_p1.domain.model.Meta
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.DeleteMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.GetMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.UpsertMetaUseCase
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.MetaValidations
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMetaViewModel @Inject constructor(
    private val getMetaUseCase: GetMetaUseCase,
    private val upsertMetaUseCase: UpsertMetaUseCase,
    private val deleteMetaUseCase: DeleteMetaUseCase,
    private val metaValidations: MetaValidations,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditMetaUiState())
    val uiState = _uiState.asStateFlow()

    private val metaId: Int = savedStateHandle.get<Int>("metaId") ?: 0

    init {
        if (metaId > 0) {
            loadMeta(metaId)
        }
    }

    fun onEvent(event: EditMetaUiEvent) {
        when (event) {
            is EditMetaUiEvent.DescripcionChanged -> {
                _uiState.update { it.copy(descripcion = event.descripcion, descripcionError = null) }
            }
            is EditMetaUiEvent.ObservacionesChanged -> {
                _uiState.update { it.copy(observaciones = event.observaciones, observacionesError = null) }
            }
            is EditMetaUiEvent.MontoChanged -> {
                _uiState.update { it.copy(monto = event.monto, montoError = null) }
            }
            is EditMetaUiEvent.LoadMeta -> loadMeta(event.idMeta)
            is EditMetaUiEvent.Save -> save()
            is EditMetaUiEvent.Delete -> delete()
            is EditMetaUiEvent.ClearErrors -> clearErrors()
        }
    }

    private fun loadMeta(id: Int) {
        viewModelScope.launch {
            getMetaUseCase(id)?.let { meta ->
                _uiState.update {
                    it.copy(
                        idMeta = meta.idMeta,
                        descripcion = meta.descripcion,
                        observaciones = meta.observaciones,
                        monto = meta.monto.toString()
                    )
                }
            }
        }
    }

    private fun save() {
        viewModelScope.launch {
            val state = _uiState.value
            val validationResult = metaValidations.validate(
                id = state.idMeta ?: 0,
                descripcion = state.descripcion,
                observaciones = state.observaciones,
                monto = state.monto
            )

            if (!validationResult.successful) {
                _uiState.update { it.copy(errorMessage = validationResult.errorMessage) }
                return@launch
            }

            val meta = Meta(
                idMeta = state.idMeta ?: 0,
                descripcion = state.descripcion,
                observaciones = state.observaciones,
                monto = state.monto.toDoubleOrNull() ?: 0.0
            )

            upsertMetaUseCase(meta)
            _uiState.update { it.copy(success = true) }
        }
    }

    private fun delete() {
        viewModelScope.launch {
            _uiState.value.idMeta?.let { deleteMetaUseCase(it) }
            _uiState.update { it.copy(success = true) }
        }
    }

    private fun clearErrors() {
        _uiState.update { it.copy(descripcionError = null, observacionesError = null, montoError = null, errorMessage = null) }
    }
}
