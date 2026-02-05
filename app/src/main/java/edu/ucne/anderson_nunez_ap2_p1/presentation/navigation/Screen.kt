package edu.ucne.anderson_nunez_ap2_p1.presentation.navigation

import kotlinx.serialization.Serializable

sealed class Screen {
    @Serializable
    data object BorrameList : Screen()

    @Serializable
    data class EditBorrame(val borrameId: Int?) : Screen()
}

