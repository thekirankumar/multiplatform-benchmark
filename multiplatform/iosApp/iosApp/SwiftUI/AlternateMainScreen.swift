import SwiftUI
import shared
import React_RCTAppDelegate


struct ReactView: UIViewControllerRepresentable {
    let moduleName: String

    func makeUIViewController(context: Context) -> UIViewController {
//         let reactViewController = UIViewController()
//         if let factory = (RCTSharedApplication()?.delegate as? RCTAppDelegate)?.rootViewFactory {
//             reactViewController.view = factory.view(withModuleName: moduleName)
//         }
        return ReactViewController();
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Handle updates if necessary
    }
}

struct ReactContentView: View {
    var body: some View {
        ReactView(moduleName: "HelloWorld")
//            .edgesIgnoringSafeArea(.all) // Adjust as needed for layout
    }
}

struct MainScreen: View {
    var body: some View {
        NavigationView {
            VStack(spacing: 16) {
                NavigationLink(destination: ComposeView(useCaseId: nil, closeable: true).navigationTitle("Compose")) {
                    Text("Compose")
                        .font(.system(size: 20))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }

                NavigationLink(destination: ReactContentView().navigationTitle("React Native")) {
                    Text("React Native")
                        .font(.system(size: 20))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.green)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
                NavigationLink(destination: CounterAppSwiftUI().navigationTitle("Swift UI")) {
                    Text("SwiftUI")
                        .font(.system(size: 20))
                        .padding()
                        .frame(maxWidth: .infinity)
                        .background(Color.orange)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
            }
            .padding()
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(UIColor.systemBackground))
            .edgesIgnoringSafeArea(.all)
        }
    }
}

struct MainScreen_Previews: PreviewProvider {
    static var previews: some View {
        MainScreen()
    }
}
