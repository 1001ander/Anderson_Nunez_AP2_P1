package edu.ucne.anderson_nunez_ap2_p1.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import edu.ucne.anderson_nunez_ap2_p1.presentation.metas.edit.EditMetaScreen
import edu.ucne.anderson_nunez_ap2_p1.presentation.metas.list.ListMetaScreen

@Composable
fun MetaNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.ListMeta
    ) {
        composable<Screen.ListMeta> {
            ListMetaScreen(
                onAddMeta = {
                    navController.navigate(Screen.EditMeta(metaId = 0))
                },
                onEditMeta = { metaId ->
                    navController.navigate(Screen.EditMeta(metaId = metaId))
                }
            )
        }

        composable<Screen.EditMeta> {
            val args = it.toRoute<Screen.EditMeta>()
            EditMetaScreen(
                onNavigateBack = {
                    navController.navigateUp()
                }
            )
        }
    }
}