package com.example.bigdocto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.bigdocto.data.model.User;
import com.example.bigdocto.ui.login.Constants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tech.gusavila92.websocketclient.WebSocketClient;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyChatAdapter adapter;
    private ArrayList<Map<String,Object>> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    private String token=null;
    private String userId=null;
    public EditText messageET;
    public String to;
    private WebSocketClient webSocketClient;
    public Button send;
    public  String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        SharedPreferences sharedPreferences=this.getSharedPreferences("mySharedPref", Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        userId=sharedPreferences.getString("userId","");
        //  progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyChatAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);
        Intent intent=getIntent();
        to=intent.getStringExtra("to");
        createWebSocketClient();
        messageET=(EditText) findViewById(R.id.msg);
        send=(Button) findViewById(R.id.send);
        Content content = new Content();
        content.execute(0);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                message=messageET.getText().toString().trim();
                if(message!=null && !message.isEmpty()) {
                  //  webSocketClient.addHeader("Authorization","Bearer"+token);
                    webSocketClient.send(userId+"|"+to+"|"+message);
                }

                messageET.setText("");
            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==parseItems.size()-1) {
                    Content content=new Content();
                    content.execute(parseItems.size()-1);
                }

            }
        });

    }



    // Fetch Previous Chats

    private class Content extends AsyncTask<Integer,Void,Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //   progressBar.setVisibility(View.VISIBLE);
            //   progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_in));
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // progressBar.setVisibility(View.GONE);
            //    progressBar.startAnimation(AnimationUtils.loadAnimation(MainActivity.this, android.R.anim.fade_out));
            adapter.notifyDataSetChanged();
        }

        @Override
        protected Void doInBackground(Integer... integers) {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization","Bearer"+token);
            Map<String,Object> data=new HashMap<>();
            data.put("from",userId);
            data.put("to",to);
            data.put("skip",integers[0]);
            //   Log.i("Username:","########"+integers[0]);
            HttpEntity<Map<String,Object>> entity = new HttpEntity<Map<String,Object>>(data,headers);
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Object[] users=null;
            List<User> usersData=new ArrayList<>();
            try{
                users = restTemplate.postForObject(Constants.URL+"chat/fetchChat", entity, Object[].class);
                for(Object object:users){
                    Map<String,Object> userData=(Map<String,Object>) object;
                    parseItems.add(userData);
                }

            }catch(Exception e){

            }

            return null;
        }
    }


    //Websocket

    private void createWebSocketClient() {
        URI uri;
        try {
            // Connect to local host
            uri = new URI("ws://192.168.1.8:8080/name");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }
        webSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen() {
                Log.i("WebSocket", "Session is starting");
                webSocketClient.send("");
            }

            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                if(s.isEmpty())
                    return;
                final String message = s;
                String[] types=s.split("\\|");
                //type[0] -> coneect/disconnect type[1] -> userId
              //  String userId=types[1];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(types.length!=3)
                                return;
                            if(parseItems!=null){
                                if(types[0].equals(userId) || types[1].equals(userId)){
                                    Map<String,Object> showMessage=new HashMap<>();
                                    showMessage.put("from",(String)types[0]);
                                    showMessage.put("msg",(String)types[2]);
                                    parseItems.add(showMessage);
                                    adapter.notifyItemInserted(parseItems.size());
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

            @Override
            public void onBinaryReceived(byte[] data) {
            }

            @Override
            public void onPingReceived(byte[] data) {
            }

            @Override
            public void onPongReceived(byte[] data) {
            }

            @Override
            public void onException(Exception e) {
                System.out.println(e.getMessage());
            }

            @Override
            public void onCloseReceived() {
                Log.i("WebSocket", "Closed ");
                System.out.println("onCloseReceived");
            }
        };
        // webSocketClient.setConnectTimeout(10000);
        //    webSocketClient.setReadTimeout(60000);
        webSocketClient.enableAutomaticReconnection(5000);
        webSocketClient.addHeader("Authorization","Bearer"+token);
        webSocketClient.connect();
    }
}