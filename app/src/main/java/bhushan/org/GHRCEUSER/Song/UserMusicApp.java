package bhushan.org.GHRCEUSER.Song;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class UserMusicApp extends AppCompatActivity {

    RecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    DatabaseReference mDatabase;
    ProgressDialog progressDialog;
    private List<Upload> uploads;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_music_app);

        recyclerView = findViewById(R.id.recyclerview_id);
        recyclerView.setLayoutManager(new GridLayoutManager(this,3));
        progressDialog = new ProgressDialog(this);
        uploads = new ArrayList<>();
        progressDialog.setMessage("please wait....");
        progressDialog.show();
        mDatabase = FirebaseDatabase.getInstance().getReference("uploads");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                progressDialog.dismiss();
                for (DataSnapshot  postsnapshot: datasnapshot.getChildren()) {
                    Upload upload = postsnapshot.getValue(Upload.class);
                    uploads.add(upload);

                }

                adapter = new RecyclerViewAdapter(getApplicationContext(),uploads);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();

            }
        });
    }
}