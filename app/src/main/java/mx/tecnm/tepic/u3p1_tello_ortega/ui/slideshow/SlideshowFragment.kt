package mx.tecnm.tepic.u3p1_tello_ortega.ui.slideshow

import android.R
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import mx.tecnm.tepic.u3p1_tello_ortega.EditarMascato
import mx.tecnm.tepic.u3p1_tello_ortega.Mascota
import mx.tecnm.tepic.u3p1_tello_ortega.Propietario
import mx.tecnm.tepic.u3p1_tello_ortega.databinding.FragmentSlideshowBinding

class SlideshowFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    var listId = ArrayList<Int>()
    var listaId = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root
        mostrarDatos()
        var id = " "
        binding.btnInser.setOnClickListener {
            val mascota = Mascota(requireContext())
            mascota.nombrem = binding.nombreM.text.toString()
            mascota.raza = binding.raza.text.toString()
            mascota.curps = binding.curp1.text.toString()

            val res = mascota.insertar()

            if (res){
                Toast.makeText(requireContext(), "SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatos()
                binding.nombreM.setText("")
                binding.raza.setText("")
                binding.curp1.setText("")

            } else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("No se pudo insertar")
                    .show()
            }

        }


        binding.btnCurp2.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Mascota(requireContext()).selectPropi())
        }

        binding.btnNombre.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Mascota(requireContext()).selecNombre())
        }

        binding.btnRaza.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Mascota(requireContext()).selectRaza())
        }



        binding.btnCurp.setOnClickListener {
            nombrePropietario()
        }
        return root
    }

    fun nombrePropietario(){
        val lis = Propietario(requireContext()).mostrarDatos()
        val nombreP = ArrayList<String>()

        listaId.clear()
        (0..lis.size-1).forEach {
            val al = lis.get(it)
            nombreP.add(al.nombre)
            listaId.add(al.curp)
        }

        binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,nombreP)
        binding.lista.setOnItemClickListener { adapterView, view, indice, l ->
            val curplis = listaId.get(indice)
            val propietario = Propietario(requireContext()).mostrarDatosCurp(curplis)

            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("Que deseas hacer con el propietario ${propietario.nombre},\n Con la curp ${propietario.curp} ?")
                .setNegativeButton("CANCELAR"){d,i->

                }
                .setPositiveButton("TOMAR LA CURP"){d,i->
                    binding.curp1.setText(propietario.curp)
                }
                .show()
        }
    }

    private fun activarEvento(listaConductor: ListView) {
        listaConductor.setOnItemClickListener { adapterView, view, indice, l ->
            val idSeleccionado = listId[indice]
            AlertDialog.Builder(requireContext())
                .setTitle("ATENCION")
                .setMessage("Que desea hacer con la mascota")
                .setPositiveButton("EDITAR"){d,i ->  actualizar(idSeleccionado)}
                .setNegativeButton("Eliminar"){d, i -> eliminar(idSeleccionado)}
                .setNeutralButton("Cancelar"){id, i->}
                .show()
        }

    }

    fun mostrarDatos() {
        val mascotas = Mascota(requireContext()).mostrarDatos()
        binding.lista.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1,mascotas)
        listId.clear()
        listId = Mascota(requireContext()).obtenerid()
        activarEvento(binding.lista)
    }

    private fun eliminar(idSeleccionado: Int) {
        AlertDialog.Builder(requireContext())
            .setTitle("ATENCIO")
            .setMessage("Seguro que deseas eliminar la mascota")
            .setPositiveButton("SI"){d,i ->
                val resultado = Mascota(requireContext()).eliminan(idSeleccionado)
                if(resultado){
                    Toast.makeText(requireContext(),"SE ELIMINO CORRECTAMENTE",Toast.LENGTH_LONG).show()
                    mostrarDatos()
                }else{
                    Toast.makeText(requireContext(),"NO SE ELIMINO CORRECTAMENTE",Toast.LENGTH_LONG).show()
                }
            }
            .setNegativeButton("NO"){d,i->
                d.cancel()
            }
            .show()

    }

    private fun actualizar(idSeleccionado: Int){

        val intento = Intent(requireContext(),EditarMascato::class.java)
        intento.putExtra("idActualizar", idSeleccionado.toString())
        startActivity(intento)

        AlertDialog.Builder(requireContext()).setMessage("QUIERES ACTUALIZAR LA LISTA")
            .setPositiveButton("si"){d,i-> mostrarDatos()}
            .setNegativeButton("no"){d,i-> d.cancel()}
            .show()

    }



}