package com.example.rokkha

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import java.util.ArrayList;
import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.provider.ContactsContract
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.PhoneLookup;
import android.provider.ContactsContract.RawContacts;

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast

class addcontacts : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addcontacts)



    }

}
