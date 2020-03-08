package fr.ace.mareu.ui;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;

public class MeetingRecyclerViewAdapter extends RecyclerView.Adapter<MeetingRecyclerViewAdapter.ViewHolder> {

    // Adapter
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
