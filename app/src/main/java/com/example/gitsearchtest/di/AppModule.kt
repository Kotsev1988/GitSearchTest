package com.example.gitsearchtest.di

import android.app.Application
import androidx.room.Room
import com.example.gitsearchtest.data.api.IGitHubAPI
import com.example.gitsearchtest.data.data_source.Database
import com.example.gitsearchtest.data.repository.ArchiveReposImpl
import com.example.gitsearchtest.data.repository.GetSearchImpl
import com.example.gitsearchtest.domain.repository.IArchiveRepos
import com.example.gitsearchtest.domain.use_cases.AddRepoArchive
import com.example.gitsearchtest.domain.use_cases.ArchiveUseCase
import com.example.gitsearchtest.domain.use_cases.GetArchives
import com.example.gitsearchtest.domain.use_cases.GetUserReposUseCase
import com.example.gitsearchtest.domain.use_cases.GetUsersUseCase
import com.example.gitsearchtest.domain.use_cases.UserReposUseCase
import com.example.gitsearchtest.domain.use_cases.UsersUseCase
import com.example.gitsearchtest.presentation.SharedViewModel
import com.example.gitsearchtest.presentation.util.DownloadUtil
import com.example.gitsearchtest.presentation.util.DownloadUtilImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(app: Application) =
        Room.databaseBuilder(app, Database::class.java, Database.DB_NAME).build()

    @Provides
    @Singleton
    fun providePlanRepository(db: Database): IArchiveRepos = ArchiveReposImpl(db)

    @Provides
    @Singleton
    fun providesGetBlogsImpl(api: IGitHubAPI) = GetSearchImpl(api)

    @Provides
    @Singleton
    fun providesContentsUseCases(repository: GetSearchImpl): UsersUseCase = UsersUseCase(
        getUsersUseCase = GetUsersUseCase(repository)
    )

    @Provides
    @Singleton
    fun providesContentInfoUseCases(repository: GetSearchImpl): UserReposUseCase = UserReposUseCase(
        getUserReposUseCase = GetUserReposUseCase(repository)
    )

    @Provides
    @Singleton
    fun providesArchiveUseCases(repository: IArchiveRepos): ArchiveUseCase = ArchiveUseCase(
        getAllReposFromDB = GetArchives(repository),
        addArchiveToDB = AddRepoArchive(repository)
    )

    @Named("baseURL")
    @Provides
    fun baseURL(): String = "https://api.github.com"

    @Singleton
    @Provides
    fun api(@Named("baseURL") baseURL: String, gson: Gson): IGitHubAPI =
        Retrofit.Builder().baseUrl(baseURL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(IGitHubAPI::class.java)

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideDownloadManager(context: Application) : DownloadUtil = DownloadUtilImpl(context)

    @Provides
    @Singleton
    fun provideSharedViewModel(): SharedViewModel {
        return SharedViewModel()
    }
}