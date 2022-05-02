package mx.tecnm.tepic.u3p1_tello_ortega

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException

class Mascota(este : Context) {
    var nombrem = ""
    var raza = " "
    var curps = " "
    var este = este
    private var err= ""

    fun insertar(): Boolean{
        var basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = ""
        try{
            var tabla = basedeDatos.writableDatabase
            var datos = ContentValues()
            datos.put("NOMBREM",nombrem)
            datos.put("RAZA",raza)
            datos.put("CURPS",curps)
            var res = tabla.insert("MASCOTS",null,datos)
            if (res == -1L){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedeDatos.close()
        }
        return true
    }

    fun mostrarDatos() : ArrayList<String>{
        val basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        val arreglo = ArrayList<String>()
        err = ""
        try{
            val tabla = basedeDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM MASCOTS"
            val cursor = tabla.rawQuery(SQL_SELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val mascota = cursor.getString(0)+"  "+cursor.getString(1)+"  "+cursor.getString(2)+"  "+cursor.getString(3)
                    arreglo.add(mascota)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedeDatos.close()
        }
        return arreglo
    }

    fun obtenerid() : ArrayList<Int> {
        val tablaConductor = BasedeDatos(este,"veterinaria", null,1).readableDatabase
        val res = ArrayList<Int>()
        val cursos= tablaConductor.query("MASCOTS", arrayOf("*"),null,null,null,null,null)
        if(cursos.moveToFirst()){
            do {
                res.add(cursos.getInt(0))
            }while (cursos.moveToNext())
        }
        return res

    }

    fun mostrarDatosNombre(buscarNombre:String) : Mascota{
        val basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = ""
        val mascota = Mascota(este)
        try{
            val tabla = basedeDatos.readableDatabase
            val SQL_SELECT = "SELECT * FROM MASCOTS WHERE IDMASCOTA=?"
            val cursor = tabla.rawQuery(SQL_SELECT, arrayOf(buscarNombre))
            if(cursor.moveToFirst()){
                mascota.nombrem = cursor.getString(1)
                mascota.raza = cursor.getString(2)
                mascota.curps = cursor.getString(3)

            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedeDatos.close()
        }
        return mascota
    }

    fun actualizar(idActualizar : String ):Boolean{
        var basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = " "
        try{
            val tabla = basedeDatos.writableDatabase
            val datosActualizados = ContentValues()
            datosActualizados.put("NOMBREM",nombrem)
            datosActualizados.put("RAZA",raza)
            val res = tabla.update("MASCOTS",datosActualizados,"IDMASCOTA=?", arrayOf(idActualizar))

            if (res==0){
                return false
            }

        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedeDatos.close()
        }
        return true
    }


    fun eliminan(idEliminar : Int ):Boolean{
        val basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = " "
        try{
            val tabla = basedeDatos.writableDatabase
            val res = tabla.delete("MASCOTS","IDMASCOTA=?", arrayOf(idEliminar.toString()))

            if (res==0){
                return false
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
            return false
        }finally {
            basedeDatos.close()
        }
        return true
    }


    fun selecNombre(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT IDMASCOTA,NOMBREM FROM MASCOTS WHERE NOMBREM LIKE '%a%'",null)

        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) + " ; " +cursor.getString(1))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }

    fun selectPropi(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT NOMBREM,CURPS FROM MASCOTS WHERE CURPS='TEOP27019'",null)

        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) + " ; " +cursor.getString(1))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }

    fun selectRaza(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT * FROM MASCOTS WHERE RAZA='Aleman'",null)

        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) + " ; " +cursor.getString(1) + " ; "  + cursor.getString(2)+ " ; "
                    + cursor.getString(3))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }

}