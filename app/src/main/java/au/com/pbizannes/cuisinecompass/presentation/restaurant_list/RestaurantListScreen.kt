package au.com.pbizannes.cuisinecompass.presentation.restaurant_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.domain.use_case.GetRestaurantsUseCase
import au.com.pbizannes.cuisinecompass.presentation.RestaurantListUiState
import au.com.pbizannes.cuisinecompass.presentation.RestaurantViewModel
import au.com.pbizannes.cuisinecompass.presentation.components.RestaurantTopAppBar
import au.com.pbizannes.cuisinecompass.presentation.models.sampleRestaurants
import au.com.pbizannes.cuisinecompass.ui.theme.CuisineCompassTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantListScreen(
    onValueChange: (String) -> Unit = {},
    onFavouriteClicked: (Restaurant) -> Unit = {},
    onCardClick: (Restaurant) -> Unit = {},
    modifier: Modifier = Modifier,
    uiState: RestaurantListUiState
) {
    Scaffold(
        topBar = {
            RestaurantTopAppBar(
                onProfileClick = { /*TODO*/ },
                onFilterClick = { /*TODO*/ }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
        ) {
            // Search Bar
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = onValueChange,
                placeholder = { Text("e.g. chinese, pizza") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = "Search") },
                singleLine = true,
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant.copy(
                            alpha = 0.5f
                        ), CircleShape
                    )
            )

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (uiState.error != null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                }
            } else if (uiState.restaurants.isEmpty() && uiState.searchQuery.isNotEmpty()) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), contentAlignment = Alignment.Center) {
                    Text("No results found for \"${uiState.searchQuery}\". Try a different search!")
                }
            } else if (uiState.restaurants.isEmpty()) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp), contentAlignment = Alignment.Center) {
                    Text("No restaurants available right now. Check back later!")
                }
            }
            else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(items = uiState.restaurants, key = { it.objectId }) { restaurant ->
                        RestaurantInfoCard(
                            restaurant = restaurant,
                            onFavouriteClick = { onFavouriteClicked.invoke(restaurant) },
                            onCardClick = { onCardClick(restaurant) }
                        )
                    }
                }
            }
        }
    }
}

val getRestaurantsUseCase: GetRestaurantsUseCase = object : GetRestaurantsUseCase {
    override suspend fun invoke(): Result<List<Restaurant>> {
        return Result.success(sampleRestaurants)
    }
}

// --- Previews ---
@Preview(showBackground = true, name = "Restaurant List Screen")
@Composable
fun RestaurantListScreenPreview() {
    CuisineCompassTheme {
        val previewViewModel = RestaurantViewModel(getRestaurantsUseCase)

        RestaurantListScreen(uiState = RestaurantListUiState())
    }
}
