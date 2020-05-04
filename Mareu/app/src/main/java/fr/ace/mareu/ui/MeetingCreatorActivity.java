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

/**
 * Activity called to create a new meeting
 */
public class MeetingCreatorActivity
        extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, DurationDialogFragment.OnDurationSetListener, View.OnClickListener {

    /** Instance of API */
    private ApiService mApiService;
    /** Meeting that will be created if validation */
    private Meeting mMeeting;
    /** List of meeting rooms must be displayed on the place spinner */
    private List<MeetingRoom> mMeetingRoomList;
    /** List of members proposed on the multi auto complete text view */
    private List<String> mMemberReminderList;

    /** Custom toolbar*/
    Toolbar mToolbar;
    /** Allows to type the topic */
    EditText mEditTextTopic;
    /** Allows to select the place */
    Spinner mSpinnerPlace;
    /** Allows to select the date */
    TextView mTextViewDate;
    /** Allows to select the start time */
    TextView mTextViewHour;
    /** Allows to select the duration */
    TextView mTextViewDuration;
    /** Allows to type or select several members */
    MultiAutoCompleteTextView mMultiAutoCompleteTextViewMembers;
    /** Allows to finish the activity with no actions */
    Button mButtonCancellation;
    /** Allows to validates the characteristics of the meeting */
    Button mButtonValidation;
    /** Allows to add the email to the chip group*/
    ImageButton mImageButtonAddEmail;
    /** Allows to display members by chips */
    ChipGroup mChipGroupEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_creator);

        // Find view
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
        mMeeting = new Meeting(null, null, initCalendar(), initCalendar(), new ArrayList<>(Arrays.asList((""))));

        // Initialization
        setToolbar();
        setMeetingRoomList();
        setMemberReminderList();
        setOnClickListener();
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

    /**
     * used for instantiation of an empty meeting
     * @return a {@link Calendar} with all parameters initialized with "0"
     */
    public Calendar initCalendar(){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar;
    }

    /**
     * Initialization of the toolbar
     */
    public void setToolbar() {
        mToolbar.setTitle("Création d'une Réunion");
        setSupportActionBar(mToolbar);
    }

    /**
     * Initialization of the place spinner
     */
    public void setMeetingRoomList(){
        mMeetingRoomList = mApiService.getMeetingRoomsList();
        String defaultValue = "- Lieu -";
        if(!(mMeetingRoomList.get(0).getName() == defaultValue)){
            mMeetingRoomList.add(0, new MeetingRoom(defaultValue));
        }
        ArrayAdapter<MeetingRoom> adapter;
        adapter= new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,mMeetingRoomList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerPlace.setAdapter(adapter);
        mSpinnerPlace.setSelection(0);
    }

    /**
     * Initialization of the multi auto complete text view with all members saved
     */
    public void setMemberReminderList(){
        mMemberReminderList = mApiService.getMembersReminderList();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,mMemberReminderList);
        mMultiAutoCompleteTextViewMembers.setAdapter(adapter);
        mMultiAutoCompleteTextViewMembers.setTokenizer(new CustomTokenizer());
    }

    /**
     * Initialization of all the setOnClickListener
     */
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
                if (allFieldsCompleted()){
                    passParameters(mMeeting);
                    if (mApiService.checkIfNoDuplicationMeeting(mMeeting)) {
                        EventBus.getDefault().post(new AddMeetingEvent(mMeeting));
                        finish();
                    } else {
                        Toast.makeText(MeetingCreatorActivity.this, "La salle de réunion est déjà prise", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        mButtonCancellation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * Verify if all fields are completed
     * @return a {@link Boolean} with the result
     */
    public Boolean allFieldsCompleted(){
        Boolean result = false;
        Calendar todayDate = Calendar.getInstance();
        if (
                mEditTextTopic.getText().toString().equals("") ||
                mSpinnerPlace.getSelectedItem() == mSpinnerPlace.getItemAtPosition(0) ||
                mTextViewDate.getText().toString().equals("") ||
                mTextViewHour.getText().toString().equals("") ||
                mTextViewDuration.getText().toString().equals("")
        ){
            Toast.makeText(MeetingCreatorActivity.this, "Tous les champs ne sont pas renseignés", Toast.LENGTH_SHORT).show();
        } else if (((mMeeting.getEndTime()).compareTo(todayDate)) < 0){
            Toast.makeText(MeetingCreatorActivity.this, "Date ou heure dépassée", Toast.LENGTH_SHORT).show();
        } else {
            result = true;
        }
        return result;
    }

    /**
     * Add the email typed on the multi auto complete text view to the chip group
     * Display an error if email structure is invalid
     * @param email to add
     */
    public void addEmailToChipGroup(String email){
        String emailErrorMessage = "Adresse email non valide";
        String existingMemberErrorMessage = "Le membre est déjà existant";
        if (checkEmailStructure(email)) {
            if (checkIfMemberAlreadyExist(email, mChipGroupEmail)){
                Chip chip = new Chip(this);
                chip.setText(email);
                chip.setCloseIconVisible(true);
                chip.setCheckable(false);
                chip.setClickable(false);
                chip.setOnCloseIconClickListener(MeetingCreatorActivity.this);
                mChipGroupEmail.addView(chip);
                mChipGroupEmail.setVisibility(View.VISIBLE);
                mMultiAutoCompleteTextViewMembers.setText("");
                hideSoftKeyboard();
            } else {
                Toast.makeText(getApplicationContext(), existingMemberErrorMessage,Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), emailErrorMessage,Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Allows to get the string contained in the chips displayed
     * @param chipGroup where we get the chips
     * @return an {@link ArrayList} of {@link String} the emails contained in the chips
     */
    public ArrayList<String> getTextFromChipGroup(ChipGroup chipGroup){
        ArrayList<String> list = new ArrayList<>();
        Chip chip;
        for (int i=0; i < chipGroup.getChildCount(); i++){
            chip = (Chip) chipGroup.getChildAt(i);
            list.add(chip.getText().toString());
        }
        return list;
    }

    /**
     * Allows to hide the keyboard displayed
     */
    public void hideSoftKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }
    }

    /**
     * Check if the string typed is an email
     * @param email to check
     * @return a {@link Boolean} with the result
     */
    public Boolean checkEmailStructure(String email){
        boolean emailValid = false;
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.isEmpty()){
            emailValid = false;
        } else {
            if (email.trim().matches(emailPattern)) {
                emailValid = true;
            } else {
                emailValid = false;
            }
        }
        return emailValid;
    }

    /**
     * check if email tested already exist on the chip group
     * @param email
     * @param chipGroup
     * @return a {@link Boolean} with the result
     */
    public Boolean checkIfMemberAlreadyExist(String email, ChipGroup chipGroup){
        boolean result = false;

        if (email.isEmpty()){
            result = false;
        } else {
            if (getTextFromChipGroup(chipGroup).contains(email)){
                result = false;
            } else {
                result = true;
            }
        }
        return result;
    }

    /**
     * Pass parameters from fields to the meeting
     * @param meeting whose parameters we wish to apply
     */
    private void passParameters(Meeting meeting) {
        meeting.setTopic(mEditTextTopic.getText().toString());
        meeting.setPlace(mSpinnerPlace.getSelectedItem().toString());
        meeting.setMembers(getTextFromChipGroup(mChipGroupEmail));
    }

    /**
     * Fired if the user clicks on add member button (to add member to the chip group)
     * @param view
     */
    @Override
    public void onClick(View view) {
        Chip chip = (Chip) view;
        mChipGroupEmail.removeView(chip);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /**
     * Set meeting date
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
        calendar.set(Calendar.HOUR_OF_DAY, mMeeting.getDate().get(Calendar.HOUR_OF_DAY));
        calendar.set(Calendar.MINUTE, mMeeting.getDate().get(Calendar.MINUTE));
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mMeeting.setDate(calendar);
        mTextViewDate.setText(mMeeting.getStringDate());
    }

    /**
     * Set meeting start time
     * @param timePicker
     * @param hour
     * @param minute
     */
    @Override
    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, mMeeting.getDate().get(Calendar.YEAR));
        calendar.set(Calendar.MONTH, mMeeting.getDate().get(Calendar.MONTH));
        calendar.set(Calendar.DAY_OF_MONTH, mMeeting.getDate().get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mMeeting.setDate(calendar);
        mTextViewHour.setText(mMeeting.getStringStartTime());
    }

    /**
     * set meeting duration
     * @param hour
     * @param minute
     */
    @Override
    public void onDurationSet(int hour, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 0);
        calendar.set(Calendar.MONTH, 0);
        calendar.set(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        mMeeting.setDuration(calendar);
        mTextViewDuration.setText(mMeeting.getStringDuration());
    }

    /**
     * Fired if the user clicks on validation button
     * @param event
     */
    @Subscribe
    public void onAddMeetingEvent(AddMeetingEvent event){
        mApiService.addMeeting(event.mMeeting);
    }
}
