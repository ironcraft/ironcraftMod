package ironcraft.doubi125.util;

import ironcraft.doubi125.town.BoundBox;
import ironcraft.doubi125.town.home.HomeFloor;
import ironcraft.doubi125.town.home.HomeRoof;
import ironcraft.doubi125.util.blocks.BlockPattern;
import ironcraft.doubi125.util.blocks.OrientableBlock;
import net.minecraft.src.World;

public class ToolBox
{
	public static void buildSimpleWalls(World world, BoundBox floor, int h, BuildingBlock block)
	{
		for(int i=0; i<floor.getW(); i++)
		{
			floor.setBlock(world, i, h, 0, block);
			floor.setBlock(world, i, h, floor.getH() - 1, block);
		}
		for(int i=0; i<floor.getH(); i++)
		{
			floor.setBlock(world, 0, h, i, block);
			floor.setBlock(world, floor.getW() - 1, h, i, block);
		}
	}
	
	public static void buildSimpleWalls(World world, BoundBox floor, int h, BlockPattern pattern)
	{
		for(int i=0; i<floor.getW(); i++)
		{
			BuildingBlock block = pattern.getBlockWithMirrorPattern(i, floor.getW());
			floor.setBlock(world, i, h, 0, block);
			floor.setBlock(world, i, h, floor.getH() - 1, block);
		}
		for(int i=0; i<floor.getH(); i++)
		{
			BuildingBlock block = pattern.getBlockWithMirrorPattern(i, floor.getH());
			floor.setBlock(world, 0, h, i, block);
			floor.setBlock(world, floor.getW() - 1, h, i, block);
		}
	}
	
	public static void buildBevelWalls(World world, BoundBox floor, int bevelSize, int h, BuildingBlock block)
	{
		for(int i=0; i<floor.getW() - 2*bevelSize; i++)
		{
			floor.setBlock(world, i + bevelSize, h, 0, block);
			floor.setBlock(world, i + bevelSize, h, floor.getH() - 1, block);
		}
		for(int i=0; i<floor.getH() - 2*bevelSize; i++)
		{
			floor.setBlock(world, 0, h, i + bevelSize, block);
			floor.setBlock(world, floor.getW() - 1, h, i + bevelSize, block);
		}
		for(int i=0; i<bevelSize; i++)
		{
			floor.setBlock(world, bevelSize -i  - 1, h, i, block);
			floor.setBlock(world, i, h, floor.getH() - bevelSize +i, block);
			floor.setBlock(world, floor.getW() - bevelSize + i, h, i, block);
			floor.setBlock(world, floor.getW() - i - 1, h, floor.getH() - bevelSize + i, block);
		}
	}
	
	public static void buildBevelWalls(World world, HomeFloor floor, int bevelSize, int h, BlockPattern edgeBlocks, BlockPattern wallBlocks)
	{
		for(int i=0; i<floor.getW() - 2*bevelSize; i++)
		{
			floor.setBlock(world, i + bevelSize, h, 0, wallBlocks.getBlockWithMirrorPattern(i, floor.getW() - 2*bevelSize));
			floor.setBlock(world, i + bevelSize, h, floor.getH() - 1, wallBlocks.getBlockWithMirrorPattern(i, floor.getW() - 2*bevelSize));
		}
		for(int i=0; i<floor.getH() - 2*bevelSize; i++)
		{
			floor.setBlock(world, 0, h, i + bevelSize, wallBlocks.getBlockWithMirrorPattern(i, floor.getW() - 2*bevelSize));
			floor.setBlock(world, floor.getW() - 1, h, i + bevelSize, wallBlocks.getBlockWithMirrorPattern(i, floor.getW() - 2*bevelSize));
		}
		for(int i=0; i<bevelSize; i++)
		{
			BuildingBlock block = edgeBlocks.getBlockWithMirrorPattern(i, bevelSize);
			floor.setBlock(world, bevelSize -i  - 1, h, i, block);
			floor.setBlock(world, i, h, floor.getH() - bevelSize +i, block);
			floor.setBlock(world, floor.getW() - bevelSize + i, h, i, block);
			floor.setBlock(world, floor.getW() - i - 1, h, floor.getH() - bevelSize + i, block);
		}
	}
	
	public static void buildFlatRoof(World world, HomeRoof floor, BuildingBlock block)
	{
		for(int i=0; i<floor.getW(); i++)
		{
			for(int j=0; j<floor.getH(); j++)
			{
				floor.setBlock(world, i, 0, j, block);
			}
		}
	}
	
	public static void buildSimpleFlooring(World world, HomeFloor floor, int h, BuildingBlock block)
	{
		for(int i=0; i<floor.getW(); i++)
		{
			for(int j=0; j<floor.getH(); j++)
			{
				floor.setBlock(world, i, h, j, block);
			}
		}
	}
	
