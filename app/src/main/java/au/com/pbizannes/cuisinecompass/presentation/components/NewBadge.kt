package au.com.pbizannes.cuisinecompass.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import au.com.pbizannes.cuisinecompass.ui.theme.CuisineCompassTheme

@Composable
fun NewBadge(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(4.dp),
        color = MaterialTheme.colorScheme.error // Or a specific red like Color(0xFFE53935)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Icon(
                imageVector = Icons.Filled.Star, // Using a star for "New"
                contentDescription = null, // Content description provided by Text
                tint = Color.White,
                modifier = Modifier.size(14.sp.value.dp) // Scale with text size
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "New",
                color = Color.White,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
            )
        }
    }
}

@Preview(showBackground = true, name = "New Badge Preview")
@Composable
fun NewBadgePreview() {
    CuisineCompassTheme {
        NewBadge()
    }
}
