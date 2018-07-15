package flickr.com.flickrimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import flickr.com.flickrimage.apiservice.APIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements PhotoAdapter.OnPhotoClickListener {
    private PhotoAdapter photoAdapter;
    private RecyclerView rcPhotos;
    private Button btnSearch;
    private EditText etSearchTag;
    private int page = 0;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcPhotos = findViewById(R.id.rc_photos);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcPhotos.setLayoutManager(gridLayoutManager);
        photoAdapter = new PhotoAdapter(MainActivity.this, this);
        rcPhotos.setAdapter(photoAdapter);

        etSearchTag = findViewById(R.id.et_search);

        btnSearch = findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                InputMethodManager
                    imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
                getPhotos(etSearchTag.getText().toString());
            }
        });

        rcPhotos.addOnScrollListener(new EndlessRecyclerViewOnScrollListener() {
            @Override public void onLoadMore() {
                getPhotos(etSearchTag.getText().toString());
            }
        });
    }

    private void getPhotos(String tag) {
        page++;
        Call<FlickrAPIResponse> response = APIService.provideGetRecentPhotosService()
            .getPhotos("6bf318919bbbc455f3573d18798a58e3", 10, tag, page);
        response.enqueue(new Callback<FlickrAPIResponse>() {
            @Override public void onResponse(
                Call<FlickrAPIResponse> call, Response<FlickrAPIResponse> response) {
                photoAdapter.addListOfPhotoItems(response.body().getPhotos().getPhoto());
                photoAdapter.notifyDataSetChanged();
            }

            @Override public void onFailure(Call<FlickrAPIResponse> call, Throwable t) {
                Log.d("MHH", t.getMessage());
            }
        });
    }

    @Override public void onClick(String url) {
        Intent intent = new Intent(this, PhotoDetailsActivity.class);
        intent.putExtra(PhotoDetailsActivity.IMG_URL, url);
        startActivity(intent);
    }
}
