package com.kidzyfit.kidzyapp;

import android.app.Activity;
import android.util.Log;

import java.util.ArrayList;
import java.util.Random;

public class videoplayer {
    int score=0;
    poscalculator calculator;
    video currentvideo;
    Activity context;
    int random;
    ArrayList<video> all_video=new ArrayList<>();
    public videoplayer(poscalculator calculator,int character) {
        this.calculator=calculator;
        all_video=new ArrayList<>();
        if (character==1){
           all_video.add(creator(String.valueOf(R.raw.astrasquat),"squat",15)); // working
            all_video.add(creator(String.valueOf(R.raw.astraarmstretching),"arm",15));// working
//            all_video.add(creator(String.valueOf(R.raw.astrapistol),"pistol",15));// working
            all_video.add(creator(String.valueOf(R.raw.astrastablesword),"sword",15));// working
//            all_video.add(creator(String.valueOf(R.raw.astracapoeria),"capoeria",15));//pending
            all_video.add(creator(String.valueOf(R.raw.astracorssjumprotation),"jump",15));//pending
            all_video.add(creator(String.valueOf(R.raw.astracrossjump1),"jump",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrafrontraises),"front",15));//working
            all_video.add(creator(String.valueOf(R.raw.astraharvesting),"harvest",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrahook),"hook",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrajazz),"jazz",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrajog),"jog",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrajump),"jump",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrajumpingjack),"jjack",15));//working
            all_video.add(creator(String.valueOf(R.raw.astralegsweep),"sweep",15));//working
            all_video.add(creator(String.valueOf(R.raw.astrasidejumps),"sidejump",15));//working
            all_video.add(creator(String.valueOf(R.raw.astravictory),"victory",15));//working
        }
        else if(character==2)
        {
            all_video.add(creator(String.valueOf(R.raw.doozy_arm),"arm",15));
            all_video.add(creator(String.valueOf(R.raw.doozysword),"sword",15));
//            all_video.add(creator(String.valueOf(R.raw.doozycapoeria),"capoeria",15));
            all_video.add(creator(String.valueOf(R.raw.doozycrossjumps),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.doozycrossroation),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.doozyfrontraises),"front",15));
            all_video.add(creator(String.valueOf(R.raw.doozyharvesting),"harvest",15));
            all_video.add(creator(String.valueOf(R.raw.doozyhook),"hook",15));
            all_video.add(creator(String.valueOf(R.raw.doozyjazz),"jazz",15));
            all_video.add(creator(String.valueOf(R.raw.doozyjog),"jog",15));
            all_video.add(creator(String.valueOf(R.raw.doozyjump),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.doozyjumpjack),"jjack",15));
//            all_video.add(creator(String.valueOf(R.raw.doozypistols),"pistol",15));
            all_video.add(creator(String.valueOf(R.raw.doozysidejumps),"sidejump",15));
            all_video.add(creator(String.valueOf(R.raw.doozysquats),"squat",15));
            all_video.add(creator(String.valueOf(R.raw.doozyvictory),"victory",15));
            all_video.add(creator(String.valueOf(R.raw.doozysweep),"sweep",15));
        }
        else if(character==3)
        {
//            all_video.add(creator(String.valueOf(R.raw.grannypistol),"pistol",15));
            all_video.add(creator(String.valueOf(R.raw.grannysword),"sword",15));
            all_video.add(creator(String.valueOf(R.raw.grannyarm),"arm",15));
//            all_video.add(creator(String.valueOf(R.raw.grannycaperio),"capoeria",15));
            all_video.add(creator(String.valueOf(R.raw.grannycrossjumprotation),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.grannycrossjumps),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.grannyfrontraises),"front",15));
            all_video.add(creator(String.valueOf(R.raw.grannyharvesting),"harvest",15));
            all_video.add(creator(String.valueOf(R.raw.grannyhook),"hook",15));
            all_video.add(creator(String.valueOf(R.raw.grannyjack),"jjack",15));
            all_video.add(creator(String.valueOf(R.raw.grannyjazz),"jazz",15));
            all_video.add(creator(String.valueOf(R.raw.grannyjog),"jog",15));
            all_video.add(creator(String.valueOf(R.raw.grannyjump),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.grannyneckexer),"neck",15));
            all_video.add(creator(String.valueOf(R.raw.grannysidemove),"sidejump",15));
            all_video.add(creator(String.valueOf(R.raw.grannysquat),"squat",15));
            all_video.add(creator(String.valueOf(R.raw.grannysweep),"sweep",15));
            all_video.add(creator(String.valueOf(R.raw.grannyvictory),"victory",15));
        }
        else if(character==4)
        {
//            all_video.add(creator(String.valueOf(R.raw.ninjapistol),"pistol",15));
            all_video.add(creator(String.valueOf(R.raw.ninjasword),"sword",15));
//            all_video.add(creator(String.valueOf(R.raw.ninja_capoeria),"capoeria",15));
            all_video.add(creator(String.valueOf(R.raw.ninjaarmstretch),"arm",15));
            all_video.add(creator(String.valueOf(R.raw.ninjacrossjumprotate),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.ninjacrossjumps),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.ninjafrontraises),"front",15));
            all_video.add(creator(String.valueOf(R.raw.ninjaharvest),"harvest",15));
            all_video.add(creator(String.valueOf(R.raw.ninjajack),"jjack",15));
            all_video.add(creator(String.valueOf(R.raw.ninjajazzdance),"jazz",15));
            all_video.add(creator(String.valueOf(R.raw.ninjajog),"jog",15));
            all_video.add(creator(String.valueOf(R.raw.ninjajump),"jump",15));
            all_video.add(creator(String.valueOf(R.raw.ninjalegsweep),"sweep",15));
            all_video.add(creator(String.valueOf(R.raw.ninjaneckst),"neck",15));
            all_video.add(creator(String.valueOf(R.raw.ninjaquicksteps),"sidejump",15));
            all_video.add(creator(String.valueOf(R.raw.ninjarighthook),"hook",15));
            all_video.add(creator(String.valueOf(R.raw.ninjasquats),"squat",15));
            all_video.add(creator(String.valueOf(R.raw.ninjavictory),"victory",15));

        }
        int random = new Random().nextInt(all_video.size());
        Log.i("random", "videoplayer: "+random);
        currentvideo=all_video.get(random);
        calculator.scorereset();
    }
  public video  creator(String temp, String func, int duration){

      video tempp=new video();
      tempp.duration=duration;
      tempp.name=temp;
      tempp.funcname=func;
      return tempp;
  }
    public int getvideoduration() {
        return currentvideo.duration;
    }

    public String getvideoname() {
        return currentvideo.name;
    }

    public void update() {
        int temp=new Random().nextInt(all_video.size());;
        while (temp==random)
        { temp=new Random().nextInt(all_video.size());}
        random = temp;
        Log.i("random", "videoplayer: "+random);
        currentvideo=all_video.get(random);
        calculator.scorereset();
    }
}

class video{
String name;
String funcname;
int duration;
}