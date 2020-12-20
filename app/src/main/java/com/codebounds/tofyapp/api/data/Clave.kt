package com.codebounds.tofyapp.api.data

import androidx.lifecycle.LiveData
import androidx.room.*



@Entity
data class Clave(
    var token: String,
    var titulo: String,
    var usuarioToken: String,
    var usuario: String,
    var contrasena: String,
    var valor: String,
    var sincronizado: Boolean,
    var fechaInclusion: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

@Dao
interface ClaveDao{

    @Query("SELECT * FROM Clave")
    fun getAllClaves() : LiveData<List<Clave>>

    @Update
    suspend fun update(clave: Clave)

    @Insert
    suspend fun insert(Claves: List<Clave>)

    @Delete
    suspend fun delete(clave: Clave)

    @Query("DELETE FROM Clave")
    suspend fun deleteAll()

}

@Database(entities = [Clave::class],version = 1)
abstract class ClaveDb: RoomDatabase() {
    abstract fun claveDao(): ClaveDao
}

data class ClaveListaResponse(
    var claves: List<ClaveModel>
)

data class ClaveResponse(
    var clave: ClaveModel
)

data class ClaveModel(
    var tokenUsuario: String,
    var token: String,
    var titulo: String,
    var usuario: String,
    var contrasena: String,
    var valor: String,
    var fecha: String
)