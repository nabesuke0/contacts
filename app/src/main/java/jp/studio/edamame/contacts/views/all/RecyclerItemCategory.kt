package jp.studio.edamame.contacts.views.all

/**
 * Created by Watanabe on 2018/01/17.
 */
class RecyclerItemCategory(val categoryName : String) : ContactsRecyclerItemable {
    override var itemType: ContactsViewItemType = ContactsViewItemType.CATEGORY
}