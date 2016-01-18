package com.theoapps.remotepicviewer;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.theoapps.remotepicviewer.dummy.DummyContent;

/**
 * A fragment representing a single Picture detail screen.
 * This fragment is either contained in a {@link PictureListActivity}
 * in two-pane mode (on tablets) or a {@link PictureDetailActivity}
 * on handsets.
 */
public class PictureDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private FlikrPhoto mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public PictureDetailFragment(FlikrPhoto photo) {
        mItem = photo;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.picture_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.image);
            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.displayImage(mItem.getURI(), imageView);
        }

        return rootView;
    }
}
