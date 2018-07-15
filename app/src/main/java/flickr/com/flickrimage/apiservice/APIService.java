package flickr.com.flickrimage.apiservice;

import flickr.com.flickrimage.BuildConfig;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIService {

    private static HttpUrl endPoint = HttpUrl.parse("https://api.flickr.com/");

    public static Retrofit provideAdapter() {
        okhttp3.OkHttpClient client = new OkHttpClient();
        final OkHttpClient.Builder builder = client.newBuilder();
        if (BuildConfig.DEBUG) {
            final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(loggingInterceptor);
        }

        final Retrofit.Builder retrofitBuilder = new Retrofit.Builder().client(builder.build())
            .baseUrl(endPoint)
            .addConverterFactory(GsonConverterFactory.create());
        return retrofitBuilder.build();
    }

    public static GetImageService provideGetRecentPhotosService() {
        return provideAdapter().create(GetImageService.class);
    }
}
