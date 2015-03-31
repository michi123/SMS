package Contact;



    import android.content.ContentResolver;
    import android.database.Cursor;
    import android.provider.ContactsContract;

    import com.example.michal.smssync.Messenger;

    import Client.Client;
    import dojson.ContactCo;
    import dojson.JSONBroker;
    import dojson.MsgPack;
    import dojson.ObjectFactory;

public class GetContactsDemo {
        /** Called when the activity is first created. */
        JSONBroker broker = new JSONBroker();
        ObjectFactory factory = new ObjectFactory();



        public void readContacts(ContentResolver cr){
            String name="";
            String phone="";
            String email="";
            Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                    null, null, null, null);
            if (cur.getCount() > 0) {
                while (cur.moveToNext()) {

                    //name
                    String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                    name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                        System.out.println("name : " + name );
                        // get the phone number
                        Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                                new String[]{id}, null);
                        while (pCur.moveToNext()) {
                            phone = pCur.getString(
                                    pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            System.out.println("phone" + phone);
                        }
                        pCur.close();
                        // get email
                        Cursor emailCur = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (emailCur.moveToNext()) {
                            // This would allow you get several email addresses
                            // if the email addresses were stored in an array
                            email = emailCur.getString(
                                    emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                            System.out.println("Email " + email);
                        }
                        emailCur.close();
                        /*
                        String noteWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] noteWhereParams = new String[]{id,
                                ContactsContract.CommonDataKinds.Note.CONTENT_ITEM_TYPE};
                        Cursor noteCur = cr.query(ContactsContract.Data.CONTENT_URI, null, noteWhere, noteWhereParams, null);
                        if (noteCur.moveToFirst()) {
                            String note = noteCur.getString(noteCur.getColumnIndex(ContactsContract.CommonDataKinds.Note.NOTE));
                            System.out.println("Note " + note);
                        }
                        noteCur.close();*/

                        //Get Postal Address....
/*
                        String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] addrWhereParams = new String[]{id,
                                ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                        Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, null, null, null);
                        while(addrCur.moveToNext()) {
                            String poBox = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POBOX));
                            String street = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                            String city = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                            String state = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                            String postalCode = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                            String country = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                            String type = addrCur.getString(
                                    addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));
                            // Do something with these....
                        }
                        addrCur.close();
                        // Get Instant Messenger.........
 /*                       String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] imWhereParams = new String[]{id,
                                ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                        Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, imWhere, imWhereParams, null);
                        if (imCur.moveToFirst()) {
                            String imName = imCur.getString(
                                    imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                            String imType;
                            imType = imCur.getString(
                                    imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                        }
                        imCur.close();
                        // Get Organizations.........
                        String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                        String[] orgWhereParams = new String[]{id,
                                ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                        Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                                null, orgWhere, orgWhereParams, null);
                        if (orgCur.moveToFirst()) {
                            String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                            String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                        }
                        orgCur.close();*/
                        newContact(email,name,phone);
                        System.out.println("poslani do metody: "+email+" "+name+" "+phone);
                    }
                }
            }
        }
    /*
    private void newContact(String email, String name, String phone) {
        Messenger messenger = new Messenger();
        ContactCo conCo = factory.createContact(
                email,
                name,
                name,
                name,
                phone);
        MsgPack packedContact = factory.createMsgPack(conCo, conCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.CON);
        messenger.send(broker.contactToJson(packedContact));
    }
    */

    private void newContact(String email, String name, String phone) {
        ContactCo conCo = factory.createContact(
                email,
                name,
                name,
                name,
                phone);
        MsgPack packedContact = factory.createMsgPack(conCo, conCo.hashCode(), MsgPack.ActionType.NEW, MsgPack.ObjectType.CON);
        new Messenger(broker.contactToJson(packedContact),false);
    }
}
