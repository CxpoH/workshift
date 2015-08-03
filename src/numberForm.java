
import javax.microedition.lcdui.*;

public class numberForm extends Form implements CommandListener
{

	private dateWorkShift dateWorkShift;
	private Display dpy;
	private Displayable prev;
	private globalCalendar now;
	private Command cmdMenu, cmdGo;
	private DateField date2;
	private StringItem dateStr, numStr;
	private int d2, m2, y2;

	public numberForm(dateWorkShift dateWorkShift, String hdr, int d2, int m2, int y2)
	{
		super(hdr);
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		this.d2 = d2;
		this.m2 = m2;
		this.y2 = y2;
		cmdMenu = new Command("Назад", Command.BACK, 0);
		cmdGo = new Command("Ввод", Command.ITEM, 1);
		date2 = new DateField("Дата", DateField.DATE);
		now = new globalCalendar();
		date2.setDate(now.initDateField(d2, m2, y2));
		dateStr = new StringItem("Номер дня в году:", "" + (now.kolDays0(1, 0, d2, m2, y2) + 1));
		numStr = new StringItem("Номер недели в году:", "" + now.getNumberOfWeek(d2, m2, y2));
		append(date2);
		append(dateStr);
		append(numStr);
		addCommand(cmdMenu);
		addCommand(cmdGo);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d)
	{
		if (c == cmdMenu)
		{
			dpy.setCurrent(prev);
		}
		else
		{
			if (c == cmdGo)
			{
				d2 = now.getDateFields(0, date2);
				m2 = now.getDateFields(1, date2);
				y2 = now.getDateFields(2, date2);
				dateStr.setText("" + (now.kolDays0(1, 0, d2, m2, y2) + 1));
				numStr.setText("" + now.getNumberOfWeek(d2, m2, y2));
				dateWorkShift.days[2][0] = d2;
				dateWorkShift.days[2][1] = m2;
				dateWorkShift.days[2][2] = y2;
			}
		}
	}
}