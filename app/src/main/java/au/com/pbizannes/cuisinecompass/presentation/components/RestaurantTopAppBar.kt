package au.com.pbizannes.cuisinecompass.presentation.components

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import au.com.pbizannes.cuisinecompass.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantTopAppBar(
    onProfileClick: () -> Unit = { },
    onFilterClick: () -> Unit = {},
    onNavigateBack: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Image(
                painter = painterResource(id = R.drawable.redish_crescent), // Replace with your logo
                contentDescription = "App Logo",
                modifier = Modifier.size(36.dp), // Adjust size as needed
            )
        },
        navigationIcon = {
            IconButton(onClick = onProfileClick) {
                Icon(
                    imageVector = Icons.Filled.Person,
                    contentDescription = "Profile"
                )
            }
        },
        actions = {
            IconButton(onClick = onFilterClick) {
                Icon(
                    imageVector = Icons.Filled.Menu, // Filter icon
                    contentDescription = "Filter"
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface // Or your desired color
        ),
        modifier = modifier
    )
}
