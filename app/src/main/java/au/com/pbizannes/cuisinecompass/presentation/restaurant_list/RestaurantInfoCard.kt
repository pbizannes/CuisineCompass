package au.com.pbizannes.cuisinecompass.presentation.restaurant_list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.pbizannes.cuisinecompass.R
import au.com.pbizannes.cuisinecompass.domain.models.Restaurant
import au.com.pbizannes.cuisinecompass.presentation.mapper.RestaurantMapper
import au.com.pbizannes.cuisinecompass.presentation.models.sampleRestaurants
import au.com.pbizannes.cuisinecompass.ui.theme.CuisineCompassTheme
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun RestaurantInfoCard(
    restaurant: Restaurant,
    onFavouriteClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onCardClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp), // Subtle elevation
        shape = androidx.compose.foundation.shape.RoundedCornerShape(12.dp)
    ) {
        val displayableItem = RestaurantMapper.toPresentation(restaurant)

        Column {
            Box(contentAlignment = Alignment.TopStart) { // For the deal banner
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(displayableItem.imageUrl)
                        .crossfade(true)
                        .build(),
                    // Use a generic placeholder for food
                    placeholder = painterResource(R.drawable.clipart_restaurant_location),
                    error = painterResource(R.drawable.clipart_restaurant_location),
                    contentDescription = "Image of ${displayableItem.name}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp) // Adjust height as needed
                )
                displayableItem.deal?.let { deal ->
                    DealBanner(deal = deal, modifier = Modifier.padding(12.dp))
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.Top // Align favourite icon to the top of text block
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = displayableItem.name,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = displayableItem.distanceInfo,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = displayableItem.cuisines.joinToString(", "),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = displayableItem.services.joinToString(" • "),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary // Use accent for services
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(onClick = onFavouriteClick) {
                    Icon(
                        imageVector = if (displayableItem.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = if (displayableItem.isFavourite) "Remove from favorites" else "Add to favorites",
                        tint = if (displayableItem.isFavourite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Restaurant Info Card - With Deal")
@Composable
fun RestaurantInfoCardPreviewWithDeal() {
    CuisineCompassTheme {
        RestaurantInfoCard(
            restaurant = sampleRestaurants[0],
            onFavouriteClick = {},
            onCardClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Restaurant Info Card - No Deal")
@Composable
fun RestaurantInfoCardPreviewNoDeal() {
    CuisineCompassTheme {
        RestaurantInfoCard(
            restaurant = sampleRestaurants[2],
            onFavouriteClick = {},
            onCardClick = {}
        )
    }
}

@Preview(showBackground = true, name = "Restaurant Info Card - Favourited")
@Composable
fun RestaurantInfoCardPreviewFavourited() {
    CuisineCompassTheme {
        RestaurantInfoCard(
            restaurant = sampleRestaurants[1],
            onFavouriteClick = {},
            onCardClick = {}
        )
    }
}
