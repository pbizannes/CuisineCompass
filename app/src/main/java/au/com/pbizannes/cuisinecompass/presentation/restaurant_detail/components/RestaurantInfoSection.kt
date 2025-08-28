package au.com.pbizannes.cuisinecompass.presentation.restaurant_detail.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.twotone.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import au.com.pbizannes.cuisinecompass.presentation.components.InfoRow
import au.com.pbizannes.cuisinecompass.presentation.models.DisplayableRestaurant

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

        InfoRow(
            icon = Icons.TwoTone.DateRange,
            text = "Hours: ${displayableRestaurant.openTime} - ${displayableRestaurant.closeTime}"
        )
        Spacer(modifier = Modifier.height(8.dp))
        InfoRow(
            icon = Icons.Outlined.LocationOn,
            text = "${displayableRestaurant.address} • ${displayableRestaurant.distanceInfo}"
        )
    }
}
