package br.com.maximatech.appb

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import br.com.maximatech.appb.databinding.ActivityMainBinding
import br.com.maximatech.appb.model.PDV
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private var pdv: PDV? = null
    private var _binding: ActivityMainBinding? = null
    private val binding: ActivityMainBinding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("pdv")) {
            pdv = Gson().fromJson(intent.getStringExtra("pdv"), PDV::class.java)
            pdv?.let {
                binding.codigo.text = it.codigo
                binding.razaoSocial.text = it.RazaoSocial
                binding.cpfCnpj.text = it.cpfCnpj
                if(it.tarefa){
                    binding.check1.isChecked = true
                    binding.check2.isChecked = true
                    binding.btnFechar.isEnabled = true
                }
            }
        }

        binding.check1.setOnCheckedChangeListener { _, checked ->
            binding.btnFechar.isEnabled = binding.check1.isChecked && binding.check2.isChecked
            if (checked) {
                binding.img1.visibility = View.VISIBLE
            } else{
                binding.img1.visibility = View.GONE
            }
        }

        binding.check2.setOnCheckedChangeListener { _, checked ->
            binding.btnFechar.isEnabled = binding.check1.isChecked && binding.check2.isChecked
            if (checked) {
                binding.img2.visibility = View.VISIBLE
            } else {
                binding.img2.visibility = View.GONE
            }
        }

        binding.btnFechar.setOnClickListener {
            if(pdv != null) {
                pdv!!.tarefa = true
                intent.putExtra("tarefas", Gson().toJson(pdv))
                setResult(RESULT_OK, intent)
                finish()
            }else{
                Snackbar.make(this, binding.root, "Inicie o app pelo app A", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}