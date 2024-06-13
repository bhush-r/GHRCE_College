package bhushan.org.GHRCEUSER.menuitems.News;


import android.content.Context;
        import android.content.Intent;
        import android.net.Uri;
        import android.text.TextUtils;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.LinearLayout;
        import android.widget.TextView;
        import bhushan.org.GHRCEUSER.R;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.RecyclerView;

        import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {

    private Context context;
    private List<Newss> newsList;

    public NewsAdapter(Context context, List<Newss> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_item, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        Newss news = newsList.get(position);
        holder.newsText.setText(news.title);
        holder.noticeText.setText(news.notice); // Display the notice content
        holder.dateText.setText(news.date);

        if (!TextUtils.isEmpty(news.link)) {
            holder.linkLayout.setVisibility(View.VISIBLE);
            holder.linkLayout.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(news.link));
                context.startActivity(intent);
            });
        } else {
            holder.linkLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {

        TextView newsText, noticeText,dateText;
        LinearLayout linkLayout;

        NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            newsText = itemView.findViewById(R.id.news);
            noticeText = itemView.findViewById(R.id.notice); // Assuming you have this TextView in your layout
            dateText = itemView.findViewById(R.id.date);
            linkLayout = itemView.findViewById(R.id.link);
        }
    }
}

