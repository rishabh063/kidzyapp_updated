package com.kidzyfit.kidzyapp;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.xwray.groupie.Group;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class menu extends game_basic {
    Button start_button;
    Activity act;
    TextView  fruit_name;
    Button continoue;
    RelativeLayout shop_background;
    ImageView place_background;
    float scale;
    private boolean showingBack=false;
    AnimatorSet front_anim;
    AnimatorSet back_anim;
    private boolean isFront=true;
    private final ArrayList<fruits_ifno> colorList = (new ArrayList());
    private final GroupAdapter adapter = new GroupAdapter();
    public RecyclerView rv;
    int progress;
    int result;
    private float back_pressed=0;
    ImageView fruit_gif;
    ImageView back;
    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        act=this;
        back=findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intenti = new Intent(menu.this, Map.class);
                startActivity(intenti);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
        shop_background=findViewById(R.id.shop_background);
        place_background=findViewById(R.id.place_background);
        start_button=findViewById(R.id.new_fruit_unlocked_button);
        fruit_name=findViewById(R.id.fruit_name);
        fruit_gif=findViewById(R.id.new_fruit_gif);
        front_anim = (AnimatorSet) AnimatorInflater.loadAnimator(this,R.anim.flip_out);
        back_anim =(AnimatorSet) AnimatorInflater.loadAnimator(this,R.anim.flip_in);
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        Intent intent = getIntent();
        result = intent.getIntExtra("level",1);
        int result2=intent.getIntExtra("num2",0);
         progress=1;
        Log.i("logg", "onCreate: "+result);
        continoue=findViewById(R.id.new_fruit_unlocked_button);
           if (result2==1){

              continoue.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
                       temp_1.setVisibility(View.INVISIBLE);
                   }
               });
           }
        colorList.add(new fruits_ifno(R.drawable.apple,"APPLE",""));
        colorList.add(new fruits_ifno(R.drawable.banana,"BANANA",""));
        colorList.add(new fruits_ifno(R.drawable.orange,"ORANGE",""));
        if (result==1) {
             shop_background.setBackgroundResource(R.drawable.fruit_shop);
             place_background.setImageResource(R.drawable.village_background);
            SharedPreferences prefs = getSharedPreferences("level1", MODE_PRIVATE);
           progress = prefs.getInt("myInt", 1); // 0 is default
            colorList.add(new fruits_ifno(R.drawable.grapefruit,"GRAPEFRUIT",""));
            colorList.add(new fruits_ifno(R.drawable.grapes,"GRAPES",""));
            colorList.add(new fruits_ifno(R.drawable.pomegranate,"POMEGRANATE",""));
            colorList.add(new fruits_ifno(R.drawable.coconut,"COCONUT",""));

            if (result2==1)
            {
                RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
                temp_1.setVisibility(View.VISIBLE);
                // temp_1.setBackgroundResource(R.drawable.new_fruit_1);
                ImageView temp=findViewById(R.id.new_fruit_photo);
                Glide.with(this).asGif().load(R.raw.city1_new_card).into(fruit_gif);
                ImageView temp_card=findViewById(R.id.card_color);
                ImageView temp_card_1=findViewById(R.id.card_color_1);
                temp_card.setImageResource(R.drawable.green_card);
                temp_card_1.setImageResource(R.drawable.green_card);
                Bitmap temp_fruit = null;
                if (progress==2)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.banana);
                     fruit_name.setText("Banana");
                }
                if (progress==3)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.orange);
                     fruit_name.setText("Orange");
                }
                if (progress==4)
                {temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.grapefruit);
                 fruit_name.setText("Grapefruit");
                }
                else if(progress==5)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.grapes);
                     fruit_name.setText("Grapes");
                }
                else if (progress==6)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.pomegranate);
                     fruit_name.setText("Pomegranate");
                }
                else if (progress==7)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.coconut);
                     fruit_name.setText("Coconut");
                }
                temp.setImageBitmap(temp_fruit);
            }
            else if (result2==2){

            }
        }
        else if (result==2) {
            shop_background.setBackgroundResource(R.drawable.shop_2);
            place_background.setImageResource(R.drawable.city_background);
            SharedPreferences prefs = getSharedPreferences("level2", MODE_PRIVATE);
            progress = prefs.getInt("myInt", 1); // 0 is default
            colorList.add(new fruits_ifno(R.drawable.cherries,"CHERRIES",""));
            colorList.add(new fruits_ifno(R.drawable.mango,"MANGO",""));
            colorList.add(new fruits_ifno(R.drawable.strawberries,"STRAWBERRIES",""));
            colorList.add(new fruits_ifno(R.drawable.blackberries,"BLUEBERRIES",""));
            if (result2==1)
            { RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
                temp_1.setVisibility(View.VISIBLE);
                // temp_1.setBackgroundResource(R.drawable.new_fruit_2);
                ImageView temp=findViewById(R.id.new_fruit_photo);
                Glide.with(this).asGif().load(R.raw.city2_new_card).into(fruit_gif);
                Bitmap temp_fruit = null;
                ImageView temp_card=findViewById(R.id.card_color);
                ImageView temp_card_1=findViewById(R.id.card_color_1);
                temp_card.setImageResource(R.drawable.red_card);
                temp_card_1.setImageResource(R.drawable.red_card);
                if (progress==2)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.banana);
                    fruit_name.setText("Banana");
                }
                if (progress==3)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.orange);
                    fruit_name.setText("Orange");
                }
                if (progress==4)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.cherries);
                    fruit_name.setText("Cherries");
                }
                else if(progress==5)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.mango);
                    fruit_name.setText("Mango");
                }
                else if (progress==6)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.strawberries);
                    fruit_name.setText("Strawberries");
                }
                else if (progress==7)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.blueberries);
                    fruit_name.setText("Blueberries");
                }
                temp.setImageBitmap(temp_fruit);
            }
            else if (result2==2){

            }
        }
        else if (result==3) {
            shop_background.setBackgroundResource(R.drawable.shop_3);
            place_background.setImageResource(R.drawable.beach_background);
            SharedPreferences prefs = getSharedPreferences("level3", MODE_PRIVATE);
            progress = prefs.getInt("myInt", 1); // 0 is default
            colorList.add(new fruits_ifno(R.drawable.blackberries,"BLACKBERRIES",""));
            colorList.add(new fruits_ifno(R.drawable.blueberries,"BLUEBERRIES",""));
            colorList.add(new fruits_ifno(R.drawable.watermelon,"WATERMELON",""));
            colorList.add(new fruits_ifno(R.drawable.pear,"PEAR",""));
            if (result2==1)
            { RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
                temp_1.setVisibility(View.VISIBLE);
                // temp_1.setBackgroundResource(R.drawable.new_fruit_3);
                ImageView temp=findViewById(R.id.new_fruit_photo);
                Glide.with(this).asGif().load(R.raw.city3_new_card).into(fruit_gif);
                Bitmap temp_fruit = null;
                ImageView temp_card=findViewById(R.id.card_color);
                ImageView temp_card_1=findViewById(R.id.card_color_1);
                temp_card.setImageResource(R.drawable.purple_card);
                temp_card_1.setImageResource(R.drawable.purple_card);
                if (progress==2)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.banana);
                    fruit_name.setText("Banana");
                }
                if (progress==3)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.orange);
                    fruit_name.setText("Orange");
                }
                if (progress==4)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.blackberries);
                    fruit_name.setText("Blackberries");
                }
                else if(progress==5)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.blueberries);
                    fruit_name.setText("Blueberries");
                }
                else if (progress==6)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.watermelon);
                    fruit_name.setText("watermelon");
                }
                else if (progress==7)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.pear);
                    fruit_name.setText("Pear");
                }
                temp.setImageBitmap(temp_fruit);
            }
            else if (result2==2){

            }
        }
        else if (result==4) {
            shop_background.setBackgroundResource(R.drawable.shop_4);
            place_background.setImageResource(R.drawable.amusement_background);
            SharedPreferences prefs = getSharedPreferences("level4", MODE_PRIVATE);
            progress = prefs.getInt("myInt", 1); // 0 is default
            colorList.add(new fruits_ifno(R.drawable.papaya,"PAPAYA",""));
            colorList.add(new fruits_ifno(R.drawable.guava,"GUAVA",""));
            colorList.add(new fruits_ifno(R.drawable.kiwi,"KIWI",""));
            colorList.add(new fruits_ifno(R.drawable.melon,"MELON",""));
            if (result2==1)
            {RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
                temp_1.setVisibility(View.VISIBLE);
                // temp_1.setBackgroundResource(R.drawable.new_fruit_4);
                ImageView temp=findViewById(R.id.new_fruit_photo);
                Bitmap temp_fruit = null;
                Glide.with(this).asGif().load(R.raw.city4_new_card).into(fruit_gif);
                ImageView temp_card=findViewById(R.id.card_color);
                ImageView temp_card_1=findViewById(R.id.card_color_1);
                temp_card.setImageResource(R.drawable.blue_card);
                temp_card_1.setImageResource(R.drawable.blue_card);
                if (progress==2)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.banana);
                    fruit_name.setText("Banana");
                }
                if (progress==3)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.orange);
                    fruit_name.setText("Orange");
                }
                if (progress==4)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.papaya);
                    fruit_name.setText("Papaya");
                }
                else if(progress==5)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.guava);
                    fruit_name.setText("Guava");
                }
                else if (progress==6)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.kiwi);
                    fruit_name.setText("Kiwi");
                }
                else if (progress==7)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.melon);
                    fruit_name.setText("Melon");
                }
                temp.setImageBitmap(temp_fruit);
            }
            else if (result2==2){

            }
        }
        else if (result==5) {
            shop_background.setBackgroundResource(R.drawable.shop_5);
            place_background.setImageResource(R.drawable.hotel_background);
            SharedPreferences prefs = getSharedPreferences("level5", MODE_PRIVATE);progress = prefs.getInt("myInt", 1); // 0 is default
            colorList.add(new fruits_ifno(R.drawable.lemon,"LEMON",""));
            colorList.add(new fruits_ifno(R.drawable.raspberries,"RASPBERRIES",""));
            colorList.add(new fruits_ifno(R.drawable.plum,"PLUM",""));
            colorList.add(new fruits_ifno(R.drawable.cranbery,"CRANBERRY",""));
            if (result2==1)
            {   RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
                temp_1.setVisibility(View.VISIBLE);
                // temp_1.setBackgroundResource(R.drawable.new_fruit_5);
                ImageView temp=findViewById(R.id.new_fruit_photo);
                Bitmap temp_fruit = null;
                Glide.with(this).asGif().load(R.raw.city5_new_card).into(fruit_gif);
                ImageView temp_card=findViewById(R.id.card_color);
                ImageView temp_card_1=findViewById(R.id.card_color_1);
                temp_card.setImageResource(R.drawable.yellow_card);
                temp_card_1.setImageResource(R.drawable.yellow_card);
                if (progress==2)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.banana);
                    fruit_name.setText("Banana");
                }
                if (progress==3)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.orange);
                    fruit_name.setText("Orange");
                }
                if (progress==4)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.lemon);
                    fruit_name.setText("Lemon");
                }
                else if(progress==5)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.raspberries);
                    fruit_name.setText("Raspberries");
                }
                else if (progress==6)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.plum);
                    fruit_name.setText("Plum");
                }
                else if (progress==7)
                {
                    temp_fruit=BitmapFactory.decodeResource(getResources(),R.drawable.cranbery);
                    fruit_name.setText("Cranberry");
                }
                temp.setImageBitmap(temp_fruit);
            }
            else if (result2==2){

            }
        }
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        scale = getResources().getDisplayMetrics().density;
        if(result2==1)
        start_button.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           flipCard();
                                           RelativeLayout temp_1=findViewById(R.id.new_fruit_unlocked);
