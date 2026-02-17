package edu.ucne.anderson_nunez_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object ListMeta : Screen()

    @Serializable
    data class EditMeta(val metaId: Int = 0) : Screen()
}