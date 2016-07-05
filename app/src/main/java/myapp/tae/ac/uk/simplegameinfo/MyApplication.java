package myapp.tae.ac.uk.simplegameinfo;

import android.app.Application;

import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.DI.components.APIComponent;
import myapp.tae.ac.uk.simplegameinfo.DI.components.DaggerAPIComponent;
import myapp.tae.ac.uk.simplegameinfo.DI.components.DaggerNetComponent;
import myapp.tae.ac.uk.simplegameinfo.DI.components.NetComponent;
import myapp.tae.ac.uk.simplegameinfo.DI.modules.APIModule;
import myapp.tae.ac.uk.simplegameinfo.DI.modules.AppModule;
import myapp.tae.ac.uk.simplegameinfo.DI.modules.NetModule;


/**
 * Created by Karma on 20/06/16..
 */
public class MyApplication extends Application {
    private NetComponent mNetComponent;
    private APIComponent mApiComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        mNetComponent = DaggerNetComponent.builder()
                .netModule(new NetModule(Constants.BASE_URL))
                .appModule(new AppModule(this))
                .build();

        mApiComponent = DaggerAPIComponent.builder()
                .netComponent(mNetComponent)
                .aPIModule(new APIModule())
                .build();

    }

    public NetComponent getNetComponent() {
        return mNetComponent;
    }

    public APIComponent getApiComponent() {
        return mApiComponent;
    }
}
