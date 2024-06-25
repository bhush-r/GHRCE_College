//package bhushan.org.GHRCEUSER.Song;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ProgressBar;
//import android.widget.Toast;
//
//import com.example.jean.jcplayer.model.JcAudio;
//import com.example.jean.jcplayer.view.JcPlayerView;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import bhushan.org.GHRCEUSER.R;
//
//public class SongsActivity extends AppCompatActivity {
//
//    RecyclerView recyclerView;
//    ProgressBar progressBar;
//    Boolean checkin = false;
//    List<GetSongs> mupload;
//    JcSongsAdapter adapter;
//    DatabaseReference databaseReference;
//    ValueEventListener valueEventListener;
//    JcPlayerView jcPlayerView;
//    ArrayList<JcAudio> jcAudios = new ArrayList<>();
//    private int currentIndex;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_songs);
//
//        recyclerView = findViewById(R.id.recyclerview);
//        progressBar = findViewById(R.id.progressbarshowsong);
//        jcPlayerView = findViewById(R.id.jcplayer);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mupload = new ArrayList<>();
//        recyclerView.setAdapter(adapter);
//        adapter = new JcSongsAdapter(getApplicationContext(), mupload, new JcSongsAdapter.RecyclerViewItemClickListener() {
//            @Override
//            public void onClickListener(GetSongs songs, int position) {
//
//                changeSelectedSong(position);
//
//                jcPlayerView.playAudio(jcAudios.get(position));
//                jcPlayerView.setVisibility(View.VISIBLE);
//                jcPlayerView.createNotification();
//            }
//        });
//
//        databaseReference= FirebaseDatabase.getInstance().getReference("songs");
//        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                mupload.clear();
//                for (DataSnapshot dss: dataSnapshot.getChildren()){
//                    GetSongs getSongs = dss.getValue(GetSongs.class);
//                    getSongs.setmKey(dss.getKey());
//                    currentIndex = 0;
//                    final String s = getIntent().getExtras().getString("songsCategory");
//                    if (s.equals(getSongs.getSongsCategory())) {
//                        mupload.add(getSongs);
//                        checkin = true;
//                        jcAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(),getSongs.getSongLink()));
//
//                    }
//                }
//                adapter.setSelectedPosition(0);
//                recyclerView.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//                progressBar.setVisibility(View.GONE);
//
//                if (checkin) {
//                    jcPlayerView.initPlaylist(jcAudios,null);
//
//                }else {
//                    Toast.makeText(SongsActivity.this, "There is no songs", Toast.LENGTH_SHORT).show();
//                }
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                progressBar.setVisibility(View.GONE);
//            }
//        });
//
//    }
//    public void changeSelectedSong(int index){
//        adapter.notifyItemChanged(adapter.getSelectedPosition());
//        currentIndex = index;
//        adapter.setSelectedPosition(currentIndex);
//        adapter.notifyItemChanged(currentIndex);
//    }
//}


package bhushan.org.GHRCEUSER.Song;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class SongsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProgressBar progressBar;
    Boolean checkin = false;
    List<GetSongs> mupload;
    JcSongsAdapter adapter;
    DatabaseReference databaseReference;
    ValueEventListener valueEventListener;
    JcPlayerView jcPlayerView;
    ArrayList<JcAudio> jcAudios = new ArrayList<>();
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbarshowsong);
        jcPlayerView = findViewById(R.id.jcplayer);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mupload = new ArrayList<>();

        adapter = new JcSongsAdapter(this, mupload, new JcSongsAdapter.RecyclerViewItemClickListener() {
            @Override
            public void onClickListener(GetSongs songs, int position) {
                changeSelectedSong(position);
                jcPlayerView.playAudio(jcAudios.get(position));
                jcPlayerView.setVisibility(View.VISIBLE);
                jcPlayerView.createNotification();
            }
        });

        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("songs");
        valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mupload.clear();
                jcAudios.clear(); // Clear previous jcAudios to avoid duplication
                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    GetSongs getSongs = dss.getValue(GetSongs.class);
                    if (getSongs != null) {
                        getSongs.setmKey(dss.getKey());
                        final String songsCategory = getIntent().getStringExtra("songsCategory");
                        if (songsCategory != null && songsCategory.equals(getSongs.getSongsCategory())) {
                            mupload.add(getSongs);
                            checkin = true;
                            jcAudios.add(JcAudio.createFromURL(getSongs.getSongTitle(), getSongs.getSongLink()));
                        }
                    }
                }
                adapter.setSelectedPosition(0);
                adapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);

                if (checkin) {
                    jcPlayerView.initPlaylist(jcAudios, null);
                } else {
                    Toast.makeText(SongsActivity.this, "There are no songs", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void changeSelectedSong(int index) {
        adapter.notifyItemChanged(adapter.getSelectedPosition());
        currentIndex = index;
        adapter.setSelectedPosition(currentIndex);
        adapter.notifyItemChanged(currentIndex);
    }
}
