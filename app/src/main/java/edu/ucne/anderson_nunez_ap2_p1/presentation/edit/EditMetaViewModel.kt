package edu.ucne.anderson_nunez_ap2_p1.presentation.metas.edit

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.model.Meta
import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository
import edu.ucne.anderson_nunez_ap2_p1.presentation.navigation.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditMetaViewModel @Inject constructor(
    private val repository: MetaRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(EditMetaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        val metaId = savedStateHandle.toRoute<Screen.EditMeta>().metaId
        if (metaId > 0) {
            onEvent(EditMetaUiEvent.LoadMeta(metaId))
        }
    }

    fun onEvent(event: EditMetaUiEvent) {
        when (event) {
            is EditMetaUiEvent.DescripcionChanged -> {
                _uiState.update {
                    it.copy(descripcion = event.descripcion, descripcionError = null)
                }
            }
            is EditMetaUiEvent.ObservacionesChanged -> {
                _uiState.update {
                    it.copy(observaciones = event.observaciones, observacionesError = null)
                }
            }
            is EditMetaUiEvent.MontoChanged -> {
                _uiState.update {
                    it.copy(monto = event.monto, montoError = null)
                }
            }
            is EditMetaUiEvent.LoadMeta -> loadMeta(event.idMeta)
            EditMetaUiEvent.Save -> save()
            EditMetaUiEvent.Delete -> delete()
        }
    }

    private fun loadMeta(id: Int) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val meta = repository.getMeta(id)

            _uiState.update {
                it.copy(
                    idMeta = meta?.idMeta,
                    descripcion = meta?.descripcion ?: "",
                    observaciones = meta?.observaciones ?: "",
                    monto = meta?.monto?.toString() ?: "",
                    isLoading = false
                )
            }
        }
    }

    private fun save() {
        val descripcion = _uiState.value.descripcion.trim()
        val observaciones = _uiState.value.observaciones.trim()
        val montoStr = _uiState.value.monto.trim()
        val idMeta = _uiState.value.idMeta

        var hasError = false

        if (descripcion.isBlank()) {
            _uiState.update { it.copy(descripcionError = "La descripción es requerida") }
            hasError = true
        }

        if (observaciones.isBlank()) {
            _uiState.update { it.copy(observacionesError = "Las observaciones son requeridas") }
            hasError = true
        }

        val monto = montoStr.toDoubleOrNull()
        if (monto == null || monto <= 0) {
            _uiState.update { it.copy(montoError = "El monto debe ser un número mayor a 0") }
            hasError = true
        }

        if (hasError) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            val exists = repository.existsByDescripcion(descripcion, idMeta)

            if (exists) {
                _uiState.update {
                    it.copy(
                        descripcionError = "Ya existe una meta con esta descripción",
                        isLoading = false
                    )
                }
                return@launch
            }

            val meta = Meta(
                idMeta = idMeta ?: 0,
                descripcion = descripcion,
                observaciones = observaciones,
                monto = monto!!
            )

            repository.upsert(meta)

            _uiState.update {
                it.copy(isLoading = false, success = true)
            }
        }
    }

    private fun delete() {
        viewModelScope.launch {
            _uiState.value.idMeta?.let { id ->
                repository.delete(id)
                _uiState.update { it.copy(success = true) }
            }
        }
    }
}