package com.kidzyfit.kidzyapp;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.xwray.groupie.Group;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class Map extends game_basic {
    RelativeLayout level1;
    RelativeLayout level2;
    RelativeLayout level3;
    RelativeLayout level4;
    RelativeLayout level5;
    ImageView level2_image;
    ImageView level3_image;
    ImageView level4_image;
    ImageView level5_image;
    private final ArrayList<Integer> colorList = (new ArrayList());
    private final GroupAdapter adapter = new GroupAdapter();
    public RecyclerView rv;
    int level=1;
    private float back_pressed=0;
    ImageView back;
    private FirebaseAnalytics firebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("level1", MODE_PRIVATE);
        setContentView(R.layout.activity_map);
        back=findViewById(R.id.back_button);
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        Bundle bundle = new Bundle();
         firebaseAnalytics = FirebaseAnalytics.getInstance(this);
 firebaseAnalytics = FirebaseAnalytics.getInstance(this);
firebaseAnalytics.logEvent("map", bundle);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(Map.this, sample_video_images.class);
                intenti.putExtra("select",2);
                startActivity(intenti);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        int progress = prefs.getInt("myInt", 3); // 0 is default
        if (progress>=8)
            level=2;
        prefs = getSharedPreferences("level2", MODE_PRIVATE);
        progress = prefs.getInt("myInt", 3); // 0 is default
        if (progress>=8)
            level=3;
        prefs = getSharedPreferences("level3", MODE_PRIVATE);
        progress = prefs.getInt("myInt", 3); // 0 is default
        if (progress>=8)
            level=4;
        prefs = getSharedPreferences("level4", MODE_PRIVATE);
        progress = prefs.getInt("myInt", 3); // 0 is default
        if (progress>=8)
            level=5;
        rv = this.findViewById(R.id.rvOffer);
        this.dummyStrings();
        this.setUpRecyclerView();
        Intent intent = getIntent();
        int result2=intent.getIntExtra("new_area",0);
        int result3=intent.getIntExtra("val",0);
        if (result2==1){
          RelativeLayout new_area=findViewById(R.id.new_area_unlocked);
          ImageView temp_image=findViewById(R.id.new_area_unlocked_image);
          if (result3==1)
          Glide.with(getApplicationContext()).asGif().load(R.raw.city1_gif).into(temp_image);
          else if (result3==2)
          Glide.with(getApplicationContext()).asGif().load(R.raw.city2_gif).into(temp_image);
          else if (result3==3)
          Glide.with(getApplicationContext()).asGif().load(R.raw.city3_gif).into(temp_image);
          else if (result3==4)
              Glide.with(getApplicationContext()).asGif().load(R.raw.city4_gif).into(temp_image);
          else if (result3==5)
              Glide.with(getApplicationContext()).asGif().load(R.raw.city5_gif).into(temp_image);
          new_area.setVisibility(View.VISIBLE);
          Button temp_button=findViewById(R.id.new_area_unlocked_button);
          temp_button.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
                  aniFade.setDuration(1200);
                  new_area.setAnimation(aniFade);
                  new_area.setVisibility(View.INVISIBLE);
              }
          });
        }
    }
    public final List getColorList() {
        return this.colorList;
    }
    public final GroupAdapter getAdapter() {
        return this.adapter;
    }
    public final RecyclerView getRv() {
        return rv;
    }
    public final void setRv(@NotNull RecyclerView var1) {
        this.rv = var1;
    }
    private final void dummyStrings() {
        this.colorList.add(R.drawable.village_icon);
        this.colorList.add(R.drawable.city_icon);
        this.colorList.add(R.drawable.beach_icon);
        this.colorList.add(R.drawable.amusement_icon);
        this.colorList.add(R.drawable.hotel_icon);
    }
    private final void setUpRecyclerView() {
        int i = 0;
        int var2 = this.colorList.size() - 1;
        if (i <= var2) {
            while(true) {
                this.adapter.add((Group)(new InnerClass(this.colorList.get(i))));
                if (i == var2) {
                    break;
                }

                ++i;
            }
        }

        rv.setLayoutManager((RecyclerView.LayoutManager)(new CenterZoomLayoutManager((Context)this, 0, false)));
        rv.animate();
        rv.setAdapter((RecyclerView.Adapter)this.adapter);
        RecyclerView.Adapter var3 = rv.getAdapter();
        var3.notifyDataSetChanged();
        rv.scheduleLayoutAnimation();
        rv.invalidate();
        float offsetPx = width/3;
        menu.BottomOffsetDecoration bottomOffsetDecoration = new menu.BottomOffsetDecoration((int) offsetPx);
        rv.addItemDecoration(bottomOffsetDecoration);
    }

    public final class InnerClass extends Item {
        @NotNull
        private int imgList;

        public int getLayout() {
            return R.layout.map_slider;
        }

        public void bind(@NotNull ViewHolder viewHolder, int position) {
                ImageView temp = viewHolder.itemView.findViewById(R.id.ivProductImg);
                temp.setImageResource(imgList);
                temp.getLayoutParams().height = width / 3;
                temp.getLayoutParams().width = width / 3;
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.height=width/3;
                layoutParams.width=width/3;
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                temp.setLayoutParams(layoutParams);
                ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
                if (position+1 > level) {
                    temp.setColorFilter(filter);
                }
                Intent intent = new Intent(getApplicationContext(), menu.class);
                temp.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (position+1  <= level) {
                                                    intent.putExtra("level", position+1);
                                                    startActivity(intent);
                                                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                                                }
                                            }
                                        }
                );
        }


        public InnerClass( int imgList) {
            super();
            this.imgList = imgList;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        rv.post(new Runnable() {
            @Override
            public void run() {
                rv.smoothScrollBy(20,0);
            }
        });

    }
    static class BottomOffsetDecoration extends RecyclerView.ItemDecoration {
        private int mBottomOffset;

        public BottomOffsetDecoration(int bottomOffset) {
            mBottomOffset = bottomOffset;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int dataSize = state.getItemCount();
            int position = parent.getChildAdapterPosition(view);
            if (dataSize > 0 && position == dataSize - 1) {
                outRect.set(0, 0, mBottomOffset,0 );
            }
            else if (dataSize > 0 && position == 0) {
                outRect.set(mBottomOffset, 0, 0,0 );
            }
            else {
                outRect.set(0, 0, 0, 0);
            }

        }
    }
    boolean doubleBackToExitPressedOnce = false;
    private Toast toast = null;
    public void onBackPressed() {
        final Dialog dialog = new Dialog(Map.this);

        // setting content view to dialog
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setContentView(R.layout.custom_dialogue);

        // getting reference of TextView
        TextView dialogButtonYes = (TextView) dialog.findViewById(R.id.textViewYes);
        TextView dialogButtonNo = (TextView) dialog.findViewById(R.id.textViewNo);

        // click listener for No
        dialogButtonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog
                dialog.dismiss();

            }
        });

        // click listener for Yes
        dialogButtonYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // dismiss the dialog and exit the exit
                dialog.dismiss();
                finishAffinity();

            }
        });

        // show the exit dialog
        dialog.show();
    }
}