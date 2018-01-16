package jp.studio.edamame.contacts.views.all

import jp.studio.edamame.contacts.model.Contact
import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsViewModel {
    val contacts: MutableList<Contact> = ContactsProvider.queryAll()
}