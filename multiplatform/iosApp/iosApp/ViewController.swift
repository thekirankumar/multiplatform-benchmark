//
//  ViewController.swift
//  iosApp
//
//  Created by Kiran Kumar on 23/12/24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import UIKit

class ViewController: UIViewController {

  var reactViewController: ReactViewController?

  override func viewDidLoad() {
    super.viewDidLoad()
      self.view = MainScreen()
  }
}
