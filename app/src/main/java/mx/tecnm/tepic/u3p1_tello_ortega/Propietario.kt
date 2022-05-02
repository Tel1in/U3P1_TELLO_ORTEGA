package mx.tecnm.tepic.u3p1_tello_ortega

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException


class Propietario(este: Context){

    var curp = " "
    var nombre = " "
    var telefono = " "
    var edad = 0
    var este = este
    private var err = " "

    fun insertar(): Boolean{
        var basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = ""
        try{
            var tabla = basedeDatos.writableDatabase
            var datos = ContentValues()
            datos.put("CURP",curp)
            datos.put("NOMBRE",nombre)
            datos.put("TELEFONO",telefono)
            datos.put("EDAD",edad)

            var res = tabla.insert("PROPIETARIO",null,datos)
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

    fun mostrarDatos() : ArrayList<Propietario>{
        var basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        var arreglo = ArrayList<Propietario>()
        err = ""
        try{
            var tabla = basedeDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM PROPIETARIO"
            var cursor = tabla.rawQuery(SQL_SELECT,null)
            if(cursor.moveToFirst()){
                do{
                    val propietario = Propietario(este)
                    propietario.curp = cursor.getString(0)
                    propietario.nombre = cursor.getString(1)
                    propietario.telefono = cursor.getString(2)
                    propietario.edad = cursor.getInt(3)
                    arreglo.add(propietario)
                }while (cursor.moveToNext())
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedeDatos.close()
        }
        return arreglo
    }



    fun mostrarDatosCurp(curpAbuscar:String) : Propietario{
        var basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = ""
        val propietario = Propietario(este)
        try{
            var tabla = basedeDatos.readableDatabase
            var SQL_SELECT = "SELECT * FROM PROPIETARIO WHERE CURP=?"
            var cursor = tabla.rawQuery(SQL_SELECT, arrayOf(curpAbuscar))
            if(cursor.moveToFirst()){

                propietario.curp = cursor.getString(0)
                propietario.nombre = cursor.getString(1)
                propietario.telefono = cursor.getString(2)
                propietario.edad = cursor.getString(3).toInt()
            }
        }catch (err: SQLiteException){
            this.err = err.message!!
        }finally {
            basedeDatos.close()
        }
        return propietario
    }



    fun actualizar():Boolean{
        var basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = " "
        try{
            var tabla = basedeDatos.writableDatabase
            var datosActualizados = ContentValues()
            datosActualizados.put("NOMBRE",nombre)
            datosActualizados.put("TELEFONO",telefono)
            datosActualizados.put("EDAD",edad)
            val res = tabla.update("PROPIETARIO",datosActualizados,"CURP=?", arrayOf(curp))

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


    fun eliminan():Boolean{
        val basedeDatos = BasedeDatos(este, "veterinaria",null,1)
        err = " "
        try{
            val tabla = basedeDatos.writableDatabase
            val res = tabla.delete("PROPIETARIO","CURP=?", arrayOf(curp))

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

    fun selectAll(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT * FROM PROPIETARIO",null)

        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) + " ; " +cursor.getString(1) + " ; "  + cursor.getString(2)+ " ; "
                    + cursor.getString(3))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }

    fun selectPhone(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT CURP,TELEFONO FROM PROPIETARIO WHERE TELEFONO LIKE '%311%'",null)
        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) +"  "+cursor.getString(1))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }

    fun selectEdadMayor(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT CURP,EDAD FROM PROPIETARIO WHERE EDAD >40 AND EDAD <100",null)
        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) +"  "+cursor.getString(1))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }

    fun selectEdadMenor(): ArrayList<String>{
        val result = ArrayList<String>()
        val cursor =  BasedeDatos(este,"veterinaria",null,1).readableDatabase.rawQuery("SELECT CURP,EDAD FROM PROPIETARIO WHERE EDAD >20 AND  EDAD <=40 ",null)
        if(cursor.moveToFirst()){
            do{ result.add(cursor.getString(0) +"  "+cursor.getString(1))
            }while (cursor.moveToNext())
        }//if
        else { result.add("NO SE ENCONTRO DATOS") }//else

        return result
    }


}