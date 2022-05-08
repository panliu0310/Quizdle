package edu.cuhk.csci3310.quizdle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import edu.cuhk.csci3310.quizdle.customview.DropDownMenuView;

public class CreateQuestionActivity extends AppCompatActivity {

    String TAG = "CreateQuestionActivity";

    TextView tvGreeting;
    DropDownMenuView ddmvCategory;
    DropDownMenuView ddmvSubcategory;
    Button btnSubmit;

    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_question);

        setToolbar();

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        tvGreeting = findViewById(R.id.tv_greeting);
        ddmvCategory = findViewById(R.id.ddmv_category);
        ddmvSubcategory = findViewById(R.id.ddmv_subcategory);
        btnSubmit = findViewById(R.id.btn_submit);

        tvGreeting.setText(String.format(getString(R.string.create_question_greeting),username));

        setCategoryDropDownMenu();

        ddmvCategory.dropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                Object item = parent.getItemAtPosition(pos);
                Log.d(TAG, item.toString() + " category is picked");
                // set subcategory drop down menu
                ddmvSubcategory.setSpinner(CreateQuestionActivity.this, item.toString());
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
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

    private void submitForm(){
        // TODO: get values in the custom view and push to the firebase
    }

}