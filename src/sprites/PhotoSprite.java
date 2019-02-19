package sprites;

import java.util.ArrayList;
import java.util.List;

import controllers.ImageFactory;
import controllers.ImageFactory.ImageType;
import events.GameEvent;

public class PhotoSprite extends Sprite{
	
	private ImageType iType;
	
	public PhotoSprite(ImageType iType){
		this.iType = iType;
		this.setImage(ImageFactory.getImageFactory().getImage(iType));
		this.setFitHeight(iType.getHeight());
		this.setFitWidth(iType.getWidth());
	}

	@Override
	public List<GameEvent<?>> tick(long cur_millis) {
		return new ArrayList<>();
	}

}
