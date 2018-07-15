package flickr.com.flickrimage.apiservice;

import flickr.com.flickrimage.FlickrAPIResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetImageService {
    @GET("/services/rest/?nojsoncallback=1&method=flickr.photos.getRecent&format=json")
    Call<FlickrAPIResponse> getPhotos(
        @Query("api_key") String apiKey, @Query("per_page") int perPage, @Query("tag") String tag,
        @Query("page") int page);
}
