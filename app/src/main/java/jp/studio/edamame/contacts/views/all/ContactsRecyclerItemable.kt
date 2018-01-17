package jp.studio.edamame.contacts.views.all

import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Watanabe on 2018/01/17.
 */
interface ContactsRecyclerItemable {
    var itemType : ContactsViewItemType
}

enum class ContactsViewItemType(val rawValue : Int) {
    CATEGORY(1),
    CONTACTS(2)
}