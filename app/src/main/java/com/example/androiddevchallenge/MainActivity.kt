/*
 * Copyright 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.androiddevchallenge

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import androidx.navigation.compose.navigate
import androidx.navigation.compose.rememberNavController
import com.example.androiddevchallenge.data.AdoptionCenter
import com.example.androiddevchallenge.nav.Route
import com.example.androiddevchallenge.ui.detail.DogDetail
import com.example.androiddevchallenge.ui.feed.PawesomeFeed
import com.example.androiddevchallenge.ui.theme.PawesomeTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

            NavHost(
                navController = navController,
                startDestination = Route.Feed.route,
                builder = {
                    composable(Route.Feed.route) {
                        PawesomeTheme {
                            PawesomeFeed(
                                onNavigateToDog = { dog ->
                                    navController.navigate(Route.Detail.withArg(dog.name))
                                }
                            )
                        }
                    }

                    composable(
                        Route.Detail.route,
                        arguments = listOf(
                            navArgument(Route.Detail.token) {
                                type = NavType.StringType
                                defaultValue = AdoptionCenter.dogs.last().name
                                nullable = false
                            }
                        )
                    ) { backstackEntry ->
                        DogDetail(
                            dogName = backstackEntry.arguments?.getString(Route.Detail.token)
                        )
                    }
                }
            )
        }
    }
}
