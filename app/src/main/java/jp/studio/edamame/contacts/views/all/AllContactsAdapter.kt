package jp.studio.edamame.contacts.views.all

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import jp.studio.edamame.contacts.model.Contact
import android.view.LayoutInflater
import jp.studio.edamame.contacts.R
import timber.log.Timber


/**
 * Created by Watanabe on 2017/05/17.
 */
class AllContactsAdapter(
        var mContacts: MutableList<Contact>,
        var viewItems : MutableList<ContactsRecyclerItemable>,
        context: Context)
    : RecyclerView.Adapter<AllContactsAdapter.ViewHolder>()
{
    val gridLayoutManager = GridLayoutManager(context, 2)

    init {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return 2// if (viewItems[position].itemType == ContactsViewItemType.CATEGORY) 1 else 2
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent!!.context).inflate(R.layout.grid_item_contact, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.bindData(mContacts[position])
    }

    override fun getItemCount(): Int {
        return mContacts.count()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view.findViewById(R.id.grid_item_image)
        var textView: TextView = view.findViewById(R.id.grid_item_name)

        fun bindData(contact: Contact) {
            textView.text = contact.displayName

            contact.getPhoto().subscribe(
                    { bitmap ->
                        imageView.setImageBitmap(bitmap)
                    },
                    { e ->
                        Timber.d(e.localizedMessage)
                    })
        }
    }
}