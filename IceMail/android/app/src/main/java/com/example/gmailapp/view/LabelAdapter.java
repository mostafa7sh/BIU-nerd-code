package com.example.gmailapp.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmailapp.R;
import com.example.gmailapp.model.Label;

import java.util.ArrayList;
import java.util.List;

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.LabelViewHolder> {

    private List<Label> labelList ;

    private OnLabelClickListener clickListener;
    private OnLabelLongClickListener longClickListener;

    public LabelAdapter(List<Label> labels) {
        this.labelList = labels;
    }

    public void updateList(List<Label> newLabels) {
        this.labelList = newLabels;
        notifyDataSetChanged();
    }

    public void setLabelList(List<Label> newList) {
        labelList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LabelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_label, parent, false);
        return new LabelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LabelViewHolder holder, int position) {
        Label label = labelList.get(position);
        holder.labelName.setText(label.getName());

        holder.itemView.setOnClickListener(v -> {
            Toast.makeText(v.getContext(), "Clicked on: " + label.getName(), Toast.LENGTH_SHORT).show();
        });

        holder.itemView.setOnClickListener(v -> {
            if (clickListener != null) clickListener.onClick(label);
        });

        holder.itemView.setOnLongClickListener(v -> {
            if (longClickListener != null) {
                longClickListener.onLongClick(label);
                return true;
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return labelList.size();
    }

    static class LabelViewHolder extends RecyclerView.ViewHolder {
        TextView labelName;

        public LabelViewHolder(@NonNull View itemView) {
            super(itemView);
            labelName = itemView.findViewById(R.id.labelName);
        }
    }

    public interface OnLabelClickListener {
        void onClick(Label label);
    }
    public interface OnLabelLongClickListener {
        void onLongClick(Label label);
    }

    public void setOnLabelClickListener(OnLabelClickListener listener) {
        this.clickListener = listener;
    }

    public void setOnLabelLongClickListener(OnLabelLongClickListener listener) {
        this.longClickListener = listener;
    }

}
