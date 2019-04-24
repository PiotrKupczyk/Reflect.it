package com.example.reflectit.ui.data.db.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.reflectit.ui.data.db.entity.CachedWidgetsEntry

interface WidgetsDao {
    @Query("SELECT * FROM widgets")
    fun getAllWidgets(): List<CachedWidgetsEntry>

    @Query("SELECT * FROM widgets WHERE widget_category LIKE :category")
    fun getWidgetsByCategory(category: String): List<CachedWidgetsEntry>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg widgets: CachedWidgetsEntry)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertWidgets(widget: CachedWidgetsEntry)

}