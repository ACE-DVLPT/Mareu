package fr.ace.mareu.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import fr.ace.mareu.R;

public class DurationDialogFragment extends DialogFragment {

    private NumberPicker mHourPicker;
    private NumberPicker mMinutePicker;
    private OnDurationSetListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AlertDialogCustom));


        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_time_picker, null);

        mHourPicker = view.findViewById(R.id.fragment_dialog_duration_number_picker_hours);
        mMinutePicker = view.findViewById(R.id.fragment_dialog_duration_number_picker_minutes);

        mHourPicker.setMinValue(0);
        mHourPicker.setMaxValue(23);

        mMinutePicker.setMinValue(0);
        mMinutePicker.setMaxValue(59);
        mMinutePicker.setValue(45);

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        int hour = mHourPicker.getValue();
                        int minute = mMinutePicker.getValue();
                        mListener.onDurationSet(hour,minute);
                    }
                })
                .setNegativeButton("ANNULER", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        try {
            mListener = (DurationDialogFragment.OnDurationSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement PlaceFilterDialogListener");
        }
    }

    public interface OnDurationSetListener {
        void onDurationSet(int hour, int minute);
    }
}
