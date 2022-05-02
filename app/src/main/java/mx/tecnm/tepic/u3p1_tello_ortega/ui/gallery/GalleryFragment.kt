package mx.tecnm.tepic.u3p1_tello_ortega.ui.gallery

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import mx.tecnm.tepic.u3p1_tello_ortega.Propietario
import mx.tecnm.tepic.u3p1_tello_ortega.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {

    private var _binding: FragmentGalleryBinding? = null
    var listaId = ArrayList<String>()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        mostrarDatos()
        binding.btnUpdate.isVisible=false

        binding.btnInser.setOnClickListener {
            val propietario = Propietario(requireContext())
            propietario.curp = binding.curp.text.toString()
            propietario.nombre = binding.nombre.text.toString()
            propietario.telefono = binding.tel.text.toString()
            propietario.edad = binding.edad.text.toString().toInt()

            val res = propietario.insertar()

            if (res){
                Toast.makeText(requireContext(), "SE INSERTO CON EXITO", Toast.LENGTH_LONG)
                    .show()
                mostrarDatos()
                binding.curp.setText("")
                binding.nombre.setText("")
                binding.tel.setText("")
                binding.edad.setText("")

            } else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("No se pudo insertar")
                    .show()
            }

        }

        binding.btnAll.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Propietario(requireContext()).selectAll())
        }

        binding.btnCel.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Propietario(requireContext()).selectPhone())
        }

        binding.btnMayor.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Propietario(requireContext()).selectEdadMayor())
        }

        binding.btnMenor.setOnClickListener {
            binding.lista.adapter = ArrayAdapter<String>(requireContext(),android.R.layout.simple_list_item_1,Propietario(requireContext()).selectEdadMenor())
        }

        binding.btnUpdate.setOnClickListener {
            val propietario = Propietario(requireContext())
            propietario.curp = binding.curp.text.toString()
            propietario.nombre = binding.nombre.text.toString()
            propietario.telefono = binding.tel.text.toString()
            propietario.edad = binding.edad.text.toString().toInt()

            val res = propietario.actualizar()
            if (res){
                Toast.makeText(requireContext(), "SE ACTUALIZO CORRECTAMENTE", Toast.LENGTH_LONG)
                    .show()
                mostrarDatos()
                binding.curp.setText("")
                binding.nombre.setText("")
                binding.tel.setText("")
                binding.edad.setText("")
                binding.btnUpdate.isVisible=false
            } else{
                AlertDialog.Builder(requireContext())
                    .setTitle("ERROR")
                    .setMessage("No se pudo actualizar")
                    .show()
            }
        }



        return root


    }


    fun mostrarDatos(){
        var lis = Propietario(requireContext()).mostrarDatos()
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
                .setNegativeButton("ELIMINAR"){d,i->
                    propietario.eliminan()
                    mostrarDatos()
                }
                .setPositiveButton("ACTUALIZAR"){d,i->
                    binding.btnUpdate.isVisible=true
                    binding.curp.setText(propietario.curp)
                    binding.nombre.setText(propietario.nombre)
                    binding.tel.setText(propietario.telefono)
                    binding.edad.setText(propietario.edad.toString())
                }
                .setNeutralButton("CERRAR"){d,i->}
                .show()
        }
    }

}