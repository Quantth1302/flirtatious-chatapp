package com.quannv.flirtatiouschat.adapter;

//import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quannv.flirtatiouschat.R;
import com.quannv.flirtatiouschat.fragment.OptionsFragment;

import java.util.ArrayList;

public class OptionAdapter extends RecyclerView.Adapter<OptionAdapter.OptionViewHolder> {
    private ArrayList<String> mExampleList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public static class OptionViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView txtOption;

        public OptionViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            txtOption = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }

    public OptionAdapter() {
        mExampleList = new ArrayList<>();
        mExampleList.add("Profile");
        mExampleList.add("Find friend");
        mExampleList.add("Logout");
    }

    @Override
    public OptionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.option_item, parent, false);
        OptionViewHolder evh = new OptionViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(OptionViewHolder holder, int position) {
        String currentItem = mExampleList.get(position);

        if (position== OptionsFragment.PROFILE) {
            holder.mImageView.setImageResource(R.drawable.ic_profile);
        }
        if (position== OptionsFragment.FIND_FRIEND) {
            holder.mImageView.setImageResource(R.drawable.ic_find_friend);
        }
        if (position== OptionsFragment.LOGOUT) {
            holder.mImageView.setImageResource(R.drawable.ic_logout);
        }
        holder.txtOption.setText(currentItem);
    }

    @Override
    public int getItemCount() {
        return mExampleList.size();
    }
}
