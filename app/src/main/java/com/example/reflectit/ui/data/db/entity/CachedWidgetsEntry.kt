package com.example.reflectit.ui.data.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reflectit.ui.data.models.RemoteWidget

@Entity(tableName = "widgets")
data class CachedWidgetsEntry (
    @Embedded(prefix = "widget_")
    val remoteWidgets: RemoteWidget
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

//TODO change here