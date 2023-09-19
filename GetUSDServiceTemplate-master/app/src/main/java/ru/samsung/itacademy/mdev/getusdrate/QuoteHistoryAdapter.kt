package ru.samsung.itacademy.mdev.getusdrate

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class QuoteHistoryAdapter : RecyclerView.Adapter<QuoteHistoryAdapter.QuoteViewHolder>() {
    private var quoteHistory: List<Quote> = emptyList()

    class QuoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val dateView: TextView = itemView.findViewById(R.id.date_view)
        val quoteView: TextView = itemView.findViewById(R.id.quote_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.quote_history_item, parent, false)
        return QuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuoteViewHolder, position: Int) {
        val quote = quoteHistory[position]
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val shortDate = dateFormat.format(quote.date)

        holder.dateView.text = shortDate
        holder.quoteView.text = "${quote.currency}: ${quote.quote}"
    }

    override fun getItemCount() = quoteHistory.size

    fun submitList(quoteHistory: List<Quote>) {
        this.quoteHistory = quoteHistory
        notifyDataSetChanged()
    }
}