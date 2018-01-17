package jp.studio.edamame.contacts.views.all

/**
 * Created by Watanabe@Neopa on 2018/01/17.
 */
interface ContactsRecyclerItemable {
    var itemType : ContactsViewItemType
}

enum class ContactsViewItemType {
    CATEGORY,
    CONTACTS
}