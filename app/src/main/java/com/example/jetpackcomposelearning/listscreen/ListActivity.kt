package com.example.jetpackcomposelearning.listscreen

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.transition
import androidx.compose.material.Text
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumnFor
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.ui.tooling.preview.Preview
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.jetpackcomposelearning.data.Movie
import com.example.jetpackcomposelearning.ui.JetpackComposeLearningTheme
import com.example.jetpackcomposelearning.ui.ProgressColorTransition
import com.example.jetpackcomposelearning.ui.colorPropKey
import com.skydoves.landscapist.glide.GlideImage
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListActivity : AppCompatActivity() {

    private val viewModel: ListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            JetpackComposeLearningTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    setContent()
                }
            }
        }
    }

    @Composable
    fun setContent() {
        //flow state of content
        val movieStateFlow = viewModel.data.observeAsState()

        PageStateContent(pageState = viewModel.pageState, list = movieStateFlow.value)
    }

    @Composable
    fun PageStateContent(
        pageState: State<ListViewModel.PageState>,
        errorMessage: String = "Oops! Something went wrong, Please try again after some time",
        emptyMessage: String = "Nothing in here yet!, Please comeback later",
        list: List<Movie>?
    ) {
        Column {
            TopAppBar(title = {
                Text(
                    text = "Popular movies",
                    style = MaterialTheme.typography.caption
                )
            })
            when (pageState.value) {
                ListViewModel.PageState.LOADING -> {
                    MoviesLoadingComponent()
                }
                ListViewModel.PageState.ERROR -> {
                    EmptyMoviesComponent(errorMessage)
                }
                ListViewModel.PageState.DATA -> {
                    MoviesListComponent(list)
                }
                ListViewModel.PageState.EMPTY -> {
                    EmptyMoviesComponent(emptyMessage)
                }
            }
        }
    }

    @Preview(showDecoration = true)
    @Composable
    fun MoviesLoadingComponent() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = CenterHorizontally
        ) {

            val colorState = transition(
                definition = ProgressColorTransition,
                initState = 0,
                toState = 1
            )

            CircularProgressIndicator(
                modifier = Modifier.wrapContentWidth(CenterHorizontally),
                color = colorState[colorPropKey]
            )
        }
    }
}

@Composable
fun EmptyMoviesComponent(errorMessage: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
        )
    }
}

@Composable
fun MoviesListComponent(list: List<Movie>?) {
    Column {
        LazyColumnFor(items = list!!, modifier = Modifier) {
            MovieItem(movie = it)
        }
    }
}

@Composable
fun MovieItem(movie: Movie) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .padding(all = 16.dp)
            .fillMaxWidth()
    ) {
        Column {
            ConstraintLayout(
                modifier = Modifier.background(Color.Black).height(200.dp).fillMaxWidth(),
            ) {

                // Create references for the composables to constrain
                val (image) = createRefs()
                GlideImage(
                    imageModel = "https://image.tmdb.org/t/p/w500" + movie.posterPath!!,
                    requestOptions = RequestOptions()
                        .override(256, 256)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .centerCrop(),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.height(200.dp).fillMaxWidth().constrainAs(image) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                    alignment = Alignment.Center,
                )
            }
            Text(
                text = movie.header,
                modifier = Modifier.padding(all = 8.dp),
                style = MaterialTheme.typography.body1
            )
            Row(
                modifier = Modifier.padding(all = 8.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Release Date: ${movie.releaseDate}",
                    style = MaterialTheme.typography.subtitle2
                )
                Text(
                    text = "Vote count: ${movie.voteCount}",
                    style = MaterialTheme.typography.subtitle2
                )
            }
            Text(
                text = movie.description!!,
                modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 16.dp, bottom = 8.dp),
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2
            )
        }
    }
}
