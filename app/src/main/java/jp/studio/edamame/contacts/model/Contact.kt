package jp.studio.edamame.contacts.model

/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class Contact(id: Long, displayName: String) {

    var id: Long = id
    var displayName: String = displayName
    val phoneList: MutableList<Phone> = mutableListOf()
    val emailAddresList: MutableList<MailAddress> = mutableListOf()
}