package com.grammarkadrama.DataHandler

import com.google.firebase.database.DataSnapshot

object Variables {
    val selectedOptionsMap = mutableMapOf<Int, Int>()
    lateinit var mainDataSnapshot: DataSnapshot
}