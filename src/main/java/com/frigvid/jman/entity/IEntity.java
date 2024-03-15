package com.frigvid.jman.entity;

import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;

public interface IEntity
{
	void load(Group gameBoard);
	void setSpawn(TileType[][] logicGrid);
}
