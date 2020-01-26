package com.example.checklist;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private Context mContext;
    private int mResource;
    private ArrayList<ToDo> mTodos;
    private OnClickListener mOnClickListener;

    public ToDoAdapter(Context context, int resource, ArrayList<ToDo> todos, OnClickListener onClickListener) {
        mContext = context;
        mResource = resource;
        mTodos = todos;
        mOnClickListener = onClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View view = layoutInflater.inflate(mResource, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ToDo todo = mTodos.get(position);

        holder.tvActivity.setText(todo.getActivity());
        holder.cbDone.setChecked(todo.isDone());

        if (todo.isDone()) {
            holder.tvActivity.setPaintFlags(holder.tvActivity.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvActivity.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            holder.tvActivity.setPaintFlags(holder.tvActivity.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvActivity.setTypeface(null, Typeface.NORMAL);
        }

        holder.cbDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v;
                mOnClickListener.onChecked(holder.getAdapterPosition(), cb.isChecked());
            }
        });

        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnClickListener.onDelete(holder.getAdapterPosition());
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTodos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox cbDone;
        TextView tvActivity;
        ImageView ivDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cbDone = itemView.findViewById(R.id.cbDone);
            tvActivity = itemView.findViewById(R.id.tvActivity);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }
    }

    public interface OnClickListener {
        void onChecked(int position, boolean isChecked);

        void onDelete(int position);
    }
}
