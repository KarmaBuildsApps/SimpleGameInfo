package myapp.tae.ac.uk.simplegameinfo.DI.components;

import android.content.SharedPreferences;

import javax.inject.Singleton;

import dagger.Component;
import myapp.tae.ac.uk.simplegameinfo.DI.modules.AppModule;
import myapp.tae.ac.uk.simplegameinfo.DI.modules.NetModule;
import retrofit2.Retrofit;

/**
 * Created by Karma on 20/06/16.
 */
@Singleton
@Component(modules = {NetModule.class, AppModule.class})
public interface NetComponent {
    Retrofit retrofit();
    SharedPreferences sharedPreferences();
}
