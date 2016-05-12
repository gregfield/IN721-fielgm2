package bit.fielgm2.accessingcameraapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    File photoFile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.photoBtn);
        button.setOnClickListener(new StartCameraOnClick());
    }

    public void createImageFile()
    {
        File imageRootPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File imageStorageDIR = new File(imageRootPath, "CameraDemo1");
        if (!imageStorageDIR.exists())
            imageStorageDIR.mkdirs();

        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMDD_HHmmss");
        Date currentTime = new Date();
        String timeStamp = timeStampFormat.format(currentTime);

        String photoFileName = "IMG_" + timeStamp + ".jpg";

        photoFile = new File(imageStorageDIR.getPath() + File.separator + photoFileName);
    }

    public void openCamera()
    {
        createImageFile();

        Uri photoFileURI = Uri.fromFile(photoFile);

        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFileURI);
        
        startActivityForResult(imageCaptureIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                String realFilePath = photoFile.getPath();

                Bitmap bitmap = BitmapFactory.decodeFile(realFilePath);

                ImageView image1 = (ImageView) findViewById(R.id.imageView);
                ImageView image2 = (ImageView) findViewById(R.id.imageView2);
                ImageView image3 = (ImageView) findViewById(R.id.imageView3);
                ImageView image4 = (ImageView) findViewById(R.id.imageView4);

                image1.setImageBitmap(bitmap);
                image2.setImageBitmap(bitmap);
                image3.setImageBitmap(bitmap);
                image4.setImageBitmap(bitmap);
            }
        }
    }

    public class StartCameraOnClick implements View.OnClickListener
    {
        @Override
        public void onClick(View v) {
            openCamera();
        }
    }
}
