package com.example.api_crud_vothanhcong;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {


    private Context context;
    private LayoutInflater inflater;
    private ArrayList<User> users;

    ISendUser sendData;

    public UserAdapter(Context context, ArrayList<User> users) {
        //gõ zô
        inflater=LayoutInflater.from(context);
        this.context = context;
        this.users = users;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        View itemView=inflater.inflate(R.layout.item_user,parent,false );
        return new UserViewHolder(itemView,this );
    }

    @Override
    public void onBindViewHolder(@NonNull  UserViewHolder holder, int position) {

            User user=users.get(position);

            holder.tvID.setText(user.getId());
            holder.tvName.setText(user.getName());
            holder.tvDiaChi.setText(user.getDiaChi());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //
                    sendData = (ISendUser)context;
                    sendData.sendUser(users.get(position));
                }
            });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        // item_user có gì thì mình khai báo hết
        TextView tvName,tvDiaChi,tvID;
        // nhớ nè
        UserAdapter userAdapter;
                                                        //nhớ
        public UserViewHolder(@NonNull  View itemView, UserAdapter userAdapter) {
            super(itemView);

            //findviewbyid
            tvName=itemView.findViewById(R.id.tvName);
            tvDiaChi=itemView.findViewById(R.id.tvDiachi);
            tvID=itemView.findViewById(R.id.tvID);
            //this
            this.userAdapter=userAdapter;
        }
    }
}
