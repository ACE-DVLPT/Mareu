package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);

        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        EventBus.getDefault().register(this);


        mMeetingsList = new ArrayList<>(DI.getApiService().getMeetingsList());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new MeetingsRecyclerViewAdapter(mMeetingsList);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        refreshRecycleViewAdapter(mAdapter);

    }

    public void displayMessageIfRecyclerViewIsEmpty() {
        if (mMeetingsList.isEmpty()){
            mTextViewEmptyView.setVisibility(View.VISIBLE);
        } else {
            mTextViewEmptyView.setVisibility(View.INVISIBLE);
        }
    }

    public void refreshRecycleViewAdapter(RecyclerView.Adapter adapter) {
        adapter.notifyDataSetChanged();
        displayMessageIfRecyclerViewIsEmpty();
    }

    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event){
        mMeetingsList.remove(event.mMeeting);
        refreshRecycleViewAdapter(mAdapter);
    }

}
