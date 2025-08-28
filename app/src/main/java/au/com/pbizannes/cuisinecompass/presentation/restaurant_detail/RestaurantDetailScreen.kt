package au.com.pbizannes.cuisinecompass.presentation.restaurant_detail

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.pbizannes.cuisinecompass.R
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.presentation.components.RestaurantTopAppBar
import au.com.pbizannes.cuisinecompass.presentation.mapper.RestaurantMapper
import au.com.pbizannes.cuisinecompass.presentation.models.ActionItem
import au.com.pbizannes.cuisinecompass.presentation.models.DisplayableRestaurant
import au.com.pbizannes.cuisinecompass.presentation.models.RestaurantDeal
import au.com.pbizannes.cuisinecompass.presentation.restaurant_detail.components.DealItem
import au.com.pbizannes.cuisinecompass.presentation.restaurant_list.RestaurantListUiState
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailTopAppBar(
    onNavigateBack: () -> Unit,
    onFilterClick: () -> Unit // Or whatever the right icon action is
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.redish_crescent), // Your app logo
                contentDescription = "App Logo",
                modifier = Modifier.size(36.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary)
            )
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) { // Changed person to back arrow for detail screen
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Default.Build, // Filter/Settings icon
                    contentDescription = "Filter"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

@Composable
fun ActionButtonsRow(
    actionItems: List<ActionItem>,
    onFavouriteToggle: () -> Unit,
    onActionItemClick: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically
    ) {
        actionItems.forEach { item ->
            if (item.id == "favourite") {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onFavouriteToggle() }
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.FavoriteBorder,
                        contentDescription = item.label,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.label, style = MaterialTheme.typography.labelMedium)
                }
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onActionItemClick(item.id) }
                        .padding(vertical = 8.dp)
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(item.label, style = MaterialTheme.typography.labelMedium)
                }
            }
        }
    }
}

@Composable
fun RestaurantInfoSection(displayableRestaurant: DisplayableRestaurant) {
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = displayableRestaurant.name,
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(bottom = 4.dp)
        )

        Text(
            text = displayableRestaurant.cuisines.joinToString(" • "),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        InfoRow(icon = Icons.TwoTone.DateRange, text = "Hours: ${displayableRestaurant.openTime} - ${displayableRestaurant.closeTime}")
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(
            icon = Icons.Outlined.LocationOn,
            text = "${displayableRestaurant.address} • ${displayableRestaurant.distanceInfo}"
        )
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null, // Decorative
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
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
