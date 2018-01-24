package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.entities.MailEntity

/**
 * Created by Watanabe on 2018/01/23.
 */
class Mail(mailEntity: MailEntity) {
    var mailAddress: BehaviorSubject<String> = BehaviorSubject.createDefault(mailEntity.mailAddress)
    var typeLabel: BehaviorSubject<String> = BehaviorSubject.createDefault(mailEntity.typeLabel)
}