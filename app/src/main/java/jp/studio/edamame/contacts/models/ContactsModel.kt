package jp.studio.edamame.contacts.models

import io.reactivex.subjects.BehaviorSubject
import jp.studio.edamame.contacts.extensions.katakanaToHiragana
import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2018/01/23.
 */
class ContactsModel {
    var contactModels: BehaviorSubject<MutableList<Contact>> = BehaviorSubject.createDefault(ContactsProvider.queryAll().map {
        Contact(it)
    }.toMutableList())

    companion object {
        fun distributeKey(key : String) : Pair<Int, String> {
            return when {
                key.length > 1 -> Pair(99, "#") // 2文字以上は不正
                key.matches(Regex("[0-9a-zA-Z]+")) -> Pair(10, key) // アルファベット
                key.matches(Regex("^[\\u3040-\\u309F]+$")) -> Pair(0, key) // ひらがな
                key.matches(Regex("^[\\\\u30A0-\\\\u30FF]+\$")) -> Pair(0, key.katakanaToHiragana()) // カタカナはひらがなに変換して返す
                else -> Pair(99, "#") // 読み仮名が漢字などその他のものは全て#で返す
            }
        }

        fun sortContacts(rxcontactList: MutableList<Contact>): MutableMap<String, MutableList<Contact>> {
            val mutableMap = mutableMapOf<Int, MutableMap<String, MutableList<Contact>>>()
            rxcontactList.forEach { contactModel ->
                var sortKey = contactModel.rx_sortKey.value.substring(0..0)
                val categoryPair = ContactsModel.distributeKey(sortKey)

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

            val sortedMap = mutableMapOf<String, MutableList<Contact>>()
            mutableMap.toSortedMap().values.forEach {
                it.toSortedMap().forEach { (k, v) ->
                    sortedMap.put(k, v)
                }
            }

            return sortedMap
        }
    }
}