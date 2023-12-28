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
        list.add(new BranchModel(R.drawable.ic_computer,"Computer Science","Computer science encompasses theoretical foundations of information and computation, along with practical techniques for implementation. Computer Engineers devise algorithms, create abstractions, and model complex systems. The field includes sub-disciplines like computational complexity theory, Programing Language."));
        list.add(new BranchModel(R.drawable.ic_it,"Information Technology","Information Technology (IT) is the branch of engineering dealing with the use of computers and telecommunications, developing and maintaining systems as well as retrieving, storing and transmitting information. The branch is responsible for the efficient operation of IT services and infrastructure."));
        list.add(new BranchModel(R.drawable.ic_iot,"DS,IoT, Cyber security (DIC)","To develop professionals who are skilled in the area of Data Science, Internet of Things, and Cyber security. To impart quality and value based education and contribute towards the innovation of Data Science computing, IoT system design and Cyber security to raise satisfaction level of all stakeholders."));
        list.add(new BranchModel(R.drawable.ic_ai,"Artificial Intelligence","AI undergrad program imparts advanced machine learning knowledge, enabling app creation. Emphasis on problem-solving through computer programming, with applications like predicting behavior and optimizing robots. Curriculum unifies machine learning across fields for a comprehensive approach."));
        list.add(new BranchModel(R.drawable.ic_ee,"Electrical Engineering","Electrical engineering is a field of engineering that generally deals with the study and application of electricity, electronics and electromagnetism. It now covers a range of subtopics including power, electronics, control systems, signal processing. The main aim is to ensure the safe use of electricity."));
        list.add(new BranchModel(R.drawable.ic_etce,"Electronics Engineering","Electronics Engineer's career path can lead to a broad range of Electronics Jobs in different specialties in various industries. Electronics engineering prepare a student to design, build, research and test electronic circuits and components used in hi-tech devices so, prominent in today's society."));
        list.add(new BranchModel(R.drawable.ic_et,"Electronics & Telec Engineering","Telecommunications Engineering or Telecom Engineering is a major field within Electronic Engineering. The work ranges from basic circuit design to strategic mass developments. A Telecommunication Engineer is responsible for designing and overseeing the installation of telecommunications."));
        list.add(new BranchModel(R.drawable.ic_mech,"Mechanical Engineering","Mechanical engineering is a discipline of engineering that applies the principles of physics and materials science for analysis, design, manufacturing and maintenance of mechanical systems. It involves the production and usage of heat and mechanical power for the design, operation of machines and tools."));
        list.add(new BranchModel(R.drawable.ic_civil,"Civil Engineering","Civil Engineering is one of the major basic branch in engineering. The work ranges from basic concepts related to engineering to the overall developments of the nation. A Civil Engineer responsible for Planning, Analysis, Designing and Execution of the all construction related activities."));
        list.add(new BranchModel(R.drawable.ic_mba,"Management Studies (MBA)","We have an excellent track record in offering high quality education, and students from our institutes get the pick of corporate and management jobs directly from campus. We give special thrust on modern state of the art Education methodologies so that we can bring the best of the knowledge close to the doorstep of our students."));

        adapter = new BranchAdapter(getContext(),list);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setAdapter(adapter);

        ImageView imageView = view.findViewById(R.id.college_image);

        Glide.with(getContext())
                .load("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2FaboutimageView.png?alt=media&token=25b5421d-fdb7-482b-bb10-5c1bbe7ea6a1")
                .into(imageView);


        return view;
    }
}