package pe.edu.idat.app_componentes

import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.idat.app_componentes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val listHobbies = ArrayList<String>()
    private val listUsuario = ArrayList<String>()
    private var estadoCivil = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.cbfutbol.setOnClickListener(this)
        binding.cbmusica.setOnClickListener(this)
        binding.cbotros.setOnClickListener(this)

        binding.btnregistrar.setOnClickListener(this)
        binding.btnirlista.setOnClickListener(this)
    }

    //obteniendo los valores de radiobutton, checkbox, y spinner
    fun getGenero(): String{
        var genero = ""
        when(binding.rggenero.checkedRadioButtonId){
            R.id.rbmasculino -> genero = binding.rbmasculino.text.toString()
            R.id.rbfemenino -> genero = binding.rbfemenino.text.toString()
            R.id.rbne -> genero = binding.rbne.text.toString()
        }
        return genero
    }

    override fun onClick(view: View) {
        if(view is CheckBox){
            getHobbies(view)
        }else{
            when(view.id){
                R.id.btnregistrar -> registrarUsuario()
                R.id.btnirlista -> verUsuarios()
            }
        }
    }
    fun getHobbies(view: View){
        val checkbox = view as CheckBox
        if(checkbox.isChecked){
            listHobbies.add(checkbox.text.toString())
        }else{
            listHobbies.remove(checkbox.text.toString())
        }
    }
    fun registrarUsuario(){}
    fun verUsuarios(){}

}