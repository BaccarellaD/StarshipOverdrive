package debugging;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import javafx.scene.text.Text;

public class FPSCounter extends Text{
	
	private long prev_millis;
	
	public FPSCounter(long cur_millis){
		this.prev_millis = cur_millis;
		this.setText("0.00");
	}
	
	public void tick(long cur_millis){
		double dt = cur_millis - prev_millis;
		double framerate = 1d / (dt/1000d);
		DecimalFormat df = new DecimalFormat("#.##");
		df.setRoundingMode(RoundingMode.HALF_UP);
		String fpsString = df.format(framerate);
		setText(fpsString);
		System.out.println(fpsString);
		prev_millis = cur_millis;
	}
	
}
