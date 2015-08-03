
/**
 *
 * @author Yoshka
 */
import javax.microedition.lcdui.*;

public class settingsForm extends Form implements CommandListener
{

	private dateWorkShift dateWorkShift;
	private Display dpy;
	private Displayable prev;
	private globalCalendar now;
	private Command cmdMenu, cmdGo;
	private DateField viewDate;
	private StringItem dateStr, nextStr, firstStr;
	private int dayStart, monthStart, yearStart, dayUserSet, monthUserSet, yearUserSet, i, y0;
	private static String drString = "";

	public settingsForm(dateWorkShift dateWorkShift, String hdr, int dayStart, int monthStart, int yearStart)
	{
		super(hdr);
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		this.dayStart = dayStart;
		this.monthStart = monthStart;
		this.yearStart = yearStart;
		now = new globalCalendar();
		dayUserSet = now.getDay();
		monthUserSet = now.getMonth();
		yearUserSet = now.getYear();

		cmdMenu = new Command("Назад", Command.BACK, 0);
		cmdGo = new Command("Ok", Command.ITEM, 1);
		firstStr = new StringItem("Начало отсчета рабочих смен.", "\nНадо ввести дату первой утренней смены");

		viewDate = new DateField("Ввод даты:", DateField.DATE);
		viewDate.setDate(now.initDateField(dayStart, monthStart, yearStart));

		append(viewDate);
		append(firstStr);
		addCommand(cmdGo);
		addCommand(cmdMenu);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d)
	{
		if (c == cmdMenu)
		{
			dpy.setCurrent(prev);
		}
		else if (c == cmdGo)
		{
			dayStart = now.getDateFields(0, viewDate);
			monthStart = now.getDateFields(1, viewDate);
			yearStart = now.getDateFields(2, viewDate);

			dateWorkShift.days[6][0] = dayStart;
			dateWorkShift.days[6][1] = monthStart;
			dateWorkShift.days[6][2] = yearStart;

			dpy.setCurrent(prev);
		}
	}
}
