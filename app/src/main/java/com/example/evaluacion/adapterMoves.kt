package com.example.evaluacion

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.evaluacion.models.movimientos

class adapterMoves(val context: Activity, val movimientos: ArrayList<movimientos>): RecyclerView.Adapter<adapterMoves.adapterMovesViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): adapterMovesViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.cardview_movimientos, parent, false)
        return  adapterMovesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return movimientos.size
    }

    override fun onBindViewHolder(holder: adapterMovesViewHolder, position: Int) {
        val m = movimientos[position]

        holder.tvDescripcion.text = "${m.Descripcion}"
        holder.tvMonto.text = "-$${m.Monto}"
        holder.tvFecha.text = "${m.Fecha}"
    }

    class adapterMovesViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tvDescripcion: TextView
        val tvFecha: TextView
        val tvMonto: TextView

        init {
            tvDescripcion = view.findViewById(R.id.tvDescripcion)
            tvFecha = view.findViewById(R.id.tv_fecha)
            tvMonto = view.findViewById(R.id.tv_monto)
        }
    }
}