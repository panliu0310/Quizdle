package edu.cuhk.csci3310.quizdle;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;


public class CategoryListAdapter extends Adapter<CategoryListAdapter.CategoryViewHolder> {
    private static final String TAG = "CategoryListAdapter";
    private Context context;

    private LayoutInflater mInflater;

    private LinkedList<String> mCategoryList;

    public CategoryListAdapter(Context context, LinkedList<String> categoryList){
        mInflater = LayoutInflater.from(context);
        this.mCategoryList = categoryList;
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageItemView;
        public TextView categoryTextView;
        final CategoryListAdapter mAdapter;

        public CategoryViewHolder(View itemView, CategoryListAdapter adapter){
            super(itemView);
            imageItemView = itemView.findViewById(R.id.category_item_image);
            categoryTextView = itemView.findViewById(R.id.category_item_name);
            this.mAdapter = adapter;

            //TODO: Set onClickHandler
        }
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String mCategoryName = mCategoryList.get(position);
        holder.categoryTextView.setText(mCategoryName);
        //String mImagePath = mCategoryList.get(position);
        //Uri uri = Uri.parse(mImagePath);
        //holder.imageItemView.setImageURI(uri);
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCategoryList.size();
    }


    public void updateData(LinkedList<String> imagePathList) {
        this.mCategoryList = imagePathList;
        // Notify the adapter, that the data has changed so it can
        // update the RecyclerView to display the data.
        notifyDataSetChanged();
    }

}
