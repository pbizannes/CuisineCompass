package au.com.pbizannes.cuisinecompass.presentation.restaurant_list

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import au.com.pbizannes.cuisinecompass.presentation.models.RestaurantDeal
import au.com.pbizannes.cuisinecompass.ui.theme.CuisineCompassTheme

@Composable
fun DealBanner(deal: RestaurantDeal, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .background(
                // Using a gradient similar to the image, or a solid color
                // color = Color(0xFFE53935).copy(alpha = 0.9f), // A vibrant red
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        Color(0xFFF44336),
                        Color(0xFFD32F2F)
                    )
                ),
                shape = androidx.compose.foundation.shape.RoundedCornerShape(6.dp)
            )
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Text(
            text = deal.discountText,
            color = Color.White,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            textAlign = TextAlign.Center
        )
        deal.conditionText?.let {
            Text(
                text = it,
                color = Color.White.copy(alpha = 0.85f),
                style = MaterialTheme.typography.labelSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}


@Preview(name = "Deal Banner - Full", showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun DealBannerFullPreview() {
    CuisineCompassTheme { // Wrap with your app's theme for accurate font and Material styling
        DealBanner(
            deal = RestaurantDeal(
                id = "1",
                discountText = "30% OFF",
                conditionText = "Anytime today",
                dealsLeftText = "5 Deals Left",
            )
        )
    }
}

@Preview(name = "Deal Banner - Only Discount", showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun DealBannerOnlyDiscountPreview() {
    CuisineCompassTheme {
        DealBanner(
            deal = RestaurantDeal(
                id = "2",
                discountText = "FREE DRINK",
                // conditionText is null by default
                dealsLeftText = "1 Deal Left"

            )
        )
    }
}

@Preview(name = "Deal Banner - Long Text", showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun DealBannerLongTextPreview() {
    CuisineCompassTheme {
        DealBanner(
            deal = RestaurantDeal(
                id = "3",
                discountText = "50% OFF Entire Menu For Two People",
                conditionText = "Valid on weekdays after 6:00 PM with reservation",
                dealsLeftText = "2 Deals Left"
            )
        )
    }
}

@Preview(name = "Deal Banner - Short Discount", showBackground = true, backgroundColor = 0xFFF0F0F0)
@Composable
fun DealBannerShortDiscountPreview() {
    CuisineCompassTheme {
        DealBanner(
            deal = RestaurantDeal(
                id = "4",
                discountText = "SALE",
                conditionText = "Limited Time",
                dealsLeftText = "3 Deals Left"
            )
        )
    }
}
