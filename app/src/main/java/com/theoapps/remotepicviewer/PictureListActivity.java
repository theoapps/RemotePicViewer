package com.theoapps.remotepicviewer;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.List;

/**
 * An activity representing a list of Pictures. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link PictureDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class PictureListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        if (findViewById(R.id.picture_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }


        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);

        FlikrClient fc = new FlikrClient();
        try {
            List<FlikrPhoto> photos = (List<FlikrPhoto>) fc.execute().get();
            Log.d("PictureList", String.valueOf(photos.size()));
            View recyclerView = findViewById(R.id.picture_list);
            assert recyclerView != null;
            setupRecyclerView((RecyclerView) recyclerView, photos);
        }
        catch(Exception e) {
            Log.d("PictureList", "exception " + e);
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView, List<FlikrPhoto> photos) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(photos));
    }

    public class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<FlikrPhoto> mValues;

        public SimpleItemRecyclerViewAdapter(List<FlikrPhoto> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.mItem = mValues.get(position);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(mValues.get(position).getURI(), holder.mImageView);

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        PictureDetailFragment fragment = new PictureDetailFragment(mValues.get(position));
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.picture_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Bundle bundle = new Bundle();
                        Intent intent = new Intent(context, PictureDetailActivity.class);
                        bundle.putParcelable(PictureDetailActivity.FLIKR_PHOTO, mValues.get(position));
                        intent.putExtras(bundle);
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView mImageView;
            public FlikrPhoto mItem;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (ImageView) view.findViewById(R.id.image);
            }
        }
    }
}
