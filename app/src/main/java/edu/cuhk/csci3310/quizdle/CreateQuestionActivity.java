package edu.cuhk.csci3310.quizdle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import edu.cuhk.csci3310.quizdle.customview.DropDownMenuView;

public class CreateQuestionActivity extends AppCompatActivity {

    String TAG = "CreateQuestionActivity";

    DropDownMenuView ddmvCategory;
    DropDownMenuView ddmvSubcategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        setToolbar();

        ddmvCategory = findViewById(R.id.ddmv_category);
        ddmvSubcategory = findViewById(R.id.ddmv_subcategory);

        setCategoryDropDownMenu();

        ddmvCategory.dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                Log.d(TAG, item.toString() + " category is picked");
                ddmvSubcategory.setSpinner(CreateQuestionActivity.this, item.toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setToolbar(){
        // assigning ID of the toolbar to a variable
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked
                finish();
            }
        });
    }

    private void setCategoryDropDownMenu(){
        ddmvCategory.setSpinner(this);
    }

}