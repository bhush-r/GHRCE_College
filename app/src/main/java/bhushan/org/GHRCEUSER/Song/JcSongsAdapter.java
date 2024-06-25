////package bhushan.org.GHRCEUSER.Song;
////
////import android.content.Context;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.ImageView;
////import android.widget.TextView;
////
////import bhushan.org.GHRCEUSER.R;
////import androidx.annotation.NonNull;
////import androidx.core.content.ContextCompat;
////import androidx.recyclerview.widget.RecyclerView;
////
////import java.util.List;
////
////public class JcSongsAdapter extends RecyclerView.Adapter<JcSongsAdapter.SongsAdapterViewHolder> {
////
////    private int selectedPosition;
////    Context context;
////    List<GetSongs> arraylistSongs;
////    private RecyclerViewItemClickListener listener;
////
////
////    public JcSongsAdapter(Context context, List<GetSongs> arraylistSongs, RecyclerViewItemClickListener listener) {
////        this.context = context;
////        this.arraylistSongs = arraylistSongs;
////        this.listener = listener;
////    }
////
////    @NonNull
////    @Override
////    public SongsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
////
////        View view = LayoutInflater.from(context).inflate(R.layout.songs_row,parent,false);
////
////
////        return new SongsAdapterViewHolder(view);
////    }
////
////    @Override
////    public void onBindViewHolder(@NonNull SongsAdapterViewHolder holder, int position) {
////
////        GetSongs getSongs = arraylistSongs.get(position);
////
////        if (getSongs != null) {
////            if (selectedPosition == position) {
////                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
////                holder.iv_play_active.setVisibility(View.VISIBLE);
////            }else {
////                holder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.transparent));
////                holder.iv_play_active.setVisibility(View.INVISIBLE);
////
////            }
////
////        }
////
////
////        holder.tv_title.setText(getSongs.getSongTitle());
////        holder.tv_artist.setText(getSongs.getArtist());
////        String duration = Utility.converDuration(Long.parseLong(getSongs.getSongDuration()));
////        holder.tv_duration.setText(duration);
////
////        holder.bind(getSongs, listener);
////
////
////    }
////
////    @Override
////    public int getItemCount() {
////
////
////        return arraylistSongs.size();
////    }
////
////    public class SongsAdapterViewHolder extends RecyclerView.ViewHolder{
////
////
////        private TextView tv_title, tv_artist, tv_duration;
////        ImageView iv_play_active;
////        public SongsAdapterViewHolder(@NonNull View itemView) {
////            super(itemView);
////
////            tv_title = itemView.findViewById(R.id.tv_title);
////            tv_artist = itemView.findViewById(R.id.tv_artist);
////            tv_duration = itemView.findViewById(R.id.tv_duration);
////            iv_play_active = itemView.findViewById(R.id.iv_play_active);
////
////
////        }
////
////        public void bind(GetSongs getSongs, RecyclerViewItemClickListener listener) {
////
////            itemView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////
////                    listener.onClickListener(getSongs,getAdapterPosition());
////                }
////            });
////        }
////    }
////    public interface RecyclerViewItemClickListener {
////
////        void onClickListener (GetSongs songs, int position);
////    }
////
////    public int getSelectedPosition() {
////        return selectedPosition;
////    }
////
////    public void setSelectedPosition(int selectedPosition) {
////        this.selectedPosition = selectedPosition;
////    }
////}
//
//
//package bhushan.org.GHRCEUSER.Song;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.core.content.ContextCompat;
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
//import bhushan.org.GHRCEUSER.R;
//
//public class JcSongsAdapter extends RecyclerView.Adapter<JcSongsAdapter.SongsAdapterViewHolder> {
//
//    private int selectedPosition = -1;
//    private final Context context;
//    private final List<GetSongs> songsList;
//    private final RecyclerViewItemClickListener listener;
//
//    public JcSongsAdapter(Context context, List<GetSongs> songsList, RecyclerViewItemClickListener listener) {
//        this.context = context;
//        this.songsList = songsList;
//        this.listener = listener;
//    }
//
//    @NonNull
//    @Override
//    public SongsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(context).inflate(R.layout.songs_row, parent, false);
//        return new SongsAdapterViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SongsAdapterViewHolder holder, int position) {
//        GetSongs song = songsList.get(position);
//        if (song != null) {
//            holder.bind(song, position, listener);
//            if (selectedPosition == position) {
//                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
//                holder.ivPlayActive.setVisibility(View.VISIBLE);
//            } else {
//                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
//                holder.ivPlayActive.setVisibility(View.INVISIBLE);
//            }
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return songsList.size();
//    }
//
//    public int getSelectedPosition() {
//        return selectedPosition;
//    }
//
//    public void setSelectedPosition(int selectedPosition) {
//        this.selectedPosition = selectedPosition;
//    }
//
//    public class SongsAdapterViewHolder extends RecyclerView.ViewHolder {
//
//        private final TextView tvTitle;
//        private final TextView tvArtist;
//        private final TextView tvDuration;
//        private final ImageView ivPlayActive;
//
//        public SongsAdapterViewHolder(@NonNull View itemView) {
//            super(itemView);
//            tvTitle = itemView.findViewById(R.id.tv_title);
//            tvArtist = itemView.findViewById(R.id.tv_artist);
//            tvDuration = itemView.findViewById(R.id.tv_duration);
//            ivPlayActive = itemView.findViewById(R.id.iv_play_active);
//        }
//
//        public void bind(GetSongs song, int position, RecyclerViewItemClickListener listener) {
//            tvTitle.setText(song.getSongTitle());
//            tvArtist.setText(song.getArtist());
//            String duration = Utility.convertDuration(Long.parseLong(song.getSongDuration()));
//            tvDuration.setText(duration);
//
//            itemView.setOnClickListener(v -> {
//                listener.onClickListener(song, position);
//                setSelectedPosition(position);
//                notifyDataSetChanged();
//            });
//        }
//    }
//
//    public interface RecyclerViewItemClickListener {
//        void onClickListener(GetSongs song, int position);
//    }
//}

