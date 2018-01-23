package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.extensions.katakanaToHiragana
import jp.studio.edamame.contacts.provider.ContactsProvider
import timber.log.Timber

/**
 * Created by Watanabe on 2018/01/23.
 */
class ContactScreenModel {
    var rx_contacts: BehaviorSubject<MutableList<ContactModel>> = BehaviorSubject.createDefault(ContactsProvider.queryAll().map {
        ContactModel(it)
    }.toMutableList())

    var rx_sortedContacts: BehaviorSubject<MutableMap<String, MutableList<ContactModel>>> = BehaviorSubject.create()

    init {
        this.sortContacts()
    }

    fun sortContacts() {
        val mutableMap = mutableMapOf<Int, MutableMap<String, MutableList<ContactModel>>>()
        rx_contacts.value.forEach { contactModel ->
            var sortKey = contactModel.sortKey.value.substring(0..0)
            val categoryPair = this.distributeKey(sortKey)

            val sortOrderPrimary = categoryPair.first
            val categoryKey = categoryPair.second

            if (mutableMap[sortOrderPrimary] == null) {
                mutableMap[sortOrderPrimary] = mutableMapOf()
            }

            if (!mutableMap[sortOrderPrimary]!!.containsKey(categoryKey)) {
                mutableMap[sortOrderPrimary]!!.put(categoryKey, mutableListOf())
            }

            mutableMap[sortOrderPrimary]!![categoryKey]!!.add(contactModel)
        }

        val sortedMap = mutableMapOf<String, MutableList<ContactModel>>()
        mutableMap.toSortedMap().values.forEach {
            it.toSortedMap().forEach { (k, v) ->
                sortedMap.put(k, v)
            }
        }

        rx_sortedContacts.onNext(sortedMap)
    }

    private fun distributeKey(key : String) : Pair<Int, String> {
        return when {
            key.length > 1 -> Pair(99, "#") // 2文字以上は不正
            key.matches(Regex("[0-9a-zA-Z]+")) -> Pair(10, key) // アルファベット
            key.matches(Regex("^[\\u3040-\\u309F]+$")) -> Pair(0, key) // ひらがな
            key.matches(Regex("^[\\\\u30A0-\\\\u30FF]+\$")) -> Pair(0, key.katakanaToHiragana()) // カタカナはひらがなに変換して返す
            else -> Pair(99, "#") // 読み仮名が漢字などその他のものは全て#で返す
        }
    }
}