package Contact;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

import com.example.michal.smssync.MainActivity;

/**
 * Created by Jan on 11. 3. 2015.
 */

public class ContactObserver extends ContentObserver {
    public ContactObserver() {
        super(new Handler());
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        MainActivity.getMainActivity().loadContact();

    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }
}
