package com.example.gitsearchtest.presentation.main

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.gitsearchtest.R
import com.example.gitsearchtest.presentation.archives.ArchiveScreen
import com.example.gitsearchtest.presentation.search.SearchScreen

private const val TAB_SEARCH = "search_screen"
private const val TAB_ARCHIVE = "archive_screen"

@SuppressLint("SuspiciousIndentation")
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val context: Context = LocalContext.current
    var title by remember { mutableStateOf("") }

    LaunchedEffect(navController){
        navController.currentBackStackEntryFlow.collect {
            title = it.destination.route ?: ""
        }
    }


    val bottomItems = listOf(

        BottomMenuItem(
            label = "search_screen",
            stringId = R.string.search_screen,
            icon = painterResource(id = R.drawable.ic_search)
        ),
        BottomMenuItem(
            label = "archive_screen",
            stringId = R.string.archive_screen,
            icon = painterResource(id = R.drawable.ic_archive)
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = getTitle(title, context),
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(400),
                    lineHeight = 28.8.sp
                )
            ) },

                navigationIcon = {

                    if (currentDestination?.route != TAB_SEARCH && currentDestination?.route!=null) {

                        IconButton(
                            modifier = Modifier
                                .clip(RoundedCornerShape(100))
                                .requiredWidth(40.dp)
                                .requiredHeight(40.dp)
                                .background(MaterialTheme.colorScheme.inversePrimary),
                            onClick = {
                                navController.navigateUp()
                            },
                        )
                        {
                            Icon(
                                Icons.Filled.ArrowBack,
                                "backIcon")
                        }


                    }
                }
            )
        },

        bottomBar = {

            BottomNavigation(backgroundColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.primary
            ) {
                bottomItems.forEach { screen ->
                    val selected =
                        currentDestination?.hierarchy?.any { it.route == screen.label } == true

                    BottomNavigationItem(
                        selected = selected,
                        onClick = {
                            navController.navigate(screen.label) {

                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },

                        selectedContentColor = Color(0xFF4096FB),
                        unselectedContentColor = Color(0x4D0D0D0D),
                        label = {
                            Text(
                                stringResource(screen.stringId),

                                color = if (selected) Color(0xFF4096FB) else MaterialTheme.colorScheme.onSecondary,
                                textAlign = TextAlign.Center,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(400),
                                lineHeight = 16.sp
                            )
                        },
                        icon = {

                            Icon(
                                painter = screen.icon, contentDescription = screen.label,
                                tint = if (selected) Color(0xFF4096FB) else MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    )
                }
            }
        }
    ) { contentPadding ->

        NavHost(
            navController = navController,
            startDestination = Screens.SearchScreen.route,
            Modifier.padding(contentPadding)
        ) {

            composable(TAB_SEARCH) { SearchScreen() }
            composable(TAB_ARCHIVE) { ArchiveScreen() }

        }
    }
}

fun getTitle(route: String, context: Context): String{
    return  when(route){
        TAB_SEARCH -> context.getString(R.string.search_screen)
        TAB_ARCHIVE -> context.getString(R.string.archive_screen)
        else -> ""
    }
}