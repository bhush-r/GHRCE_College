package bhushan.org.GHRCEUSER.menuitems.News;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import bhushan.org.GHRCEUSER.R;

import android.view.MenuItem;
import android.view.View;
        import android.widget.LinearLayout;
        import androidx.annotation.NonNull;
        import androidx.recyclerview.widget.LinearLayoutManager;
        import androidx.recyclerview.widget.RecyclerView;

        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.database.ValueEventListener;

        import java.util.ArrayList;
import java.util.List;

public class news extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayout noNewsLayout;
    private NewsAdapter newsAdapter;
    private List<Newss> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Announcement");

        recyclerView = findViewById(R.id.recycler_view);
        noNewsLayout = findViewById(R.id.no_news);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsList = new ArrayList<>();
        newsAdapter = new NewsAdapter(this, newsList);
        recyclerView.setAdapter(newsAdapter);

        fetchNews();
    }

    private void fetchNews() {
        FirebaseDatabase.getInstance().getReference("news")
                .orderByChild("timestamp")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        newsList.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Newss news = dataSnapshot.getValue(Newss.class);
                            if (news != null) {
                                newsList.add(news);
                            }
                        }
                        newsAdapter.notifyDataSetChanged();
                        if (newsList.isEmpty()) {
                            recyclerView.setVisibility(View.GONE);
                            noNewsLayout.setVisibility(View.VISIBLE);
                        } else {
                            recyclerView.setVisibility(View.VISIBLE);
                            noNewsLayout.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        // Handle error
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}



//package bhushan.org.GHRCEUSER.menuitems.News;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.os.Build;
//import android.os.Bundle;
//
//import bhushan.org.GHRCEUSER.R;
//
//import android.view.View;
//import android.widget.LinearLayout;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class news extends AppCompatActivity {
//
//    private RecyclerView recyclerView;
//    private LinearLayout noNewsLayout;
//    private NewsAdapter newsAdapter;
//    private List<Newss> newsList;
//
//    private static final String CHANNEL_ID = "news_channel";
//    private static final String CHANNEL_NAME = "News Notifications";
//    private static final String CHANNEL_DESC = "Notifications for news updates";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_news);
//
//        createNotificationChannel();
//
//        recyclerView = findViewById(R.id.recycler_view);
//        noNewsLayout = findViewById(R.id.no_news);
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        newsList = new ArrayList<>();
//        newsAdapter = new NewsAdapter(this, newsList);
//        recyclerView.setAdapter(newsAdapter);
//
//        fetchNews();
//    }
//
//    private void fetchNews() {
//        FirebaseDatabase.getInstance().getReference("news")
//                .orderByChild("timestamp")
//                .addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        newsList.clear();
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            Newss news = dataSnapshot.getValue(Newss.class);
//                            if (news != null) {
//                                newsList.add(news);
//                            }
//                        }
//                        newsAdapter.notifyDataSetChanged();
//                        if (newsList.isEmpty()) {
//                            recyclerView.setVisibility(View.GONE);
//                            noNewsLayout.setVisibility(View.VISIBLE);
//                        } else {
//                            recyclerView.setVisibility(View.VISIBLE);
//                            noNewsLayout.setVisibility(View.GONE);
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//                        // Handle error
//                    }
//                });
//    }
//
//    private void createNotificationChannel() {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel(
//                    CHANNEL_ID,
//                    CHANNEL_NAME,
//                    NotificationManager.IMPORTANCE_DEFAULT
//            );
//            channel.setDescription(CHANNEL_DESC);
//
//            NotificationManager notificationManager = getSystemService(NotificationManager.class);
//            notificationManager.createNotificationChannel(channel);
//        }
//    }
//}

