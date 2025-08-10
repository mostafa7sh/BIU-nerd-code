package com.example.gmailapp.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gmailapp.R;
import com.example.gmailapp.model.Mail;
import com.example.gmailapp.viewmodel.InboxViewModel;

import java.util.List;

public class MailAdapter extends RecyclerView.Adapter<MailAdapter.MailViewHolder> {

    private List<Mail> mailList;
    private InboxViewModel viewModel;

    public MailAdapter(List<Mail> mailList) {
        this.mailList = mailList;
    }

    public void setViewModel(InboxViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void updateList(List<Mail> newList) {
        mailList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mail, parent, false);
        return new MailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MailViewHolder holder, int position) {
        Mail mail = mailList.get(position);

        holder.textSender.setText(mail.getFrom());
        holder.textSubject.setText(mail.getSubject());
        holder.textBody.setText(mail.getContent());

        holder.btnImportant.setOnClickListener(v -> {
            Context context = v.getContext();
            SharedPreferences prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
            String jwt = prefs.getString("jwt", null);
            if (viewModel != null && jwt != null) {
                boolean newStatus = !mail.isImportant();
                viewModel.markAsImportant(jwt, mail.getId(), newStatus).observeForever(success -> {
                    if (success) {
                        mail.setImportant(newStatus);
                        notifyItemChanged(holder.getAdapterPosition());
                    } else {
                        Toast.makeText(context, "Failed to mark as Important", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

        holder.btnImportant.setImageResource(
                mail.isImportant() ? R.drawable.ic_star_filled : R.drawable.ic_star_outline
        );

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ViewMailActivity.class);
            intent.putExtra("mail", mail);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return mailList.size();
    }

    static class MailViewHolder extends RecyclerView.ViewHolder {
        TextView textSender, textSubject, textBody;
        ImageButton btnImportant;

        public MailViewHolder(@NonNull View itemView) {
            super(itemView);
            textSender = itemView.findViewById(R.id.textViewSender);
            textSubject = itemView.findViewById(R.id.textViewSubject);
            textBody = itemView.findViewById(R.id.textViewBody);
            btnImportant = itemView.findViewById(R.id.btnImportant);
        }
    }
}
