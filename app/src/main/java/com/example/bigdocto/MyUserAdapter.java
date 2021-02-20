package com.example.bigdocto;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.bigdocto.data.model.User;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MyUserAdapter extends RecyclerView.Adapter<MyUserAdapter.ViewHolder> {

    public List<Map<String,Object>> users;
    public Context context;

    public MyUserAdapter(List<Map<String,Object>> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.home_screen_user_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Map<String,Object> UserList =users.get(position);
        holder.textViewName.setText((String)UserList.get("userName"));
        holder.textViewDate.setText((String)UserList.get("status"));
        if("ACTIVE".equals((String)UserList.get("status")))
        holder.textViewDate.setTextColor(Color.parseColor("#008000"));
        else
            holder.textViewDate.setTextColor(Color.parseColor("#FF0000"));


        //holder.movieImage.setImageResource(UserList.getMovieImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String to=(String)UserList.get("userName");
                Intent intent=new Intent(context,ChatActivity.class);
                intent.putExtra("to",to);
                context.startActivity(intent);
                Toast.makeText(context, (String)UserList.get("userName"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        if(users!=null)
        return users.size();
        else
            return 0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView movieImage;
        TextView textViewName;
        TextView textViewDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            movieImage = itemView.findViewById(R.id.imageview);
            textViewName = itemView.findViewById(R.id.textName);
            textViewDate = itemView.findViewById(R.id.textdate);

        }
    }
    public void setFilter (List<Map<String,Object>> newList) {
        users = new ArrayList<>();
        users.addAll(newList);
        notifyDataSetChanged();
    }
}