import androidx.compose.material.MaterialTheme
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import component.MainScreen

fun main() = application {
    val icon = painterResource("wallstreet_icon.png")
    Window(onCloseRequest = ::exitApplication, title = "WallStreet", icon = icon) {
        MaterialTheme {
            MainScreen()
        }
    }
}
