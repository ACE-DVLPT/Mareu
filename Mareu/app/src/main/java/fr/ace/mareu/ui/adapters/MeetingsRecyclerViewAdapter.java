package fr.ace.mareu.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import fr.ace.mareu.R;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.utils.events.DeleteMeetingEvent;
import fr.ace.mareu.utils.events.ClickOnItemInRecyclerViewEvent;

public class MeetingsRecyclerViewAdapter extends RecyclerView.Adapter<MeetingsRecyclerViewAdapter.ViewHolder> {

    // Adapter
    private final ArrayList<Meeting> mMeetingsList;

    public MeetingsRecyclerViewAdapter(ArrayList<Meeting> meetingsList) {
        mMeetingsList = meetingsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_meeting, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Meeting meeting = mMeetingsList.get(position);
        holder.mTextViewLine1.setText(meeting.getTopic() + " - " + meeting.getPlace());
        holder.mTextViewDuration.setText(" (" + meeting.getDuration() + ")");
        holder.mTextViewLine2.setText(meeting.getDate() + " - " + meeting.getHour() );
        holder.mTextViewLine3.setText(setTextViewLine3(meeting.getMembers()));

        // Listeners
        holder.mImageViewDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ClickOnItemInRecyclerViewEvent(meeting, position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingsList.size();
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageViewColoredCircle;
        ImageView mImageViewDeleteButton;
        TextView mTextViewLine1;
        TextView mTextViewDuration;
        TextView mTextViewLine2;
        TextView mTextViewLine3;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageViewColoredCircle = itemView.findViewById(R.id.item_meeting_colored_circle);
            mImageViewDeleteButton = itemView.findViewById(R.id.item_meeting_delete_button);
            mTextViewLine1 = itemView.findViewById(R.id.item_meeting_txt_line1);
            mTextViewDuration = itemView.findViewById(R.id.item_meeting_txt_Duration);
            mTextViewLine2 = itemView.findViewById(R.id.item_meeting_txt_line2);
            mTextViewLine3 = itemView.findViewById(R.id.item_meeting_txt_line3);
        }
    }

    public String setTextViewLine3(ArrayList<String> list){
        StringBuilder stringBuilder = new StringBuilder();

        for ( int i = 0; i < list.size(); i++){

            if (i == (list.size()-1)){
                stringBuilder.append(list.get(i));
            } else {
                stringBuilder.append(list.get(i) + ", ");
            }
        }


        String line3 = stringBuilder.toString();
        return line3;
    }
}
