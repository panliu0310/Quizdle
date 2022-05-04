package edu.cuhk.csci3310.quizdle.dialogfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import edu.cuhk.csci3310.quizdle.R;

public class ModifyUsernameDialogFragment extends DialogFragment {

    String username;

    public static ModifyUsernameDialogFragment newInstance(String username) {
        ModifyUsernameDialogFragment fragment = new ModifyUsernameDialogFragment();

        // Supply num input as an argument.
        Bundle args = new Bundle();
        args.putString("username", username);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        username = getArguments().getString("username");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_modify_username_dialog, container, false);
        EditText etUsername = view.findViewById(R.id.et_username);
        etUsername.setText(username);
        return view;
    }
}
