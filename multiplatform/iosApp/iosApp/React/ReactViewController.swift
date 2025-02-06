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

class ReactViewController: UIViewController {

  override func viewDidLoad() {
    super.viewDidLoad()

    let factory = (RCTSharedApplication()?.delegate as? RCTAppDelegate)?.rootViewFactory
    self.view = factory?.view(withModuleName: "HelloWorld")
  }
}
