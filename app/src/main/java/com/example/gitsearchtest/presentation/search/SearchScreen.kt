package com.example.gitsearchtest.presentation.search

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gitsearchtest.R
import com.example.gitsearchtest.presentation.SharedViewModel

@RequiresApi(Build.VERSION_CODES.TIRAMISU)

@Composable
fun SearchScreen() {

    val viewModel: SearchViewModel = hiltViewModel()
    val titleState = viewModel.planComment.value
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isSearching by viewModel.isSearching.collectAsState()
    val sharedViewModel: SharedViewModel = hiltViewModel()
    val context = LocalContext.current

    Surface(
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                value = titleState.text,
                singleLine = true,
                onValueChange = {
                    viewModel.handleViewEvent(SearchAppState.EnteredComment(it))
                },
                label = {
                    Text(text = "Enter username")
                },
            )

            Spacer(modifier = Modifier.height(16.dp))
            if (isSearching) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface)
                ) {
                    itemsIndexed(state) { _, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(
                                defaultElevation = 2.dp
                            ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.onBackground
                            ),
                            onClick = {
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.html_url))
                                try {
                                    context.startActivity(intent)
                                } catch (e: ActivityNotFoundException) {

                                }
                            }
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 10.dp, end = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    item.name?.let {
                                        Text(
                                            maxLines = 1,
                                            overflow = TextOverflow.Ellipsis,
                                            modifier = Modifier.padding(
                                                start = 4.dp,
                                                end = 4.dp,
                                                top = 4.dp
                                            ),
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            textAlign = TextAlign.Start,
                                            fontSize = 12.sp,
                                            fontWeight = FontWeight(500),
                                            lineHeight = 16.sp,
                                            text = it
                                        )
                                    }
                                    item.owner?.login?.let {
                                        Text(
                                            modifier = Modifier.padding(
                                                start = 4.dp,
                                                end = 4.dp,
                                                top = 4.dp
                                            ),
                                            style = TextStyle(
                                                color = MaterialTheme.colorScheme.onPrimary,
                                                fontSize = 12.sp,
                                                fontWeight = FontWeight(400),
                                                lineHeight = 16.sp
                                            ),
                                            text = it
                                        )
                                    }
                                }

                                IconButton(
                                    onClick = {
                                        item.owner?.login?.let { login ->
                                            item.name?.let { repo ->
                                                viewModel.downloadZip(
                                                    login, repo
                                                )
                                                sharedViewModel.setSharedData(repo, login)
                                            }
                                        }
                                    }) {
                                    Icon(painterResource(id = R.drawable.download_icon), "")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}