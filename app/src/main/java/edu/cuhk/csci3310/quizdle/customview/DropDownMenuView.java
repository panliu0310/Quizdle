package edu.cuhk.csci3310.quizdle.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import edu.cuhk.csci3310.quizdle.R;

public class DropDownMenuView extends ConstraintLayout {

    String TAG = "DropDownMenuView";

    TextView tvName;
    public Spinner dropdown;

    private final String name;

    public DropDownMenuView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context,R.layout.view_drop_down_menu,this);
        tvName = findViewById(R.id.tv_name);
        dropdown = findViewById(R.id.spinner);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.DropDownMenuView,
                0, 0);
        try {
            name = a.getString(R.styleable.DropDownMenuView_dropDownMenuName);
        } finally {
            a.recycle();
        }

        setName();
    }

    private void setName(){
        tvName.setText(name);
    }

    public void setSpinner(Context context) {
        // set spinner item for category
        int resource;
        if (name.equals("category")){
            resource = R.array.category;

            // create an adapter to describe how the items are displayed, adapters are used in several places in android.
            // There are multiple variations of this, but this is the basic variant.
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, resource, android.R.layout.simple_spinner_item);;
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            adapter.notifyDataSetChanged();
            // set the spinners adapter to the previously created one.
            dropdown.setAdapter(adapter);
        }
    }

    public void setSpinner(Context context, String category){
        Log.d(TAG, "category is: " + category);
        // set spinner item for subcategory
        int resource = R.array.empty;
        switch (category){
            case "Mathematics":
                resource = R.array.mathsubcategory;
                break;
            case "Computer Science":
                resource = R.array.cssubcategory;
                break;
        }
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, resource, android.R.layout.simple_spinner_item);;
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        adapter.notifyDataSetChanged();
        dropdown.setAdapter(adapter);
    }

}
