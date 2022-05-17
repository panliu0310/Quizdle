package edu.cuhk.csci3310.quizdle.adapter;

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

import java.util.ArrayList;
import java.util.LinkedList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import edu.cuhk.csci3310.quizdle.QuestionActivity;
import edu.cuhk.csci3310.quizdle.R;
import edu.cuhk.csci3310.quizdle.SubCategorySelectActivity;


public class SubCategoryListAdapter extends Adapter<SubCategoryListAdapter.SubCategoryViewHolder> {
    private static final String TAG = "SubCategoryListAdapter";
    final String mCustomizeDrawableFilePath = "android.resource://edu.cuhk.csci3310.quizdle/drawable/category_cus";
    final String mDrawableFilePath = "android.resource://edu.cuhk.csci3310.quizdle/drawable/subcategory_";
    private Context context;

    private LayoutInflater mInflater;

    private ArrayList<String> mSubCategoryList;
    private String mCategory;

    public SubCategoryListAdapter(Context context, String category, ArrayList<String> subCategoryList){
        mInflater = LayoutInflater.from(context);
        this.mSubCategoryList = subCategoryList;
        this.mCategory = category;
    }

    public class SubCategoryViewHolder extends RecyclerView.ViewHolder{
        public static final String CATEGORY = "edu.cuhk.csci3310.quizdle.extra.CATEGORY";
        public static final String SUBCATEGORY = "edu.cuhk.csci3310.quizdle.extra.SUBCATEGORY";
        public ImageView imageItemView;
        public TextView subCategoryTextView;
        final SubCategoryListAdapter mAdapter;

        public SubCategoryViewHolder(View itemView, SubCategoryListAdapter adapter){
            super(itemView);
            imageItemView = itemView.findViewById(R.id.subcategory_item_image);
            subCategoryTextView = itemView.findViewById(R.id.subcategory_item_name);
            this.mAdapter = adapter;

            imageItemView.setOnClickListener(view -> {
                int mPosition = getLayoutPosition();
                Intent intent = new Intent(view.getContext(), QuestionActivity.class);
                intent.putExtra(CATEGORY, mCategory);
                intent.putExtra(SUBCATEGORY, mSubCategoryList.get(mPosition));
                view.getContext().startActivity(intent);

                Log.d(TAG, "Image OnClick "
                        + mCategory + " " + mSubCategoryList.get(mPosition));
            });
        }
    }

    @NonNull
    @Override
    public SubCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.item_subcategory, parent, false);
        return new SubCategoryViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull SubCategoryViewHolder holder, int position) {
        String mCategoryName = mSubCategoryList.get(position);
        holder.subCategoryTextView.setText(mCategoryName);
        holder.imageItemView.setImageURI(Uri.parse(mDrawableFilePath
                + mCategory.toLowerCase().substring(0,3) + "_"
                + mCategoryName.toLowerCase().substring(0,3).trim()));
        if(mCategory.equals("Customize")){
            holder.imageItemView.setImageURI(Uri.parse(mCustomizeDrawableFilePath));
        }
    }

    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mSubCategoryList.size();
    }


    /*public void updateData(ArrayList<String> imagePathList) {
        this.mSubCategoryList = imagePathList;
        // Notify the adapter, that the data has changed so it can
        // update the RecyclerView to display the data.
        notifyDataSetChanged();
    }*/

}
