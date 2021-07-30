package com.deshbangla.shrimpandfish.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.deshbangla.shrimpandfish.R;

public class OfferAdapter extends PagerAdapter {

    Context context;

    public OfferAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.offer_item, container, false);

        TextView offerName = view.findViewById(R.id.offerName);
        TextView offerFishName = view.findViewById(R.id.offerFishName);
        TextView discount = view.findViewById(R.id.discount);
        ImageView offerFishImage = view.findViewById(R.id.offerFishImage);

        offerName.setText("EID OFFER");
        offerFishName.setText("Fresh Deshi Koi");
        discount.setText("40% DISCOUNT");

        offerFishImage.setImageResource(R.drawable.gallery4);


        container.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
