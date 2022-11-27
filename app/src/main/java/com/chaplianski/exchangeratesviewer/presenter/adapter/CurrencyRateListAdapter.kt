package com.chaplianski.exchangeratesviewer.presenter.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chaplianski.exchangeratesviewer.R
import com.chaplianski.exchangeratesviewer.domain.model.Currency
import com.chaplianski.exchangeratesviewer.presenter.helper.getCurrencyRate
import com.chaplianski.exchangeratesviewer.presenter.helper.getFlagImage
import com.chaplianski.exchangeratesviewer.presenter.helper.getFullNameCurrency
import com.squareup.picasso.Picasso


class CurrencyRateListAdapter() : RecyclerView.Adapter<CurrencyRateListAdapter.ViewHolder>() {

    var currencyList = mutableListOf<Currency>()
    var currencyRV: RecyclerView? = null


    interface CurrencyClickListener {
        fun onClickItem(currency: Currency, position: Int)
        fun onChangeText(currency: Currency)
    }

    var currencyClickListener: CurrencyClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_currency, parent, false)
        val h = object : ViewHolder(v) {}
        v.setOnClickListener { it: View? ->
            val adapterPosition = h.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                currencyClickListener?.onClickItem(currencyList[adapterPosition], adapterPosition)
            }
        }
        return h
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        currencyRV = recyclerView
    }


    fun updateData(list: List<Currency>) {
        val diffCallback = CurrencyDiffCallback(currencyList, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        currencyList = list as MutableList<Currency>
        if (currencyRV?.scrollState == RecyclerView.SCROLL_STATE_IDLE && !currencyRV?.isComputingLayout!!) {
            diffResult.dispatchUpdatesTo(this)
        }
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(currencyList[position])

        if (currencyList.indexOf(currencyList[position]) == 0) {
            holder.rateValue.setSelection(holder.rateValue.length())
            holder.rateValue.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun afterTextChanged(p0: Editable?) {
                    val value = if (p0.isNullOrEmpty()) "0" else p0.toString()
                    if (value.length >= 2) {
                        if (value.startsWith("0") && value[1] != '.') {
                            val checkZeroValue = value.removePrefix(0.toString())
                            holder.rateValue.setText(checkZeroValue)
                            holder.rateValue.setSelection(holder.rateValue.length())
                        }
                    }
                   if (value.startsWith(".")) {
                       val checkCommaValue = "0$value"
                       holder.rateValue.setText(checkCommaValue)
                       holder.rateValue.setSelection(holder.rateValue.length())
                   }
                    val newValue = value.replace(",", "")
                    val currency =
                        Currency(currencyList[0].date, newValue.toFloatOrNull(), currencyList[0].base)
                    currencyClickListener?.onChangeText(currency)
                }
            })
        } else {
            holder.rateValue.isFocusable = false
        }
    }

    override fun getItemCount(): Int {
        return currencyList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    open class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val flagView = itemView.findViewById<ImageView>(R.id.flag)
        val shortName = itemView.findViewById<TextView>(R.id.short_name)
        val fullName = itemView.findViewById<TextView>(R.id.full_name)
        val rateValue = itemView.findViewById<EditText>(R.id.currency_value)

        fun onBind(currency: Currency) {
            shortName.text = currency.base
            rateValue.setText(currency.rate?.let { getCurrencyRate(it) })
            fullName.text = getFullNameCurrency(currency.base.toString(), itemView.context)
            Picasso.get().load(getFlagImage(currency.base.toString())).into(flagView)
        }
    }

    class CurrencyDiffCallback(
        val oldList: List<Currency>,
        val newList: List<Currency>
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].base == newList[newItemPosition].base
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldCurrency = oldList[oldItemPosition]
            val newCurrency = newList[newItemPosition]
            return oldCurrency == newCurrency
        }
    }
}