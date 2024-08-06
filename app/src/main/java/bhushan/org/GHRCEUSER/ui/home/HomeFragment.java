//package bhushan.org.gpgcollege.ui.home;
//
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//
//import androidx.fragment.app.Fragment;
//
//import com.smarteist.autoimageslider.DefaultSliderView;
//import com.smarteist.autoimageslider.IndicatorAnimations;
//import com.smarteist.autoimageslider.SliderAnimations;
//import com.smarteist.autoimageslider.SliderLayout;
//
//import bhushan.org.gpgcollege.R;
//
//public class HomeFragment extends Fragment {
//
//    private SliderLayout sliderLayout;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        sliderLayout = view.findViewById(R.id.slider);
//        sliderLayout.setIndicatorAnimation(IndicatorAnimations.FILL);
//        sliderLayout.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
//        sliderLayout.setScrollTimeInSec(1);
//
//        setSliderView();
//
//        return view;
//    }
//
//    private void setSliderView() {
//        for (int i = 0; i < 5; i++) {
//            DefaultSliderView sliderView = new DefaultSliderView(requireContext());
//
//            switch (i) {
//                case 0:
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/gallery%2F%5BB%403b44e32jpg?alt=media&token=8b7850fe-0fa0-4e77-9b64-403184b8bc92");
//                    break;
//                case 1:
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2Fgpgphoto2.jpg?alt=media&token=6d5adc67-a19c-4784-b90f-ea0db30ba1d0");
//                    break;
//                case 2:
//                    sliderView.setImageUrl("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2Fsunset-3689760.jpg?alt=media&token=eb77c4de-8343-41be-ac36-2cbfb297417b");
//                    break;
//                case 3:
//                    break;
//            }
//
//            // You can set the image scale type like this:
//            sliderView.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            sliderLayout.addSliderView(sliderView);
//        }
//    }
//}

package bhushan.org.GHRCEUSER.ui.home;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;

import bhushan.org.GHRCEUSER.R;

public class HomeFragment extends Fragment {

    private ImageView map;
    private TextView marqueeTextView;
    private DatabaseReference databaseReference;
    private List<SpanInfo> spanInfoList;
    private Handler handler;
    private int currentIndex = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("marqueeText");

        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

        // Add image slides
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Fclg2.jpg?alt=media&token=12272b75-916a-4e65-a592-2f968eddf1db", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech1.jpg?alt=media&token=81193a52-4e53-4abc-9c15-e205e08f8b21", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech2.jpg?alt=media&token=4449441c-328e-4b30-939e-27bfc87270d5", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech3.jpg?alt=media&token=0ef58d37-d4ec-4e97-bb46-26eefd35946b", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech4.jpg?alt=media&token=a559f168-6ccf-4873-92e8-24e27ad7a41f", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Fclg1.jpg?alt=media&token=765f90d6-7027-4349-b43a-e7af138fd257", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = view.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

        // MapLink
        map = view.findViewById(R.id.map);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMap();
            }
        });

        // Initialize Marquee TextView
        marqueeTextView = view.findViewById(R.id.marquee_text);
        setupMarquee();

        // Initialize the Handler
        handler = new Handler();

        // Fetch and update marquee text from Firebase
        fetchMarqueeText();

        return view;
    }

    private void setupMarquee() {
        if (marqueeTextView != null) {
            marqueeTextView.setSelected(true); // Ensure the marquee starts
            marqueeTextView.setMovementMethod(LinkMovementMethod.getInstance()); // Make links clickable
        }
    }

    private void fetchMarqueeText() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                spanInfoList = new ArrayList<>();

                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                    String text = childSnapshot.child("text").getValue(String.class);
                    String link = childSnapshot.child("link").getValue(String.class);

                    if (text != null && link != null) {
                        spanInfoList.add(new SpanInfo(text, link));
                    }
                }

                startMarquee();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle possible errors.
            }
        });
    }

    private void startMarquee() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (spanInfoList != null && !spanInfoList.isEmpty()) {
                    SpanInfo spanInfo = spanInfoList.get(currentIndex);

                    SpannableString spannableString = new SpannableString(spanInfo.text);
                    ClickableSpan clickableSpan = new ClickableSpan() {
                        @Override
                        public void onClick(@NonNull View widget) {
                            openLink(spanInfo.link);
                        }

                        @Override
                        public void updateDrawState(@NonNull android.text.TextPaint ds) {
                            ds.setUnderlineText(false);
                            ds.setColor(Color.RED); // Set the text color to black or any color you want
                        }
                    };

                    spannableString.setSpan(clickableSpan, 0, spanInfo.text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    marqueeTextView.setText(spannableString);

                    currentIndex = (currentIndex + 1) % spanInfoList.size();
                    marqueeTextView.post(new Runnable() {
                        @Override
                        public void run() {
                            marqueeTextView.setSelected(true);
                        }
                    });

                    // Schedule the next update
                    handler.postDelayed(this, getMarqueeDuration(spanInfo.text));
                }
            }
        }, 0);
    }

    private long getMarqueeDuration(String text) {
        // Estimate duration based on text length
        // You can fine-tune this to match your marquee speed
        return text.length() * 200; // Example: 200ms per character
    }

    private void openLink(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=G. H. Raisoni College of Engineering");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);
    }

    private static class SpanInfo {
        String text;
        String link;

        SpanInfo(String text, String link) {
            this.text = text;
            this.link = link;
        }
    }
}






