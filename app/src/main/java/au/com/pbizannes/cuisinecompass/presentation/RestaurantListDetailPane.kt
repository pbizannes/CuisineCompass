package au.com.pbizannes.cuisinecompass.presentation

import android.widget.Toast
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.NavigableListDetailPaneScaffold
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.presentation.restaurant_detail.RestaurantDetailScreen
import au.com.pbizannes.cuisinecompass.presentation.restaurant_list.RestaurantListScreen
import au.com.pbizannes.cuisinecompass.presentation.restaurant_list.RestaurantListViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun RestaurantListDetailPane(
    modifier: Modifier = Modifier,
    viewModel: RestaurantListViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val scaffoldNavigator = rememberListDetailPaneScaffoldNavigator<Restaurant>()
    val scope = rememberCoroutineScope()

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val makeToast = fun(value: String) = Toast.makeText(context, value, Toast.LENGTH_SHORT).show()

    NavigableListDetailPaneScaffold(
        navigator = scaffoldNavigator,
        listPane = {
            AnimatedPane {
                RestaurantListScreen(
                    uiState = state,
                    onValueChange = { viewModel.onSearchQueryChanged(it) },
                    onCardClick = { restaurant ->
                        scope.launch {
                            scaffoldNavigator.navigateTo(ListDetailPaneScaffoldRole.Detail, restaurant)
                        }
                    }
                )
            }
        },
        detailPane = {
            AnimatedPane {
                // Show the detail pane content if selected item is available
                scaffoldNavigator.currentDestination?.contentKey?.let { restaurant ->
                    RestaurantDetailScreen(
                        restaurant,
                        uiState = state,
                        onNavigateBack = {
                            scope.launch {
                                scaffoldNavigator.navigateBack()
                            }
                        },
                        onRedeemDealClick = { restaurantDeal ->
                            makeToast("Clicked on deal with id ${restaurantDeal.id}")
                        }
                    )
                }
            }
        },
    )
}
