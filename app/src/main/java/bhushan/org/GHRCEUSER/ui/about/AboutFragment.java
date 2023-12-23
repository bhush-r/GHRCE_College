package bhushan.org.GHRCEUSER.ui.about;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class AboutFragment extends Fragment {

    private ViewPager viewPager;
    private BranchAdapter adapter;
    private List<BranchModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_about, container, false);
        list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_computer,"Computer Science","Computer Science & Engineering department aims at producing technically competent professionals who can excel in today's age of technology and fulfill demands of trained IT professionals in the industry. The curriculum is designed in such a manner that the students are able to sail through the intense competition smoothly."));
        list.add(new BranchModel(R.drawable.ic_mech,"Mechanical Engineering","Welcome to GPG Mechanical Engineering Department. Our focus on core subjects and experienced faculty drives academic excellence. Industry-experienced guest faculties provide practical insights. We go beyond academics, emphasizing leadership, management, internships, entrepreneurship, and student activities to prepare students for impactful future roles."));
        list.add(new BranchModel(R.drawable.ic_it,"Information Technology","Information Technology is the study, design, development, application, implementation, support or management of computer-based information systems. In the era where there is demand for trained IT professionals this department provides the students with a good infrastructure and highly qualified faculty to produce skilled professionals."));
        list.add(new BranchModel(R.drawable.ic_civil,"Civil Engineering","Department of Electronics & Communication is one of the most sought after department of SSGPURC and boasts of excellent infrastructure. The students are motivated to take up challenging projects and enhance their practical skills in electronics. The department is well equipped in all aspects and has the following labs."));
        list.add(new BranchModel(R.drawable.ic_ee,"Electrical Engineering","Information Technology is the study, design, development, application, implementation, support or management of computer-based information systems. In the era where there is demand for trained IT professionals this department provides the students with a good infrastructure and highly qualified faculty to produce skilled professionals."));
        list.add(new BranchModel(R.drawable.ic_etce,"Electronics & Tele Comm Engineering","Department of Electronics & Communication is one of the most sought after department of SSGPURC and boasts of excellent infrastructure. The students are motivated to take up challenging projects and enhance their practical skills in electronics. The department is well equipped in all aspects and has the following labs."));

        adapter = new BranchAdapter(getContext(),list);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.college_image);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2Fgpgphoto2.jpg?alt=media&token=6d5adc67-a19c-4784-b90f-ea0db30ba1d0")
                .into(imageView);


        return view;
    }
}