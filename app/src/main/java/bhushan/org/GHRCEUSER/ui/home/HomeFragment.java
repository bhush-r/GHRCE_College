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
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import bhushan.org.GHRCEUSER.R;

public class HomeFragment extends Fragment {

    private ImageView map;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        ArrayList<SlideModel> imageList = new ArrayList<>(); // Create image list

//        Notification
        FirebaseMessaging.getInstance().subscribeToTopic("Home");

        // Add image slides
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2FGHRCE1.jpg?alt=media&token=6367e435-559e-47ee-a6e4-7329535454c7&_gl=1*jfa0ha*_ga*MTM5NDUwNDgzMS4xNjc4MDI3ODcz*_ga_CW55HF8NVT*MTY5ODE0MDYwNC4xOS4xLjE2OTgxNDA5NzMuMjMuMC4w", ScaleTypes.CENTER_CROP));
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2FGHRCE3.png?alt=media&token=a2654834-eb9f-4b3e-b851-49c916c8f0f3&_gl=1*v1rnq5*_ga*MTM5NDUwNDgzMS4xNjc4MDI3ODcz*_ga_CW55HF8NVT*MTY5ODE0MDYwNC4xOS4xLjE2OTgxNDEwNDYuNDcuMC4w", ScaleTypes.CENTER_CROP));
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2FGHRCE4.png?alt=media&token=49f081a7-1ca8-410a-9c98-4e8788234a41&_gl=1*15s794s*_ga*MTM5NDUwNDgzMS4xNjc4MDI3ODcz*_ga_CW55HF8NVT*MTY5ODE0MDYwNC4xOS4xLjE2OTgxNDEwNzIuMjEuMC4w", ScaleTypes.CENTER_CROP));
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2FGHRCE2.png?alt=media&token=13a100b8-0006-4c09-b03f-2234d5f5905b&_gl=1*u8pt92*_ga*MTM5NDUwNDgzMS4xNjc4MDI3ODcz*_ga_CW55HF8NVT*MTY5ODE0MDYwNC4xOS4xLjE2OTgxNDExMzAuNjAuMC4w", ScaleTypes.CENTER_CROP));
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2Fsunset-3689760.jpg?alt=media&token=eb77c4de-8343-41be-ac36-2cbfb297417b", ScaleTypes.CENTER_CROP));
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-gpg-app.appspot.com/o/SliderImages%2Fgpgphoto2.jpg?alt=media&token=6d5adc67-a19c-4784-b90f-ea0db30ba1d0", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Fclg2.jpg?alt=media&token=12272b75-916a-4e65-a592-2f968eddf1db", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech1.jpg?alt=media&token=81193a52-4e53-4abc-9c15-e205e08f8b21", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech2.jpg?alt=media&token=4449441c-328e-4b30-939e-27bfc87270d5", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech3.jpg?alt=media&token=0ef58d37-d4ec-4e97-bb46-26eefd35946b", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Ftech4.jpg?alt=media&token=a559f168-6ccf-4873-92e8-24e27ad7a41f", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Fclg1.jpg?alt=media&token=765f90d6-7027-4349-b43a-e7af138fd257", ScaleTypes.CENTER_CROP));
//        imageList.add(new SlideModel("https://firebasestorage.googleapis.com/v0/b/my-ghrce-app.appspot.com/o/Headline%2Fclg2.jpg?alt=media&token=12272b75-916a-4e65-a592-2f968eddf1db", ScaleTypes.CENTER_CROP));

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

        return view;
    }

    private void openMap() {
        Uri uri = Uri.parse("geo:0, 0?q=G. H. Raisoni College of Engineering");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        intent.setPackage("com.google.android.apps.maps");
        startActivity(intent);

    }
}



