package com.example.gitsearchtest.presentation.main

import androidx.annotation.StringRes
import com.example.gitsearchtest.R

sealed class Screens(val route: String, @StringRes resId: Int) {

    object SearchScreen: Screens("search_screen", R.string.search_screen)

    object ArchiveScreen: Screens("archive_screen", R.string.archive_screen)

}
