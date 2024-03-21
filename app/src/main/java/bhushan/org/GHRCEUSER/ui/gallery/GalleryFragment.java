package bhushan.org.GHRCEUSER.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;
import java.util.List;
import bhushan.org.GHRCEUSER.R;
public class GalleryFragment extends Fragment {
    RecyclerView convocationRecycler,independenceRecycler, annualRecycler,otherRecycler,workshopsRecycler, festRecycler, campusRecycler, sportsRecycler, placementsRecycler;
    GalleryAdapter adapter;
    DatabaseReference reference;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

//sportsRecycler = view.findViewById(R.id.sportsRecycler);
        FirebaseMessaging.getInstance().subscribeToTopic("Gallery");

        convocationRecycler = view.findViewById(R.id.convocationRecycler);
        annualRecycler = view.findViewById(R.id.annualRecycler);
        otherRecycler = view.findViewById(R.id.otherRecycler);
        festRecycler = view.findViewById(R.id.festRecycler);
        campusRecycler = view.findViewById(R.id.campusRecycler);
        workshopsRecycler = view.findViewById(R.id.workshopsRecycler);
        independenceRecycler = view.findViewById(R.id.independenceRecycler);
        placementsRecycler = view.findViewById(R.id.placementsRecycler);

        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

//        getSportsImage();
        getConvocationImage();
        getCampusImage();
        getFestImage();
        getIndependenceImage();
        getOtherImage();
        getAnnualImage();
        getWorkshopsImage();
        getPlacementsImage();
        return view;
    }
    private void getConvocationImage() {
        reference.child("Convocation").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(),imageList);
                convocationRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                convocationRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getCampusImage() {
        reference.child("Campus").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                campusRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                campusRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void getIndependenceImage() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                independenceRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                independenceRecycler.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getFestImage() {
        reference.child("Fests").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(),imageList);
                festRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                festRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
    private void getOtherImage() {
        reference.child("Other Event").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                otherRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                otherRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getAnnualImage() {
        reference.child("Annual").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                annualRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                annualRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void getWorkshopsImage() {
        reference.child("Workshops").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                workshopsRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                workshopsRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getPlacementsImage() {
        reference.child("Placements").addValueEventListener(new ValueEventListener() {
            List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }
                adapter = new GalleryAdapter(getContext(),imageList);
                placementsRecycler.setLayoutManager(new GridLayoutManager(getContext(),3));
                placementsRecycler.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}