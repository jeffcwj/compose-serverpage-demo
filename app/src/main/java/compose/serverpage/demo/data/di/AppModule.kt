package compose.serverpage.demo.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import compose.serverpage.demo.data.net.SampRemoteDataSource
import compose.serverpage.demo.data.net.api.SampWebApi

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideSampWebApi(
        dataSource: SampRemoteDataSource
    ) : SampWebApi {
        return dataSource.buildApi(SampWebApi::class.java)
    }

}