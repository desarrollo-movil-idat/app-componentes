package pe.edu.idat.app_componentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.idat.app_componentes.databinding.ActivityListaBinding

class ListaActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityListaBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.btnirregistro.setOnClickListener(this)
        getUsuarios()
    }

    fun getUsuarios(){
        val listaUsuarios = intent.getStringArrayListExtra("listausuarios") ?: arrayListOf()
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_list_item_1,
            listaUsuarios)
        binding.lvpersonas.adapter = adapter
    }

    override fun onClick(p0: View?) {
        //localhost:8080/usuarios
        startActivity(Intent(this, MainActivity::class.java))
    }
}