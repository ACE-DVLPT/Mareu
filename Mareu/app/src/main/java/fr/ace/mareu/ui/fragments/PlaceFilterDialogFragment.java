package fr.ace.mareu.ui.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import java.util.ArrayList;
import java.util.List;

import fr.ace.mareu.R;
import fr.ace.mareu.api.ApiService;
import fr.ace.mareu.model.MeetingRoom;
import fr.ace.mareu.utils.di.DI;

public class PlaceFilterDialogFragment extends DialogFragment {

    private Spinner mSpinnerPlace;
    private List<MeetingRoom> mMeetingRoomList;
    private ApiService mApiService;
    private OnPlaceSetListener mListener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_filter_place, null);

        mSpinnerPlace = view.findViewById(R.id.fragment_dialog_filter_place_spinner_place);

        mMeetingRoomList = new ArrayList<>();
        mApiService = DI.getApiService();

        setMeetingRoomList();

        builder.setView(view)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String place = mSpinnerPlace.getSelectedItem().toString();
                        mListener.onPlaceSet(place);
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
            mListener = (OnPlaceSetListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement PlaceFilterDialogListener");
        }
    }

    public void setMeetingRoomList(){
        mMeetingRoomList = mApiService.getMeetingRoomList();

        String defaultValue = "- Lieu -";
        if(!(mMeetingRoomList.get(0).getName() == defaultValue)){
            mMeetingRoomList.add(0, new MeetingRoom(defaultValue,""));
        }

        ArrayAdapter<MeetingRoom> adapter;
        adapter= new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item,mMeetingRoomList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mSpinnerPlace.setAdapter(adapter);
        mSpinnerPlace.setSelection(0);
    }

    public interface OnPlaceSetListener {
        void onPlaceSet(String place);
    }
}
