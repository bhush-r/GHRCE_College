package bhushan.org.GHRCEUSER.paper;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class PaperActivity extends AppCompatActivity {

    private RecyclerView paperRecycler;
    private DatabaseReference reference;
    private List<PaperData> list;
    private PaperAdapter adapter;

    private LinearLayout shimmerLayout;
    private ShimmerFrameLayout shimmerFrameLayout;
    private EditText search;
    private Spinner branchSpinner, semesterSpinner;

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
        branchSpinner = findViewById(R.id.branchSpinner);
        semesterSpinner = findViewById(R.id.semesterSpinner);

        setupSpinners();
        getData();
    }

    private void setupSpinners() {
        // Setup branch spinner
        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(this,
                R.array.branches_array, android.R.layout.simple_spinner_item);
        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);

        // Setup semester spinner
        ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(this,
                R.array.semesters_array, android.R.layout.simple_spinner_item);
        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);

        // Set spinner listeners
        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                applyFilters();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void getData() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    PaperData data = snapshot.getValue(PaperData.class);
                    list.add(data);
                }

                adapter = new PaperAdapter(PaperActivity.this, list);
                paperRecycler.setLayoutManager(new LinearLayoutManager(PaperActivity.this));
                paperRecycler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerLayout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(PaperActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void applyFilters() {
        if (list == null || list.isEmpty()) {
            return; // Ensure that list is not null or empty before applying filters
        }

        String selectedBranch = branchSpinner.getSelectedItem().toString();
        String selectedSemester = semesterSpinner.getSelectedItem().toString();

        if (selectedBranch.equalsIgnoreCase("All") && selectedSemester.equalsIgnoreCase("All")) {
            adapter.Filteredlist(new ArrayList<>(list)); // Show all papers
        } else {
            List<PaperData> filteredList = new ArrayList<>();
            for (PaperData item : list) {
                if (item.getBranch() != null && item.getSemester() != null) {
                    if ((selectedBranch.equalsIgnoreCase("All") || item.getBranch().equalsIgnoreCase(selectedBranch)) &&
                            (selectedSemester.equalsIgnoreCase("All") || item.getSemester().equalsIgnoreCase(selectedSemester))) {
                        filteredList.add(item);
                    }
                }
            }
            adapter.Filteredlist(new ArrayList<>(filteredList));
        }
    }

    private void filter(String text) {
        List<PaperData> filteredList = new ArrayList<>();
        for (PaperData item : list) {
            if (item.getPaperTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        adapter.Filteredlist(new ArrayList<>(filteredList));
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
