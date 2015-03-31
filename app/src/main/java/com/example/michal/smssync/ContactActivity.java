package com.example.michal.smssync;


import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import library.SMSSyncLibrary;

public class ContactActivity extends ListActivity {
    Cursor cursor1;
    ListView lv;

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        cursor1 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        startManagingCursor(cursor1);


        String[] from = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone._ID}; // get the list items for the listadapter could be TITLE or URI

        int[] to = {android.R.id.text1, android.R.id.text2}; // sets the items from above string to listview
        // new listadapter, created to use android checked template
        SimpleCursorAdapter listadapter = new SimpleCursorAdapter(this,
                android.R.layout.simple_list_item_2,
                cursor1,
                from,
                to);


        setListAdapter(listadapter);

        // adds listview so I can get data from it
        lv = getListView();
        lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos,
                                    long arg3) {

                cursor1.moveToPosition(pos);
                String number = cursor1.getString(cursor1.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                Intent intentNumber = new Intent();
                intentNumber.putExtra("PHONE_NUMBER",number);
                setResult(SMSSyncLibrary.CONTACT_LIST_REQUEST_CODE,intentNumber);
                finish();
            }
        });

    }

}

