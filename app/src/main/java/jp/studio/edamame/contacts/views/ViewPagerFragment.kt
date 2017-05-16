package jp.studio.edamame.contacts.views

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by Watanabe@Neopa on 2017/05/16.
 */
abstract class ViewPagerFragment : Fragment() {
    abstract fun layoutId(): Int
    abstract fun getTitle(): String

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(this.layoutId(), container, false)
        return view
    }
}