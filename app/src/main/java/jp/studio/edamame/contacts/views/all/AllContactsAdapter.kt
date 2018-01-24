package jp.studio.edamame.contacts.views.all

import android.content.Context
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.view.LayoutInflater
import android.widget.RelativeLayout
import io.reactivex.disposables.CompositeDisposable
import jp.studio.edamame.contacts.R


/**
 * Created by Watanabe on 2017/05/17.
 */
class AllContactsAdapter(
        var viewItems : MutableList<ContactsRecyclerItemable>,
        context: Context,
        private var itemClick: ((ContactThumbnailViewModel) -> Unit)? = null)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    val gridLayoutManager = GridLayoutManager(context, 2)

    init {
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (viewItems[position].itemType != ContactsViewItemType.CATEGORY) 1 else gridLayoutManager.spanCount
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return viewItems[position].itemType.rawValue
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ContactsViewItemType.CATEGORY.rawValue -> {
                val view = LayoutInflater.from(parent!!.context).inflate(R.layout.grid_section_contacts, parent, false)
                CategoryViewHolder(view)
            }
            else -> { // ContactsViewItemType.CONTACTS.rawValue
                val view = LayoutInflater.from(parent!!.context).inflate(R.layout.grid_item_contact, parent, false)
                ContactsViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val item = viewItems[position]
        when(item.itemType) {
            ContactsViewItemType.CATEGORY -> {
                val viewHolder = holder as CategoryViewHolder
                viewHolder.bindItem(item as CategoryItemViewModel)
            }
            ContactsViewItemType.CONTACTS -> {
                val viewHolder = holder as ContactsViewHolder
                val contactItem = item as ContactThumbnailViewModel
                viewHolder.bindItem(contactItem)
                viewHolder.layout.setOnClickListener {
                    itemClick?.invoke(contactItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return viewItems.count()
    }
}

class ContactsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    var layout: RelativeLayout = view.findViewById(R.id.grid_item_contact_layout)
    private var imageView: ImageView = view.findViewById(R.id.grid_item_image)
    private var textView: TextView = view.findViewById(R.id.grid_item_name)
    private var phoneImage: ImageView = view.findViewById(R.id.grid_item_image_phone)
    private var mailImage: ImageView = view.findViewById(R.id.grid_item_image_mail)

    private val disposable = CompositeDisposable()

    fun bindItem(item : ContactThumbnailViewModel) {
        imageView.setImageResource(R.drawable.user_icon_default)

        disposable.addAll(
                item.rx_displayName.subscribe { name ->
                    textView.text = name
                },
                item.hasPhoneNumber.subscribe { hasNumber ->
                    phoneImage.visibility = if (hasNumber) View.VISIBLE else View.GONE
                },
                item.hasMail.subscribe { hasMail ->
                    mailImage.visibility = if (hasMail) View.VISIBLE else View.GONE
                },
                item.photoUri.subscribe { _ ->
                    item.getPhoto().subscribe { bitmap ->
                        imageView.setImageBitmap(bitmap)
                    }
                })
    }
}

class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private var textView: TextView = view.findViewById(R.id.grid_section_title)

    fun bindItem(item : CategoryItemViewModel) {
        textView.text = item.categoryName
    }
}