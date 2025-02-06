import UIKit
import SwiftUI
import React
import shared
import React_RCTAppDelegate

@main
class AppDelegate: RCTAppDelegate {
    override func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        self.automaticallyLoadReactNativeWindow = false
        super.application(application, didFinishLaunchingWithOptions: launchOptions)
        window = UIWindow()
        IOSPlatform().initialize { useCase in
            self.openUseCase(useCase: useCase)
        }
        let hostingController = UIHostingController(rootView: ComposeView(useCaseId: nil, closeable: false))
//        let hostingController = UIHostingController(rootView: MainScreen());  // use this if the homescreen also should be native swift ui instead of compose
        window.rootViewController = hostingController
        window.makeKeyAndVisible()
        return true
    }
    
    func openUseCase(useCase: UseCase) {
        let viewController: UIViewController
        if useCase.stack == Stacks.shared.STACK_COMPOSE_ID {
            viewController = UIHostingController(rootView: ComposeView(useCaseId: useCase.id, closeable: true))
        } else if (useCase.stack == Stacks.shared.STACK_REACT_NATIVE_ID) {
            viewController = UIHostingController(rootView: ReactView(useCaseId: useCase.id))
        } else {
            viewController = UIHostingController(rootView: SwiftUIView(useCaseId: useCase.id))
        }
        window.rootViewController?.present(viewController, animated: true, completion: nil)
    }
    
    override func sourceURL(for bridge: RCTBridge) -> URL? {
        self.bundleURL()
    }

    override func bundleURL() -> URL? {
        #if DEBUG
        RCTBundleURLProvider.sharedSettings().jsBundleURL(forBundleRoot: "index")
        #else
        Bundle.main.url(forResource: "main", withExtension: "jsbundle")
        #endif
      }
}
