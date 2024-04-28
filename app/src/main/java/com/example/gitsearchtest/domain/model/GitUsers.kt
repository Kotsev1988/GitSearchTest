package com.example.gitsearchtest.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitUsers(
    val items: List<Item> = emptyList(),
): Parcelable
