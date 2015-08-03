
import javax.microedition.lcdui.*;

class calendarShiftsCanvas extends Canvas
{

	private dateWorkShift dateWorkShift;
	private Display display;
	private Displayable displayable;
	private Font font;
	private globalCalendar now;
	private static int groundColor = 0xffffff;
	private static int linesColor = 0xcccccc;
	private static int drawColor = 0x000000;
	private static int drawWeekendColor = 0xff0000;
	private static int hsp = 1, vsp = 1;
	private int day, mon, year, weekDay;
	private int h, w, x, y;
	private String s;

	public calendarShiftsCanvas(dateWorkShift dateWorkShift, int month, int year)
	{
		this.dateWorkShift = dateWorkShift;
		display = Display.getDisplay(dateWorkShift);
		displayable = display.getCurrent();
		font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_PLAIN, Font.SIZE_MEDIUM);
		w = 3 * font.charWidth('0') + 2 * hsp;
		h = font.getHeight() + 2 * vsp;
		x = (getWidth() - 7 * w) / 2;
		y = (getHeight() - 7 * h) / 2;
		now = new globalCalendar();
		this.mon = month;
		this.year = year;
	}

	protected void keyPressed(int keyCode)
	{
		switch (getGameAction(keyCode))
		{
			case Canvas.LEFT:
				if (mon > 0)
				{
					mon--;
				}
				else
				{
					mon = 11;
					if (year > 0)
					{
						year--;
					}
				}
				repaint();
				break;
			case Canvas.RIGHT:
				if (mon < 11)
				{
					mon++;
				}
				else
				{
					mon = 0;
					if (year < 9999)
					{
						year++;
					}
				}
				repaint();
				break;
			case Canvas.DOWN:
				if (year > 0)
				{
					year--;
				}
				repaint();
				break;
			case Canvas.UP:
				if (year < 9999)
				{
					year++;
				}
				repaint();
				break;
			default:
				dateWorkShift.days[4][1] = mon;
				dateWorkShift.days[4][2] = year;
				display.setCurrent(displayable);
				break;
		}
	}

	protected void paint(Graphics g)
	{
		int i, j;
		g.setColor(groundColor);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(linesColor);
		for (i = 0; i < 8; i++)
		{
			g.drawLine(x + i * w, y, x + i * w, y + 7 * h);
		}
		for (i = 0; i < 8; i++)
		{
			g.drawLine(x, y + i * h, x + 7 * w, y + i * h);
		}
		g.setColor(drawColor);
		for (i = 1; i < 7; i++)
		{
			g.drawString(now.shortDay(i), x + hsp + (i - 1) * w, y + vsp, Graphics.TOP | Graphics.LEFT);
		}
		g.drawString(now.shortDay(0), x + hsp + 6 * w, y + vsp, Graphics.TOP | Graphics.LEFT);
		int up_day = now.getMonthDays(mon, year);
		int wkday = now.getWeekDayInAnyYear(1, mon, year);
		if (wkday == 0)
		{
			wkday = 7;
		}
		int ypos = y + h + vsp, xpos = x + hsp;
		for (i = 0; i < wkday - 1; i++)
		{
			xpos += w;
		}
		int day = 1;
		while (day <= up_day)
		{
			if (wkday > 5)
			{
				g.setColor(drawWeekendColor);
			}
			else
			{
				g.setColor(drawColor);
			}
			g.drawString("" + day, xpos, ypos, Graphics.TOP | Graphics.LEFT);
			day++;
			wkday++;
			xpos += w;
			if (wkday > 7)
			{
				wkday = 1;
				xpos = x + hsp;
				ypos += h;
			}
		}
		g.setColor(drawColor);
		g.drawString(now.oNum(mon + 1) + "/" + now.oNum4(year), getWidth(), getHeight(), Graphics.RIGHT | Graphics.BOTTOM);
	}
}
