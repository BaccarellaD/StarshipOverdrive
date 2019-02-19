package backgrounds;

import java.util.List;

import events.GameEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public abstract class GameBackground extends Pane{
	
	public abstract List<GameEvent<?>> tick(long cur_millis);
	
}
