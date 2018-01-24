package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.ContactEntity


/**
 * Created by Watanabe on 2018/01/23.
 */
class Contact(private var contactEntity: ContactEntity) {
    var id: BehaviorSubject<Long> = BehaviorSubject.createDefault(contactEntity.id)
    var displayName: BehaviorSubject<String> = BehaviorSubject.createDefault(contactEntity.displayName)
    var rx_sortKey: BehaviorSubject<String> = BehaviorSubject.createDefault(contactEntity.sortKey)
    var photoUri: BehaviorSubject<String> = BehaviorSubject.createDefault(contactEntity.photoUri)
    val phoneList: BehaviorSubject<MutableList<Phone>> = BehaviorSubject.createDefault(
            contactEntity.phoneEntityList.map {
                Phone(it)
            }.toMutableList()
    )
    val mailList: BehaviorSubject<MutableList<Mail>> = BehaviorSubject.createDefault(
            contactEntity.mailEntityList.map {
                Mail(it)
            }.toMutableList()
    )

    // 名前が変わった際に並び替えを通知する
//    private val disposable: CompositeDisposable = CompositeDisposable()
//
//    init {
//        disposable.add(rx_sortKey.subscribe {
//            val application = ContactsApplication.getApp()
//
//            val i = Intent()
//            i.action = application.resources.getString(R.string.local_intent_contact_name_changed)
//
//            LocalBroadcastManager.getInstance(application).sendBroadcast(i)
//        })
//    }
}