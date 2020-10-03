package tbc.uncagedmist.sarkarisahayata.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;

import tbc.uncagedmist.sarkarisahayata.MainActivity;
import tbc.uncagedmist.sarkarisahayata.R;

public class Fragment2 extends Fragment {

    LottieAnimationView animationView;
    TextView txtSkip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment2,container,false);

        animationView = root.findViewById(R.id.lottieServices);
        txtSkip = root.findViewById(R.id.txtSkip);

        animationView.playAnimation();

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), MainActivity.class));
            }
        });

        setUpAnimation();

        return root;
    }

    private void setUpAnimation() {
        animationView.animate()
                .setDuration(5000)
                .setStartDelay(1000);
    }
}
