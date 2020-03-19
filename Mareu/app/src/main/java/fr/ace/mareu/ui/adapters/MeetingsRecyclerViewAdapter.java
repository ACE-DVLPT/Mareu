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

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.utils.events.DeleteMeetingEvent;

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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Meeting meeting = mMeetingsList.get(position);
        holder.mTextViewLine1.setText(meeting.getTopic() + " - " + meeting.getPlace() +  " (" + meeting.getDuration() + ")");
        holder.mTextViewLine2.setText(meeting.getDate() + " - " + meeting.getHour() );
        holder.mTextViewLine3.setText(meeting.getMembersByString());

        // Listeners
        holder.mImageViewDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeetingsList.size();
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_meeting_colored_circle)
        ImageView mImageViewColoredCircle;
        @BindView(R.id.item_meeting_delete_button)
        ImageView mImageViewDeleteButton;
        @BindView(R.id.item_meeting_txt_line1)
        TextView mTextViewLine1;
        @BindView(R.id.item_meeting_txt_line2)
        TextView mTextViewLine2;
        @BindView(R.id.item_meeting_txt_line3)
        TextView mTextViewLine3;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
