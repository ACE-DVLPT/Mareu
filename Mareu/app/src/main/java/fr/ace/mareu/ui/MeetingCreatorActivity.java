package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.ui.fragments.DateDialogFragment;
import fr.ace.mareu.ui.fragments.DurationDialogFragment;
import fr.ace.mareu.ui.fragments.HourDialogFragment;
import fr.ace.mareu.utils.di.DI;
import fr.ace.mareu.utils.events.AddMeetingEvent;

public class MeetingCreatorActivity
        extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private ApiService mApiService;
    private Meeting mMeeting;
    private List<MeetingRoom> mMeetingRoomList;
    private List<String> mMemberReminderList;
    int timePickerKey = 0;

    @BindView(R.id.activity_meeting_creator_button_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.activity_meeting_creator_editText_topic)
    EditText mEditTextTopic;
    @BindView(R.id.activity_meeting_creator_spinner_place)
    Spinner mSpinnerPlace;
    @BindView(R.id.activity_meeting_creator_text_view_date)
    TextView mTextViewDate;
    @BindView(R.id.activity_meeting_creator_text_view_hour)
    TextView mTextViewHour;
    @BindView(R.id.activity_meeting_creator_text_view_duration)
    TextView mTextViewDuration;
    @BindView(R.id.activity_meeting_creator_edit_text_member)
    MultiAutoCompleteTextView mAutoCompleteTextViewMembers;
    @BindView(R.id.activity_meeting_creator_button_cancellation)
    Button mButtonCancellation;
    @BindView(R.id.activity_meeting_creator_button_validation)
    Button mButtonValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creator);
        ButterKnife.bind(this);

        // Data
        mApiService = DI.getApiService();
        mMeeting = new Meeting(null, null, null, null, null, new ArrayList<>(Arrays.asList((""))));

        setToolbar();
        setMeetingRoomList();
        setOnClickListener();
        setMemberReminderList();
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

    public void setToolbar() {
        mToolbar.setTitle("Création d'une Réunion");
        setSupportActionBar(mToolbar);
    }

    public void setMemberReminderList(){
        mMemberReminderList = mApiService.getMembersReminderList();


        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,mMemberReminderList);

        mAutoCompleteTextViewMembers.setAdapter(adapter);
        mAutoCompleteTextViewMembers.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

    }

    public void setMeetingRoomList(){
        mMeetingRoomList = mApiService.getMeetingRoomList();

        String defaultValue = "- Lieu -";
        if(!(mMeetingRoomList.get(0).getName() == defaultValue)){
            mMeetingRoomList.add(0, new MeetingRoom(defaultValue,""));
        }

        ArrayAdapter<MeetingRoom> adapter;
        adapter= new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,mMeetingRoomList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerPlace.setAdapter(adapter);

        mSpinnerPlace.setSelection(0);
    }

    public void setOnClickListener(){
        mTextViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment dateDialog = new DateDialogFragment();
                dateDialog.show(getSupportFragmentManager(), "dateDialog");
            }
        });

        mTextViewHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment hourDialog = new HourDialogFragment();
                hourDialog.show(getSupportFragmentManager(), "hourDialog");
                timePickerKey = 1;
            }
        });

        mTextViewDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment durationDialog = new DurationDialogFragment();
                durationDialog.show(getSupportFragmentManager(), "durationDialog");
                timePickerKey = 2;
            }
        });

        mButtonValidation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passCharacters();
                EventBus.getDefault().post(new AddMeetingEvent(mMeeting));
                finish();
            }
        });

        mButtonCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void passCharacters() {
        mMeeting.setTopic(mEditTextTopic.getText().toString());
        mMeeting.setPlace(mSpinnerPlace.getSelectedItem().toString());
        mMeeting.setDate(mTextViewDate.getText().toString());
        mMeeting.setHour(mTextViewHour.getText().toString());
        mMeeting.setDuration(mTextViewDuration.getText().toString());
        mMeeting.setMembersByArrayList(stringToArrayConverter(mAutoCompleteTextViewMembers.getText().toString()));
    }

    private ArrayList<String> stringToArrayConverter(String string){
        String[] elements = string.split(",");
        List<String> list = Arrays.asList(elements);
        ArrayList<String> arrayList = new ArrayList<>(list);
        return arrayList;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        String dateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        mTextViewDate.setText(dateString);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        if (timePickerKey == 1) {
            mTextViewHour.setText(String.format("%02d",hour) + "h" + String.format("%02d",minute));
        } else if (timePickerKey == 2){
            setTextViewDuration(hour,minute);
        }
    }

    public void setTextViewDuration(int hour, int minutes){

        if (hour > 0){
            if (minutes == 0){
                mTextViewDuration.setText(hour + "h");
            } else {
                mTextViewDuration.setText(hour + "h" + String.format("%02d", minutes));
            }
        } else {
            mTextViewDuration.setText(String.format("%02d", minutes) + "min");
        }

    }

    @Subscribe
    public void onAddMeetingEvent(AddMeetingEvent event){
        mApiService.addMeeting(event.mMeeting);
    }

}
