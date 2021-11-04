package com.kidzyfit.kidzyapp;

import android.util.Log;

public class poscalculator {
    public int orintation;
    int capoeria_score=0;
    int victory_score=0;
    int victory_status=0;
    int legsweep_score=0;
    int jazzscore=0;
    int squat_time=0;
    int pistol_Score=0;
    float jazz_status=0;
    int status_legsweep=0;
    int stable_sword_score=0;
    int status_stable_sword1=0;
    int status_stable_sword2=0;
    int hand_bend_time=0;
    float clateralstablex=0;
    int jumpstatus=0;
    float clateralstabley=0;
    float plateralstablex=0;
    float plateralstabley=0;
    float time_lateralmove=0;
    float csidejstablex=0;
    float csidejstabley=0;
    float psidejstablex=0;
    float psidejstabley=0;
    float time_sidejmove=0;
    int lateralcount=0;
    int sidejcount=0;
    int squatscore=0;
    int arm_score=0;
    int time_movedown=0;
    float[][] cord = new float[40][6];
    float[][] previouscord = new float[40][6];
    int jump_score = 0;
    int jump_clap = 0;
    int jump_jack=0;
    int still_jog=0;
    int bicep_score=0;
    int bicepstat=0;
    boolean status = false;
    int timeclap=0;
    int rhandsup=0;
    int lhandsup=0;
    int time_for_callibration=0;
    int timejump=0;
    int time_softjump=0;
    int timer_5=0;
    int sidestatus=0;
    int sidebend=0;
    int time_side_bend=0;
    int sidescore=0;
    int time_sweep=0;
    int side_jump_score=0;
    int neck_right_left_status=0;
    int neck_right_left_score=0;
    int hand_strech_status=0;
    int lateraljump_score=0;
    int frontrasiesscore=0;
    int frontraisestime=0;
    int frontraisesstatus=0;
    int time_softjump_side=0;
    int outside_frame_time=0;

    public void Scorecal() {
        Log.i("sidejtime", "Scorecal: sidemovem "+time_sidejmove+ "");
        jumpcheck();
        softjump();
        clap();
        //  jumpclap();
        jumpingjack();
        alternatehandsup();
        run();
        arm_stretch();
        sidesoftjump();
        side_bend();
        sidejump();
        neckleftright();
        lateraljump();
        setTime_movedown();
        squat();
        bicupcirl();
        frontraises();
        capoeria();
        handbend();
        setStable_sword_score();
        setLegsweep_score();
        setVictory_score();
        jazzdance();
        update();
        setPistol_Score();
        setTime_for_callibration();
        timeclap++;
        timejump++;
        lhandsup++;
        rhandsup++;
        time_softjump++;
        timer_5++;
        time_lateralmove++;
        time_softjump++;
        time_movedown++;
        hand_bend_time++;
        squat_time++;
        frontraisestime++;
        time_softjump_side++;

    }
    public void capoeria(){
        Log.i("capoeria" ,"capoeria: "+time_softjump+" "+hand_bend_time+" "+time_lateralmove);
        if (time_softjump<5 && hand_bend_time<5 && time_lateralmove<15)
        {
            capoeria_score++;
        }
    }
    public void handbend(){
        if ((cord[12][1]-cord[14][1])*(cord[16][1]-cord[14][1])>0 ||(cord[11][1]-cord[13][1])*(cord[15][1]-cord[13][1])>0)
            hand_bend_time=0;
    }
    public void scorereset() {
        squatscore = 0;
        stable_sword_score = 0;
        arm_score = 0;
        jump_score = 0;
        bicep_score = 0;
        sidescore = 0;
        side_jump_score = 0;
        neck_right_left_score = 0;
        lateraljump_score = 0;
        frontrasiesscore = 0;
        capoeria_score = 0;
        still_jog=0;
        jump_jack=0;
        legsweep_score=0;
        victory_score=0;
        neck_right_left_score=0;
        jazzscore=0;
        pistol_Score=0;
    }
    public void setLegsweep_score()
    {
        if(cord[12][1]-cord[11][1]>0 && status_legsweep==0)
        {
            status_legsweep=1;
            legsweep_score++;

        }
        else if (cord[12][1]-cord[11][1]<0 && status_legsweep==1){
            status_legsweep=0;
            legsweep_score++;

        }

    }
    public void jazzdance(){
        if ((cord[15][1]-cord[11][1])*(cord[15][1]-cord[13][1])>0 && (cord[16][1]-cord[14][1])*(cord[16][1]-cord[12][1])>0 && (cord[15][1]-cord[11][1])*(cord[16][1]-cord[14][1])>0 )
        {
            if(jazz_status*(cord[15][1]-cord[11][1])<=0)
            {
                jazz_status=cord[15][1]-cord[11][1];
                jazzscore++;
            }
        }
    }
    public  void setTime_for_callibration(){

        for (int i = 0; i <15 ; i++) {
            if ((cord[i][3]<0.9 || cord[i][0]>0.85 || cord[i][1]>0.9 || cord[i][0]<0.1 || cord[i][1]<0.015 ))
            {

                time_for_callibration=0;
                outside_frame_time--;
               return;
            }

        }
        outside_frame_time=0;
        if (cord[0][0]>0.8)
        {
            time_for_callibration=0;

        }
        else if (cord[0][0]<0.6)
        {
            time_for_callibration=0;
        }

        else if (((cord[11][1]+cord[12][1])/2)<0.4)
        {
            //right
            time_for_callibration=0;

        }
        else if (((cord[11][1]+cord[12][1])/2)>0.6)
        {
            //left
            time_for_callibration=0;
        }
       else if (mod(cord[12][1]-cord[11][1])>0.35)
        {
            time_for_callibration=0;
        }
        else if (mod(cord[12][1]-cord[11][1])<0.1)
        {
            time_for_callibration=0;
        }
        else {
            //calibrating
            time_for_callibration+=3;
        }
    }
    public  String getTime_for_callibration(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
            { Log.i("pausing", "Scorecal:here also heeeee");
                return "STAND IN THE BOUNDARY";}


        }
        Log.i("pausing", "Scorecal:here also heeeee"+cord[0][0]);
        Log.i("nose y ", "getTime_for_callibration: ");
        if (cord[0][0]>0.8)
        {
            return "MOVE DOWN";
        }
        if (cord[0][0]<0.6)
        {
            return "MOVE UP";
        }

