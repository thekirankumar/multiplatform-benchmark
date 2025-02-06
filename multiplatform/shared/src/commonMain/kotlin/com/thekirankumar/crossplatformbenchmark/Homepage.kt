import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.thekirankumar.crossplatformbenchmark.android.ComposeActivity
import com.thekirankumar.crossplatformbenchmark.android.ReactNativeActivity
import com.thekirankumar.crossplatformbenchmark.android.Stacks
import com.thekirankumar.crossplatformbenchmark.android.UseCases
import kotlinx.coroutines.launch

@Composable
fun SwipeableTabs() {
    val pagerState = rememberPagerState(pageCount = { 3 })
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.tabIndicatorOffset(tabPositions[pagerState.currentPage])
                )
            }
        ) {
            listOf("Native", "React Native", "CMP").forEachIndexed { index, title ->
                Tab(
                    text = { Text(title) },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        // Horizontal Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            when (page) {
                0 -> NativeTabContent()
                1 -> ReactNativeTabContent()
                2 -> CMPTabContent()
            }
        }
    }
}

// Data Class for Use Cases
data class UseCase(val stack: String, val id: String, val title: String, val description: String? = null)
// Data Class for Nested Use Cases
data class NestedUseCase(val root: UseCase, val children: List<UseCase>)
@Composable
fun NativeTabContent() {
    val nativeUseCases = listOf(
        NestedUseCase(
            root = UseCase(Stacks.STACK_NATIVE_ID, UseCases.REDUX_ID, UseCases.REDUX_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_NATIVE_ID, UseCases.REDUX_COUNTER_ID, UseCases.REDUX_COUNTER_TITLE, "A counter implementation using Redux.")
            )
        ),
        NestedUseCase(
            root = UseCase(Stacks.STACK_NATIVE_ID, UseCases.IMAGE_ID, UseCases.IMAGE_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_NATIVE_ID, UseCases.IMAGE_REMOTE_ID, UseCases.IMAGE_REMOTE_TITLE, "Loading of remote images via coil library")
            )
        ),

    )

    NestedUseCaseList(useCases = nativeUseCases)
}

@Composable
fun ReactNativeTabContent() {
    val reactNativeUseCases = listOf(
        NestedUseCase(
            root = UseCase(Stacks.STACK_REACT_NATIVE_ID, UseCases.REDUX_ID, UseCases.REDUX_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_REACT_NATIVE_ID, UseCases.REDUX_COUNTER_ID, UseCases.REDUX_COUNTER_TITLE, "A counter implementation using Redux.")
            )
        ),
        NestedUseCase(
            root = UseCase(Stacks.STACK_REACT_NATIVE_ID, UseCases.IMAGE_ID, UseCases.IMAGE_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_REACT_NATIVE_ID, UseCases.IMAGE_REMOTE_ID, UseCases.IMAGE_REMOTE_TITLE, "Loading of a grid of remote images")
            )
        )
    )

    NestedUseCaseList(useCases = reactNativeUseCases)
}
// CMP Tab Content
@Composable
fun CMPTabContent() {
    val cmpUseCases = listOf<NestedUseCase>()

    NestedUseCaseList(useCases = cmpUseCases)
}

// Reusable Composable for Displaying Nested Use Cases
@Composable
fun NestedUseCaseList(useCases: List<NestedUseCase>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(useCases) { nestedUseCase ->
            ExpandableUseCaseItem(nestedUseCase = nestedUseCase)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

// Composable for Expandable Use Case Item
@Composable
fun ExpandableUseCaseItem(nestedUseCase: NestedUseCase) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.elevatedCardElevation()
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            ) {
                Text(
                    text = nestedUseCase.root.title,
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.ArrowDropDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand"
                )
            }

            if (isExpanded) {
                Spacer(modifier = Modifier.height(8.dp))
                Column {
                    nestedUseCase.children.forEach { child ->
                        UseCaseItem(useCase = child)
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }
        }
    }
}
@Composable
fun UseCaseItem(useCase: UseCase) {
    val context = LocalContext.current


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                // Launch the appropriate activity based on the use case

                val intent = if (Stacks.STACK_NATIVE_ID == useCase.stack) {
                    Intent(context, ComposeActivity::class.java)
                } else {
                    Intent(context, ReactNativeActivity::class.java)
                }
                intent.putExtra(UseCases.USE_CASE_INTENT_ID, useCase.id)
                intent.putExtra(UseCases.USE_CASE_INTENT_TITLE, useCase.title)
                context.startActivity(intent)
            },
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = useCase.title,
                style = MaterialTheme.typography.bodyMedium
            )
            if (useCase.description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = useCase.description,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}