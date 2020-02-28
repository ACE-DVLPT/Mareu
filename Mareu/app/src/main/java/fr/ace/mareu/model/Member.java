package fr.ace.mareu.model;

import android.provider.ContactsContract;

public class Member {

    private ContactsContract.CommonDataKinds.Email mEmail;

    public Member(ContactsContract.CommonDataKinds.Email email) {
        mEmail = email;
    }

    public ContactsContract.CommonDataKinds.Email getEmail() {
        return mEmail;
    }

    public void setEmail(ContactsContract.CommonDataKinds.Email email) {
        mEmail = email;
    }
}
