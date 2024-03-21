package bhushan.org.GHRCEUSER;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class Share extends AppCompatActivity {

    private ImageView imageViewQRCode;
    private String link = "https://drive.google.com/drive/folders/1mJE4Pr_Dvonxg6wOUeWJ4efv4uTW2PEv?usp=sharing\n";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
        FirebaseMessaging.getInstance().subscribeToTopic("Share");
        imageViewQRCode = findViewById(R.id.imageViewQRCode);
        ImageView imageViewShare = findViewById(R.id.imageViewShare);

        // Generate QR code when activity is created
        generateQRCode(link);

        // Set click listener on the share image view
        imageViewShare.setOnClickListener(view -> shareQRCodeLink());
    }

    private void generateQRCode(String link) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(link, BarcodeFormat.QR_CODE, 400, 400);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? getResources().getColor(android.R.color.black) : getResources().getColor(android.R.color.white));
                }
            }
            imageViewQRCode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            Toast.makeText(this, "Failed to generate QR code", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareQRCodeLink() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, link);

        startActivity(Intent.createChooser(shareIntent, "Share link via"));
    }
}
