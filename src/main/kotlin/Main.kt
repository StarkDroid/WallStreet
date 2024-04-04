import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import ui.MainScreen

fun main() = application {
    val icon = painterResource("drawables/WallStreet.png")
    Window(onCloseRequest = ::exitApplication, title = "WallStreet", icon = icon) {
        MaterialTheme {
            MainScreen()
        }
    }
}
