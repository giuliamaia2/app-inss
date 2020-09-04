package com.giulia.appinss

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isInvisible
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }


    fun calcular(view: View) {
        val nome: String = et_nome.text.toString()
        val salario: String = et_salario.text.toString()
        var desconto = et_outros.text.toString()

        var color = Color.BLACK
        var porcentagem = 0.0
        if (validar(nome, salario, desconto)) {
            when (salario.toDouble()) {
                in 700.0..1_045.0 -> {
                    porcentagem = 7.5
                    color = Color.BLUE
                }
                in 1045.01..2089.60 -> {
                    porcentagem = 9.0
                    color = Color.GREEN
                }
                in 2089.61..3134.40 -> {
                    porcentagem = 12.0
                    color = Color.MAGENTA
                }
                in 3134.41..6101.06 -> {
                    porcentagem = 14.0
                    color = Color.RED
                }
                else -> {
                    porcentagem = -1.0
                    color = Color.BLACK
                }
            }


            var inss = 0.0
            var text = ""
            when (porcentagem) {
                -1.0 -> {
                    text = "$nome, seu desconto do INSS será de R$ 713,09"
                    inss = 713.09
                }
                else -> {
                    inss = (salario.toDouble() * porcentagem / 100)
                    text = "$nome, sua porcentagem de contribuição é de $porcentagem% " +
                            "\n O desconto ficará = ${inss}"
                }
            }

            tv_aliquota.setTextColor(color)
            tv_aliquota.text = text
            tv_aliquota.visibility = View.VISIBLE

            val total: Double = inss + (if (desconto == "") 0.0 else desconto.toDouble())
            tv_resultado.text =
                "Com os descontos, seu salário líquido é de: ${salario.toDouble()
                    .minus(total)} "
            tv_resultado.visibility = View.VISIBLE;

        }


    }


    fun validar(nome: String, salario: String, desconto: String): Boolean {
        if (nome == "") {
            Toast.makeText(this, "O nome é obrigatório", Toast.LENGTH_SHORT).show()
            return false
        } else if (salario == "") {
            Toast.makeText(this, "Salário é obrigatório", Toast.LENGTH_SHORT).show()
            return false
        } else if (salario != "" && salario.toDouble() < 700.0) {
            Toast.makeText(this, "Salário deve ser maior que R$700", Toast.LENGTH_SHORT)
                .show()
            return false
        } else if (desconto != "" && desconto.toDouble() < 0) {
            Toast.makeText(this, "O desconto não pode ser negativo!", Toast.LENGTH_SHORT).show()
            return false
        } else {
            return true
        }
    }

    fun limpar() {
        tv_resultado.visibility = View.GONE
        tv_aliquota.visibility = View.GONE
    }

}