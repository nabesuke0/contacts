package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.Phone

/**
 * Created by Watanabe on 2018/01/23.
 */
class PhoneModel(phone: Phone) {
    var phoneNumber: BehaviorSubject<String> = BehaviorSubject.createDefault(phone.phoneNumber)
    var typeLabel: BehaviorSubject<String> = BehaviorSubject.createDefault(phone.typeLabel)
}