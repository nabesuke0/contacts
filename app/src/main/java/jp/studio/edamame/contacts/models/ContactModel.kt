package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.Contact

/**
 * Created by Watanabe on 2018/01/23.
 */
class ContactModel(private var contact: Contact) {
    var id: BehaviorSubject<Long> = BehaviorSubject.createDefault(contact.id)
    var displayName: BehaviorSubject<String> = BehaviorSubject.createDefault(contact.displayName)
    var sortKey : BehaviorSubject<String> = BehaviorSubject.createDefault(contact.sortKey)
    var photoUri: BehaviorSubject<String> = BehaviorSubject.createDefault(contact.photoUri)
    val phoneList: BehaviorSubject<MutableList<PhoneModel>> = BehaviorSubject.createDefault(
            contact.phoneList.map {
                PhoneModel(it)
            }.toMutableList()
    )
    val mailList: BehaviorSubject<MutableList<MailModel>> = BehaviorSubject.createDefault(
            contact.mailList.map {
                MailModel(it)
            }.toMutableList()
    )
}