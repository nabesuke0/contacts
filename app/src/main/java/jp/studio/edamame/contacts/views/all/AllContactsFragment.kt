package jp.studio.edamame.contacts.views.all

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import jp.studio.edamame.contacts.ContactsApplication
import jp.studio.edamame.contacts.R
import jp.studio.edamame.contacts.views.ViewPagerFragment
import kotlinx.android.synthetic.main.fragment_allcontacts.*
import timber.log.Timber

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsFragment: ViewPagerFragment() {
    lateinit var viewModel: AllContactsViewModel
    private  val disposable = CompositeDisposable()

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

        allcontacts_grid.setHasFixedSize(true)

        viewModel = AllContactsViewModel(
                ContactsApplication.getApp().contactsModel.contactModels)

        disposable.add(viewModel.viewItems.subscribe { items ->
            val adapter = AllContactsAdapter(items, this.context) { selectedItem ->
                Timber.d("item name = %s", selectedItem.rx_displayName.value)
            }

            allcontacts_grid.layoutManager = adapter.gridLayoutManager
            allcontacts_grid.adapter = adapter
        })
    }

    override fun onResume() {
        super.onResume()
    }
}