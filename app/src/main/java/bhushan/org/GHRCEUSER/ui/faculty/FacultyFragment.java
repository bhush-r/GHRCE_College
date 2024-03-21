package bhushan.org.GHRCEUSER.ui.faculty;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class FacultyFragment extends Fragment {

    private RecyclerView csDepartment, mechanicalDepartment, informationDepartment, civilDepartment,electricalDepartment,electronicDepartment,electDepartment,cyberDepartment,csaiDepartment,aimlDepartment,iotDepartment,dsDepartment,aiDepartment;
    private LinearLayout csNoData,mechNoData,informationNoData,civilNoData,electricalNoData,electronicNoData,electNoData,cyberNoData,csaiNoData,aimlNoData,iotNoData,dsNoData,aiNoData;
    private List<TeacherData> list1, list2, list3, list4, list5, list6,list7,list8,list9,list10,list11,list12,list13;
    private TeacherAdapter adapter;
    private ProgressBar progressBar1,progressBar2,progressBar3,progressBar4,progressBar5,progressBar6,progressBar7,progressBar8,progressBar9,progressBar10,progressBar11,progressBar12,progressBar13;
    private DatabaseReference reference,dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        csDepartment = view.findViewById(R.id.csDepartment);
        mechanicalDepartment = view.findViewById(R.id.mechanicalDepartment);
        informationDepartment = view.findViewById(R.id.informationDepartment);
        civilDepartment = view.findViewById(R.id.civilDepartment);
        electricalDepartment = view.findViewById(R.id.electricalDepartment);
        electronicDepartment = view.findViewById(R.id.electronicDepartment);
        ////////////////
        electDepartment = view.findViewById(R.id.electDepartment);
        cyberDepartment = view.findViewById(R.id.cyberDepartment);
        csaiDepartment = view.findViewById(R.id.csaiDepartment);
        aimlDepartment = view.findViewById(R.id.aimlDepartment);
        iotDepartment = view.findViewById(R.id.iotDepartment);
        dsDepartment = view.findViewById(R.id.dsDepartment);
        aiDepartment = view.findViewById(R.id.aiDepartment);

        electronicNoData = view.findViewById(R.id.electronicNoData);
        electricalNoData = view.findViewById(R.id.electricalNoData);
        civilNoData = view.findViewById(R.id.civilNoData);
        informationNoData = view.findViewById(R.id.informationNoData);
        mechNoData = view.findViewById(R.id.mechNoData);
        csNoData = view.findViewById(R.id.csNoData);
        ///////////////
        electNoData = view.findViewById(R.id.electNoData);
        cyberNoData = view.findViewById(R.id.cyberNoData);
        csaiNoData = view.findViewById(R.id.csaiNoData);
        aimlNoData = view.findViewById(R.id.aimlNoData);
        iotNoData = view.findViewById(R.id.iotNoData);
        dsNoData = view.findViewById(R.id.dsNoData);
        aiNoData = view.findViewById(R.id.aiNoData);

        progressBar1 = view.findViewById(R.id.progressBar1);
        progressBar2 = view.findViewById(R.id.progressBar2);
        progressBar3 = view.findViewById(R.id.progressBar3);
        progressBar4 = view.findViewById(R.id.progressBar4);
        progressBar5 = view.findViewById(R.id.progressBar5);
        progressBar6 = view.findViewById(R.id.progressBar6);
        progressBar7 = view.findViewById(R.id.progressBar7);
        progressBar8 = view.findViewById(R.id.progressBar8);
        progressBar9 = view.findViewById(R.id.progressBar9);
        progressBar10 = view.findViewById(R.id.progressBar10);
        progressBar11 = view.findViewById(R.id.progressBar11);
        progressBar12 = view.findViewById(R.id.progressBar12);
        progressBar13 = view.findViewById(R.id.progressBar13);


        reference= FirebaseDatabase.getInstance().getReference().child("teacher");

        csDepartment();
        mechanicalDepartment();
        informationDepartment();
        civilDepartment();
        electricalDepartment();
        electronicDepartment();
        ///////
        electDepartment();
        cyberDepartment();
        csaiDepartment();
        aimlDepartment();
        iotDepartment();
        dsDepartment();
        aiDepartment();

        return view;
    }

    private void csDepartment() {
        dbRef = reference.child("Computer Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    csNoData.setVisibility(View.VISIBLE);
                    csDepartment.setVisibility(View.GONE);
                }else {
                    csNoData.setVisibility(View.GONE);
                    csDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    csDepartment.setHasFixedSize(true);
                    csDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list1,getContext());
                    progressBar1.setVisibility(View.GONE);
                    csDepartment.setAdapter(adapter);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar1.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }



    private void mechanicalDepartment() {
        dbRef = reference.child("Mechanical Eng");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    mechNoData.setVisibility(View.VISIBLE);
                    mechanicalDepartment.setVisibility(View.GONE);
                }else {
                    mechNoData.setVisibility(View.GONE);
                    mechanicalDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    mechanicalDepartment.setHasFixedSize(true);
                    mechanicalDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list2,getContext());
                    mechanicalDepartment.setAdapter(adapter);
                    progressBar2.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar2.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void informationDepartment() {
        dbRef = reference.child("Information Technology");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    informationNoData.setVisibility(View.VISIBLE);
                    informationDepartment.setVisibility(View.GONE);
                }else {
                    informationNoData.setVisibility(View.GONE);
                    informationDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    informationDepartment.setHasFixedSize(true);
                    informationDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list3,getContext());
                    informationDepartment.setAdapter(adapter);
                    progressBar3.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar3.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void civilDepartment() {
        dbRef = reference.child("Civil Eng");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    civilNoData.setVisibility(View.VISIBLE);
                    civilDepartment.setVisibility(View.GONE);
                }else {
                    civilNoData.setVisibility(View.GONE);
                    civilDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list4.add(data);
                    }
                    civilDepartment.setHasFixedSize(true);
                    civilDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list4,getContext());
                    civilDepartment.setAdapter(adapter);
                    progressBar4.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar4.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void electricalDepartment() {
        dbRef = reference.child("Electrical Eng");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list5 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    electricalNoData.setVisibility(View.VISIBLE);
                    electricalDepartment.setVisibility(View.GONE);
                }else {
                    electricalNoData.setVisibility(View.GONE);
                    electricalDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list5.add(data);
                    }
                    electricalDepartment.setHasFixedSize(true);
                    electricalDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list5,getContext());
                    electricalDepartment.setAdapter(adapter);
                    progressBar5.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar5.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void electronicDepartment() {
        dbRef = reference.child("Electronic Tele Eng");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list6 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    electronicNoData.setVisibility(View.VISIBLE);
                    electronicDepartment.setVisibility(View.GONE);
                }else {
                    electronicNoData.setVisibility(View.GONE);
                    electronicDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list6.add(data);
                    }
                    electronicDepartment.setHasFixedSize(true);
                    electronicDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list6,getContext());
                    electronicDepartment.setAdapter(adapter);
                    progressBar6.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar6.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void electDepartment() {
        dbRef = reference.child("Electronics Engineering");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list7 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    electNoData.setVisibility(View.VISIBLE);
                    electDepartment.setVisibility(View.GONE);
                }else {
                    electNoData.setVisibility(View.GONE);
                    electDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list7.add(data);
                    }
                    electDepartment.setHasFixedSize(true);
                    electDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list7,getContext());
                    electDepartment.setAdapter(adapter);
                    progressBar7.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar7.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void cyberDepartment() {
        dbRef = reference.child("Computer Science (Cyber Security)");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list8 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    cyberNoData.setVisibility(View.VISIBLE);
                    cyberDepartment.setVisibility(View.GONE);
                }else {
                    cyberNoData.setVisibility(View.GONE);
                    electDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list8.add(data);
                    }
                    cyberDepartment.setHasFixedSize(true);
                    cyberDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list8,getContext());
                    cyberDepartment.setAdapter(adapter);
                    progressBar8.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar8.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void csaiDepartment() {
        dbRef = reference.child("Computer Science (AI)");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list9 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    csaiNoData.setVisibility(View.VISIBLE);
                    csaiDepartment.setVisibility(View.GONE);
                }else {
                    csaiNoData.setVisibility(View.GONE);
                    csaiDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list9.add(data);
                    }
                    csaiDepartment.setHasFixedSize(true);
                    csaiDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list9,getContext());
                    csaiDepartment.setAdapter(adapter);
                    progressBar9.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar9.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void aimlDepartment() {
        dbRef = reference.child("Computer Science (AIML)");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list10 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    aimlNoData.setVisibility(View.VISIBLE);
                    aimlDepartment.setVisibility(View.GONE);
                }else {
                    aimlNoData.setVisibility(View.GONE);
                    aimlDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list10.add(data);
                    }
                    aimlDepartment.setHasFixedSize(true);
                    aimlDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list10,getContext());
                    aimlDepartment.setAdapter(adapter);
                    progressBar10.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar10.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void iotDepartment() {
        dbRef = reference.child("Computer Science (IoT)");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list11 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    iotNoData.setVisibility(View.VISIBLE);
                    iotDepartment.setVisibility(View.GONE);
                }else {
                    iotNoData.setVisibility(View.GONE);
                    iotDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list11.add(data);
                    }
                    iotDepartment.setHasFixedSize(true);
                    iotDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list11,getContext());
                    iotDepartment.setAdapter(adapter);
                    progressBar11.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar11.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void dsDepartment() {
        dbRef = reference.child("Data Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list12 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    dsNoData.setVisibility(View.VISIBLE);
                    dsDepartment.setVisibility(View.GONE);
                }else {
                    dsNoData.setVisibility(View.GONE);
                    dsDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list12.add(data);
                    }
                    dsDepartment.setHasFixedSize(true);
                    dsDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list12,getContext());
                    dsDepartment.setAdapter(adapter);
                    progressBar12.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar12.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void aiDepartment() {
        dbRef = reference.child("Artificial Intelligence");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list13 = new ArrayList<>();
                if(!dataSnapshot.exists()){
                    aiNoData.setVisibility(View.VISIBLE);
                    aiDepartment.setVisibility(View.GONE);
                }else {
                    aiNoData.setVisibility(View.GONE);
                    aiDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list13.add(data);
                    }
                    aiDepartment.setHasFixedSize(true);
                    aiDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list13,getContext());
                    aiDepartment.setAdapter(adapter);
                    progressBar13.setVisibility(View.GONE);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar13.setVisibility(View.GONE);
                Toast.makeText(getContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

}