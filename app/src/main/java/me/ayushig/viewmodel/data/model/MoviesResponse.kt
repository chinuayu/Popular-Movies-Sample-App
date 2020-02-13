/*
 * Copyright 2018 Felipe Joglar Santos
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.ayushig.viewmodel.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Raw movie service from server
 */
data class MoviesResponse(

    @SerializedName("page")
    @Expose
    val page: Int = 0,
    @SerializedName("total_results")
    @Expose
    val totalResults: Int = 0,
    @SerializedName("total_pages")
    @Expose
    val totalPages: Int = 0,
    @SerializedName("results")
    @Expose
    val movies: List<Movie>? = null
)

