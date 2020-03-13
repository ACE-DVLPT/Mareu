package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
import fr.ace.mareu.di.DI;
import fr.ace.mareu.model.Meeting;

public class MeetingsListActivity extends AppCompatActivity {

    @BindView(R.id.activity_meetings_list_btn_add_meeting)
    FloatingActionButton mBtnAddMeeting;
    @BindView(R.id.activity_meetings_list_recycler_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_meetings_list_txt_empty_view)
    TextView mTextViewEmptyView;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public ArrayList<Meeting> mMeetingsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        ButterKnife.bind(this);

        mMeetingsList = new ArrayList<>(DI.getApiService().getMeetingsList());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetingsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        displayMessageIfEmptyRecyclerView();
    }

    public void displayMessageIfEmptyRecyclerView() {
        if (mMeetingsList.isEmpty()){
            mTextViewEmptyView.setVisibility(View.VISIBLE);
        }
    }
}
