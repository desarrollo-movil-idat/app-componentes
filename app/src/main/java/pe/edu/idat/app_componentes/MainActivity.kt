package pe.edu.idat.app_componentes

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import pe.edu.idat.app_componentes.databinding.ActivityMainBinding
import pe.edu.idat.app_componentes.utils.AppMensaje
import pe.edu.idat.app_componentes.utils.TipoMensaje

class MainActivity : AppCompatActivity(), View.OnClickListener, AdapterView.OnItemSelectedListener {

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
        ArrayAdapter.createFromResource(this, R.array.estado_civil_array,
            android.R.layout.simple_spinner_item).also {
                adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spestadocivil.adapter = adapter
        }
        binding.cbfutbol.setOnClickListener(this)
        binding.cbmusica.setOnClickListener(this)
        binding.cbotros.setOnClickListener(this)

        binding.btnregistrar.setOnClickListener(this)
        binding.btnirlista.setOnClickListener(this)
        binding.spestadocivil.onItemSelectedListener = this
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
    fun registrarUsuario(){
        if(formularioValido()){
            val usuario = binding.etnombres.text.toString()+"-"+
                    binding.etapellidos.text.toString()+"-"+
                    getGenero()+"-"+
                    listHobbies.toTypedArray().contentToString()+"-"+
                    estadoCivil+"-"+binding.swnotificar.isChecked
            listUsuario.add(usuario)
            setearControles()
            snackbarMensaje("Usuario registrado correctamente", TipoMensaje.SUCCESS)
        }
    }
    fun verUsuarios(){
        //localhost:8080/usuarios?nombre=&fecha=
        val intentLista = Intent(this, ListaActivity::class.java).apply {
            putStringArrayListExtra("listausuarios", listUsuario)
            //solo para un valor de tipo string, int, double, float, boolean
            //putExtra("usuario", 2)
        }
        startActivity(intentLista)
    }
    fun setearControles(){
        listHobbies.clear()
        binding.etnombres.text.clear()
        binding.etapellidos.text.clear()
        binding.rggenero.clearCheck()
        binding.cbfutbol.isChecked = false
        binding.cbmusica.isChecked = false
        binding.cbotros.isChecked = false
        binding.swnotificar.isChecked = false
        binding.spestadocivil.setSelection(0)
        binding.etnombres.isFocusableInTouchMode=true
        binding.etnombres.requestFocus()
    }
    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        estadoCivil = if (position > 0){
            p0!!.getItemAtPosition(position).toString()
        }else ""
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    //Validación del formulario
    private fun nombresApellidosValidos():Boolean{
        var respuesta = true
        if(binding.etnombres.text.toString().trim().isEmpty()){
            binding.etnombres.isFocusableInTouchMode = true
            binding.etnombres.requestFocus()
            respuesta = false
        }else if(binding.etapellidos.text.toString().trim().isEmpty()){
            binding.etapellidos.isFocusableInTouchMode = true
            binding.etapellidos.requestFocus()
            respuesta = false
        }
        return respuesta
    }
    private fun generoValido(): Boolean{
        return binding.rggenero.checkedRadioButtonId != -1
    }
    private fun hobbiesValido():Boolean {
        return if(binding.cbfutbol.isChecked
            || binding.cbmusica.isChecked || binding.cbotros.isChecked) true
        else false
    }
    private fun estadoCivilValido():Boolean{
        return estadoCivil != ""
    }

    private fun snackbarMensaje(mensaje: String, tipoMensaje: TipoMensaje){
        AppMensaje.enviarMensaje(binding.root, mensaje, tipoMensaje)
    }
    private fun formularioValido():Boolean{
        var respuesta = false
        if(!nombresApellidosValidos()){
            snackbarMensaje("Nombres y apellidos obligatorios", TipoMensaje.ERROR)
        }else if(!generoValido()){
            snackbarMensaje("El género es obligatorios", TipoMensaje.ERROR)
        } else if(!hobbiesValido()){
            snackbarMensaje("Seleccione al menos un hobbie", TipoMensaje.ERROR)
        } else if(!estadoCivilValido()){
            snackbarMensaje("Estado civil obligatorio", TipoMensaje.ERROR)
        }else {
            respuesta = true
        }
        return respuesta
    }

}