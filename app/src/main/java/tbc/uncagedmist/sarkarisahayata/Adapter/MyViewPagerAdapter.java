package tbc.uncagedmist.sarkarisahayata.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import tbc.uncagedmist.sarkarisahayata.Model.ImageItem;
import tbc.uncagedmist.sarkarisahayata.R;

public class MyViewPagerAdapter extends RecyclerView.Adapter<MyViewPagerAdapter.MyViewHolder> {

    private List<ImageItem> imageItems;
    private ViewPager2 viewPager2;

    public MyViewPagerAdapter(List<ImageItem> imageItems, ViewPager2 viewPager2) {
        this.imageItems = imageItems;
        this.viewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(
                LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_page,
                        parent,
                        false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.setImage(imageItems.get(position));

        if (position == imageItems.size() - 2)  {
            viewPager2.post(runnable);
        }
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        RoundedImageView imgView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.imageSlider);
        }

        void  setImage(ImageItem item)  {
            imgView.setImageResource(item.getImage());
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            imageItems.addAll(imageItems);
            notifyDataSetChanged();
        }
    };
}