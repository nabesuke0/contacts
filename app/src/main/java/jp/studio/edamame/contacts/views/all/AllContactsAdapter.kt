package jp.studio.edamame.contacts.views.all

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import jp.studio.edamame.contacts.model.Contact
import android.support.v7.widget.RecyclerView.ViewHolder
import android.support.design.widget.CoordinatorLayout.Behavior.setTag
import android.view.LayoutInflater
import jp.studio.edamame.contacts.R


/**
 * Created by Watanabe@Neopa on 2017/05/17.
 */
class AllContactsAdapter(context: Context, contacts: Array<Contact>) : BaseAdapter() {
    val mContacts: Array<Contact> = contacts
    val mInflater: LayoutInflater = LayoutInflater.from(context)

    private class ViewHolder() {
        var imageView: ImageView? = null
        var textView: TextView? = null
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val holder: ViewHolder
        val itemView : View

        if (convertView == null) {
            itemView = mInflater.inflate(R.layout.grid_item_contact, null)
            holder = ViewHolder()
            holder.imageView = itemView.findViewById(R.id.grid_item_image) as ImageView
            holder.textView  = itemView.findViewById(R.id.grid_item_name) as TextView
            itemView.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            itemView = convertView
        }

        // TODO : setValues

        return itemView
    }

    override fun getItem(position: Int): Any {
        return mContacts[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return mContacts.size
    }
}