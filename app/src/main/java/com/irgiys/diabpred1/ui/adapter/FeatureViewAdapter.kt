package com.irgiys.diabpred1.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.irgiys.diabpred1.R
import com.irgiys.diabpred1.model.FeatureItemModel

class FeatureViewAdapter(private val featureList: List<FeatureItemModel>) : RecyclerView.Adapter<FeatureViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_feature, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount(): Int {
        return featureList.size + 1
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val rowPos = holder.adapterPosition
        if (rowPos == 0) {
            holder.apply {
                featureInfo.visibility = View.VISIBLE
            }
        } else {
            holder.apply {
                val feature = featureList[rowPos - 1]

                featureName.text = feature.name
                featureType.text = feature.type
                featureInfo.visibility = View.GONE
                featureBtnInfo.visibility = View.VISIBLE
                featureBtnInfo.setOnClickListener {
                    // You can customize the click behavior here
                    val context = holder.itemView.context
                    val dialog = AlertDialog.Builder(context)
                        .setTitle("Detail ${feature.name}")
                        .setMessage("Range / Categorical : ${feature.minMax} \nDescription : ${feature.description}")
                        .setPositiveButton("OK", null)
                        .create()
                    dialog.show()
                }
            }
        }

    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val featureName: TextView = view.findViewById(R.id.feature_name)
        val featureType: TextView = view.findViewById(R.id.feature_type)
        val featureInfo: TextView = view.findViewById(R.id.feature_info)
        val featureBtnInfo: ImageButton = view.findViewById(R.id.feature_btn_info)
    }


}