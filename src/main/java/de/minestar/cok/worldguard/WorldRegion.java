package de.minestar.cok.worldguard;

import net.minecraft.util.ChunkCoordinates;

public class WorldRegion {

	private ChunkCoordinates posA;
	private ChunkCoordinates posB;
	
	public WorldRegion(ChunkCoordinates posA){
		this.posA = posA;
	}
	
	public WorldRegion(ChunkCoordinates posA, ChunkCoordinates posB){
		this.posA = posA;
		this.posB = posB;
	}
	
	/**
	 * Returns true if the given coordinates are within this region
	 * 
	 * @param pos
	 * @return
	 */
	public boolean isInRegion(ChunkCoordinates pos){
		return isInRegion(pos.posX, pos.posY, pos.posZ);
	}
	
	/**
	 * Returns true if the given coordinates are within this region
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public boolean isInRegion(int x, int y, int z){
		return(x >= Math.min(posA.posX, posB.posX) && x <= Math.max(posA.posX, posB.posX)
				&& y >= Math.min(posA.posY, posB.posY) && y <= Math.max(posA.posY, posB.posY)
				&& z >= Math.min(posA.posZ, posB.posZ) && z <= Math.max(posA.posZ, posB.posZ));
	}

	public ChunkCoordinates getPosA() {
		return posA;
	}

	public void setPosA(ChunkCoordinates posA) {
		this.posA = posA;
	}

	public ChunkCoordinates getPosB() {
		return posB;
	}

	public void setPosB(ChunkCoordinates posB) {
		this.posB = posB;
	}
	
}
