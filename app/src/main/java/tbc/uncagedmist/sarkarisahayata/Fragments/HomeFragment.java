package tbc.uncagedmist.sarkarisahayata.Fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;

import tbc.uncagedmist.sarkarisahayata.Adapter.StateAdapter;
import tbc.uncagedmist.sarkarisahayata.Database.MyDatabase;
import tbc.uncagedmist.sarkarisahayata.Model.State;
import tbc.uncagedmist.sarkarisahayata.R;

public class HomeFragment extends Fragment {

    RecyclerView recyclerView;
    ArrayList<State> stateArrayList = new ArrayList<>();
    EditText edtState;

    Context context;

    private static HomeFragment INSTANCE = null;

    public static HomeFragment getInstance()    {

        if (INSTANCE == null)   {
            INSTANCE = new HomeFragment();
        }
        return INSTANCE;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myFragment = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = myFragment.findViewById(R.id.recyclerState);
        edtState = myFragment.findViewById(R.id.edtStateName);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        edtState.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<State> stateList = new ArrayList<>();

                String edtStateName = edtState.getText().toString().trim();

                for (State stateName : stateArrayList)   {
                    if (stateName.getStateName().toLowerCase().contains(edtStateName.toLowerCase()))  {
                        Cursor cursor = new MyDatabase(
                                context).getStateByNames(edtStateName.toLowerCase());

                        while (cursor.moveToNext()) {
                            State state = new State(
                                    cursor.getString(0),
                                    cursor.getString(1),
                                    cursor.getString(2),
                                    cursor.getString(3),
                                    cursor.getString(4)
                            );
                            stateList.add(state);
                        }
                    }

                }

                StateAdapter adapter = new StateAdapter(context, stateList);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        getAllStateList();

        return myFragment;
    }

    private void getAllStateList() {
        Cursor cursor = new MyDatabase(getContext()).getAllStateData();

        while (cursor.moveToNext()) {
            State state = new State(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
            );
            stateArrayList.add(state);
        }

        StateAdapter adapter = new StateAdapter(context, stateArrayList);

        recyclerView.setAdapter(adapter);
    }
}