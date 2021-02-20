package com.example.bigdocto;

import android.content.Context;
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

public class MyChatAdapter extends RecyclerView.Adapter<MyChatAdapter.ViewHolder> {

    public List<Map<String,Object>> users;
    public Context context;

    public MyChatAdapter(List<Map<String,Object>> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.chat_screen_chat_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Map<String,Object> UserList =users.get(position);
        holder.textViewName.setText((String)UserList.get("from"));
        holder.textViewDate.setText((String)UserList.get("msg"));

        //holder.movieImage.setImageResource(UserList.getMovieImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, (String)UserList.get("from"), Toast.LENGTH_SHORT).show();
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