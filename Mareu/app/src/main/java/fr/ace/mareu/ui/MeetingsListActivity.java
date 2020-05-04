package fr.ace.mareu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.ui.fragments.DateDialogFragment;
import fr.ace.mareu.ui.fragments.PlaceFilterDialogFragment;
import fr.ace.mareu.utils.di.DI;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.ui.adapters.MeetingsRecyclerViewAdapter;
import fr.ace.mareu.utils.events.DisplayMeetingMembersEvent;
import fr.ace.mareu.utils.events.DeleteMeetingEvent;

/**
 * Activity called when the application started
 * Used to display all future meetings
 */
public class MeetingsListActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, View.OnClickListener, PlaceFilterDialogFragment.OnPlaceSetListener {

    /** Instance of API */
    ApiService mApiService;

    /** Custom toolbar */
    private Toolbar mToolbar;
    /** Allows to start MeetingCreatorActivity */
    private FloatingActionButton mBtnAddMeeting;
    /** Allows to display a list of meetings */
    private RecyclerView mRecyclerView;
    /** Text displayed when no meetings available */
    private TextView mTextViewEmptyView;
    /** Allows to display active filters by chips */
    private ChipGroup mChipGroupFilters;
    /** Allows to know if place filter already exist */
    private Boolean mPlaceFilterAlreadyExist = false;
    /** Allows to know if date filter already exist */
    private Boolean mDateFilterAlreadyExist = false;

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    /** Meeting list used on the recycler view */
    private ArrayList<Meeting> mMeetingsList;
    /** List of filters used to call with the API */
    private ArrayList<String> mFiltersList;

