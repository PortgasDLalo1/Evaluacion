package com.example.evaluacion

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.evaluacion.api.API
import com.example.evaluacion.databinding.ActivityMainBinding
import com.example.evaluacion.models.movimientos
import com.example.evaluacion.models.tarjeta
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.lang.Exception
import java.util.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var adapter : adapterMoves? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getMovimientos()
        getTarjeta()

        binding.tvCVV.setOnClickListener {
            binding.tvCounter.visibility = View.VISIBLE
            object : CountDownTimer(300000, 1000) {

                override fun onTick(millisUntilFinished: Long) {
                    val secs = millisUntilFinished / 1000
                    val formatted = "${(secs / 60).toString().padStart(2, '0')} : ${(secs % 60).toString().padStart(2, '0')} "
                    binding.tvCounter.text = "$formatted"
                }

                override fun onFinish() {
                    binding.tvCounter.visibility = View.GONE
                    binding.tvCVV.text = "Generar CVV"
                }
            }.start()

            var r = Random()
            var valor = r.nextInt(9- 1)
            var valor2 = r.nextInt(9- 1)
            var valor3 = r.nextInt(9- 1)
            binding.tvCVV.text = "$valor$valor2$valor3"
        }

    }

    private fun getTarjeta(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    getRetrofit().create(API::class.java)
                        .selectTarjeta("tarjetacredito.php/1")?.enqueue(object : Callback<tarjeta>{
                            override fun onResponse(
                                call: Call<tarjeta>,
                                response: Response<tarjeta>
                            ) {
                                if (response.body() != null){
                                    val t = response.body()!!
                                    binding.tvTitular.text = "${t.Titular}"
                                    binding.tvFechaExp.text = "${t.Fecha}"

                                    val nt = t.NTarjeta.split("-")
                                    binding.f4digits.text = "${nt[0]}"
                                    binding.s4digits.text = "${nt[1]}"
                                    binding.t4digits.text = "${nt[2]}"
                                    binding.ff4digits.text = "${nt[3]}"

                                    binding.tvBanco.text = "${t.NBanco}"
                                }
                            }

                            override fun onFailure(call: Call<tarjeta>, t: Throwable) {
                                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
        }catch (e: Exception){
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getMovimientos(){
        try {
            CoroutineScope(Dispatchers.IO).launch {
                runOnUiThread {
                    getRetrofit().create(API::class.java)
                        .selectMovimientos("tarjetacredito-movimientos.php/3")?.enqueue(object : Callback<ArrayList<movimientos>>{
                            override fun onResponse(
                                call: Call<ArrayList<movimientos>>,
                                response: Response<ArrayList<movimientos>>
                            ) {
                                if (response.body() != null){
                                    val moves = response.body()!!
                                    if (moves.size > 0) {
                                        adapter = adapterMoves(this@MainActivity, moves)
                                        binding.rcMoves.adapter = adapter
                                    }else{
                                        Toast.makeText(this@MainActivity, "No tiene movimientos", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<ArrayList<movimientos>>, t: Throwable) {
                                Toast.makeText(this@MainActivity, t.message, Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
        }catch (e: Exception){
            Toast.makeText(this, "$e", Toast.LENGTH_SHORT).show()
        }
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://xqualo.com.mx/rest/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}