package flickr.com.flickrimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

public class PhotoDetailsActivity extends AppCompatActivity {
    private ImageView ivDetails;
    public static String IMG_URL = "imgURL";

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
        ivDetails = findViewById(R.id.iv_photo_details);
        String url = getIntent().getStringExtra(IMG_URL);
        Glide.with(this).load(url).into(ivDetails);
    }
}
