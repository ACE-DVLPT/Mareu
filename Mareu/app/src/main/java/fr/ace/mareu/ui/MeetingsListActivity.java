package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.di.DI;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.Member;

public class MeetingsListActivity extends AppCompatActivity {

    @BindView(R.id.activity_meetings_list_btn_add_meeting)
    FloatingActionButton mBtnAddMeeting;
    @BindView(R.id.activity_meeting_list_recycler_view)
    RecyclerView mRecyclerView;

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
        mAdapter = new MeetingRecyclerViewAdapter(mMeetingsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
