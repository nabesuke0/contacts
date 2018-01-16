package jp.studio.edamame.contacts.model

/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class Contact(var id: Long, var displayName: String) {
    var photoUri: String? = null
    val phoneList: MutableList<Phone> = mutableListOf()
    val emailAddressList: MutableList<MailAddress> = mutableListOf()
}