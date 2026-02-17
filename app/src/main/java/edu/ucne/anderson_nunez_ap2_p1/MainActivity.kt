package edu.ucne.anderson_nunez_ap2_p1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.anderson_nunez_ap2_p1.presentation.navigation.MetaNavHost
import edu.ucne.anderson_nunez_ap2_p1.ui.theme.Anderson_Nunez_AP2_P1Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Anderson_Nunez_AP2_P1Theme {
                MetaNavHost()
            }
        }
    }
}