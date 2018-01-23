package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.MailAddress

/**
 * Created by Watanabe on 2018/01/23.
 */
class MailModel(mail: MailAddress) {
    var mailAddress: BehaviorSubject<String> = BehaviorSubject.createDefault(mail.mailAddress)
    var typeLabel: BehaviorSubject<String> = BehaviorSubject.createDefault(mail.typeLabel)
}