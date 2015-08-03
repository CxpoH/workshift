
import javax.microedition.lcdui.*;
import java.util.Timer;
//import java.util.TimerTask;

class dateWorkShiftCanvas extends Canvas
{

	private dateWorkShift dateWorkShift;
	private Display display;
	private Displayable displayable;
	private Timer timer;
//	private TimerTask timertask;
	private Font font;
	private globalCalendar now;
	private static int groundColor = 0xffffff;
	private static int drawColor = 0x000000;
	public static int hour, min, sec, day, mon, year, weekDay;
	private int x, y, dx, dy;
	private String s;
	private boolean first = true;

	public dateWorkShiftCanvas(dateWorkShift dateWorkShift)
	{
		this.dateWorkShift = dateWorkShift;
		display = Display.getDisplay(dateWorkShift);
		displayable = display.getCurrent();
//		animateTo();
		x = getWidth() / 2;
		y = getHeight() / 2;
		font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
		dx = font.charWidth('M') * 14 + 2;
		dy = font.getHeight();
		now = new globalCalendar();
	}

	public void destroy()
	{
//		cancelTo();
		if (timer != null)
		{
			timer.cancel();
			timer = null;
		}
	}

	protected void keyPressed(int keyCode)
	{
		display.setCurrent(displayable);
	}
	/*
	 * private void animateTo() { if (timer == null) { timer = new Timer(); } if
	 * (timertask != null) { timertask.cancel(); timertask = null; } timertask =
	 * new dateWorkShiftTimer(this); timer.schedule(timertask, (long) 100,
	 * (long) 1000); }
	 */
	/*
	 * private void cancelTo() { if (timertask != null) { timertask.cancel(); }
	 * }
	 */

	protected void paint(Graphics g)
	{
		if (first == true)
		{
			first = false;
			g.setColor(groundColor);
			g.fillRect(0, 0, getWidth(), getHeight());
		}
		g.setColor(groundColor);
		g.fillRect(x - dx, y - 2 * dy, x + dx, y + 2 * dy);
		g.setColor(drawColor);
		s = now.globalDate();
		g.drawString(s, x, y - dy - dy / 2, Graphics.TOP | Graphics.HCENTER);
		s = now.globalTime();
		g.drawString(s, x, y + dy / 2, Graphics.TOP | Graphics.HCENTER);
	}
}
