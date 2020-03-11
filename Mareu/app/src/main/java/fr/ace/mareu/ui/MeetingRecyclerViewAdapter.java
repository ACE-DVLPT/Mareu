package fr.ace.mareu.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
import fr.ace.mareu.model.Meeting;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    // Adapter
    private final ArrayList<Meeting> mMeetingsList;

    public MeetingRecyclerViewAdapter(ArrayList<Meeting> meetingsList) {
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
        Meeting meeting = mMeetingsList.get(position);
        holder.mTextViewLine1.setText(meeting.getTopic() + " - " + meeting.getHour() + " - " + meeting.getPlace());
        holder.mTextViewLine2.setText(meeting.getMembersByString());
    }

    @Override
    public int getItemCount() {
        return mMeetingsList.size();
    }

    // ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.fragment_meeting_colored_circle)
        ImageView mImageViewColoredCircle;
        @BindView(R.id.fragment_meeting_delete_button)
        ImageView mImageViewDeleteButton;
        @BindView(R.id.fragment_meeting_txt_line1)
        TextView mTextViewLine1;
        @BindView(R.id.fragment_meeting_txt_line2)
        TextView mTextViewLine2;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
