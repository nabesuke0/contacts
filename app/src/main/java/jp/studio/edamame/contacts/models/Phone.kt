package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.PhoneEntity

/**
 * Created by Watanabe on 2018/01/23.
 */
class Phone(phoneEntity: PhoneEntity) {
    var phoneNumber: BehaviorSubject<String> = BehaviorSubject.createDefault(phoneEntity.phoneNumber)
    var typeLabel: BehaviorSubject<String> = BehaviorSubject.createDefault(phoneEntity.typeLabel)
}