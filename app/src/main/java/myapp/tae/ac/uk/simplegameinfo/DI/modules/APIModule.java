package myapp.tae.ac.uk.simplegameinfo.DI.modules;

import dagger.Module;
import dagger.Provides;
import myapp.tae.ac.uk.simplegameinfo.Api.GameApiService;
import myapp.tae.ac.uk.simplegameinfo.DI.scopes.ActivityScope;
import retrofit2.Retrofit;

/**
 * Created by Karma on 20/06/16.
 */
@Module
public class APIModule {
    @ActivityScope
    @Provides
    GameApiService provideMovieAPI(Retrofit retrofit) {
        return retrofit.create(GameApiService.class);
    }
}
