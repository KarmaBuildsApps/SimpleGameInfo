package myapp.tae.ac.uk.simplegameinfo.Api;



import myapp.tae.ac.uk.simplegameinfo.Constants.Constants;
import myapp.tae.ac.uk.simplegameinfo.Model.Games;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Karma on 20/06/2016.
 */
public interface GameApiService {
    @GET(Constants.DATA_TO_FETCH)
    Observable<Games> getResult();
}
