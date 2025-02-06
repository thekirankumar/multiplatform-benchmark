import UIKit
import SwiftUI
import shared


struct ComposeView: UIViewControllerRepresentable {
    let useCaseId: String?
    let closeable: Bool
    
    func makeUIViewController(context: Context) -> UIViewController {
        let viewController = MainViewControllerKt.MainViewController(useCaseId: useCaseId ?? "")
        let navigationController = UINavigationController(rootViewController: viewController)
        
        // Add a Close button to the navigation bar
        viewController.navigationItem.leftBarButtonItem = UIBarButtonItem(
            title: "Close",
            style: .done,
            target: context.coordinator,
            action: #selector(Coordinator.dismiss)
        )
        return closeable ? navigationController : viewController
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {}

    func makeCoordinator() -> Coordinator {
        return Coordinator()
    }

    class Coordinator {
        @objc func dismiss() {
            UIApplication.shared.windows.first?.rootViewController?.dismiss(animated: true, completion: nil)
        }
    }
}
struct ContentView: View {
    var body: some View {
        ComposeView(useCaseId: "test", closeable: true)
                .ignoresSafeArea(.keyboard) // Compose has own keyboard handler
    }
}



