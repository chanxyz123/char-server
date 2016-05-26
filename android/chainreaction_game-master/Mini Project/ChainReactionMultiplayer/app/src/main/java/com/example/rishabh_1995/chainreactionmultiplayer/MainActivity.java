package com.example.rishabh_1995.chainreactionmultiplayer;

import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Log;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    int turn=-1;
    boolean can_finish=false;
    int[][] flag = new int[10][6];
    int n=2;
    int[] boxes=new int[n];
    int red[]={220,24};
    int green[]={20,116};
    int blue[]={60,205};
    int clicked=0;
    int turn_detector=0;
    int playerno=-1;
    boolean finished=false;
    private static final String TAG= "Mymessage";
    static final String[] numbers = new String[] {
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " ",
            " ", " ", " "
          /*  " ", " ", " ", " ", " ",
            " ", " ", " ", " ", " "*/};


    public void init()
    {
        for (int i=0;i<8;i++)
        {
            for(int j=0;j<6;j++)
            {
                flag[i][j]=-1;
            }
        }
        for(int i=0;i<n;i++) boxes[i]=0;
       /* for(int i=0;i<=59;i++)
        {
            View obj= gridView.getChildAt(i);
            TextView v=(TextView)obj;
            v.setBackgroundColor(Color.rgb(255,255,255));
        }*/
    }

    public View GetViewByPosition(int position){

        View obj= gridView.getChildAt(position);
        TextView v=(TextView)obj;

        return obj;

    }

    public void inc_or_split(int position,long id,View v)
    {

        for(int i=0;i<n;i++)
        {
            if(boxes[i]>=48) return;
        }
        if(flag[position/6][position%6]==-1)boxes[turn%n]++;
        else if(flag[position/6][position%6]!=(turn%n) )
        {
            boxes[flag[position/6][position%6]]--;
            boxes[turn%n]++;
        }

        TextView txt_obj=(TextView)v;
        txt_obj.setBackgroundColor(Color.rgb(red[turn%n],green[turn%n],blue[turn%n]));

        flag[position/6][position%6]=turn%n;

        //  Log.i(TAG,""+boxes[turn%n]);
        String txt=txt_obj.getText().toString();
        int num_data;
        if(txt==" ")num_data=0;
        else num_data=Integer.parseInt(txt);

        if(id==0 || id==5|| id==42 || id==47)
        {
            if(num_data==0) {
                // View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);

            }
            else if(num_data==1){
                boxes[turn%n]--;
                //txt=Integer.toString();
                txt_obj.setText(" ");
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                if(id==0){

                    View right=GetViewByPosition(position+1);
                    if(right==null) return;//log.i("Hi");
                    else inc_or_split(position+1,id+1,right);

                    View bottom=GetViewByPosition(position+6);
                    if(bottom==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position+6,id+6,bottom);


                }

                else if(id==5){
                    View left=GetViewByPosition(position-1);
                    if(left==null)return; //log.i("Hi");  @..................
                    else inc_or_split(position-1,id-1,left);

                    View bottom=GetViewByPosition(position+6);
                    if(bottom==null)return; //log.i("Hi");  @..................
                    else inc_or_split(position+6,id+6,bottom);


                }
                else if(id==42){
                    View right=GetViewByPosition(position+1);
                    if(right==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position+1,id+1,right);

                    View upper=GetViewByPosition(position-6);
                    if(upper==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position-6,id-6,upper);


                }
                if(id==47){
                    View left=GetViewByPosition(position-1);
                    if(left==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position-1,id-1,left);

                    View upper=GetViewByPosition(position-6);
                    if(upper==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position-6,id-6,upper);


                }

            }
        }

        else if(id%6==0){
            if(num_data==0 || num_data==1) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                // @backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                txt_obj.setText(" ");
                flag[position/6][position%6]=-1;
                View upper=GetViewByPosition(position-6);
                if(upper==null) return;//log.i("Hi"); // @..................
                else inc_or_split(position-6,id-6,upper);

                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  //@..................
                else inc_or_split(position+1,id+1,right);

                View bottom=GetViewByPosition(position+6);
                if(bottom==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }

        else if(id%6==5){
            if(num_data==0 || num_data==1 ) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                //@backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View upper=GetViewByPosition(position-6);
                if(upper==null)return;// log.i("Hi");  @..................
                else inc_or_split(position-6,id-6,upper);

                View left=GetViewByPosition(position-1);
                if(left==null) return;//log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View bottom=GetViewByPosition(position+6);
                if(bottom==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }

        else if(id<5){
            if(num_data==0 || num_data==1) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                // @backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+1,id+1,right);

                View left=GetViewByPosition(position-1);
                if(left==null)return; //log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View bottom=GetViewByPosition(position+6);
                if(bottom==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }

        else if(id>42){
            if(num_data==0 || num_data==1 ) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                //@backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+1,id+1,right);

                View left=GetViewByPosition(position-1);
                if(left==null)return;// log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View upper=GetViewByPosition(position-6);
                if(upper==null)return; //log.i("Hi");  @..................
                else inc_or_split(position-6,id-6,upper);
            }
        }

        else if(id >47){

            return;// log.i("Error :id out of bounds");
        }

        else {
            if(num_data==0 || num_data==1 || num_data==2) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                // @backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+1,id+1,right);

                View left=GetViewByPosition(position-1);
                if(left==null) return;//log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View upper=GetViewByPosition(position-6);
                if(upper==null)return;// log.i("Hi");  @..................
                else inc_or_split(position-6,id-6,upper);


                View bottom=GetViewByPosition(position+6);
                if(bottom==null)return; //log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }
    }

    public void inc_or_split(int position)
    {
        Log.i(TAG,"Oncclick1\n");
        long id= position;
        View v=GetViewByPosition(position);

        for(int i=0;i<n;i++)
        {
            if(boxes[i]>=48 ) return;
        }
        if(flag[position/6][position%6]==-1)boxes[turn%n]++;
        else if(flag[position/6][position%6]!=(turn%n) )
        {
            boxes[flag[position/6][position%6]]--;
            boxes[turn%n]++;
        }

        TextView txt_obj=(TextView)v;
        txt_obj.setBackgroundColor(Color.rgb(red[turn%n],green[turn%n],blue[turn%n]));

        flag[position/6][position%6]=turn%n;

        //  Log.i(TAG,""+boxes[turn%n]);
        String txt=txt_obj.getText().toString();
        int num_data;
        if(txt==" ")num_data=0;
        else num_data=Integer.parseInt(txt);

        if(id==0 || id==5|| id==42 || id==47)
        {
            if(num_data==0) {
                // View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);

            }
            else if(num_data==1){
                boxes[turn%n]--;
                //txt=Integer.toString();
                txt_obj.setText(" ");
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                if(id==0){

                    View right=GetViewByPosition(position+1);
                    if(right==null) return;//log.i("Hi");
                    else inc_or_split(position+1,id+1,right);

                    View bottom=GetViewByPosition(position+6);
                    if(bottom==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position+6,id+6,bottom);


                }

                else if(id==5){
                    View left=GetViewByPosition(position-1);
                    if(left==null)return; //log.i("Hi");  @..................
                    else inc_or_split(position-1,id-1,left);

                    View bottom=GetViewByPosition(position+6);
                    if(bottom==null)return; //log.i("Hi");  @..................
                    else inc_or_split(position+6,id+6,bottom);


                }
                else if(id==42){
                    View right=GetViewByPosition(position+1);
                    if(right==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position+1,id+1,right);

                    View upper=GetViewByPosition(position-6);
                    if(upper==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position-6,id-6,upper);


                }
                if(id==47){
                    View left=GetViewByPosition(position-1);
                    if(left==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position-1,id-1,left);

                    View upper=GetViewByPosition(position-6);
                    if(upper==null) return;//log.i("Hi");  @..................
                    else inc_or_split(position-6,id-6,upper);


                }

            }
        }

        else if(id%6==0){
            if(num_data==0 || num_data==1) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                // @backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                txt_obj.setText(" ");
                flag[position/6][position%6]=-1;
                View upper=GetViewByPosition(position-6);
                if(upper==null) return;//log.i("Hi"); // @..................
                else inc_or_split(position-6,id-6,upper);

                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  //@..................
                else inc_or_split(position+1,id+1,right);

                View bottom=GetViewByPosition(position+6);
                if(bottom==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }

        else if(id%6==5){
            if(num_data==0 || num_data==1 ) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                //@backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View upper=GetViewByPosition(position-6);
                if(upper==null)return;// log.i("Hi");  @..................
                else inc_or_split(position-6,id-6,upper);

                View left=GetViewByPosition(position-1);
                if(left==null) return;//log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View bottom=GetViewByPosition(position+6);
                if(bottom==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }

        else if(id<5){
            if(num_data==0 || num_data==1) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                // @backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+1,id+1,right);

                View left=GetViewByPosition(position-1);
                if(left==null)return; //log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View bottom=GetViewByPosition(position+6);
                if(bottom==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }

        else if(id>42){
            if(num_data==0 || num_data==1 ) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                //@backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+1,id+1,right);

                View left=GetViewByPosition(position-1);
                if(left==null)return;// log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View upper=GetViewByPosition(position-6);
                if(upper==null)return; //log.i("Hi");  @..................
                else inc_or_split(position-6,id-6,upper);
            }
        }

        else if(id >47){

            return;// log.i("Error :id out of bounds");
        }

        else {
            if(num_data==0 || num_data==1 || num_data==2) {
                View ob=GetViewByPosition(position);
                txt=Integer.toString(num_data+1);
                txt_obj.setText(txt);
                // @backgrooud
            }

            else 		{
                boxes[turn%n]--;
                v.setBackgroundColor(Color.rgb(178,255,102));
                flag[position/6][position%6]=-1;
                txt_obj.setText(" ");
                View right=GetViewByPosition(position+1);
                if(right==null) return;//log.i("Hi");  @..................
                else inc_or_split(position+1,id+1,right);

                View left=GetViewByPosition(position-1);
                if(left==null) return;//log.i("Hi");  @..................
                else inc_or_split(position-1,id-1,left);

                View upper=GetViewByPosition(position-6);
                if(upper==null)return;// log.i("Hi");  @..................
                else inc_or_split(position-6,id-6,upper);


                View bottom=GetViewByPosition(position+6);
                if(bottom==null)return; //log.i("Hi");  @..................
                else inc_or_split(position+6,id+6,bottom);
            }
        }
    }




    static final int SocketServerPORT = 8080;

    LinearLayout loginPanel, chatPanel;
    GridView gridView;
        /*GridView gridView;*/

    EditText editTextUserName, editTextAddress;
    Button buttonConnect;
    TextView textPort,textcountdown;



    Button buttonDisconnect;

    String msgLog = "";

    ChatClientThread chatClientThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginPanel = (LinearLayout)findViewById(R.id.loginpanel);
        chatPanel = (LinearLayout)findViewById(R.id.chatpanel);
        gridView = (GridView) findViewById(R.id.gridView1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, numbers) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                View v = super.getView(position, convertView, parent);

                v.setBackgroundColor(Color.rgb(178,255,102));


                return v;
            };
        };

        gridView.setAdapter(adapter);
        init();

        editTextUserName = (EditText) findViewById(R.id.username);
        editTextAddress = (EditText) findViewById(R.id.address);
        textPort = (TextView) findViewById(R.id.port);
        textcountdown = (TextView) findViewById(R.id.countdown);
        textPort.setText("port: " + SocketServerPORT);
        buttonConnect = (Button) findViewById(R.id.connect);
        buttonDisconnect = (Button) findViewById(R.id.disconnect);
        //chatMsg = (TextView) findViewById(R.id.chatmsg);

        buttonConnect.setOnClickListener(buttonConnectOnClickListener);
        buttonDisconnect.setOnClickListener(buttonDisconnectOnClickListener);

        //editTextSay = (EditText)findViewById(R.id.say);
        //buttonSend = (Button)findViewById(R.id.send);

        //buttonSend.setOnClickListener(buttonSendOnClickListener);


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                if(playerno==-1)
                    playerno=0;
                if(((turn+2)%2==1 && playerno==0) || ((turn+2)%2==0  && playerno==1)) {
                    turn++;
                    clicked = 1;
                    chatClientThread.sendMsg("" + position);
                    TextView obj = (TextView) v;
                    //obj.setBackgroundColor(Color.rgb(93, 94, 94));
                    // Toast.makeText(getApplicationContext(),
                    //       ""+id, Toast.LENGTH_SHORT).show();
                    if (can_finish == true && (boxes[0]==0||boxes[1]==0)) {

                        if((playerno==0 && boxes[0]==0)|| (playerno==1 && boxes[1]==0)){
                            textcountdown.setText( "You Lose!!");
                            Toast.makeText(getApplicationContext(),
                                    "You Lose", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            textcountdown.setText( "You Win!!");
                            Toast.makeText(getApplicationContext(),
                                    "You Win", Toast.LENGTH_SHORT).show();
                        }
                           /* Toast.makeText(getApplicationContext(),
                                    "Game Finished" + boxes[0], Toast.LENGTH_SHORT).show();*/
                        turn--;

                    } else {
                        if (flag[position / 6][position % 6] == turn % n || flag[position / 6][position % 6] == -1) {
                            inc_or_split(position, id, v);

                        } else turn--;
                        if (turn >= 1) can_finish = true;
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "Opponent's Turn", Toast.LENGTH_SHORT).show();
                }
                //TextView obj1 = (TextView) GetViewByPosition(position+1);
                //obj1.setBackgroundColor(Color.rgb(25,30,220));
                //obj1.setText("1");
                //GetViewByPosition(id);
            }
        });
    }

    OnClickListener buttonDisconnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            if(chatClientThread==null){
                return;
            }
            chatClientThread.disconnect();
        }

    };

      /*  OnClickListener buttonSendOnClickListener = new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (editTextSay.getText().toString().equals("")) {
                    return;
                }

                if(chatClientThread==null){
                    return;
                }

                chatClientThread.sendMsg(editTextSay.getText().toString() + "\n");
            }

        };*/

    OnClickListener buttonConnectOnClickListener = new OnClickListener() {

        @Override
        public void onClick(View v) {
            String textUserName = editTextUserName.getText().toString();
            if (textUserName.equals("")) {
                Toast.makeText(MainActivity.this, "Enter User Name",
                        Toast.LENGTH_LONG).show();
                return;
            }

            String textAddress = editTextAddress.getText().toString();
            if (textAddress.equals("")) {
                Toast.makeText(MainActivity.this, "Enter Addresse",
                        Toast.LENGTH_LONG).show();
                return;
            }

            msgLog = "";
            // chatMsg.setText(msgLog);
            loginPanel.setVisibility(View.GONE);
            chatPanel.setVisibility(View.VISIBLE);
               /* gridView.setVisibility(View.VISIBLE);*/
            Log.i(TAG,"Oncclick1\n");
            chatClientThread = new ChatClientThread(
                    textUserName, textAddress, SocketServerPORT);
            Log.i(TAG,"Oncclick2\n");
            chatClientThread.start();
            Log.i(TAG, "Oncclick3\n");
        }

    };




    private class ChatClientThread extends Thread {

        String name;
        String dstAddress;
        int dstPort;

        String msgToSend = "";
        boolean goOut = false;

        ChatClientThread(String name, String address, int port) {
            this.name = name;
            dstAddress = address;
            dstPort = port;
        }
        private Handler customHandler = new Handler();

        long startTime = 0L;
        long timeInMilliseconds = 0L;

        @Override
        public void run() {
            Log.i(TAG, "Chatclient1\n");
            Socket socket = null;
            DataOutputStream dataOutputStream = null;
            DataInputStream dataInputStream = null;

            try {
                socket = new Socket(dstAddress, dstPort);
                Log.i(TAG, "Chatclient2\n");
                dataOutputStream = new DataOutputStream(
                        socket.getOutputStream());
                dataInputStream = new DataInputStream(socket.getInputStream());
                dataOutputStream.writeUTF(name);
                dataOutputStream.flush();

                while (!goOut) {
                    Log.i(TAG, "Chatclient3\n");
                    if (dataInputStream.available() > 0) {
                        Log.i(TAG, "Chatclient356\n");
                        String rec = dataInputStream.readUTF();
                        Log.i(TAG, rec);
                        final int x=Integer.parseInt(rec);
                           /* if(x==70){
                                if(finished==false){
                                    if(playerno==0){
                                        boxes[1]=0;
                                    }
                                    else if(playerno==1){
                                        boxes[0]=0;
                                    }
                                }
                                textcountdown.setText("Chain Reaction          " + "You Win!");
                            }*/





                        MainActivity.this.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {

                                customHandler.removeCallbacks(updateTimerThread);




                                if(x>=0&&x<=59) {
                                    if (clicked == 0) {
                                        if(playerno==-1)
                                            playerno=1;
                                        turn++;
                                        turn_detector=1;

                                        //obj.setBackgroundColor(Color.rgb(93, 94, 94));
                                        // Toast.makeText(getApplicationContext(),
                                        //       ""+id, Toast.LENGTH_SHORT).show();
                                        if (can_finish == true && (boxes[0] == 0 || boxes[1] == 0)) {

                                            if((playerno==0 && boxes[0]==0)|| (playerno==1 && boxes[1]==0)){
                                                textcountdown.setText( "You Lose!!");
                                                Toast.makeText(getApplicationContext(),
                                                        "You Lose", Toast.LENGTH_SHORT).show();
                                            }
                                            else{
                                                textcountdown.setText( "You Win!!");
                                                Toast.makeText(getApplicationContext(),
                                                        "You Win", Toast.LENGTH_SHORT).show();
                                            }
                                               /* Toast.makeText(getApplicationContext(),
                                                        "Game Finished" + boxes[0], Toast.LENGTH_SHORT).show();*/
                                            turn--;

                                        } else {
                                            if (flag[x / 6][x % 6] == turn % n || flag[x / 6][x % 6] == -1) {
                                                inc_or_split(x);
                                            } else turn--;
                                            if (turn >= 1) can_finish = true;
                                        }

                                    }
                                    else {
                                        turn_detector=0;
                                        // textcountdown.setText("Chain Reaction    " + 0);
                                        clicked=0;
                                    }
                                }
                                //inc_or_split(x);

                                startTime = SystemClock.uptimeMillis();
                                customHandler.postDelayed(updateTimerThread, 0);}

                        });
                    }
                    Log.i(TAG, "Chatclient4\n");
                    if(!msgToSend.equals("")){
                        dataOutputStream.writeUTF(msgToSend);
                        dataOutputStream.flush();
                        msgToSend = "";
                    }
                }

            } catch (UnknownHostException e) {
                Log.i(TAG, "Chatclient5\n");
                e.printStackTrace();
                final String eString = e.toString();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } catch (IOException e) {
                Log.i(TAG, "Chatclient6\n");
                e.printStackTrace();
                final String eString = e.toString();
                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, eString, Toast.LENGTH_LONG).show();
                    }

                });
            } finally {
                if (socket != null) {
                    try {
                        Log.i(TAG, "Chatclient7\n");
                        socket.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataOutputStream != null) {
                    try {
                        dataOutputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                if (dataInputStream != null) {
                    try {
                        dataInputStream.close();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                MainActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {
                        loginPanel.setVisibility(View.VISIBLE);
                        chatPanel.setVisibility(View.GONE);
                            /*gridView.setVisibility(View.GONE);*/
                    }

                });
            }

        }

        private Runnable updateTimerThread = new Runnable() {
            public void run() {

                int flag=0;
                timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
                long timer=timeInMilliseconds / 1000;
                if(timer==20){
                    flag=1;
                    if (turn_detector == 0) {
                        if(playerno==0){
                            boxes[1]=0;
                        }
                        if(playerno==1){
                            boxes[0]=0;
                        }
                        textcountdown.setText("Opponent's Turn             " + "You Win");
                        //customHandler.postDelayed(this, 0);
                    }
                    if (turn_detector == 1) {
                        boxes[playerno]=0;
                        textcountdown.setText("Your Turn                " + "You Lose");
                        //customHandler.postDelayed(this, 0);
                    }

                }


                if(flag==0) {
                    if (turn_detector == 0) {
                        if( can_finish==false || (can_finish==true &&(boxes[0]!=0 && boxes[1]!=0))){
                            textcountdown.setText("Opponent's Turn                " + timer);
                            customHandler.postDelayed(this, 0);}
                        else {
                            if((playerno==0 && boxes[0]==0)||(playerno==1 && boxes[1]==0)){
                                textcountdown.setText("You Lose");
                            }
                            else if((playerno==0 && boxes[1]==0)||(playerno==1 && boxes[0]==0)){
                                textcountdown.setText("You Win");
                            }
                        }
                    }
                    if (turn_detector == 1) {
                        if( can_finish==false || (can_finish==true &&(boxes[0]!=0 && boxes[1]!=0))){
                            textcountdown.setText("Your Turn                " + timer);
                            customHandler.postDelayed(this, 0);}
                        else {
                            if((playerno==0 && boxes[0]==0)||(playerno==1 && boxes[1]==0)){
                                textcountdown.setText("You Lose");
                            }
                            else if((playerno==0 && boxes[1]==0)||(playerno==1 && boxes[0]==0)){
                                textcountdown.setText("You Win");
                            }
                        }
                    }

                }


            }
        };
        private void sendMsg(String msg){
            msgToSend = msg;
        }

        private void disconnect(){
            goOut = true;
        }
    }

}