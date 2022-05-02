package mx.tecnm.tepic.u3p1_tello_ortega

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import mx.tecnm.tepic.u3p1_tello_ortega.databinding.ActivityEditarMascatoBinding


class EditarMascato : AppCompatActivity() {

    lateinit var binding: ActivityEditarMascatoBinding
    var id = " "
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditarMascatoBinding.inflate(layoutInflater)
        setContentView(binding.root)


        id = this.intent.extras!!.getString("idActualizar")!!

        val mascota = Mascota(this).mostrarDatosNombre(id)
        binding.actNombre.setText(mascota.nombrem)
        binding.actRaza.setText(mascota.raza)

        binding.btnACT.setOnClickListener {
            val mascota = Mascota(this)
            mascota.nombrem = binding.actNombre.text.toString()
            mascota.raza=binding.actRaza.text.toString()
            val res = mascota.actualizar(id)
            if(res){
                Toast.makeText(this,"SE ACTUALIZO CORRECTAMENTE", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(this,"ERROR NO ACTUALIZO CORRECTAMENTE", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnRegre.setOnClickListener {
            finish()
        }



    }
}