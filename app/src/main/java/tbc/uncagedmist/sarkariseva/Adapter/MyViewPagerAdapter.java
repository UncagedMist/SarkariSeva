package tbc.uncagedmist.sarkariseva.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tbc.uncagedmist.sarkariseva.R;

public class MyViewPagerAdapter extends RecyclerView.Adapter<MyViewPagerAdapter.MyViewHolder> {

    Context context;

    public MyViewPagerAdapter(Context context) {
        this.context = context;
    }

    int[][] color_icon_col = new int[][] {
            {Color.parseColor("#258CFF"),R.drawable.ic_baseline_home_24},
            {Color.parseColor("#ffc107"),R.drawable.ic_baseline_settings_applications_24},
            {Color.parseColor("#76ff03"),R.drawable.ic_baseline_white_emoji_people_24},
            {Color.parseColor("#6d4c41"),R.drawable.ic_baseline_security_24},
            {Color.parseColor("#ff0000"),R.drawable.ic_baseline_exit_to_app_24}
    };

    int[][] color_icon_matrix = new int[][] {
            {android.R.color.holo_red_light,R.drawable.ic_baseline_home_24},
            {android.R.color.holo_blue_dark,R.drawable.ic_baseline_settings_applications_24},
            {android.R.color.darker_gray,R.drawable.ic_baseline_white_emoji_people_24},
            {android.R.color.holo_green_dark,R.drawable.ic_baseline_security_24},
            {android.R.color.holo_purple,R.drawable.ic_baseline_exit_to_app_24}
    };

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_page,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.imgView.setImageResource(color_icon_matrix[position][1]);
        holder.container.setBackgroundResource(color_icon_matrix[position][0]);
    }

    @Override
    public int getItemCount() {
        return color_icon_matrix.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imgView;
        RelativeLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imgView = itemView.findViewById(R.id.image);
            container = itemView.findViewById(R.id.container);
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

        }
    };
}