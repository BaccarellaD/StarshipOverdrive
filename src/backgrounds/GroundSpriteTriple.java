package backgrounds;

import controllers.ImageFactory.ImageType;

public class GroundSpriteTriple implements Comparable<GroundSpriteTriple> {
	
	private final int x;
	private final int y;
	private final ImageType type;

	
	
	public GroundSpriteTriple(int x, int y, ImageType type) {
		super();
		this.x = x;
		this.y = y;
		this.type = type;
	}



	public int getX() {
		return x;
	}



	public int getY() {
		return y;
	}



	public ImageType getType() {
		return type;
	}



	@Override
	public int compareTo(GroundSpriteTriple triple) {
		return Integer.compare(getY(), triple.getY());
	}

}
