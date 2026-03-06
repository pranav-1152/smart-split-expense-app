package com.example.smartsplit;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SplitAdapter extends RecyclerView.Adapter<SplitAdapter.ViewHolder> {

    Context context;
    ArrayList<String> splits;
    SplitDBHelper db;

    public SplitAdapter(Context context, ArrayList<String> splits) {
        this.context = context;
        this.splits = splits;
        db = new SplitDBHelper(context);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvSplitText;
        Button btnEdit, btnDelete, btnShare;

        public ViewHolder(View itemView) {
            super(itemView);

            tvSplitText = itemView.findViewById(R.id.tvSplitText);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
            btnShare = itemView.findViewById(R.id.btnShare);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_split_card, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        String item = splits.get(position);

        String displayText = item.contains("|") ? item.substring(item.indexOf("|") + 1) : item;

        holder.tvSplitText.setText(displayText);

        // DELETE
        holder.btnDelete.setOnClickListener(v -> {

            try {

                int splitId = Integer.parseInt(item.split("\\|")[0]);

                db.deleteSplit(splitId);

                splits.remove(position);

                notifyItemRemoved(position);

                Toast.makeText(context, "Split deleted", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {

                Toast.makeText(context, "Delete error", Toast.LENGTH_SHORT).show();
            }
        });

        // EDIT
        holder.btnEdit.setOnClickListener(v -> {

            Intent intent = new Intent(context, EditSplitActivity.class);
            intent.putExtra("split_data", item);
            context.startActivity(intent);

        });

        // SHARE
        holder.btnShare.setOnClickListener(v -> {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");

            shareIntent.putExtra(Intent.EXTRA_TEXT, displayText);

            context.startActivity(Intent.createChooser(shareIntent, "Share Split"));

        });
    }

    @Override
    public int getItemCount() {
        return splits.size();
    }

}