    // Orientation LandScape
    /** Container used to set visibility. Allows to hide TextView when no meeting displayed on the recycler view */
    private LinearLayout mLinearLayoutContainer;
    /** allows to display the members of the selected meeting */
    private TextView mTextViewMembersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);

        mBtnAddMeeting = findViewById(R.id.activity_meetings_list_btn_add_meeting);
        mRecyclerView = findViewById(R.id.recyclerview_meetings_list);
        mTextViewEmptyView = findViewById(R.id.activity_meetings_list_txt_empty_view);
        mToolbar = findViewById(R.id.activity_meetings_list_toolbar);
        mChipGroupFilters = findViewById(R.id.activity_meetings_list_chip_group_filters);
        // Orientation LandScape
        mLinearLayoutContainer = findViewById(R.id.activity_meetings_list_linear_layout_container);
        mTextViewMembersList = findViewById(R.id.fragment_member_list_text_view);

        setSupportActionBar(mToolbar);

        mMeetingsList = new ArrayList<>();
        mFiltersList = new ArrayList<>();

        mApiService = DI.getApiService();

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.filter, menu);
        return true;
    }

    /**
     * Filter menu
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_filter_place:
                if (mPlaceFilterAlreadyExist){
                    Toast.makeText(this, "Le filtre par salle est déjà actif", Toast.LENGTH_SHORT).show();
                } else {
                    DialogFragment placeDialog = new PlaceFilterDialogFragment();
                    placeDialog.show(getSupportFragmentManager(), "placeDialog");
                }
                return true;
            case R.id.menu_filter_date:
                if (mDateFilterAlreadyExist){
                    Toast.makeText(this, "Le filtre par date est déjà actif", Toast.LENGTH_SHORT).show();
                } else {
                    DialogFragment dateDialog = new DateDialogFragment();
                    dateDialog.show(getSupportFragmentManager(), "dateDialog");
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Used to filter by place
     * @param place
     */
    @Override
    public void onPlaceSet(String place) {
        mPlaceFilterAlreadyExist = true;
        mFiltersList.add(place);
        addFilterToChipGroup(place);
        initList();
    }

    /**
     * Used to filter by date
     * @param datePicker
     * @param year
     * @param month
     * @param day
     */
    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        mDateFilterAlreadyExist = true;
        mFiltersList.add(dateString.toLowerCase());
        addFilterToChipGroup(dateString);
        initList();
    }

    /**
     * Add filter to the chip group
     * @param filter
     */
    public void addFilterToChipGroup(String filter){
        Chip chip = new Chip(this);
        chip.setText(filter);
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setOnCloseIconClickListener(this);
        mChipGroupFilters.addView(chip);
        mChipGroupFilters.setVisibility(View.VISIBLE);
    }

    /**
     * Used to remove the chip
     * if performed, this method update the filterList
     * @param view
     */
    @Override
    public void onClick(View view) {
        Chip chip = (Chip) view;
        mChipGroupFilters.removeView(chip);
        Boolean placeFilterRemoved = false;
        for (int i = 0 ; i < mFiltersList.size() ; i++){
            if (chip.getText().toString() == mFiltersList.get(i)){
                for (int j = 0; j < mApiService.getMeetingRoomsList().size() ; j++){
                    if (mApiService.getMeetingRoomsList().get(j).getName().equals(mFiltersList.get(i))){
                        placeFilterRemoved = true;
                    }
                }
                mFiltersList.remove(i);
            }
        }
        if (placeFilterRemoved){
            mPlaceFilterAlreadyExist = false;
        }else {
            mDateFilterAlreadyExist = false;
        }
        initList();
    }

    /**
     * Method used to start MeetingCreatorActivity
     */
    public void openMeetingCreatorActivity(){
        Intent intent = new Intent(this , MeetingCreatorActivity.class);
        startActivity(intent);
    }

    /**
     * notifies about the phone orientation status
     * @return a {@link Boolean}, if true the phone is in landscape orientation
     */
    public Boolean orientationLandScape(){
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Allows to display the message when recycler view is empty
     */
    public void displayMessageIfRecyclerViewIsEmpty() {
        if (orientationLandScape()){
            if (mMeetingsList.isEmpty()){
                mTextViewEmptyView.setVisibility(View.VISIBLE);
                mLinearLayoutContainer.setVisibility(View.GONE);
            } else {
                mTextViewEmptyView.setVisibility(View.GONE);
                mLinearLayoutContainer.setVisibility(View.VISIBLE);
            }
        } else {
            if (mMeetingsList.isEmpty()){
                mTextViewEmptyView.setVisibility(View.VISIBLE);
            } else {
                mTextViewEmptyView.setVisibility(View.GONE);
            }
        }
    }

    /**
     * Allows to set the list of members when the phone is in landscape orientation
     * @param arrayList
     * @param textView
     */
    public void setMembersListOnTextView(ArrayList arrayList, TextView textView){
        if (orientationLandScape()) {
            StringBuilder stringBuilder = new StringBuilder();

            for (int i = 0; i < arrayList.size(); i++) {

                if (i == (arrayList.size() - 1)) {
                    stringBuilder.append(arrayList.get(i));
                } else {
                    stringBuilder.append(arrayList.get(i) + "\n");
                }
            }
            textView.setText(stringBuilder.toString());
        }
    }

    /**
     * Used to init the recyclerview with the list from API service
     */
    public void initList() {
        mMeetingsList.clear();
        mMeetingsList.addAll(mApiService.getMeetingsList(mFiltersList));
        mAdapter.notifyDataSetChanged();
        displayMessageIfRecyclerViewIsEmpty();

        // Orientation LandScape
        if (orientationLandScape()){
            mTextViewMembersList.setText("");
        }
    }

    /**
     * Fired if the user clicks on a delete button
     * @param event
     */
    @Subscribe
    public void onDeleteMeetingEvent(DeleteMeetingEvent event){
        mApiService.removeMeeting(event.mMeeting);
        initList();
    }

    /**
     * Fired if the user clicks on a meeting
     * @param event
     */
    @Subscribe
    public void onClickItemInRecyclerViewEvent(DisplayMeetingMembersEvent event){
        setMembersListOnTextView(event.mMeeting.getMembers(), mTextViewMembersList);
    }
}
