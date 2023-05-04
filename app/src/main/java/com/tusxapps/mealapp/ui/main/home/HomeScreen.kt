package com.tusxapps.mealapp.ui.main.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tusxapps.mealapp.R
import com.tusxapps.mealapp.domain.meal.Meal
import com.tusxapps.mealapp.ui.composables.MealCard
import com.tusxapps.mealapp.ui.navigation.Screen

@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()

    HomeScreen(
        state = state,
        onMealClick = { navController.navigate(Screen.Meal.createRoute(it.id)) },
        onSearchValueChange = viewModel::onSearchValueChange,
        onFilterSelect = viewModel::onFilterSelect
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreen(
    state: HomeScreenState,
    onMealClick: (Meal) -> Unit,
    onSearchValueChange: (newQuery: String) -> Unit,
    onFilterSelect: (FilterTag) -> Unit,
) {
    Scaffold(
        topBar = {
            SearchAppBar(
                currentQuery = state.currentQuery,
                onSearchValueChange = onSearchValueChange,
                onFilterSelect = onFilterSelect,
                filterTag = state.filterTag
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        HomeBody(paddingValues, state, onMealClick)
    }
}

@Composable
private fun HomeBody(
    paddingValues: PaddingValues,
    state: HomeScreenState,
    onMealClick: (Meal) -> Unit,
) {
    Box(
        Modifier
            .fillMaxSize()
            .padding(paddingValues)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(state.filteredMeals) {
                MealCard(meal = it, onMealClick = onMealClick)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchAppBar(
    currentQuery: String,
    onSearchValueChange: (newQuery: String) -> Unit,
    onFilterSelect: (FilterTag) -> Unit,
    filterTag: FilterTag,
) {
    var isDropdownExpanded by remember {
        mutableStateOf(false)
    }
    val filterTagText = when (filterTag) {
        FilterTag.PRICE -> "По цене"
        FilterTag.NAME -> "По имени"
        FilterTag.POPULARITY -> "По популярности"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                val strokeWidth = Stroke.DefaultMiter
                val y = size.height - strokeWidth / 2
                drawLine(
                    Color.LightGray,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = currentQuery,
            onValueChange = onSearchValueChange,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search, contentDescription = null
                )
            },
            trailingIcon = {
                IconButton(onClick = { onSearchValueChange("") }) {
                    Icon(imageVector = Icons.Filled.Close, contentDescription = null)
                }
            },
            label = { Text(text = stringResource(R.string.search)) },
            shape = RoundedCornerShape(32.dp),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        FilledTonalButton(
            onClick = { isDropdownExpanded = true },
            modifier = Modifier.align(Alignment.Start)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.filter),
                    contentDescription = null,
                    Modifier.size(16.dp)
                )
                Spacer(Modifier.width(4.dp))
                Text(text = filterTagText)
            }
        }
        DropdownMenu(
            expanded = isDropdownExpanded,
            onDismissRequest = { isDropdownExpanded = false }) {
            DropdownMenuItem(
                text = { Text(text = "По имени") },
                onClick = {
                    onFilterSelect(FilterTag.NAME)
                    isDropdownExpanded = false
                })
            DropdownMenuItem(
                text = { Text(text = "По популярности") },
                onClick = {
                    onFilterSelect(FilterTag.POPULARITY)
                    isDropdownExpanded = false
                })
            DropdownMenuItem(
                text = { Text(text = "По цене") },
                onClick = {
                    onFilterSelect(FilterTag.PRICE)
                    isDropdownExpanded = false
                })
        }
    }
}