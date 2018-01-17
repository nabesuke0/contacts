package jp.studio.edamame.contacts.views.all

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jp.studio.edamame.contacts.R
import jp.studio.edamame.contacts.views.ViewPagerFragment
import kotlinx.android.synthetic.main.fragment_allcontacts.*

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsFragment: ViewPagerFragment() {
    lateinit var viewModel: AllContactsViewModel

    override fun getTitle(): String {
        return "全ての連絡先"
    }

    override fun layoutId(): Int {
        return R.layout.fragment_allcontacts
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_allcontacts, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()

        viewModel = AllContactsViewModel()
        this.initializeRecyclerView(viewModel)
    }

    private fun initializeRecyclerView(viewModel : AllContactsViewModel) {
        allcontacts_grid.setHasFixedSize(true)
        allcontacts_grid.layoutManager = GridLayoutManager(this.context, 2)
        allcontacts_grid.adapter = AllContactsAdapter(viewModel.contacts)
    }
}