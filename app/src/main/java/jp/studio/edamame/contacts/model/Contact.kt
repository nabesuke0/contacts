package jp.studio.edamame.contacts.model

/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class Contact(id: Long) {

    var id: Long = id
    var displayName: String? = null
    val phoneNumberArray: Array<Phone> = emptyArray()
    val emailAddresArray: Array<MailAddress> = emptyArray()
}