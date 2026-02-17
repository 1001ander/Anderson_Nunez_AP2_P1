package edu.ucne.anderson_nunez_ap2_p1.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import edu.ucne.anderson_nunez_ap2_p1.domain.usecase.ObserveMetasUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListMetaViewModel @Inject constructor(
    private val observeMetasUseCase: ObserveMetasUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ListMetaUiState())
    val uiState = _uiState.asStateFlow()

    init {
        getMetas()
    }

    fun onEvent(event: ListMetaUiEvent) {
        when (event) {
            is ListMetaUiEvent.DescripcionFilterChanged -> {
                _uiState.update { it.copy(descripcionFilter = event.descripcion) }
                getMetas()
            }
            is ListMetaUiEvent.ObservacionesFilterChanged -> {
                _uiState.update { it.copy(observacionesFilter = event.observaciones) }
                getMetas()
            }
            ListMetaUiEvent.Refresh -> getMetas()
            ListMetaUiEvent.ClearFilters -> {
                _uiState.update {
                    it.copy(
                        descripcionFilter = "",
                        observacionesFilter = ""
                    )
                }
                getMetas()
            }
        }
    }

    private fun getMetas() {
        viewModelScope.launch {
            val descripcion = _uiState.value.descripcionFilter.ifBlank { null }
            val observaciones = _uiState.value.observacionesFilter.ifBlank { null }

            observeMetasUseCase(descripcion, observaciones).collect { metas ->
                val total = metas.size
                val suma = metas.sumOf { it.monto }

                _uiState.update {
                    it.copy(
                        metas = metas,
                        totalMetas = total,
                        sumaTotal = suma,
                        isLoading = false
                    )
                }
            }
        }
    }
}
