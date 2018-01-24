package jp.studio.edamame.contacts.views.all

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.models.Contact
import jp.studio.edamame.contacts.models.ContactsModel

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsViewModel(private var contactModels: BehaviorSubject<MutableList<Contact>>) {

    val viewItems : BehaviorSubject<MutableList<ContactsRecyclerItemable>> = BehaviorSubject.create()
    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        disposable.add(
                contactModels.subscribe { models ->
                    val mutableMap = mutableMapOf<Int, MutableMap<String, MutableList<Contact>>>()
                    models.forEach { contact ->
                        var sortKey = contact.rx_sortKey.value.substring(0..0)
                        val categoryPair = ContactsModel.distributeKey(sortKey)

                        val sortOrderPrimary = categoryPair.first
                        val categoryKey = categoryPair.second

                        if (mutableMap[sortOrderPrimary] == null) {
                            mutableMap[sortOrderPrimary] = mutableMapOf()
                        }

                        if (!mutableMap[sortOrderPrimary]!!.containsKey(categoryKey)) {
                            mutableMap[sortOrderPrimary]!!.put(categoryKey, mutableListOf())
                        }

                        mutableMap[sortOrderPrimary]!![categoryKey]!!.add(contact)
                    }

                    val sortedList: MutableList<ContactsRecyclerItemable> = mutableListOf()
                    mutableMap.toSortedMap().values.forEach {
                        it.toSortedMap().forEach { (k, v) ->
                            sortedList.add(CategoryItemViewModel(k))
                            v.forEach {
                                sortedList.add(ContactThumbnailViewModel(it))
                            }
                        }
                    }

                    viewItems.onNext(sortedList)
                }
        )
    }
}