import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.thekirankumar.crossplatformbenchmark.FrameworkStacks.ComposeStack
import com.thekirankumar.crossplatformbenchmark.FrameworkStacks.ReactNativeStack
import com.thekirankumar.crossplatformbenchmark.FrameworkStacks.SwiftUIStack
import com.thekirankumar.crossplatformbenchmark.NestedUseCase
import com.thekirankumar.crossplatformbenchmark.Stacks
import com.thekirankumar.crossplatformbenchmark.UseCase
import com.thekirankumar.crossplatformbenchmark.UseCases
import com.thekirankumar.crossplatformbenchmark.getPlatform
import kotlinx.coroutines.launch

@Composable
fun SwipeableTabs() {
    val supportedStacks = remember { getPlatform().supportedStacks()}
    val pagerState = rememberPagerState(pageCount = { supportedStacks.size })
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
            supportedStacks.forEachIndexed { index, stack ->
                Tab(
                    text = { Text(stack.title) },
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
            val frameworkStack = supportedStacks[page]
            when (frameworkStack) {
                ComposeStack -> ComposeTabContent()
                ReactNativeStack -> ReactNativeTabContent()
                SwiftUIStack -> SwiftUITabContent()
            }
        }
    }
}


@Composable
fun ComposeTabContent() {
    val nativeUseCases = listOf(
        NestedUseCase(
            root = UseCase(Stacks.STACK_COMPOSE_ID, UseCases.REDUX_ID, UseCases.REDUX_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_COMPOSE_ID, UseCases.REDUX_COUNTER_ID, UseCases.REDUX_COUNTER_TITLE, "A counter implementation using Redux.")
            )
        ),
        NestedUseCase(
            root = UseCase(Stacks.STACK_COMPOSE_ID, UseCases.IMAGE_ID, UseCases.IMAGE_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_COMPOSE_ID, UseCases.IMAGE_REMOTE_ID, UseCases.IMAGE_REMOTE_TITLE, "Loading of remote images via coil library")
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
// Swift Tab Content
@Composable
fun SwiftUITabContent() {
    val swiftUseCases = listOf(
        NestedUseCase(
            root = UseCase(Stacks.STACK_SWIFTUI_ID, UseCases.REDUX_ID, UseCases.REDUX_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_SWIFTUI_ID, UseCases.REDUX_COUNTER_ID, UseCases.REDUX_COUNTER_TITLE, "A counter implementation using Redux.")
            )
        ),
        NestedUseCase(
            root = UseCase(Stacks.STACK_SWIFTUI_ID, UseCases.REDUX_ID, UseCases.IMAGE_TITLE),
            children = listOf(
                UseCase(Stacks.STACK_SWIFTUI_ID, UseCases.IMAGE_REMOTE_ID, UseCases.IMAGE_REMOTE_TITLE, "Loading of a grid of remote images")
            )
        )
    )
    NestedUseCaseList(useCases = swiftUseCases)
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

@Composable
fun ExpandableUseCaseItem(nestedUseCase: NestedUseCase) {
    var isExpanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .animateContentSize( // Animates height changes smoothly
                    animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable { isExpanded = !isExpanded }
            ) {
                Text(
                    text = nestedUseCase.root.title,
                    style = MaterialTheme.typography.h5,
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


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                // Launch the appropriate activity based on the use case
                getPlatform().openUseCase(useCase)
            },
        elevation = 2.dp
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = useCase.title,
                style = MaterialTheme.typography.body2
            )
            if (useCase.description != null) {
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = useCase.description,
                    style = MaterialTheme.typography.body2
                )
            }
        }
    }
}
