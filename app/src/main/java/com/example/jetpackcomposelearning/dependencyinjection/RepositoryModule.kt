package com.example.jetpackcomposelearning.dependencyinjection

import com.example.jetpackcomposelearning.listscreen.MoviesRepository
import com.example.jetpackcomposelearning.networking.Api
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideMoviesRepository(
        api: Api,
    ): MoviesRepository {
        return MoviesRepository(api)
    }
}