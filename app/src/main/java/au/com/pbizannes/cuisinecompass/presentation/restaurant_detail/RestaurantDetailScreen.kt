package au.com.pbizannes.cuisinecompass.presentation.restaurant_detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.pbizannes.cuisinecompass.R
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.presentation.components.RestaurantTopAppBar
import au.com.pbizannes.cuisinecompass.presentation.mapper.RestaurantMapper
import au.com.pbizannes.cuisinecompass.presentation.models.ActionItem
import au.com.pbizannes.cuisinecompass.presentation.models.RestaurantDeal
import au.com.pbizannes.cuisinecompass.presentation.restaurant_detail.components.ActionButtonsRow
import au.com.pbizannes.cuisinecompass.presentation.restaurant_detail.components.DealItem
import au.com.pbizannes.cuisinecompass.presentation.restaurant_detail.components.RestaurantInfoSection
import au.com.pbizannes.cuisinecompass.presentation.RestaurantListUiState
import au.com.pbizannes.cuisinecompass.ui.theme.CuisineCompassTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

val actionItems = listOf(
    ActionItem("menu", "Menu", Icons.Outlined.Menu),
    ActionItem("call", "Call us", Icons.Outlined.Call),
    ActionItem("location", "Location", Icons.Outlined.LocationOn),
    ActionItem("favourite", "Favourite", Icons.Outlined.FavoriteBorder)
)

@Composable
fun RestaurantDetailScreen(
    restaurant: Restaurant,
    uiState: RestaurantListUiState = RestaurantListUiState(),
    onNavigateBack: () -> Unit = {},
    onFavouriteToggle: () -> Unit = {},
    onRedeemDealClick: (RestaurantDeal) -> Unit = {},
) {
    val displayableRestaurant = RestaurantMapper.toPresentation(restaurant)

    Scaffold(
        topBar = {
            RestaurantTopAppBar(
                onNavigateBack = onNavigateBack,
            )
        }
    ) { paddingValues ->
        if (uiState.isLoading) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues), contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (uiState.error != null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues), contentAlignment = Alignment.Center
            ) {
                Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
            }
        } else if (restaurant == null) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(paddingValues), contentAlignment = Alignment.Center
            ) {
                Text("Restaurant details not found.")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                // Image Carousel
                item {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(displayableRestaurant.imageUrl)
                            .crossfade(true)
                            .build(),
                        // Use a generic placeholder for food
                        placeholder = painterResource(R.drawable.clipart_restaurant_location),
                        error = painterResource(R.drawable.clipart_restaurant_location),
                        contentDescription = "Image of ${displayableRestaurant.name}",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp) // Adjust height as needed
                    )
                }

                // Action Buttons
                item {
                    ActionButtonsRow(
                        actionItems = actionItems,
                        onFavouriteToggle = onFavouriteToggle,
                        onActionItemClick = { actionId ->
                            // TODO: Handle clicks for Menu, Call, Location
                            println("Action item clicked: $actionId")
                        }
                    )
                }

                item {
                    RestaurantInfoSection(displayableRestaurant = displayableRestaurant)
                }

                // Deals Section Title
                if (restaurant.deals.isNotEmpty()) {
                    item {
                        Text(
                            text = "Today's Deals",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(
                                start = 16.dp,
                                end = 16.dp,
                                top = 24.dp,
                                bottom = 8.dp
                            )
                        )
                    }
                }

                // Deals List
                itemsIndexed(displayableRestaurant.deals, key = { _, deal -> deal.id }) { index, deal ->
                    DealItem(
                        deal = deal,
                        onRedeemClick = { onRedeemDealClick(deal) }
                    )
                    if (index < restaurant.deals.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            )
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) } // Bottom padding
            }
        }
    }
}

// --- Preview ---
@Preview(showBackground = true, name = "Restaurant Detail Screen")
@Composable
fun RestaurantDetailScreenPreview() {
    CuisineCompassTheme {
        RestaurantDetailScreen(
            restaurant = _root_ide_package_.au.com.pbizannes.cuisinecompass.presentation.models.sampleRestaurants[0],
            onNavigateBack = {},
        )
    }
}
