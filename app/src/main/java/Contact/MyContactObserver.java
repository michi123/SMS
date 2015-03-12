package Contact;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;

/**
 * Created by Jan on 11. 3. 2015.
 */

public class MyContactObserver extends ContentObserver {
    public MyContactObserver() {
        super(new Handler());
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        System.out.println("Změna kontaktů v DB !!!");

    }

    @Override
    public boolean deliverSelfNotifications() {
        return true;
    }
}
