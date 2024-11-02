package com.lessons.samocounter.schedule

import android.R
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.lessons.samocounter.MOUNTH
import com.lessons.samocounter.VariableData
import com.lessons.samocounter.databinding.SingleElementBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>() {

    var rvList = mutableListOf<Day>()
    val variableData = VariableData()

    //заполняет разметку 2
    class ScheduleHolder(item: View, parent: ViewGroup): RecyclerView.ViewHolder(item ){
        val currentDate = Date()
        val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        val dateText = dateFormat.format(currentDate)

        val binding = SingleElementBinding.bind(item)

        fun bind(day: Day,position: Int) = with(binding){
            var posInt = position+1
            var posString: String
            if (posInt < 10) posString = "0$posInt"
            else posString = "$posInt"
            tvDay.text = posString + MOUNTH
            if(dateText == posString + MOUNTH) {
                tvDay.setTextColor(tvDay.getContext().getColor(R.color.holo_red_light))
                tvWorkOrWeekend.setTextColor(tvDay.getContext().getColor(R.color.holo_red_dark))
            }

            if(day.working == 0){
                tvWorkOrWeekend.text =  "Walk"
                val backgroundColor: Int = ContextCompat.getColor(tvDay.getContext(),R.color.holo_orange_dark)
                bgcolor.setBackgroundColor(backgroundColor)
            } else if(day.working == 1){
                tvWorkOrWeekend.text =  "Work"
                cvcolor.setOnClickListener{
                    val myAlertDialog = MyAlertDialog()
                    myAlertDialog.alertDialogShift(position, tvDay)
                }
            } else{
                tvWorkOrWeekend.text = "At 10:00"
            }
        }
    }

    //делает разметку 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHolder {
        val variableData = VariableData()
        val view = LayoutInflater.from(parent.context).inflate(variableData.singleElement,parent,false)
        return ScheduleHolder(view, parent)
    }

    override fun onBindViewHolder(holder: ScheduleHolder, position: Int) {
        holder.bind(rvList[position],position)
    }

    override fun getItemCount(): Int {
        return rvList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addList(list: MutableList<Day>){
        rvList.clear()
        rvList.addAll(list)
        notifyDataSetChanged()
    }


}