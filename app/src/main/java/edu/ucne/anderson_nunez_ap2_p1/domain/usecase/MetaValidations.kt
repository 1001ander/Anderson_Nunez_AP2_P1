package edu.ucne.anderson_nunez_ap2_p1.domain.usecase

import edu.ucne.anderson_nunez_ap2_p1.domain.metas.repository.MetaRepository

class MetaValidations(private val repository: MetaRepository) {
    suspend fun validate(id: Int, descripcion: String, observaciones: String, monto: String): ValidationResult {
        if (descripcion.isBlank()) {
            return ValidationResult(false, "La descripción no puede estar vacía.")
        }

        val existingMeta = repository.getMetaByDescripcion(descripcion)
        if (existingMeta != null && existingMeta.Idmeta != id) {
            return ValidationResult(false, "Ya existe una meta con esa descripción.")
        }

        if (observaciones.isBlank()) {
            return ValidationResult(false, "Las observaciones no pueden estar vacías.")
        }

        if (monto.isBlank()) {
            return ValidationResult(false, "El monto no puede estar vacío.")
        }

        val montoInt = monto.toIntOrNull()
        if (montoInt == null || montoInt <= 0) {
            return ValidationResult(false, "El monto debe ser un número mayor a cero.")
        }

        return ValidationResult(true)
    }
}

data class ValidationResult(
    val successful: Boolean,
    val errorMessage: String? = null
)
