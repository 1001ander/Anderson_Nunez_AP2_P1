package edu.ucne.anderson_nunez_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import edu.ucne.anderson_nunez_ap2_p1.presentation.borrame.edit.EditBorrameScreen
import edu.ucne.anderson_nunez_ap2_p1.presentation.borrame.list.ListBorrameScreen

@Composable
fun MainNavHost(
    navHostController: NavHostController
) {
    NavHost(
        navController = navHostController,
        startDestination = Screen.BorrameList
    ) {
        composable<Screen.BorrameList> {
            ListBorrameScreen(
                borrames = emptyList(),
                onAddClick = {

                    navHostController.navigate(Screen.EditBorrame(borrameId = null))
                },
                onItemClick = { id ->

                    navHostController.navigate(Screen.EditBorrame(borrameId = id))
                }
            )
        }


        composable<Screen.EditBorrame> {
            val args = it.toRoute<Screen.EditBorrame>()
            val borrameId = args.borrameId

            EditBorrameScreen(
                nombre = "",
                descripcion = "",
                onNombreChange = {},
                onDescripcionChange = {},
                onSaveClick = {

                    navHostController.navigateUp()
                },
                onDeleteClick = {

                    navHostController.navigateUp()
                },
                isNew = borrameId == null
            )
        }
    }
}

