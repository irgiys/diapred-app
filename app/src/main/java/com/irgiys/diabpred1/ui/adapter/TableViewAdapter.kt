package com.irgiys.diabpred1.ui.adapter


import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.irgiys.diabpred1.R
import com.irgiys.diabpred1.model.FeatureModel

class TableViewAdapter(private val featrueList: List<FeatureModel>) : RecyclerView.Adapter<TableViewAdapter.RowViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_table, parent, false)
        return RowViewHolder(itemView)
    }

    private fun setHeaderBg(view: View) {
        view.setBackgroundResource(R.drawable.table_header_cell_bg)
    }

    private fun setContentBg(view: View) {
        view.setBackgroundResource(R.drawable.table_content_cell_bg)
    }

    override fun onBindViewHolder(holder: RowViewHolder, position: Int) {
        val rowPos = holder.adapterPosition

        if (rowPos == 0) {
            // Header Cells. Main Headings appear here
            holder.apply {
                setHeaderBg(txtGender)
                setHeaderBg(txtAge)
                setHeaderBg(txtHypertension)
                setHeaderBg(txtHeart)
                setHeaderBg(txtSmoking)
                setHeaderBg(txtBmi)
                setHeaderBg(txtHba1c)
                setHeaderBg(txtGlucose)
                setHeaderBg(txtDiabetes)

                txtGender.text = "Gender"
                txtAge.text = "Age"
                txtHypertension.text = "Hypertension"
                txtHeart.text = "Heart Disease"
                txtSmoking.text = "Smoking Status"
                txtBmi.text = "BMI"
                txtHba1c.text = "HbA1c level"
                txtGlucose.text = "Blood Glucose"
                txtDiabetes.text = "Diabetes"
            }
        } else {
            val model = featrueList[rowPos - 1]

            holder.apply {
                setContentBg(txtGender)
                setContentBg(txtAge)
                setContentBg(txtHypertension)
                setContentBg(txtHeart)
                setContentBg(txtSmoking)
                setContentBg(txtBmi)
                setContentBg(txtHba1c)
                setContentBg(txtGlucose)
                setContentBg(txtDiabetes)


                txtGender.text = model.gender
                txtAge.text = model.age
                txtHypertension.text = model.hypertension
                txtHeart.text = model.heartDisease
                txtSmoking.text = model.smokeStatus
                txtBmi.text = model.bmi
                txtHba1c.text = model.hba1c
                txtGlucose.text = model.bloodGlucose
                txtDiabetes.text = model.diabetes
            }
        }
    }

    override fun getItemCount(): Int {
        return featrueList.size + 1 // one more to add header row
    }

    inner class RowViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtAge: TextView = itemView.findViewById(R.id.age)
        val txtGender: TextView = itemView.findViewById(R.id.gender)
        val txtBmi: TextView = itemView.findViewById(R.id.bmi)
        val txtGlucose: TextView = itemView.findViewById(R.id.bloodGlucose)
        val txtHba1c: TextView = itemView.findViewById(R.id.hba1c)
        val txtDiabetes: TextView = itemView.findViewById(R.id.diabetes)
        val txtHeart: TextView = itemView.findViewById(R.id.heartDisease)
        val txtHypertension: TextView = itemView.findViewById(R.id.hypertension)
        val txtSmoking: TextView = itemView.findViewById(R.id.smokingStatus)

    }
}