	public static void buildBevelFlooring(World world, BoundBox floor, int bevelSize, int h, BuildingBlock block)
	{
		for(int i=0; i<floor.getW(); i++)
		{
			for(int j=0; j<floor.getH(); j++)
			{
				if(i+j >= bevelSize-1 && floor.getW()-i + j >= bevelSize && floor.getW()-i + floor.getH()-j >= bevelSize+1 && i + floor.getH()-j >= bevelSize)
					floor.setBlock(world, i, h, j, block);
			}
		}
	}
	
	public static void buildSimpleRoof(World world, Dir dir, BoundBox roof, BlockPattern pattern, OrientableBlock block, BuildingBlock topBlock)
	{
		if(dir == Dir.N || dir == Dir.S)
		{
			for(int j=-1; j<=roof.getH(); j++)
			{				
				for(int i=-1; i<roof.getW()/2; i++)
				{
					block.setDir(Dir.W);
					roof.setBlock(world, i, i+1, j, block);
					block.setDir(Dir.E);
					roof.setBlock(world, roof.getW()-i-1, i+1, j, block);
					if(j==0 || j==roof.getH()-1)
					{
						for(int k=-1; k<i; k++)
						{
							roof.setBlock(world, roof.getW()-i-1, k+1, j, pattern.getBlockWithSimplePattern(i-k-1));
							roof.setBlock(world, i, k+1, j, pattern.getBlockWithSimplePattern(i-k-1));
						}
					}
				}
				if(roof.getW()%2 != 0)
				{
					roof.setBlock(world, roof.getW()/2, roof.getW()/2+1, j, topBlock);
					if(j==0 || j==roof.getH()-1)
					{
						for(int k=-1; k<roof.getW()/2; k++)
						{
							roof.setBlock(world, roof.getW()/2, k+1, j, pattern.getBlockWithSimplePattern(roof.getW()/2-k-1));						
						}
					}
				}
			}
		}
		else
		{
			for(int j=-1; j<=roof.getW(); j++)
			{				
				for(int i=-1; i<roof.getH()/2; i++)
				{
					block.setDir(Dir.N);
					roof.setBlock(world, j, i+1, i, block);
					block.setDir(Dir.S);
					roof.setBlock(world, j, i+1, roof.getH()-i-1, block);
					if(j==0 || j==roof.getW()-1)
					{
						for(int k=-1; k<i; k++)
						{
							roof.setBlock(world, j, k+1, roof.getH()-i-1, pattern.getBlockWithSimplePattern(i-k-1));
							roof.setBlock(world, j, k+1, i, pattern.getBlockWithSimplePattern(i-k-1));
						}
					}
				}
				if(roof.getH()%2 != 0)
				{
					roof.setBlock(world, j, roof.getH()/2+1, roof.getH()/2, topBlock);
					if(j==0 || j==roof.getW()-1)
					{
						for(int k=-1; k<roof.getH()/2; k++)
						{
							roof.setBlock(world, j, k+1, roof.getH()/2, pattern.getBlockWithSimplePattern(roof.getW()/2-k-1));						
						}
					}
				}
			}
		}
	}
	
	public static void buildPatternWall(World world, BoundBox floor, int x1, int y1, int z1, int x2, int y2, int z2, BlockPattern pattern)
	{
		int xMin = Math.min(x1, x2), xMax = Math.max(x1, x2),
			yMin = Math.min(x1, x2), yMax = Math.max(x1, x2),
			zMin = Math.min(x1, x2), zMax = Math.max(x1, x2);
		for(int i=yMin; i<yMax; i++)
		{
			buildPatternWall(world, floor, xMin, i, zMin, xMax-xMin, Dir.E, pattern);
			buildPatternWall(world, floor, xMax, i, zMin, zMax-zMin, Dir.S, pattern);
			buildPatternWall(world, floor, xMax, i, zMax, xMax-xMin, Dir.W, pattern);
			buildPatternWall(world, floor, xMin, i, zMax, zMax-zMin, Dir.N, pattern);
		}
	}
	
	public static void buildPatternWall(World world, BoundBox floor, int x, int y, int z, int size, Dir dir, BlockPattern pattern)
	{
		for(int i=0; i<size; i++)
		{
			switch(dir)
			{
			case N:
				floor.setBlock(world, x, y, z-i, pattern.getBlockWithMirrorPattern(i, size));
				break;
			case S:
				floor.setBlock(world, x, y, z+i, pattern.getBlockWithMirrorPattern(i, size));
				break;
			case E:
				floor.setBlock(world, x+i, y, z, pattern.getBlockWithMirrorPattern(i, size));
				break;
			case W:
				floor.setBlock(world, x-i, y, z, pattern.getBlockWithMirrorPattern(i, size));
				break;
			}
		}
	}
}
