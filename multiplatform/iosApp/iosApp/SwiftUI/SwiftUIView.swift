//
//  SwiftUIView.swift
//  iosApp
//
//  Created by Kiran Kumar on 06/02/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared

struct SwiftUIView: View {
    let useCaseId: String

    var body: some View {
        switch useCaseId {
        case UseCases.shared.REDUX_COUNTER_ID:
            CounterAppSwiftUI()

        case UseCases.shared.IMAGE_REMOTE_ID:
            RemoteImageGrid()

        default:
            Text("Unknown useCase: \(useCaseId)")
                .foregroundColor(.red)
        }
    }
}
