package Contact;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michal on 25. 2. 2015.
 */

    public class ContactsObserver extends ContentObserver {
        private final static String TAG = ContactsObserver.class.getSimpleName();

        private Context ctx;
        private List<ContactsChangeListener> listeners = new ArrayList<ContactsChangeListener>();

        private ContactsObserver(Context ctx) {
            super(new Handler()); //nwm jestli je spravny import nahore
            this.ctx = ctx.getApplicationContext();
            ctx.getContentResolver()
                    .registerContentObserver(
                            ContactsContract.Contacts.CONTENT_URI,  // uri
                            false,                                  // notifyForDescendents
                            this);                                  // observer
        }

        @Override
        public void onChange(boolean selfChange) {
            Log.i(TAG, "Contacs change");
            for(ContactsChangeListener l : listeners){
                l.onContactsChange();
            }
        }

        @Override
        public boolean deliverSelfNotifications() {
            return false; // set to true does not change anything...
        }

        public static ContactsObserver register(Context ctx){
            Log.d(TAG, "register");
            return new ContactsObserver(ctx);
        }

        public void unregister(){
            Log.d(TAG, "unregister");
            ctx.getContentResolver().unregisterContentObserver(this);
        }

        public void addContactsChangeListener(ContactsChangeListener l){
            listeners.add(l);
        }

        public interface ContactsChangeListener{
            void onContactsChange();
        }
    }

