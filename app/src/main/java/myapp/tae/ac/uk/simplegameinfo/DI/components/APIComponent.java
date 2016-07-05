package myapp.tae.ac.uk.simplegameinfo.DI.components;

import dagger.Component;
import myapp.tae.ac.uk.simplegameinfo.DI.modules.APIModule;
import myapp.tae.ac.uk.simplegameinfo.DI.scopes.ActivityScope;
import myapp.tae.ac.uk.simplegameinfo.Controller.DataService;

/**
 * Created by Karma on 20/06/16.
 */
@ActivityScope
@Component(dependencies = NetComponent.class, modules = APIModule.class)
public interface APIComponent {
    void inject(DataService dataService);
}
