package bhushan.org.GHRCEUSER.paper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class PaperAdapter extends RecyclerView.Adapter<PaperAdapter.PaperViewHolder> {

    private Context context;
    private List<PaperData> list;
    private PaperViewHolder holder;
    private int position;

    public PaperAdapter(Context context, List<PaperData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public PaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.paper_item_layout, parent, false);
        return new PaperViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull PaperViewHolder holder, int position) {

        holder.paperName.setText(list.get(position).getPaperTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(context, PaperViewerActivity.class);
                    intent.putExtra("paperUri", list.get(adapterPosition).getPaperUri());
                    // Start the activity using the intent
                    context.startActivity(intent);
                }
            }
        });

        holder.paperDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(list.get(adapterPosition).getPaperUri()));
                    // Start the activity using the intent
                    context.startActivity(intent);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PaperViewHolder extends RecyclerView.ViewHolder {

        private TextView paperName;
        private ImageView paperDownload;
        public PaperViewHolder(@NonNull View itemView) {
            super(itemView);

            paperDownload = itemView.findViewById(R.id.paperDownload);
            paperName = itemView.findViewById(R.id.paperName);

        }
    }
}
