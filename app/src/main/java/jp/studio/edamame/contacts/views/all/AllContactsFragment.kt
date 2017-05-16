package jp.studio.edamame.contacts.views.all

import jp.studio.edamame.contacts.R
import jp.studio.edamame.contacts.views.ViewPagerFragment

/**
 * Created by Watanabe@Neopa on 2017/05/16.
 */
class AllContactsFragment: ViewPagerFragment() {
    override fun getTitle(): String {
        return "Contacts"
    }

    override fun layoutId(): Int {
        return R.layout.fragment_allcontacts
    }


}