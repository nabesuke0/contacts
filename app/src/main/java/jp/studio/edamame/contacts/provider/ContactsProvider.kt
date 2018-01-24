package jp.studio.edamame.contacts.provider

import android.content.ContentUris
import android.database.DatabaseUtils
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds
import jp.studio.edamame.contacts.ContactsApplication
import android.util.Log
import jp.studio.edamame.contacts.entities.ContactEntity
import jp.studio.edamame.contacts.entities.MailEntity
import jp.studio.edamame.contacts.entities.PhoneEntity
import java.io.ByteArrayInputStream
import java.io.InputStream


/**
 * Created by Watanabe on 2017/05/17.
 */
class ContactsProvider {
    companion object {
        private var dataProjection = arrayOf(
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

        fun queryAll() : MutableList<ContactEntity> {
            val application = ContactsApplication.getApp()

            var cursor = application.contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    arrayOf(ContactsContract.Contacts._ID,
                            ContactsContract.Contacts.DISPLAY_NAME,
                            ContactsContract.Contacts.SORT_KEY_PRIMARY,
                            ContactsContract.Contacts.PHOTO_URI),
                    null,
                    null,
                    sortOrder
            )

            Log.e("ContactsProvider", DatabaseUtils.dumpCursorToString(cursor))

            var idIdx = cursor.getColumnIndex(ContactsContract.Contacts._ID)
            var nameIdx = cursor.getColumnIndex("display_name")
            var sortKeyIdx = cursor.getColumnIndex(ContactsContract.Contacts.SORT_KEY_PRIMARY)

            val photoIdx = cursor.getColumnIndex(ContactsContract.Contacts.PHOTO_URI)

            val contactEntityMap: MutableMap<Long, ContactEntity> = mutableMapOf()

            while(cursor.moveToNext()) {
                val id = cursor.getLong(idIdx)
                val photoUri = cursor.getString(photoIdx) ?: ""
                contactEntityMap[id] = ContactEntity(id, cursor.getString(nameIdx), cursor.getString(sortKeyIdx), photoUri)
            }
            cursor.close()

            // Contactのデータを取得
            cursor = application.contentResolver.query(
                    ContactsContract.CommonDataKinds.Contactables.CONTENT_URI,
                    dataProjection,
                    selection,
                    selectionArgs,
                    sortOrder
            )

            val res = application.resources

            idIdx = cursor.getColumnIndex(ContactsContract.Data.CONTACT_ID)
            nameIdx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
            sortKeyIdx = cursor.getColumnIndex(ContactsContract.Contacts.SORT_KEY_PRIMARY)

            val mimeTypeIdx = cursor.getColumnIndex(ContactsContract.Data.MIMETYPE)
            val dataIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.DATA)
            val typeIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Contactables.TYPE)

            while(cursor.moveToNext()) {
                val id = cursor.getLong(idIdx)
                val mimeType = cursor.getString(mimeTypeIdx)
                val type = cursor.getInt(typeIdx)

                val contact =
                        if (contactEntityMap.containsKey(id)) contactEntityMap[id]!!
                        else {
                            contactEntityMap[id] = ContactEntity(id, cursor.getString(nameIdx), cursor.getString(sortKeyIdx))
                            contactEntityMap[id]!!
                        }

                when (mimeType) {
                    CommonDataKinds.Phone.CONTENT_ITEM_TYPE -> {
                        val typeLabel = ContactsContract.CommonDataKinds.Phone.getTypeLabel(res, type, "") as String
                        val phone = PhoneEntity(cursor.getString(dataIdx), typeLabel)

                        contact.phoneEntityList.add(phone)
                    }

                    CommonDataKinds.Email.CONTENT_ITEM_TYPE -> {
                        val typeLabel = ContactsContract.CommonDataKinds.Email.getTypeLabel(res, type, "") as String
                        val emailAddress = MailEntity(cursor.getString(dataIdx), typeLabel)

                        contact.mailEntityList.add(emailAddress)
                    }
                }
            }

            return contactEntityMap.values.sortedBy { it.displayName }.toMutableList()
        }

        fun openPhoto(contactId: Long): InputStream? {
            val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId)
            val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
            val cursor = ContactsApplication.getApp().contentResolver.query(photoUri,
                    arrayOf(ContactsContract.Contacts.Photo.PHOTO), null, null, null) ?: return null

            cursor.use { _cursor ->
                if (_cursor.moveToFirst()) {
                    val data = _cursor.getBlob(0)
                    if (data != null) {
                        return ByteArrayInputStream(data)
                    }
                }
            }
            return null
        }
    }
}