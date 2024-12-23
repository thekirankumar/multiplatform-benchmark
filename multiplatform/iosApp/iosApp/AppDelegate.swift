import UIKit
import SwiftUI
import React

import React_RCTAppDelegate


@main
class AppDelegate: RCTAppDelegate {
    override func application(_ application: UIApplication, didFinishLaunchingWithOptions launchOptions: [UIApplication.LaunchOptionsKey: Any]?) -> Bool {
        self.automaticallyLoadReactNativeWindow = false
        super.application(application, didFinishLaunchingWithOptions: launchOptions)
        window = UIWindow()
        let hostingController = UIHostingController(rootView: MainScreen())
        window.rootViewController = hostingController
        window.makeKeyAndVisible()
        return true
    }
    
    override func sourceURL(for bridge: RCTBridge) -> URL? {
        self.bundleURL()
    }

    override func bundleURL() -> URL? {
        //#if DEBUG
        //RCTBundleURLProvider.sharedSettings().jsBundleURL(forBundleRoot: "index")
        //#else
        Bundle.main.url(forResource: "main", withExtension: "jsbundle")
        //#endif
      }
}
