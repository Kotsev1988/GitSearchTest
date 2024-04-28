package com.example.gitsearchtest.domain.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserRepositoriesItem(
    @Expose val id: String?=null,
    @Expose val name: String? = null,
    @Expose val owner: Owner? = null,
    @Expose val html_url: String? = null,
): Parcelable
