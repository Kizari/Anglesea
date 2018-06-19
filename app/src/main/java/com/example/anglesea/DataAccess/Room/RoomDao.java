package com.example.anglesea.DataAccess.Room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.IGNORE;

@Dao
public interface RoomDao
{
    @Insert(onConflict = IGNORE)
    long insert(Room room);

    @Query("SELECT * FROM room")
    List<Room> getAll();

    @Query("SELECT * FROM room WHERE roomName = :name")
    Room getByName(String name);

    @Query("SELECT * FROM room WHERE id = :id")
    Room getById(int id);

    @Delete
    void delete(Room room);
}
