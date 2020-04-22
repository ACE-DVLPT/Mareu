package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;

import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.model.Meeting;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.ui.fragments.DateDialogFragment;
import fr.ace.mareu.ui.fragments.DurationDialogFragment;
import fr.ace.mareu.ui.fragments.HourDialogFragment;
import fr.ace.mareu.utils.di.DI;
import fr.ace.mareu.utils.di.CustomTokenizer;
import fr.ace.mareu.utils.events.AddMeetingEvent;

public class MeetingCreatorActivity
        extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DurationDialogFragment.OnDurationSetListener, View.OnClickListener {

    private ApiService mApiService;
    private Meeting mMeeting;
    private List<MeetingRoom> mMeetingRoomList;
    private List<String> mMemberReminderList;

    Toolbar mToolbar;
    EditText mEditTextTopic;
    Spinner mSpinnerPlace;
    TextView mTextViewDate;
    TextView mTextViewHour;
    TextView mTextViewDuration;
    MultiAutoCompleteTextView mMultiAutoCompleteTextViewMembers;
    Button mButtonCancellation;
    Button mButtonValidation;
    ImageButton mImageButtonAddEmail;
    ChipGroup mChipGroupEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creator);

        mToolbar = findViewById(R.id.activity_meeting_creator_button_toolbar);
        mEditTextTopic = findViewById(R.id.activity_meeting_creator_editText_topic);
        mSpinnerPlace = findViewById(R.id.activity_meeting_creator_spinner_place);
        mTextViewDate = findViewById(R.id.activity_meeting_creator_text_view_date);
        mTextViewHour = findViewById(R.id.activity_meeting_creator_text_view_hour);
        mTextViewDuration = findViewById(R.id.activity_meeting_creator_text_view_duration);
        mMultiAutoCompleteTextViewMembers = findViewById(R.id.activity_meeting_creator_multi_auto_complete_text_view_members);
        mButtonCancellation = findViewById(R.id.activity_meeting_creator_button_cancellation);
        mButtonValidation = findViewById(R.id.activity_meeting_creator_button_validation);
        mImageButtonAddEmail = findViewById(R.id.activity_meeting_creator_button_add_email);
        mChipGroupEmail = findViewById(R.id.activity_meeting_creator_chip_group_email);

        // Data
        mApiService = DI.getApiService();
        mMeeting = new Meeting(null, null, null, null, null, new ArrayList<>(Arrays.asList((""))));

        setToolbar();
        setMeetingRoomList();
        setOnClickListener();
        setMemberReminderList();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
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

        mMultiAutoCompleteTextViewMembers.setAdapter(adapter);
        mMultiAutoCompleteTextViewMembers.setTokenizer(new CustomTokenizer());
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
            }
        });

        mTextViewDuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment durationDialog = new DurationDialogFragment();
                durationDialog.show(getSupportFragmentManager(), "durationDialog");
            }
        });

        mImageButtonAddEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEmailToChipGroup(mMultiAutoCompleteTextViewMembers.getText().toString());
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

    public void addEmailToChipGroup(String email){
        if (checkEmailStructure(email)) {
            addEmail(email);

            mMultiAutoCompleteTextViewMembers.setText("");
            hideSoftKeyboard();
        }
    }

    public void addEmail(String email){
        Chip chip = new Chip(this);
        chip.setText(email);
        chip.setCloseIconVisible(true);
        chip.setCheckable(false);
        chip.setClickable(false);
        chip.setOnCloseIconClickListener(MeetingCreatorActivity.this);
        mChipGroupEmail.addView(chip);
        mChipGroupEmail.setVisibility(View.VISIBLE);
    }

    public ArrayList<String> getTextFromChipGroup(ChipGroup chipGroup){
        ArrayList<String> list = new ArrayList<>();
        Chip chip;
        for (int i=0; i < chipGroup.getChildCount(); i++){
            chip = (Chip) chipGroup.getChildAt(i);
            list.add(chip.getText().toString());
        }
        return list;
    }

    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    public Boolean checkEmailStructure(String string){
        boolean emailValid = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String emailErrorMessage = "Veuillez saisir une adresse email valide";

        if (string.isEmpty()){
            Toast.makeText(getApplicationContext(), emailErrorMessage,Toast.LENGTH_SHORT).show();
            emailValid = false;
        } else {
            if (string.trim().matches(emailPattern)) {
                emailValid = true;
            } else {
                Toast.makeText(getApplicationContext(), emailErrorMessage,Toast.LENGTH_SHORT).show();
                emailValid = false;
            }
        }
        return emailValid;
    }

    @Override
    public void onClick(View view) {
        Chip chip = (Chip) view;
        mChipGroupEmail.removeView(chip);
    }

    private void passCharacters() {
        mMeeting.setTopic(mEditTextTopic.getText().toString());
        mMeeting.setPlace(mSpinnerPlace.getSelectedItem().toString());
        mMeeting.setDate(mTextViewDate.getText().toString());
        mMeeting.setHour(mTextViewHour.getText().toString());
        mMeeting.setDuration(mTextViewDuration.getText().toString());
        mMeeting.setMembers(getTextFromChipGroup(mChipGroupEmail));
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

    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        mTextViewHour.setText(String.format("%02d",hour) + "h" + String.format("%02d",minute));
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

    @Override
    public void onDurationSet(int hour, int minute) {
        setTextViewDuration(hour,minute);
    }

    @Subscribe
    public void onAddMeetingEvent(AddMeetingEvent event){
        mApiService.addMeeting(event.mMeeting);
    }

}
