package jp.studio.edamame.contacts.views.all

import android.os.Bundle
import android.view.View
import jp.studio.edamame.contacts.R
import jp.studio.edamame.contacts.views.ViewPagerFragment

/**
 * Created by Watanabe@Neopa on 2017/05/16.
 */
class AllContactsFragment: ViewPagerFragment() {
    lateinit var viewModel: AllContactsViewModel

    override fun getTitle(): String {
        return "全ての連絡先"
    }

    override fun layoutId(): Int {
        return R.layout.fragment_allcontacts
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = AllContactsViewModel()
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }

    override fun onResume() {
        super.onResume()
    }
}