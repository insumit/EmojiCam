package com.example.EmoCam.Activties;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.EmoCam.R;

import static java.lang.System.*;

public class MainActivity extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;

    private Uri cameraImagePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button2);
        Button btn2 = findViewById(R.id.button);

        btn1.setOnClickListener(v -> {
            // TODO Auto-generated method stub
            finish();
            exit(0);
            System.exit(0);
        });

        btn2.setOnClickListener(v -> takeNewPicture());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (cameraImagePath != null) {
                    Cursor imageCursor = getContentResolver().query(
                            cameraImagePath, filePathColumn, null, null, null);
                    if (imageCursor != null && imageCursor.moveToFirst()) {
                        int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
                        String filePath = imageCursor.getString(columnIndex);
                        imageCursor.close();

                        cameraImagePath = filePath != null ? Uri
                                .parse(filePath) : null;


                        //------- next Activity-----//
                        Intent intent = new Intent(this, ImageEditorActivity.class);
                        intent.putExtra("filepath",filePath);
                        startActivity(intent);

                        Log.d("INFO", "onActivityResult: " + cameraImagePath);
                    }
                }
            }
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void takeNewPicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ContentValues values = new ContentValues(3);

        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        cameraImagePath = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImagePath);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

}