        else if (((cord[11][1]+cord[12][1])/2)<0.4)
        {
            //right

            return "MOVE TOWARDS RIGHT";
        }
        else if (((cord[11][1]+cord[12][1])/2)>0.6)
        {
            //left
            return "MOVE TOWARDS LEFT";
        }
        else if (mod(cord[12][1]-cord[11][1])>0.35)
        {
            return "MOVE BACK";
        }
        else if (mod(cord[12][1]-cord[11][1])<0.1)
        {
            return "MOVE CLOSER";
        }
        else {
            //calibrating
            return "CALIBRATING PLEASE WAIT";
        }
    }
    public  void setVictory_score(){
        if (cord[14][0]<cord[12][0] && cord[13][0]<cord[11][0] && victory_status==0)
        {
            victory_score++;
            victory_status=1;
        }
        else if(( (cord[14][0]>cord[12][0] && cord[13][0]<cord[11][0]) ||(cord[14][0]<cord[12][0] && cord[13][0]>cord[11][0]) )&& victory_status==1 ) {
            victory_score++;
            victory_status=0;
        }
    }
    private void setStable_sword_score(){
        if ((cord[12][1]-cord[14][1])*(cord[12][1]-cord[16][1])>0)
        { if(cord[12][1]-cord[14][1]>0 && status_stable_sword1==0)
        {status_stable_sword1=1;
            stable_sword_score++;
        }
        else if( cord[12][1]-cord[14][1]<0 && status_stable_sword1==1) {
            status_stable_sword1 = 0;
            stable_sword_score++;
        }
        }
        if ((cord[11][1]-cord[13][1])*(cord[11][1]-cord[15][1])>0)
        {
            if(cord[11][1]-cord[13][1]>0 && status_stable_sword2==0)
            {status_stable_sword2=1;
                stable_sword_score++;
            }
            else if( cord[11][1]-cord[13][1]<0 && status_stable_sword2==1) {
                status_stable_sword2 = 0;
                stable_sword_score++;
            }
        }
    }
    private void sidejump(){
        if (time_softjump<5 && time_softjump_side<5)
        {side_jump_score+=1; time_softjump_side=6;}
    }
    private void bicupcirl(){
        if (bicepstat==0 && ((cord[16][0]>cord[14][0] ) ||( cord[15][0]>cord[13][0]) ))
        {
            bicepstat=1;
            bicep_score++;
        }
        else if (bicepstat==1 &&  cord[16][0]<cord[14][0] && cord[15][0]<cord[13][0] )
        {
            bicepstat=0;
            bicep_score++;
        }
    }
    public void frontraises(){
        if(frontraisesstatus==0 && (abso(2*cord[12][0]-cord[14][0]-cord[16][0])<0.1 || (abso(2*cord[11][0]-cord[15][0]-cord[13][0])<0.1)))
        {
            frontraisesstatus=1;
            frontrasiesscore++;
            frontraisestime=0;
        }
        else if (frontraisesstatus==1 &&  cord[16][0]<cord[14][0] && cord[15][0]<cord[13][0] )
        {
            frontraisesstatus=0;
            frontrasiesscore++;
            frontraisestime=0;
        }
    }
    private void squat()
    {
        if (time_movedown>timejump && time_movedown<5 && timejump<5)
        {
            Log.i("squateee", "squat: squat");
            squatscore++;
            squat_time=0;
            time_movedown=timejump=6;
        }
    }
    private void setTime_movedown(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
                return;
        }
        float current = 0;
        float old = 0;
        for (int i = 0; i < 13; i++) {
            current += cord[i][0];
            old += previouscord[i][0];
        }
        Log.i("Timemovedown", "setTime_movedown: "+time_movedown);
        old = old / 13;
        current = current / 13;
        if ((old - current) > 0.05) {
            Log.i("squatee", "setTime_movedown: here");
            time_movedown=0;
        }
    }
    private void lateraljump(){
        if (timejump<15 && time_lateralmove<5)
        {lateraljump_score+=1; time_lateralmove=6;}
    }
    private void jumpcheck() {

        if (jump())
        {  timejump=0;
            Log.i("jump", "jumpcheck: jump");
            jump_score++;
        }
    }
    private void jumpingjack(){

        if (  timejump<7 && cord[14][0]>cord[12][0] && cord[13][0]>cord[11][0])
        {
            jump_jack++;
            Log.i("timemove", "jumpjack: here");
            timejump=6;
        }
    }
    private void run(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
                return;
        }
        if (time_softjump<4)
        {
            still_jog++;
        }
        if(timer_5==6)
            timer_5=0;

    }
    private void alternatehandsup(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
                return;
        }
        if (cord[14][0]>cord[12][0] && cord[13][0]<cord[11][0])
        {
            rhandsup=0;
        }
        else if(cord[14][0]<cord[12][0] && cord[13][0]>cord[11][0])
        {
            lhandsup=0;
        }
    }
    private  void jumpclap(){
        if ( timejump<5 && timeclap<5  )
        {
            jump_clap++;
            Log.i("timemove", "jumpclap: here");
            timejump=6;
            timeclap=6;
        }
    }
    private boolean clap(){
        if( abso(cord[14][1]-cord[13][1])<0.35 )
        { timeclap=0;
            return true;}
        return false;
    }
    public void setPistol_Score()
    {
        if(frontraisestime<15 && squat_time<15)
        {
            pistol_Score++;
            frontraisestime=16;
            squat_time=16;
        }
    }
    private boolean jump() {
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
            {
                Log.i("jumpcheck", "jump: exit"+i);
                return false;}
        }
        if (status) {
            float current = 0;
            float old = 0;
            for (int i = 0; i < 11; i++) {
                current += cord[i][0];
                old += previouscord[i][0];
            }

            old = old / 11;
            current = current / 11;
            float currentsholder=cord[12][0]+cord[11][0];
            float oldsholder=previouscord[12][0]+previouscord[11][0];
            if ((current - old)> 0.05 && (currentsholder-oldsholder)>0.05 ) {
                jumpstatus=1;
                Log.i("jumpcheck", "jump: jumpedd");
                return true;
            }
        }

        return  false;

    }
    private void softjump() {

        if (status) {
            float current = 0;
            float old = 0;
            for (int i = 0; i < 11; i++) {
                current += cord[i][0];
                old += previouscord[i][0];
            }

            old = old / 11;
            current = current / 11;
            float currentsholder=cord[12][0]+cord[11][0];
            float oldsholder=previouscord[12][0]+previouscord[11][0];
            if ((current - old)> 0.025 && (currentsholder-oldsholder)>0.025 )  {
                time_softjump=0;
            }
        }

    }
    private void sidesoftjump() {

        if (status) {
            float current = 0;
            float old = 0;
            for (int i = 0; i < 11; i++) {
                current += cord[i][1];
                old += previouscord[i][1];
            }

            old = old / 11;
            current = current / 11;
            float currentsholder=cord[12][0]+cord[11][0];
            float oldsholder=previouscord[12][0]+previouscord[11][0];
            if (((current - old)> 0.025 && (currentsholder-oldsholder)>0.025) || ( (old-current  )> 0.025 && (oldsholder-currentsholder)>0.025 ))  {
                time_softjump_side=0;

            }
        }

    }
    public void side_bend()
    {  int tempstatus;

        if (abso(cord[11][0]-cord[12][0])<0.07)
        {
            tempstatus=0;
            sidebend=0;
            time_side_bend+=1;
        }
        else if ((abso(cord[0][1]-cord[12][1])>abso(cord[0][1]-cord[11][1])+0.03 ) )
        {
            tempstatus=1;
            time_side_bend=0;
            sidebend=1;

        }
        else if ((abso(cord[0][1]-cord[12][1])+0.03<abso(cord[0][1]-cord[11][1])) )
        {
            time_side_bend=0;
            sidebend=2;
            tempstatus=-1;
        }
        else{
            time_side_bend+=1;
            tempstatus=0;
        }
        if(sidestatus==tempstatus)
        {
            if(tempstatus!=0)
                sidescore+=1;

        }
        else{
            sidescore+=3;
        }
        sidestatus=tempstatus;
    }

    public void update()
    {   float cx=0;
        float cy=0;
        float px=0;
        float py=0;
        for (int i = 0; i <cord.length; i++) {
            if(i<13)
            {
                cx+=cord[i][0];
                cy+=cord[i][1];
                px+=previouscord[i][0];
                py+=previouscord[i][1];
            }
            previouscord[i][0]=cord[i][0];
            previouscord[i][1]=cord[i][1];
            previouscord[i][2]=cord[i][2];
            previouscord[i][3]=cord[i][3];
            previouscord[i][4]=cord[i][4];
        }
        cx=cx/13;
        px=px/13;
        cy=cy/13;
        py=py/13;
        if(abso(cy-py)<0.03 && abso(cx-px)<0.03 && lateralcount<2)
        {
            lateralcount++;
        }
        if (lateralcount==2)
        {
            clateralstabley=cy;
        }
        if(abso(clateralstabley-plateralstabley)>0.1)
            time_lateralmove=0;
        plateralstabley=clateralstabley;
        if(abso(cy-py)<0.03 && abso(cx-px)<0.03 && sidejcount<1)
        {
            sidejcount++;
        }
        if (sidejcount==1)
        {
            csidejstabley=cy;
        }
        if(abso(csidejstabley-psidejstabley)>0.05)
            time_sidejmove=0;
        psidejstabley=csidejstabley;

        status=true;
    }
    public  float abso(float a)
    {
        if (a>0)
            return a;
        else
            return -a;
    }
    public void neckleftright(){
        if (abso(cord[11][0]-cord[12][0])>0.07)
        {
            return;}
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
                return;
        }
        float right=0;
        float left=0;
        for (int i = 1; i < 4; i++) {
            right+=cord[i][0];
            left+=cord[i+3][0];
        }
        int tempstatus=0;
        right=right/3;
        left=left/3;
        if(abso(right-left)<0.015)
            tempstatus=0;
        else if (right>left)
            tempstatus=-1;
        else if (left>right)
            tempstatus=1;
        if (neck_right_left_status==tempstatus)
        {
            if (tempstatus!=0)
            {
                neck_right_left_score+=3;
            }
        }
        else{
            neck_right_left_score+=1;
        }

        neck_right_left_status=tempstatus;
    }
    public void arm_stretch(){
        for (int i = 0; i <13 ; i++) {
            if (cord[i][3]<0.9 || cord[i][0]>1 || cord[i][1]>1)
                return;
        }
        if (cord[14][3]<0.6 || cord[13][3]<0.6 || cord[14][0]>1|| cord[14][1]>1 ||cord[13][1] >1 || cord[13][0]>1)
        {
            return;
        }
        float k=abso(cord[11][1]-cord[12][1]);
        if(k>abso(cord[14][1]-cord[11][1])||k>abso(cord[13][1]-cord[12][1]))
        {
            arm_score++;

        }
    }
    public float mod(float a) {
        if (a>0)
            return a;
        return -1*a;
    }

}