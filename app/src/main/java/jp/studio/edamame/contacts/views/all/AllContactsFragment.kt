package jp.studio.edamame.contacts.views.all

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.reactivex.disposables.CompositeDisposable
import jp.studio.edamame.contacts.ContactsApplication
import jp.studio.edamame.contacts.R
import jp.studio.edamame.contacts.views.ViewPagerFragment
import jp.studio.edamame.contacts.views.detail.ContactDetailActivity
import kotlinx.android.synthetic.main.fragment_allcontacts.*
import timber.log.Timber

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsFragment: ViewPagerFragment() {
    private lateinit var viewModel: AllContactsViewModel
    private val disposable = CompositeDisposable()

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

                val intent = Intent(this.activity, ContactDetailActivity::class.java)
                intent.putExtra(ContactDetailActivity.INTENT_KEY_CONTACT_ID, selectedItem.rx_id.value)
                startActivity(intent)
            }

            allcontacts_grid.layoutManager = adapter.gridLayoutManager
            allcontacts_grid.adapter = adapter
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }
}