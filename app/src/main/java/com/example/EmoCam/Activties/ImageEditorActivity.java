package com.example.EmoCam.Activties;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.EmoCam.Adapters.ImageFiltersAdapter;
import com.example.EmoCam.R;
import com.example.EmoCam.helper.CompressImage;
import com.mukesh.image_processing.ImageProcessor;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageEditorActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img;
    private RecyclerView filters_list;
    private File originalImage;
    private String originalImagepath;
    private String filepath;
    private CompressImage compressImage;
    private List<Bitmap> bitmaps;
    private List<String> filternames;
    String currentPhotoPath;
    private File filteredfile;
    private String filteredfilepath;
    private ImageView forward_btn;
    private ImageView back_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_editor);

        forward_btn = findViewById(R.id.forward_btn);
        back_btn = findViewById(R.id.back_btn);
        originalImagepath = getIntent().getStringExtra("filepath");
        initView();

        forward_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
    }

    private void getimage() {
        Bitmap bitmap = ((BitmapDrawable) img.getDrawable()).getBitmap();
        try {
            bitmaptoimage(bitmap);
            Intent intent = new Intent(ImageEditorActivity.this, EmojiActivity.class);
            intent.putExtra("filepath", filteredfilepath);
            startActivity(intent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bitmaptoimage(Bitmap bitmap1) throws IOException {
        File file = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images");
        if (!file.exists()) {
            file.mkdirs();
        }

        filteredfile = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images" + "/" + System.currentTimeMillis() + ".jpg");
        if (!filteredfile.exists()) {
            filteredfile.createNewFile();
        }

        filteredfilepath = filteredfile.getAbsolutePath();
        Bitmap bitmap = bitmap1;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = new FileOutputStream(filteredfile);
        fos.write(bitmapdata);
        fos.flush();
        fos.close();
    }

    private void initView() {
        img = findViewById(R.id.img);
        filters_list = findViewById(R.id.filters_list);

        compressImage = new CompressImage();
        bitmaps = new ArrayList<>();
        filternames = new ArrayList<>();
        setImage();

        ImageFiltersAdapter imageFiltersAdapter = new ImageFiltersAdapter(bitmaps, filternames, img, originalImagepath, ImageEditorActivity.this);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        filters_list.setHasFixedSize(true);
        filters_list.setLayoutManager(linearLayoutManager);
        filters_list.setAdapter(imageFiltersAdapter);

    }

    private void setImage() {
        ImageProcessor imageProcessor = new ImageProcessor();
        originalImage = new File(originalImagepath);
        filepath = compressImage.getCompressImage(ImageEditorActivity.this, originalImagepath);
        Bitmap bitmap = BitmapFactory.decodeFile(filepath);
        img.setImageBitmap(bitmap);

        //---------------filter Images in bitmap-------------------//
        bitmaps.add(imageProcessor.doGreyScale(bitmap));
        bitmaps.add(imageProcessor.doInvert(bitmap));
        bitmaps.add(imageProcessor.doGamma(bitmap, 3, 2, 2));
        bitmaps.add(imageProcessor.doColorFilter(bitmap, 1, 1, 1));
        bitmaps.add(imageProcessor.doHighlightImage(bitmap, 1, 10));
        bitmaps.add(imageProcessor.doBrightness(bitmap, 20));
        bitmaps.add(imageProcessor.engrave(bitmap));
        bitmaps.add(imageProcessor.applyFleaEffect(bitmap));
        bitmaps.add(imageProcessor.applyGaussianBlur(bitmap));
        bitmaps.add(imageProcessor.applyHueFilter(bitmap, 1));
        bitmaps.add(imageProcessor.applyMeanRemoval(bitmap));
        bitmaps.add(imageProcessor.applyReflection(bitmap));
        bitmaps.add(imageProcessor.applySaturationFilter(bitmap, 1));
        bitmaps.add(imageProcessor.applySnowEffect(bitmap));
        bitmaps.add(imageProcessor.createContrast(bitmap, 1));
        bitmaps.add(imageProcessor.emboss(bitmap));
        bitmaps.add(imageProcessor.roundCorner(bitmap, 20));
        bitmaps.add(imageProcessor.sharpen(bitmap, 20));
        bitmaps.add(imageProcessor.smooth(bitmap, 50));

        filternames.add("Greyscale");
        filternames.add("Invert");
        filternames.add("Rose");
        filternames.add("Gamma");
        filternames.add("Highlight");
        filternames.add("Sakura");
        filternames.add("Engrave");
        filternames.add("Flea");
        filternames.add("Blur");
        filternames.add("Hue");
        filternames.add("Mean");
        filternames.add("Reflection");
        filternames.add("Saturation");
        filternames.add("Snow");
        filternames.add("Contrast");
        filternames.add("Emboss");
        filternames.add("Round");
        filternames.add("Sharpen");
        filternames.add("Smooth");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forward_btn:
                getimage();
                break;
            case R.id.back_btn:
                finish();
                break;
        }
    }
}
