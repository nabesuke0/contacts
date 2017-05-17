package jp.studio.edamame.contacts.provider

import android.database.DatabaseUtils
import android.provider.ContactsContract
import jp.studio.edamame.contacts.ContactsApplication
import android.util.Log


/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class ContactsProvider {
    companion object {
        var projection = arrayOf(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE
        )
        var selection = ContactsContract.Data.MIMETYPE + " in (?, ?)"
        var selectionArgs = arrayOf(
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
        var sortOrder = ContactsContract.Contacts.SORT_KEY_ALTERNATIVE

        fun query() {
            val cursor = ContactsApplication.getApp().contentResolver.query(
                    ContactsContract.CommonDataKinds.Contactables.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            )

            Log.e("ContactsProvider", DatabaseUtils.dumpCursorToString(cursor))
        }
    }
}