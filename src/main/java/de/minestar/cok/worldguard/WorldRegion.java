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
	
	public boolean isInRegion(ChunkCoordinates pos){
		return isInRegion(pos.posX, pos.posY, pos.posZ);
	}
	
	public boolean isInRegion(int x, int y, int z){
		return(x > Math.max(posA.posX, posB.posX) && x < Math.min(posA.posX, posB.posX)
				&& y > Math.max(posA.posY, posB.posY) && y < Math.min(posA.posY, posB.posY)
				&& z > Math.max(posA.posZ, posB.posZ) && z < Math.min(posA.posZ, posB.posZ));
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
