package jp.studio.edamame.contacts

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * Created by Watanabe@Neopa on 2017/05/16.
 */
class ContactsPagerAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    private val fragments: MutableList<Fragment> = mutableListOf()

    var last: Int = 0
        set(last) {
            field = last
            notifyDataSetChanged()
        }

    fun addFragment(fragment: Fragment) = fragments.add(fragment)

    fun indexOf(fragment: Fragment): Int = fragments.indexOf(fragment)

    override fun getCount(): Int = if (last < fragments.size) last + 1 else 0

    override fun getItem(position: Int): Fragment? = fragments[position]
}