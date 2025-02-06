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
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import kotlinx.coroutines.launch


const val PAGE_SIZE = 30;

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

class ProductRepository {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun getProducts(limit: Int, skip: Int): List<Product> {
        val response: ProductsResponse = client.get("${getPlatform().remoteAPI()}/products") {
            parameter("limit", limit)
            parameter("skip", skip)
        }.body()
        return response.products
    }
}

data class PaginationState(
    val products: List<Product> = emptyList(),
    val currentPage: Int = 0,
    val isLoading: Boolean = false,
    val isEndReached: Boolean = false
)

@Composable
fun RemoteImageGrid() {
    val productRepository = remember { ProductRepository() }
    val paginationState = remember { mutableStateOf(PaginationState()) }
    val coroutineScope = rememberCoroutineScope()

    // Fetch initial data
    LaunchedEffect(Unit) {
        if (paginationState.value.products.isEmpty()) {
            loadMoreProducts(productRepository, paginationState)
        }
    }

    // Lazy loading when scrolling to the bottom
    val onScrollToEnd = {
        if (!paginationState.value.isLoading && !paginationState.value.isEndReached) {
            coroutineScope.launch {
                loadMoreProducts(productRepository, paginationState)
            }
        }
    }

    // Display the grid
    ProductGrid(
        products = paginationState.value.products,
        onScrollToEnd = onScrollToEnd,
        isLoading = paginationState.value.isLoading
    )
}

private suspend fun loadMoreProducts(
    productRepository: ProductRepository,
    paginationState: MutableState<PaginationState>
) {
    paginationState.value = paginationState.value.copy(isLoading = true)

    val newProducts = productRepository.getProducts(
        limit = PAGE_SIZE,
        skip = paginationState.value.currentPage * PAGE_SIZE
    )

    paginationState.value = paginationState.value.copy(
        products = paginationState.value.products + newProducts,
        currentPage = paginationState.value.currentPage + 1,
        isLoading = false,
        isEndReached = newProducts.isEmpty()
    )
}

@Composable
fun ProductGrid(
    products: List<Product>,
    onScrollToEnd: () -> Unit,
    isLoading: Boolean
) {
    val listState = rememberLazyGridState()

    // Trigger lazy loading when the user scrolls to the bottom
    LaunchedEffect(listState, products) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                if (products.isNotEmpty() && visibleItems.isNotEmpty() && visibleItems.last().index >= products.size - 1) {
                    onScrollToEnd()
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(8.dp),
            state = listState
        ) {
            items(products) { product ->
                Column {
                    AsyncImage(
                        model = product.thumbnail.replace("https","http"),
                        contentDescription = product.title,
                        modifier = Modifier
                            .size(128.dp)
                            .padding(4.dp)
                    )
                    Text(product.title)
                }
            }
        }

        // Show a loading indicator at the bottom
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(16.dp)
            )
        }
    }
}
