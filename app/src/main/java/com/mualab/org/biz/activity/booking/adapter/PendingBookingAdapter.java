package com.mualab.org.biz.activity.booking.adapter;


import android.content.Context;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mualab.org.biz.R;
import com.mualab.org.biz.activity.booking.listner.PendingBookingListener;
import com.mualab.org.biz.helper.MyToast;
import com.mualab.org.biz.model.booking.Bookings;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


public class PendingBookingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Bookings> artistsList;
    private PendingBookingListener bookingListener = null;

    public void setCustomListener(PendingBookingListener bookingListener){
        this.bookingListener = bookingListener;
    }

    // Constructor of the class
    public PendingBookingAdapter(Context context, List<Bookings> artistsList) {
        this.context = context;
        this.artistsList = artistsList;
    }

    @Override
    public int getItemCount() {
        return artistsList.size();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout_for_pendinglist, parent, false);
        return new ViewHolder(view);

    }


    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        final ViewHolder holder = ((ViewHolder) viewHolder);
        final Bookings item = artistsList.get(position);

        SimpleDateFormat input,output;
        output = new SimpleDateFormat("d MMMM yyyy");
        input = new SimpleDateFormat("yyyy-MM-dd");
        String date = item.bookingDate;
        try {
            Date  formatedDate = input.parse(item.bookingDate);  // parse input
            date =  output.format(formatedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.tvUserName.setText(item.userDetail.userName);
        holder.tvStaffName.setText(item.pendingBookingInfos.get(0).staffName);
        holder.tvDate.setText(date);
        holder.tvBookingTime.setText(item.bookingTime);
        holder.tvServices.setText(item.artistServiceName);

        if (!item.userDetail.profileImage.equals("")){
            Picasso.with(context).load(item.userDetail.profileImage).placeholder(R.drawable.defoult_user_img).
                    fit().into(holder.ivProfilePic);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tvUserName,tvStaffName,tvDate,tvBookingTime,tvServices;
        ImageView ivProfilePic;
        AppCompatButton btnCounter,btnReject,btnAccept;
        private ViewHolder(View itemView)
        {
            super(itemView);

            ivProfilePic = itemView.findViewById(R.id.ivProfilePic);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvServices = itemView.findViewById(R.id.tvServices);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvBookingTime = itemView.findViewById(R.id.tvBookingTime);
            tvStaffName = itemView.findViewById(R.id.tvStaffName);
            btnCounter = itemView.findViewById(R.id.btnCounter);
            btnReject = itemView.findViewById(R.id.btnReject);
            btnAccept = itemView.findViewById(R.id.btnAccept);

            btnCounter.setOnClickListener(this);
            btnReject.setOnClickListener(this);
            btnAccept.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btnAccept :
                    Bookings bookingInfo = artistsList.get(getAdapterPosition());
                    if (bookingListener != null) {
                        bookingListener.onActionClick(getAdapterPosition(),bookingInfo,"accept");
                    }
                    break;
                case R.id.btnReject :
                    Bookings info = artistsList.get(getAdapterPosition());
                    if (bookingListener != null) {
                        bookingListener.onActionClick(getAdapterPosition(),info,"reject");
                    }
                    break;
                case R.id.btnCounter :
                    MyToast.getInstance(context).showDasuAlert("Under development...");
                    break;
            }
        }
    }

}