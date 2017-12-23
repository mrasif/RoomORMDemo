package com.example.asif.roomdemo.adapter;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.asif.roomdemo.R;
import com.example.asif.roomdemo.config.AppConfig;
import com.example.asif.roomdemo.database.AppDatabase;
import com.example.asif.roomdemo.models.User;

import java.util.List;

/**
 * Created by asif on 12/22/17.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {
    private static final String TAG = "UserAdapter";
    List<User> users;
    LayoutInflater inflater;
    Context ctx;
    public UserAdapter(Context ctx, List<User> users) {
        this.ctx=ctx;
        this.users=users;
        this.inflater=LayoutInflater.from(ctx);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_user,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user=users.get(position);
        holder.tvUserName.setText(user.getName());
        holder.tvUserEmail.setText(user.getEmail());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cvCardItem;
        TextView tvUserName, tvUserEmail;
        public ViewHolder(View itemView) {
            super(itemView);
            cvCardItem=itemView.findViewById(R.id.cvCardItem);
            tvUserName=itemView.findViewById(R.id.tvUserName);
            tvUserEmail=itemView.findViewById(R.id.tvUserEmail);
            cvCardItem.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
//            Log.d(TAG, "onClick: Name "+tvUserName.getText().toString());
            AppDatabase db= Room.databaseBuilder(ctx,
                    AppDatabase.class, AppConfig.DB_NAME)
                    .allowMainThreadQueries()
                    .build();
//            User user=db.userDao().getUserById(users.get(getPosition()).getUid());
            User user=users.get(getPosition());
            db.userDao().delete(user);
            users.remove(getPosition());
            notifyItemRemoved(getPosition());
        }
    }
}