//                                           temp_1.setVisibility(View.INVISIBLE);
                                       }
                                   }
            );
        rv = this.findViewById(R.id.rvOffer);
        this.setUpRecyclerView();
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
    private final void setUpRecyclerView() {
        int i = 0;
        int var2 = this.colorList.size() - 1;
        if (i <= var2) {
            while(true) {
                this.adapter.add((Group)(new InnerClass(this.colorList.get(i).image,this.colorList.get(i).Name,this.colorList.get(i).info_line)));
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
        BottomOffsetDecoration bottomOffsetDecoration = new BottomOffsetDecoration((int) offsetPx);
        rv.addItemDecoration(bottomOffsetDecoration);
    }
    public final class InnerClass extends Item {
        @NotNull
        private int imgList;
        String Name;
        String info_line;
        public int getLayout() {
            return R.layout.menu_slider;
        }

        public void bind(@NotNull ViewHolder viewHolder, int position) {
            ImageView temp = viewHolder.itemView.findViewById(R.id.ivProductImg);
            temp.setImageResource(imgList);
            CardView card=viewHolder.itemView.findViewById(R.id.cardImg);
            TextView name=viewHolder.itemView.findViewById(R.id.ivProduct_Name);
                card.getLayoutParams().width=width/3;
                card.getLayoutParams().height=width/3;
                name.setText(Name);
                name.setTextSize(30);
                RelativeLayout.LayoutParams layoutParams=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.height=width/5;
                layoutParams.width=width/5;
                layoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
                temp.setLayoutParams(layoutParams);
                PorterDuffColorFilter greyFilter = new PorterDuffColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);

                if(progress<position+1){
                    card.getBackground().setColorFilter(greyFilter);
                    name.setTextColor(0xff777777);
                    temp.setColorFilter(greyFilter);
                }
                else {
                    Intent intent = new Intent(getApplicationContext(), gameplay_0.class);
                    card.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            intent.putExtra("num", result);
                            intent.putExtra("num2", position + 1);
                            startActivity(intent);
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        }
                    });
                }
        }
        public InnerClass( int imgList,String name,String description) {
            super();
            this.imgList = imgList;
            this.Name=name;
            this.info_line=description;
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
    private void flipCard() {
        Log.i("check", "flipCard: here");
        RelativeLayout front = findViewById(R.id.newfruit_background_front);
        RelativeLayout back =findViewById(R.id.newfruit_background);
        front.setCameraDistance( 8000 * scale);
        back.setCameraDistance( 8000 * scale);
        Button temp_button=findViewById(R.id.new_fruit_unlocked_button);
        temp_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Animation aniFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out);
//                aniFade.setDuration(1200);
//                new_area.setAnimation(aniFade);
//                new_area.setVisibility(View.INVISIBLE);
            }
        });
        if(isFront)
        {
            front_anim.setTarget(front);
            back_anim.setTarget(back);
            front_anim.start();
            back_anim.start();
            isFront = false;
            back_anim.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    RelativeLayout new_area=findViewById(R.id.new_fruit_unlocked);
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

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
        else
        {
            front_anim.setTarget(back);
            back_anim.setTarget(front);
            back_anim.start();
            front_anim.start();
            isFront =true;

        }
    }
    class fruits_ifno{
        int image;
        String Name;
        String info_line;

        public fruits_ifno(int image, String name, String info_line) {
            this.image = image;
            Name = name;
            this.info_line = info_line;
        }
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
        final Dialog dialog = new Dialog(menu.this);

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