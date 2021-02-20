package com.example.bigdocto;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bigdocto.data.model.User;
import com.example.bigdocto.ui.login.Constants;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tech.gusavila92.websocketclient.WebSocketClient;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyUserAdapter adapter;
    private ArrayList<Map<String,Object>> parseItems = new ArrayList<>();
    private ProgressBar progressBar;
    private WebSocketClient webSocketClient;
    private String token=null;
    private String userId=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SharedPreferences sharedPreferences=this.getSharedPreferences("mySharedPref",Context.MODE_PRIVATE);
        token=sharedPreferences.getString("token","");
        userId=sharedPreferences.getString("userId","");
      //  progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new MyUserAdapter(parseItems, this);
        recyclerView.setAdapter(adapter);

        Content content = new Content();
        content.execute(0);
        createWebSocketClient();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(linearLayoutManager.findLastCompletelyVisibleItemPosition()==parseItems.size()-1) {
                    new Content().execute(parseItems.size()-1);
                }

            }
        });
    }



    private class Content extends AsyncTask<Integer,Void,Void>{

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
            //   Log.i("Username:","########"+integers[0]);
            HttpEntity<Integer> entity = new HttpEntity<Integer>(integers[0],headers);
            RestTemplate restTemplate=new RestTemplate();
            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            Object[] users=null;
            List<User> usersData=new ArrayList<>();
            try{
                users = restTemplate.postForObject(Constants.URL+"home/getPersons", entity, Object[].class);
                for(Object object:users){
                    Map<String,Object> userData=(Map<String,Object>) object;
                    parseItems.add(userData);
                }

            }catch(Exception e){

            }


            return null;
        }
    }



    //WEBSOCKET

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
                    webSocketClient.send("connect|"+userId);
            }

            @Override
            public void onTextReceived(String s) {
                Log.i("WebSocket", "Message received");
                final String message = s;
                String[] types=s.split("\\|");
                //type[0] -> coneect/disconnect type[1] -> userId
                String userId=types[1];
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if(parseItems!=null){
                                for(int i=0;i<parseItems.size();i++){
                                    if(parseItems.get(i).get("userName").equals(userId)){
                                        if(types[0].equals("connect")){
                                            parseItems.get(i).put("status","ACTIVE");
                                        }else
                                            parseItems.get(i).put("status","OFFLINE");
                                        adapter.notifyItemChanged(i);
                                    }

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

    @Override
    protected void onPause() {
        super.onPause();
        sendMessage("disconnect|"+userId);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(parseItems!=null && !parseItems.isEmpty())
        sendMessage("connect|"+userId);
    }

    public void sendMessage(String type) {
        webSocketClient.send(type);

    }
}