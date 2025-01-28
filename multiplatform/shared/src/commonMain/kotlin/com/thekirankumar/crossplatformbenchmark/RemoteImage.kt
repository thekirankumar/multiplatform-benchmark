package com.thekirankumar.crossplatformbenchmark
import kotlinx.serialization.Serializable
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage


@Serializable
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val thumbnail: String // URL of the image
)

@Serializable
data class ProductsResponse(
    val products: List<Product>
)

private const val API_URL = "https://dummyjson.com/products"

class ProductRepository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getProducts(): List<Product> {
        val response: ProductsResponse = client.get(API_URL).body()
        return response.products
    }
}

@Composable
fun ProductGrid(products: List<Product>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(products) { product ->
            AsyncImage(
                model = product.thumbnail,
                contentDescription = product.title,
                modifier = Modifier
                    .size(128.dp)
                    .padding(4.dp)
            )
        }
    }
}

@Composable
fun RemoteImageGrid() {
    val productRepository = remember { ProductRepository() }
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }

    LaunchedEffect(Unit) {
        products = productRepository.getProducts()
    }

    if (products.isNotEmpty()) {
        ProductGrid(products)
    } else {
        Text("Loading ... $API_URL", modifier = Modifier.padding(10.dp))
    }
}