package jp.studio.edamame.contacts.views.all

import jp.studio.edamame.contacts.extensions.katakanaToHiragana
import jp.studio.edamame.contacts.model.Contact
import jp.studio.edamame.contacts.provider.ContactsProvider

/**
 * Created by Watanabe on 2017/05/16.
 */
class AllContactsViewModel {
    private val contacts: MutableList<Contact> = ContactsProvider.queryAll()
    val viewItems : MutableList<ContactsRecyclerItemable> = mutableListOf()

    init {
        val mutableMap = mutableMapOf<Int, MutableMap<String, MutableList<Contact>>>()
        contacts.forEach { contact ->
            var sortKey = contact.sortKey.substring(0..0)
            val categoryPair = this.distributeKey(sortKey)

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

        mutableMap.toSortedMap().values.forEach {
            it.toSortedMap().forEach { (k, v) ->
                viewItems.add(RecyclerItemCategory(k))
                v.forEach {
                    viewItems.add(RecyclerItemContact(it))
                }
            }
        }
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