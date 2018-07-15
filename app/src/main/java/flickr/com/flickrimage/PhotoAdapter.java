package flickr.com.flickrimage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<PhotoItem> listOfPhotoItems = new ArrayList<>();
    private Context context;
    private OnPhotoClickListener onPhotoClickListener;

    public PhotoAdapter(Context context, OnPhotoClickListener onPhotoClickListener) {
        this.context = context;
        this.onPhotoClickListener = onPhotoClickListener;
    }

    public void addListOfPhotoItems(List<PhotoItem> listOfPhotoItems) {
        this.listOfPhotoItems.addAll(listOfPhotoItems);
    }

    @NonNull @Override public PhotoViewHolder onCreateViewHolder(
        @NonNull ViewGroup parent, int viewType) {
        View view =
            LayoutInflater.from(parent.getContext()).inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override public void onBindViewHolder(@NonNull PhotoViewHolder holder, int position) {
        final String url = constructURL(listOfPhotoItems.get(position));
        Glide.with(context).load(url).into(holder.ivPhoto);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view) {
                onPhotoClickListener.onClick(url);
            }
        });
    }

    @Override public int getItemCount() {
        return listOfPhotoItems.size();
    }

    private String constructURL(PhotoItem photoItem) {
        //https://farm{farm-id}.staticflickr.com/{server-id}/{id}_{secret}_[mstzb].jpg
        return "https://farm" + photoItem.getFarm() + ".staticflickr.com/" + photoItem.getServer()
            + "/" + photoItem.getId() + "_" + photoItem.getSecret() + "_s.jpg";
    }

    public class PhotoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivPhoto;

        public PhotoViewHolder(View view) {
            super(view);
            ivPhoto = view.findViewById(R.id.iv_photo);
        }
    }

    public interface OnPhotoClickListener {
        void onClick(String url);
    }
}
