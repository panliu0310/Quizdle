package edu.cuhk.csci3310.quizdle;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SinglePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SinglePlayerFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "singlePlayerFragment";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public SinglePlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LevelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SinglePlayerFragment newInstance(String param1, String param2) {
        SinglePlayerFragment fragment = new SinglePlayerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_level, container, false);

        View question_set_mode = view.findViewById(R.id.qsm_iv);
        question_set_mode.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Log.d(TAG,"Clicked Question Set Mode");
                 Intent intent = new Intent(getContext(), QuestionSetActivity.class);
                 startActivity(intent);
             }
         });

        View endless_mode = view.findViewById(R.id.em_iv);
        endless_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Chosen Endless Mode");
                // TODO: Link to endless mode
            }
        });

        View create_mode = view.findViewById(R.id.cm_iv);
        create_mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"Chosen Create Mode");
                // TODO: Link to create mode
            }
        });


        return view;
    }
}