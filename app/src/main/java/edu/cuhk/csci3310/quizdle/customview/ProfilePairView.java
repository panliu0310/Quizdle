package edu.cuhk.csci3310.quizdle.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import edu.cuhk.csci3310.quizdle.R;

public class ProfilePairView extends ConstraintLayout {

    TextView tvName;
    TextView tvContent;
    private final String name;
    private String content;

    public ProfilePairView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context,R.layout.view_profile_pair,this);
        tvName = findViewById(R.id.tv_name);
        tvContent = findViewById(R.id.tv_content);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.ProfilePairView,
                0, 0);
        try {
            name = a.getString(R.styleable.ProfilePairView_profileName);
        } finally {
            a.recycle();
        }
        setName();
    }

    private void setName(){
        tvName.setText(name);
    }

    public void setContent(String content){
        tvContent.setText(content);
    }

    public void setContent(String content1, String content2){
        tvContent.setText(content1 + "/" + content2);
    }
}
