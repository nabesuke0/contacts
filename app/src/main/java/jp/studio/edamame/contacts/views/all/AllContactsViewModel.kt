package jp.studio.edamame.contacts.views.all

import jp.studio.edamame.contacts.model.Contact
import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsViewModel {
    val contacts: MutableList<Contact> = ContactsProvider.queryAll()
    val viewItems : MutableList<ContactsRecyclerItemable> = mutableListOf()

    init {
        var categoryKey = ""
        contacts.forEach { contact ->
            var sortKey = contact.sortKey.substring(0..0)
            if (categoryKey != sortKey) {
                categoryKey = sortKey
                viewItems.add(RecyclerItemCategory(sortKey))
            }

            viewItems.add(RecyclerItemContact(contact))
        }
    }
}