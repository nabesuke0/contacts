package jp.studio.edamame.contacts.entities

import io.reactivex.subjects.BehaviorSubject

/**
 * Created by Watanabe@Neopa on 2017/05/18.
 */
class Phone (phoneNumeber: String, type: String) {
    var phoneNumber = phoneNumeber
    var typeLabel = type
}