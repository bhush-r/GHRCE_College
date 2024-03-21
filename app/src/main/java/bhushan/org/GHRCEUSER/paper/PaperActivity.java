package bhushan.org.GHRCEUSER.paper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;

import bhushan.org.GHRCEUSER.R;
import bhushan.org.GHRCEUSER.ebook.EbookData;

public class PaperActivity extends AppCompatActivity {

    private RecyclerView paperRecycler;
    private DatabaseReference reference;
    private List<PaperData> list;
    private PaperAdapter adapter;

    LinearLayout shimmerLayout;

    ShimmerFrameLayout shimmerFrameLayout;
    EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        FirebaseMessaging.getInstance().subscribeToTopic("Paper");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Previous Paper");

        paperRecycler = findViewById(R.id.paperRecycler);
        reference = FirebaseDatabase.getInstance().getReference().child("paper");

        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        shimmerLayout = findViewById(R.id.shimmer_layout);
        search = findViewById(R.id.searchText1);
        getData();

    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    PaperData data = snapshot.getValue(PaperData.class);
                    list.add(data);
                }

                adapter = new PaperAdapter(PaperActivity.this,list);
                paperRecycler.setLayoutManager(new LinearLayoutManager(PaperActivity.this));
                paperRecycler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaperActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());

            }
        });
    }

    private void filter(String text) {
        ArrayList<PaperData> filterlist = new ArrayList<>();
        for (PaperData item : list){
            //getName -> getPDFTitle used mein ne declare yahi kiya ek baar admin or user mein check karna
            if (item.getPaperTitle().toLowerCase().contains(text.toLowerCase())){
                filterlist.add(item);

            }
        }

        adapter.Filteredlist(filterlist);
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }
}