package edu.cuhk.csci3310.quizdle.adapter;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import edu.cuhk.csci3310.quizdle.R;
import edu.cuhk.csci3310.quizdle.SubCategorySelectActivity;
import edu.cuhk.csci3310.quizdle.model.Category;


public class CategoryListAdapter extends FirestoreRecyclerAdapter<Category, CategoryListAdapter.CategoryViewHolder>{

    final String TAG = "CategoryListAdapter";
    final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.quizdle/drawable/category_";
    LinkedList<ArrayList<String>> subCategoryList = new LinkedList<java.util.ArrayList<String>>();

    public CategoryListAdapter(@NonNull FirestoreRecyclerOptions<Category> options) {
        super(options);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
            return new CategoryViewHolder(view);
            }

    @Override
    protected void onBindViewHolder(@NonNull CategoryViewHolder holder, int i, @NonNull Category category) {
        String name = category.getName();

        holder.nameTextView.setText(name);
        holder.imageItemView
                .setImageURI(Uri.parse(mDrawableFilePath+name.toLowerCase().substring(0,3)));
        if (category.getSubCategory() != null) {
            subCategoryList.add(category.getSubCategory());
            Log.d(TAG, subCategoryList.toString());
        }
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder{

        public static final String CATEGORY = "edu.cuhk.csci3310.cusweetspot.extra.CATEGORY";
        public static final String SUBCATEGORY = "edu.cuhk.csci3310.cusweetspot.extra.SUBCATEGORY";
        private TextView nameTextView;
        private ImageView imageItemView;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            imageItemView = itemView.findViewById(R.id.category_item_image);
            nameTextView = itemView.findViewById(R.id.category_item_name);

            imageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int mPosition = getLayoutPosition();
                    Intent intent = new Intent(view.getContext(), SubCategorySelectActivity.class);
                    intent.putExtra(CATEGORY, nameTextView.getText().toString());
                    intent.putExtra(SUBCATEGORY, subCategoryList.get(mPosition));
                    view.getContext().startActivity(intent);

                    Log.d(TAG, "Image OnClick "
                            + nameTextView.getText().toString().replace(" ",""));
                }
            });
        }
    }
}