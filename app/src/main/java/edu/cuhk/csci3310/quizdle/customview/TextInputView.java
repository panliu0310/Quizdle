package edu.cuhk.csci3310.quizdle.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.w3c.dom.Text;

import edu.cuhk.csci3310.quizdle.R;

public class TextInputView extends ConstraintLayout {

    TextView tvName;
    public EditText etInput;

    private final String name;

    public TextInputView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        View.inflate(context, R.layout.view_text_input, this);
        tvName = findViewById(R.id.tv_name);
        etInput = findViewById(R.id.et_input);

        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.TextInputView,
                0, 0);
        try {
            name = a.getString(R.styleable.TextInputView_textInputName);
        } finally {
            a.recycle();
        }

        setName();

    }

    private void setName(){
        tvName.setText(name);
    }

}
