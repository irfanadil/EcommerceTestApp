package com.ecommerce.testapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.cart_item_layout.view.*

class CartItemListAdapter internal constructor(private var cartItemList: List<CartItem>, onCartItemClickListener: OnCartItemClickListener) :  RecyclerView.Adapter<CartItemListAdapter.CartItemViewHolder>(){

    private val mOnCartItemClickListener: OnCartItemClickListener = onCartItemClickListener

    inner class CartItemViewHolder(itemView: View, globalOnProductListener: OnCartItemClickListener) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        //val productImage:ImageView = itemView.product_image
        val productQuantity : TextView= itemView.item_quantity
        val productSize : TextView= itemView.item_size_field
        val productName:TextView = itemView.sizeName
        val productPrice:TextView = itemView.item_price
        //private val productRating:RatingBar= itemView.rating

        private val removeItem:TextView = itemView.remove_item
        private val addItem:TextView = itemView.add_item
        var onProductListener: OnCartItemClickListener

        init {
            addItem.setOnClickListener(this)
            removeItem.setOnClickListener(this)
            this.onProductListener = globalOnProductListener
        }

        override fun onClick(v: View) {
           if(v.tag == "RemoveItem")
                mOnCartItemClickListener.onCartItemRemoved(adapterPosition)
            else
               mOnCartItemClickListener.onCartItemAdded(adapterPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartItemViewHolder {
        val holder: CartItemViewHolder
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        holder = CartItemViewHolder(inflater.inflate(R.layout.cart_item_layout, parent, false), mOnCartItemClickListener)
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.cart_item_layout
    }

    override fun onBindViewHolder(holder: CartItemViewHolder, position: Int)          {
        val item = cartItemList[position]
        holder.productName.text = item.cartItemName
        holder.productQuantity.text = item.cartItemQuantity.toString()
        holder.productSize.text = item.cartItemSize
        holder.productPrice.text = item.cartItemPrice.toString()
        //if(item.cartItemRate!=null && item.cartItemRate>0)
        //holder.productRating.numStars = item.cartItemRate
        //holder.productImage.loadImage("https://via.placeholder.com/150" )
    }

    internal fun setAllCartItems(cartItemList: List<CartItem>) {
        this.cartItemList = cartItemList
        notifyDataSetChanged()
    }

    override fun getItemCount() = cartItemList.size

    interface OnCartItemClickListener {
        fun onCartItemRemoved(position: Int)
        fun onCartItemAdded(position: Int)
    }

}