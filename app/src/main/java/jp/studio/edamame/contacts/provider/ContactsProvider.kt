package jp.studio.edamame.contacts.provider

import android.content.ContentUris
import android.database.DatabaseUtils
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import jp.studio.edamame.contacts.ContactsApplication
import android.util.Log
import jp.studio.edamame.contacts.model.Contact
import jp.studio.edamame.contacts.model.MailAddress
import jp.studio.edamame.contacts.model.Phone
import timber.log.Timber
import java.io.ByteArrayInputStream
import java.io.InputStream


/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class ContactsProvider {
    companion object {
        private var projection = arrayOf(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.Data.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.Contacts.SORT_KEY_PRIMARY,
                ContactsContract.CommonDataKinds.Contactables.DATA,
                ContactsContract.CommonDataKinds.Contactables.TYPE
        )
        private var selection = ContactsContract.Data.MIMETYPE + " in (?, ?)"

        private var selectionArgs = arrayOf(
                ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)

        private var sortOrder = ContactsContract.Contacts.SORT_KEY_PRIMARY

        fun queryAll() : MutableList<Contact> {
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
            val sortKeyIdx = cursor.getColumnIndex(ContactsContract.Contacts.SORT_KEY_PRIMARY)

            val contactMap: MutableMap<Long, Contact> = mutableMapOf()

            while(cursor.moveToNext()) {
                val id = cursor.getLong(idIdx)
                val mimeType = cursor.getString(mimeTypeIdx)
                val type = cursor.getInt(typeIdx)

                val contact =
                        if (contactMap.containsKey(id)) contactMap[id]!!
                        else {
                            contactMap[id] = Contact(id, cursor.getString(nameIdx), cursor.getString(sortKeyIdx))
                            contactMap[id]!!
                        }

                when (mimeType) {
                    CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                        val typeLabel = ContactsContract.CommonDataKinds.Phone.getTypeLabel(res, type, "") as String
                        val phone = Phone(cursor.getString(dataIdx), typeLabel)

                        contact.phoneList.add(phone)
                    }

                    CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                        val typeLabel = ContactsContract.CommonDataKinds.Email.getTypeLabel(res, type, "") as String
                        val emailAddress = MailAddress(cursor.getString(dataIdx), typeLabel)

                        contact.emailAddressList.add(emailAddress)
                    }
                }
            }

            return contactMap.values.sortedBy { it.displayName }.toMutableList()
        }

        fun openPhoto(contactId: Long): InputStream? {
            val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
            val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
            val cursor = ContactsApplication.getApp().contentResolver.query(photoUri,
                    arrayOf(ContactsContract.Contacts.Photo.PHOTO), null, null, null) ?: return null

            cursor.use { cursor ->
                if (cursor.moveToFirst()) {
                    val data = cursor.getBlob(0)
                    if (data != null) {
                        return ByteArrayInputStream(data)
                    }
                }
            }
            return null
        }
    }
}