package com.company_name.wasl_project4.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.company_name.wasl_project4.R;

import java.util.ArrayList;
import java.util.List;

public class FirebaseAdapterChecklist extends RecyclerView.Adapter<FirebaseAdapterChecklist.ItemViewHolder>{
    private List<userAttended> mUserLsit=new ArrayList<>();
    private Context mContext;

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendance_card,parent,false);
        return new ItemViewHolder(view);
    }

    public FirebaseAdapterChecklist(Context mContext,List<userAttended> mUserLsit) {
        this.mContext=mContext;
        this.mUserLsit = mUserLsit;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        userAttended user=mUserLsit.get(position);
        holder.mTvName.setText(user.getName());
        holder.isAttended.setChecked(Boolean.parseBoolean(user.isAttended()));
    }

    @Override
    public int getItemCount() {
        return mUserLsit.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mTvName;
        CheckBox isAttended;

        public ItemViewHolder(View itemView) {
            super(itemView);
            mTvName=itemView.findViewById(R.id.nameAttendance);
            isAttended=itemView.findViewById(R.id.checkBoAttended);

        }
    }
}

