package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.utils.di.DI;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.ui.adapters.MeetingsRecyclerViewAdapter;
import fr.ace.mareu.utils.events.DeleteMeetingEvent;

public class MeetingsListActivity extends AppCompatActivity {

    @BindView(R.id.activity_meetings_list_btn_add_meeting)
    FloatingActionButton mBtnAddMeeting;
    @BindView(R.id.recyclerview_meetings_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.activity_meetings_list_txt_empty_view)
    TextView mTextViewEmptyView;
    @BindView(R.id.activity_meetings_list_toolbar)
    Toolbar mToolbar;

    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Meeting> mMeetingsList;

    ApiService mApiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);

        mApiService = DI.getApiService();

        mMeetingsList = mApiService.getMeetingsList();

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetingsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        initList();

        mBtnAddMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMeetingCreatorActivity();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        initList();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    public void openMeetingCreatorActivity(){
        Intent intent = new Intent(this , MeetingCreatorActivity.class);
        startActivity(intent);
    }

    public void displayMessageIfRecyclerViewIsEmpty() {
        if (mMeetingsList.isEmpty()){
            mTextViewEmptyView.setVisibility(View.VISIBLE);
        } else {
            mTextViewEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    public void initList() {
        mMeetingsList = mApiService.getMeetingsList();
        mAdapter.notifyDataSetChanged();
        displayMessageIfRecyclerViewIsEmpty();
    }

    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event){
        mApiService.deleteMeeting(event.mMeeting);
        initList();
    }

}
