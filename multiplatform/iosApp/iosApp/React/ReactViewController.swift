//
//  ReactViewController.swift
//  iosApp
//
//  Created by Kiran Kumar on 23/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit
import React_RCTAppDelegate
import SwiftUI

class ReactViewController: UIViewController {

    var useCaseId: String = ""
      override func viewDidLoad() {
        super.viewDidLoad()

          let initialProps: [String: Any] = [
              "useCaseID": useCaseId
          ]
          
        let factory = (RCTSharedApplication()?.delegate as? RCTAppDelegate)?.rootViewFactory
        self.view = factory?.view(withModuleName: "HelloWorld", initialProperties: initialProps)
      }
}

struct ReactView: UIViewControllerRepresentable {
    let useCaseId: String?

    func makeUIViewController(context: Context) -> UIViewController {
        let vc = ReactViewController()
        vc.useCaseId = useCaseId ?? ""
        return vc
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
        // Handle updates if necessary
    }
}
