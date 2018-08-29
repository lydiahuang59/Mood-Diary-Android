package com.example.advon.mydiary;
import android.view.View;
import android.widget.BaseAdapter;
import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return mThumbIds.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(135, 135));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 16, 8, 16);
        } else {
            imageView = (ImageView) convertView;
        }

        imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    // references to our images
    public static Integer[] mThumbIds = {
            R.drawable.angry, R.drawable.angry2,
            R.drawable.bored, R.drawable.bored1, R.drawable.bored2,
            R.drawable.confused, R.drawable.confused1,
            R.drawable.crying, R.drawable.crying1,
            R.drawable.embarrassed,
            R.drawable.happy, R.drawable.happy1, R.drawable.happy2,
            R.drawable.happy3,
            R.drawable.ill, R.drawable.inlove,
            R.drawable.kissing, R.drawable.mad,
            R.drawable.nerd, R.drawable.ninja,
            R.drawable.quiet, R.drawable.sad,
            R.drawable.secret, R.drawable.smart,
            R.drawable.smile, R.drawable.smiling,
            R.drawable.surprised, R.drawable.surprised1,
            R.drawable.suspicious, R.drawable.suspicious1,
            R.drawable.tongueout, R.drawable.tongueout1,
            R.drawable.unhappy, R.drawable.unsure, R.drawable.wink
    };
}
