package jp.studio.edamame.contacts

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import jp.studio.edamame.contacts.views.ViewPagerFragment

/**
 * Created by Watanabe@Neopa on 2017/05/16.
 */
class ContactsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragments: MutableList<ViewPagerFragment> = mutableListOf()

    var last: Int = 0
        set(last) {
            field = last
            notifyDataSetChanged()
        }

    fun addFragment(fragment: ViewPagerFragment) = fragments.add(fragment)

    fun indexOf(fragment: Fragment): Int = fragments.indexOf(fragment)

    override fun getCount(): Int = if (last < fragments.size) last + 1 else 0

    override fun getItem(position: Int): Fragment? = fragments[position]

    override fun getPageTitle(position: Int): CharSequence {
        return fragments[position].getTitle()
    }
}