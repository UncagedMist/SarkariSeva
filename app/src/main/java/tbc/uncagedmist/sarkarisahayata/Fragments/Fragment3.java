package tbc.uncagedmist.sarkarisahayata.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import tbc.uncagedmist.sarkarisahayata.MainActivity;
import tbc.uncagedmist.sarkarisahayata.R;

public class Fragment3 extends Fragment {

    FloatingActionButton btnContinue;
    LottieAnimationView animationView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup root = (ViewGroup)inflater.inflate(R.layout.fragment3,container,false);

        btnContinue = root.findViewById(R.id.btnContinue);
        animationView = root.findViewById(R.id.lottieUpdated);

        animationView.playAnimation();

        btnContinue.setOnClickListener(new View.OnClickListener() {
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
