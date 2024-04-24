package com.vio.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.vio.VioLFO
import com.vio.languageopen.R
import com.vio.listener.LFOSelectLanguage
import com.vio.model.Language

class LFOAdapter(
    private val context: Context,
    private val itemLimit: Int,
    private val listData: List<Language>
) : RecyclerView.Adapter<LFOAdapter.ViewHolder>() {
    private var lfoSelectLanguage: LFOSelectLanguage? = null
    fun registerListener(listener: LFOSelectLanguage) {
        this.lfoSelectLanguage = listener
    }

    class ViewHolder(viewItem: View) :
        RecyclerView.ViewHolder(viewItem) {
        val imgIconLanguage: ImageView = viewItem.findViewById(R.id.imgIconLanguage)
        val txtInternationalLanguage: TextView =
            viewItem.findViewById(R.id.txtInternationalLanguage)
        val txtNationalLanguage: TextView =
            viewItem.findViewById(R.id.txtNationalLanguage)
        val imgSelect: ImageView = viewItem.findViewById(R.id.imgSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(VioLFO.lfoConfig.layoutItemLanguage, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return if (itemLimit <= listData.size) {
            itemLimit
        } else {
            listData.size
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val language: Language = listData[position]
        holder.txtInternationalLanguage.text = language.internationalName

        if (language.nationalName.isBlank()) {
            holder.txtNationalLanguage.visibility = View.GONE
        } else {
            holder.txtNationalLanguage.visibility = View.VISIBLE
            holder.txtNationalLanguage.text = language.nationalName
        }

        holder.imgIconLanguage.setImageDrawable(
            AppCompatResources.getDrawable(
                context,
                language.idIcon
            )
        )
        holder.imgSelect.isSelected = language.isChoose
        holder.itemView.setOnClickListener {
            lfoSelectLanguage?.onSelectLanguage(language)
        }
    }

    fun getListData(): List<Language> {
        return listData
    }

    fun getLanguageSelected(): Language? {
        return listData.find { it.isChoose }
    }
}