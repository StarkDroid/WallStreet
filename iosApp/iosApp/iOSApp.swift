import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        IOSKoinModuleKt.doInitKoin()
    }
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}