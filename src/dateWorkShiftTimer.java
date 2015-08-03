
import java.util.Timer;
import java.util.TimerTask;

class dateWorkShiftTimer extends TimerTask
{

	dateWorkShiftCanvas canvas;

	dateWorkShiftTimer(dateWorkShiftCanvas c)
	{
		canvas = c;
	}

	public void run()
	{
		try
		{
			canvas.repaint();
		}
		catch (Exception ex)
		{
		}
	}
}
