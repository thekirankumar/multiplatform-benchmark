//
//  RemoteImage.swift
//  iosApp
//
//  Created by Kiran Kumar on 06/02/25.
//  Copyright © 2025 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import Kingfisher


struct Product: Identifiable, Decodable {
    let id: Int
    let title: String
    let description: String
    let price: Double
    let thumbnail: String
}

struct ProductsResponse: Decodable {
    let products: [Product]
}

class ProductService {

    func getProducts(limit: Int, skip: Int) async throws -> [Product] {
        guard let url = URL(string: "\(IOSPlatform().remoteAPI())/products?limit=\(limit)&skip=\(skip)") else {
            throw URLError(.badURL)
        }
        let (data, _) = try await URLSession.shared.data(from: url)
        let response = try JSONDecoder().decode(ProductsResponse.self, from: data)
        return response.products
    }
}

@MainActor
class ProductListViewModel: ObservableObject {
    @Published var products: [Product] = []
    @Published var currentPage: Int = 0
    @Published var isLoading: Bool = false
    @Published var isEndReached: Bool = false

    private let PAGE_SIZE = 30
    private let service = ProductService()

    func loadMore() {
        // Prevent multiple requests or loading beyond the end
        guard !isLoading && !isEndReached else { return }

        isLoading = true
        Task {
            do {
                let newProducts = try await service.getProducts(
                    limit: PAGE_SIZE,
                    skip: currentPage * PAGE_SIZE
                )
                products.append(contentsOf: newProducts)

                if newProducts.isEmpty {
                    isEndReached = true
                } else {
                    currentPage += 1
                }
                isLoading = false
            } catch {
                // Handle or log error
                isLoading = false
            }
        }
    }
}

struct RemoteImageGrid: View {
    @StateObject private var viewModel = ProductListViewModel()

    // Grid layout: adaptive cells of at least 128 points wide
    private let columns = [
        GridItem(.fixed(128), spacing: 16), GridItem(.fixed(128), spacing: 16)
    ]

    var body: some View {
        ScrollView {
            LazyVGrid(columns: columns, spacing: 16) {
                ForEach(viewModel.products.indices, id: \.self) { index in
                    let product = viewModel.products[index]
                    VStack {
                        // Loads image asynchronously
                        KFImage(URL(string: product.thumbnail
                            .replacingOccurrences(of: "https", with: "http"))).resizable().frame(width: 128, height: 128).scaledToFit()
                        Text(product.title)
                            .font(.caption)
                            .lineLimit(2)
                    }
                    .onAppear {
                        // Load next page when the last item appears
                        if index == viewModel.products.count - 1 {
                            viewModel.loadMore()
                        }
                    }
                }
            }
            .padding()

            // Loading indicator at the bottom
            if viewModel.isLoading {
                ProgressView()
                    .padding()
            }
        }
        // Initial load when the view first appears
        .onAppear {
            if viewModel.products.isEmpty {
                viewModel.loadMore()
            }
        }
    }
}
