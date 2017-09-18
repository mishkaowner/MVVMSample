package com.mishkaowner.mvvmsample

import android.arch.persistence.room.*

import io.reactivex.Flowable

@Dao
interface UserDao {
    @Query("SELECT * FROM Users")
    fun getAllUsers(): Flowable<List<User>>

    @Query("SELECT * FROM Users WHERE userid = :id")
    fun getUserById(id: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)

    @Delete
    fun deleteUser(user: User)

    @Delete
    fun deleteUsers(vararg user: User)
}