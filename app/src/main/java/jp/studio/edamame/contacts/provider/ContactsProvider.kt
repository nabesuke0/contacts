package jp.studio.edamame.contacts.provider

import android.database.DatabaseUtils
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import jp.studio.edamame.contacts.ContactsApplication
import android.util.Log
import jp.studio.edamame.contacts.model.Contact
import jp.studio.edamame.contacts.model.MailAddress
import jp.studio.edamame.contacts.model.Phone


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
            val application = ContactsApplication.getApp()
            val cursor = application.contentResolver.query(
                    ContactsContract.CommonDataKinds.Contactables.CONTENT_URI,
                    projection,
                    selection,
                    selectionArgs,
                    sortOrder
            )

            Log.e("ContactsProvider", DatabaseUtils.dumpCursorToString(cursor))

            val res = application.resources

            val mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
            val idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
            val nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            val dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA)
            val typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE)

            val contactMap: Map<Long, Contact> = emptyMap()

            while(cursor.moveToNext()) {
                val id = cursor.getLong(idIdx)
                val mimeType = cursor.getString(mimeTypeIdx)
                val type = cursor.getInt(typeIdx)

                var contact = contactMap[id]?.let { it } ?: Contact(id, cursor.getString(nameIdx))

                when (mimeType) {
                    CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                        val typeLabel = ContactsContract.CommonDataKinds.Phone.getTypeLabel(res, type, "") as String
                        val phone = Phone(cursor.getString(dataIdx), typeLabel)

                        contact.phoneList.add(phone)
                    }

                    CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                        val typeLabel = ContactsContract.CommonDataKinds.Email.getTypeLabel(res, type, "") as String
                        val emailAddress = MailAddress(cursor.getString(dataIdx), typeLabel)

                        contact.emailAddresList.add(emailAddress)
                    }
                }
            }
        }
    }
}