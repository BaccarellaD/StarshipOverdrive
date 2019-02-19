package menus;

import application.Game.GameLevel;
import controllers.ImageFactory.ImageType;

public class LevelInfo {
	
	private String levelTitle;
	private String levelLocation;
	private String levelDescription;
	private ImageType levelImageType;
	private double levelXCoordinate;
	private double levelYCoordinate;
	private GameLevel gameLevelType;
	
	public static class LevelInfoBuilder{
		
		private String levelTitle;
		private String levelLocation;
		private String levelDescription;
		private ImageType levelImageType;
		private double levelXCoordinate = -1;
		private double levelYCoordinate = -1;
		private GameLevel levelType;
		
		LevelInfoBuilder() {}
		
		public LevelInfoBuilder levelTitle(final String levelTitle) {
			this.levelTitle = levelTitle;
			return this;
		}
		
		public LevelInfoBuilder levelLocation(final String levelLocation) {
			this.levelLocation = levelLocation;
			return this;
		}
		
		public LevelInfoBuilder levelDescription(final String levelDescription) {
			this.levelDescription = levelDescription;
			return this;
		}
		
		public LevelInfoBuilder levelImageType(final ImageType type) {
			this.levelImageType = type;
			return this;
		}
		
		public LevelInfoBuilder levelXCoordinate(final double levelXCoordinate) {
			this.levelXCoordinate = levelXCoordinate;
			return this;
		}
		
		public LevelInfoBuilder levelYCoordinate(final double levelYCoordinate) {
			this.levelYCoordinate = levelYCoordinate;
			return this;
		}
		
		public LevelInfoBuilder gameLevelType(final GameLevel level) {
			this.levelType = level;
			return this;
		}
		
		public LevelInfo build() {
			return new LevelInfo(levelTitle, levelLocation, levelDescription, levelImageType, levelXCoordinate, levelYCoordinate, levelType);
		}
	}
	
	
	
	protected LevelInfo(String levelTitle, String levelLocation, String levelDescription, ImageType levelImageType, double levelXCoordinate,
		double levelYCoordinate, GameLevel gameLevelType) {
		this.levelTitle = levelTitle;
		this.levelLocation = levelLocation;
		this.levelDescription = levelDescription;
		this.levelImageType = levelImageType;
		this.levelXCoordinate = levelXCoordinate;
		this.levelYCoordinate = levelYCoordinate;
		this.gameLevelType = gameLevelType;
	}
	
	public String getLevelLocation() {
		return levelLocation;
	}

	public void setLevelLocation(String levelLocation) {
		this.levelLocation = levelLocation;
	}

	public static LevelInfoBuilder getBuilder() {
		return new LevelInfoBuilder();
	}

	public String getLevelTitle() {
		return levelTitle;
	}

	public void setLevelTitle(String levelTitle) {
		this.levelTitle = levelTitle;
	}

	public String getLevelDescription() {
		return levelDescription;
	}

	public void setLevelDescription(String levelDescription) {
		this.levelDescription = levelDescription;
	}

	public ImageType getLevelImageType() {
		return levelImageType;
	}

	public void setLevelImageType(ImageType levelImageType) {
		this.levelImageType = levelImageType;
	}

	public double getLevelXCoordinate() {
		return levelXCoordinate;
	}

	public void setLevelXCoordinate(double levelXCoordinate) {
		this.levelXCoordinate = levelXCoordinate;
	}

	public double getLevelYCoordinate() {
		return levelYCoordinate;
	}

	public void setLevelYCoordinate(double levelYCoordinate) {
		this.levelYCoordinate = levelYCoordinate;
	}

	public void setGameLevelType(GameLevel level) {
		this.gameLevelType = level;
	}
	
	public GameLevel getGameLevelType() {
		return gameLevelType;
	}
	
}
