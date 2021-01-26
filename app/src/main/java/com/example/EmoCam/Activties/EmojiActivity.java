package com.example.EmoCam.Activties;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.EmoCam.Adapters.EmojiAdapter;
import com.example.EmoCam.R;
import com.example.EmoCam.sticker.BitmapStickerIcon;
import com.example.EmoCam.sticker.DeleteIconEvent;
import com.example.EmoCam.sticker.FlipHorizontallyEvent;
import com.example.EmoCam.sticker.HelloIconEvent;
import com.example.EmoCam.sticker.Sticker;
import com.example.EmoCam.sticker.StickerView;
import com.example.EmoCam.sticker.TextSticker;
import com.example.EmoCam.sticker.ZoomIconEvent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EmojiActivity extends AppCompatActivity implements View.OnClickListener {

    String photoPath;
    ImageView img;
    private ImageView forward_btn;
    RecyclerView emojiView;
    private List<Drawable> bitmaps;
    private List<String> filternames;
    private StickerView stickerView;
    private File filteredfile;
    private ImageView back_btn;
    private ImageView reset_btn;
    private ImageView resetAll_btn;
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emoji);

        photoPath = getIntent().getStringExtra("filepath");
        img = findViewById(R.id.img);
        forward_btn = findViewById(R.id.forward_btn);
        back_btn = findViewById(R.id.back_btn);
        reset_btn = findViewById(R.id.reset_btn);
        resetAll_btn = findViewById(R.id.resetAll_btn);
        stickerView = findViewById(R.id.sticker_view);
        emojiView = findViewById(R.id.emoji_list);
        img.setImageURI(Uri.parse(photoPath));


        filteredfile = new File(Environment.getExternalStorageDirectory().getPath(), "MyFolder/Images"+ "/Filtered" + System.currentTimeMillis() + ".jpg");
        if (!filteredfile.exists()) {
            try {
                filteredfile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        forward_btn.setOnClickListener(this);
        back_btn.setOnClickListener(this);
        reset_btn.setOnClickListener(this);
        resetAll_btn.setOnClickListener(this);

        bitmaps = new ArrayList<>();
        bitmaps.add(getResources().getDrawable(R.drawable.love));
        bitmaps.add(getResources().getDrawable(R.drawable.sad));
        bitmaps.add(getResources().getDrawable(R.drawable.tongue));
        bitmaps.add(getResources().getDrawable(R.drawable.sorry));
        bitmaps.add(getResources().getDrawable(R.drawable.smile));
        bitmaps.add(getResources().getDrawable(R.drawable.laugh));
        filternames = new ArrayList<>();
        filternames.add("Love");
        filternames.add("Sad");
        filternames.add("Tease");
        filternames.add("Sorry");
        filternames.add("Smile");
        filternames.add("laugh");

        EmojiAdapter emojiAdapter = new EmojiAdapter(bitmaps, filternames, EmojiActivity.this, stickerView);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        emojiView.setHasFixedSize(true);
        emojiView.setLayoutManager(linearLayoutManager);
        emojiView.setAdapter(emojiAdapter);

        setemojifeature();
    }

    private void setemojifeature() {
        BitmapStickerIcon deleteIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_close_white_18dp),
                BitmapStickerIcon.LEFT_TOP);
        deleteIcon.setIconEvent(new DeleteIconEvent());

        BitmapStickerIcon zoomIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_scale_white_18dp),
                BitmapStickerIcon.RIGHT_BOTOM);
        zoomIcon.setIconEvent(new ZoomIconEvent());

        BitmapStickerIcon flipIcon = new BitmapStickerIcon(ContextCompat.getDrawable(this,
                R.drawable.sticker_ic_flip_white_18dp),
                BitmapStickerIcon.RIGHT_TOP);
        flipIcon.setIconEvent(new FlipHorizontallyEvent());

        BitmapStickerIcon heartIcon =
                new BitmapStickerIcon(ContextCompat.getDrawable(this, R.drawable.sticker_ic_close_white_18dp),
                        BitmapStickerIcon.LEFT_BOTTOM);
        heartIcon.setIconEvent(new HelloIconEvent());

        stickerView.setIcons(Arrays.asList(deleteIcon, zoomIcon, flipIcon,heartIcon));

        stickerView.setLocked(false);
        stickerView.setConstrained(true);

        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerAdded");
            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                //stickerView.removeAllSticker();
                Log.d(TAG, "onStickerClicked");
            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDeleted");
            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerDragFinished");
            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerTouchedDown");
            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerZoomFinished");
            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {
                Log.d(TAG, "onStickerFlipped");
            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                Log.d(TAG, "onDoubleTapped: double tap will be with two click");
            }
        });

    }



    public void testLock(View view) {
        stickerView.setLocked(!stickerView.isLocked());
    }

    public void testRemove(View view) {
        if (stickerView.removeCurrentSticker()) {
            Toast.makeText(EmojiActivity.this, "Remove current Sticker successfully!", Toast.LENGTH_SHORT)
                    .show();
        } else {
            Toast.makeText(EmojiActivity.this, "Remove current Sticker failed!", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void reset(View view) {
        stickerView.removeAllStickers();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.forward_btn:
                Toast.makeText(EmojiActivity.this, "Image is Saved", Toast.LENGTH_SHORT).show();
                stickerView.save(filteredfile);
                Intent intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
                break;
            case R.id.back_btn:
                finish();
                break;
            case R.id.reset_btn:
                //reset(v);
                testRemove(v);
                break;
            case R.id.resetAll_btn:
                reset(v);
                break;
        }
    }
}
