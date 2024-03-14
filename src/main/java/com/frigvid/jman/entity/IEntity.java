package com.frigvid.jman.entity;

import com.frigvid.jman.game.map.Map;
import com.frigvid.jman.game.map.TileType;
import javafx.scene.Group;

public interface IEntity
{
	void load(Group gameBoard);
	void move(Direction direction, Map map);
	void setSpawn(TileType[][] logicGrid);
}
