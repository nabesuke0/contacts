package jp.studio.edamame.contacts.models

import android.graphics.Bitmap
import io.reactivex.Maybe
import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.Contact

/**
 * Created by Watanabe on 2018/01/23.
 */
class ContactModel(contact: Contact) {
    var id: BehaviorSubject<Long> = BehaviorSubject.createDefault(contact.id)
    var displayName: BehaviorSubject<String> = BehaviorSubject.createDefault(contact.displayName)
    var sortKey : BehaviorSubject<String> = BehaviorSubject.createDefault(contact.sortKey)
    val phoneList: BehaviorSubject<MutableList<PhoneModel>> = BehaviorSubject.createDefault(
            contact.phoneList.map {
                PhoneModel(it)
            }.toMutableList()
    )
    val emailAddressList: BehaviorSubject<MutableList<MailModel>> = BehaviorSubject.createDefault(
            contact.emailAddressList.map {
                MailModel(it)
            }.toMutableList()
    )

    private var photo: Maybe<Bitmap?> = Maybe.create(null)

}