// JcSongsAdapter.java

package bhushan.org.GHRCEUSER.Song;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class JcSongsAdapter extends RecyclerView.Adapter<JcSongsAdapter.SongsAdapterViewHolder> {

    private int selectedPosition = -1;
    private final Context context;
    private final List<GetSongs> songsList;
    private final RecyclerViewItemClickListener listener;
    private static final int PERMISSION_REQUEST_CODE = 100; // Example request code

    public JcSongsAdapter(Context context, List<GetSongs> songsList, RecyclerViewItemClickListener listener) {
        this.context = context;
        this.songsList = songsList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SongsAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.songs_row, parent, false);
        return new SongsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongsAdapterViewHolder holder, int position) {
        GetSongs song = songsList.get(position);
        if (song != null) {
            holder.bind(song, position, listener);
            if (selectedPosition == position) {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
                holder.ivPlayActive.setVisibility(View.VISIBLE);
            } else {
                holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                holder.ivPlayActive.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return songsList.size();
    }

    public int getSelectedPosition() {
        return selectedPosition;
    }

    public void setSelectedPosition(int selectedPosition) {
        this.selectedPosition = selectedPosition;
    }

    public class SongsAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView tvTitle;
        private final TextView tvArtist;
        private final TextView tvDuration;
        private final ImageView ivPlayActive;

        public SongsAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvArtist = itemView.findViewById(R.id.tv_artist);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            ivPlayActive = itemView.findViewById(R.id.iv_play_active);
        }

        public void bind(GetSongs song, int position, RecyclerViewItemClickListener listener) {
            tvTitle.setText(song.getSongTitle());
            tvArtist.setText(song.getArtist());
            String duration = Utility.convertDuration(Long.parseLong(song.getSongDuration()));
            tvDuration.setText(duration);

            // Example PendingIntent creation (modify as per your notification setup)
            Intent intent = new Intent(context, UserMusicApp.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(
                    context,
                    position,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE  // Use FLAG_IMMUTABLE or FLAG_MUTABLE as per your requirements
            );

            itemView.setOnClickListener(v -> {
                listener.onClickListener(song, position);
                setSelectedPosition(position);
                notifyDataSetChanged();

                // Example of using PendingIntent for notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "your_channel_id")
                        .setSmallIcon(R.drawable.icon_logomain)
                        .setContentTitle("Notification Title")
                        .setContentText("Notification Content")
                        .setContentIntent(pendingIntent)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                // Check for notification permissions
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                    // You can request the permission here if needed
                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.POST_NOTIFICATIONS}, PERMISSION_REQUEST_CODE);
                } else {
                    // Permission is granted, proceed with notification
                    notificationManager.notify(position, builder.build());
                }
            });
        }
    }

    public interface RecyclerViewItemClickListener {
        void onClickListener(GetSongs song, int position);
    }
}
