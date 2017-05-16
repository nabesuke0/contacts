package jp.studio.edamame.contacts.views.all

import android.os.Bundle
import android.support.v4.app.Fragment
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