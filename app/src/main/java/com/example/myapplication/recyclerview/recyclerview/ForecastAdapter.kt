package com.example.myapplication.recyclerview.recyclerview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ForecastList
import com.example.myapplication.model.ForecastResult
import kotlinx.android.synthetic.main.item_forecast.view.*

class ForecastAdapter(private val myDataset: MutableList<ForecastList>)  : RecyclerView.Adapter<ForecastAdapter.MachinViewHolder>() {



    class MachinViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val _label = itemView.item_machin_label
        val _desc = itemView.item_machin_desc
        //val _date = itemView.item_machin_date
        val _avatar = itemView.item_machin_avatar
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ForecastAdapter.MachinViewHolder {
        // create a new view
        var itemView =
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.item_forecast, parent, false)
                    as View
        return MachinViewHolder(itemView)
    }


    override fun onBindViewHolder(holder: MachinViewHolder, position: Int) {
        holder._label.text = myDataset[position].weather[0].main
        holder._desc.text = " ${myDataset[position].main.temp.toString()} °C"
    }

    override fun getItemCount() = myDataset.size
}
