package com.example.jetpackcomposelearning.listscreen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.example.jetpackcomposelearning.data.Movie
import com.example.jetpackcomposelearning.networking.Api
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ListViewModel @ViewModelInject constructor(
    private val movieRepository: MoviesRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel(){

    val data = MutableLiveData<List<Movie>>()

    private fun getMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = movieRepository.getMovies()
            if (result == null){
                fetchedData(null, Throwable())
            }
            else {
                fetchedData(result, null)
            }
        }
    }

    init {
        getMovies()
    }

    private fun fetchedData(movies: List<Movie>?, error: Throwable?) {
        when {
            error != null -> {
                _pageState.value = PageState.ERROR
            }
            movies.isNullOrEmpty() -> {
                _pageState.value = PageState.EMPTY
            }
            else -> {
                data.postValue(movies)
                _pageState.value = PageState.DATA
            }
        }
    }

    private val _pageState = mutableStateOf(PageState.LOADING)
    val pageState: State<PageState>
        get() = _pageState

    enum class PageState {
        LOADING,
        DATA,
        ERROR,
        EMPTY
    }
}