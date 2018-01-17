package jp.studio.edamame.contacts.views.all

import jp.studio.edamame.contacts.model.Contact

/**
 * Created by Watanabe on 2018/01/17.
 */
class RecyclerItemContact(val contact : Contact) : ContactsRecyclerItemable {
    override var itemType: ContactsViewItemType = ContactsViewItemType.CONTACTS
}