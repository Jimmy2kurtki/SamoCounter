package com.lessons.samocounter.schedule

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lessons.samocounter.MOUNTH
import com.lessons.samocounter.VariableData
import com.lessons.samocounter.databinding.SingleElementBinding

class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ScheduleHolder>() {

    var rvList = mutableListOf<Day>()

    //заполняет разметку 2
    class ScheduleHolder(item: View): RecyclerView.ViewHolder(item ){

        val binding = SingleElementBinding.bind(item)

        fun bind(day: Day,position: Int) = with(binding){
            tvDay.text = "${position+1} $MOUNTH"
            im.setImageResource(day.img)


            if(day.working == 0){
                tvWorkOrWeekend.text =  "Weekend"
            } else if(day.working == 1){
                tvWorkOrWeekend.text =  "At 8:00"
            } else{
                tvWorkOrWeekend.text = "At 10:00"
            }
        }
    }

    //делает разметку 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleHolder {
        val variableData = VariableData()
        val view = LayoutInflater.from(parent.context).inflate(variableData.singleElement,parent,false)
        return ScheduleHolder(view)
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