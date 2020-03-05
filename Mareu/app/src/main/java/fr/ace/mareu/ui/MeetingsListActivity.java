package fr.ace.mareu.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import fr.ace.mareu.R;

public class MeetingsListActivity extends AppCompatActivity {

    @BindView(R.id.activity_meetings_list_btn_add_meeting)
    FloatingActionButton mBtnAddMeeting;
    @BindView(R.id.fragment_meeting_txt_line1)
    TextView mTxtLine1;
    @BindView(R.id.fragment_meeting_txt_line2)
    TextView mTxtLine2;
    @BindView(R.id.fragment_meeting_colored_circle)
    ImageView mColoredCircle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meetings_list);
        ButterKnife.bind(this);

    }
}
