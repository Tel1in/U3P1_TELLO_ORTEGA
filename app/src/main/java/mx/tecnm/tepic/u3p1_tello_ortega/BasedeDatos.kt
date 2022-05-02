package mx.tecnm.tepic.u3p1_tello_ortega

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BasedeDatos(
    context: Context,
    name: String,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, name, factory, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE PROPIETARIO (CURP VARCHAR(10) PRIMARY KEY NOT NULL, NOMBRE VARCHAR(200),TELEFONO VARCHAR(50),EDAD INTEGER)")

        db.execSQL("CREATE TABLE MASCOTS (IDMASCOTA INTEGER  PRIMARY KEY AUTOINCREMENT, NOMBREM VARCHAR(200),RAZA VARCHAR(50),CURPS VARCHAR(10), FOREIGN KEY (CURPS) REFERENCES PROPIETARIO(CURP))")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

    }
}