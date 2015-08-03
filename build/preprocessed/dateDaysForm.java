
import javax.microedition.lcdui.*;

public class dateDaysForm extends Form implements CommandListener
{

	private dateWorkShift dateWorkShift;
	private Display dpy;
	private Displayable prev;
	private globalCalendar now;
	private errorForm er;
	private Command cmdMenu, cmdGo;
	private TextField day1, month1, year1, days;
	private StringItem otherStr;
	private int d1, m1, y1;
	private static int dt = 0;

	public dateDaysForm(dateWorkShift dateWorkShift, String hdr, int d1, int m1, int y1)
	{
		super(hdr);
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		this.d1 = d1;
		this.m1 = m1;
		this.y1 = y1;
		cmdMenu = new Command("Назад", Command.BACK, 0);
		cmdGo = new Command("Ввод", Command.ITEM, 1);
		day1 = new TextField("День", d1 + "", 2, TextField.NUMERIC);
		month1 = new TextField("Месяц", m1 + 1 + "", 2, TextField.NUMERIC);
		year1 = new TextField("Год", y1 + "", 4, TextField.NUMERIC);
		days = new TextField("Количество дней:", dt + "", 6, TextField.NUMERIC);

		now = new globalCalendar();
		otherStr = new StringItem("Полученная дата:", now.datePlusDays(d1, m1, y1, dt));
		append(day1);
		append(month1);
		append(year1);
		append(days);
		append(otherStr);
		addCommand(cmdMenu);
		addCommand(cmdGo);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d)
	{
		er = new errorForm(dpy, "Ошибка");
		if (c == cmdMenu)
		{
			dpy.setCurrent(prev);
		}
		else if (c == cmdGo)
		{
			d1 = Integer.parseInt(day1.getString());
			m1 = Integer.parseInt(month1.getString()) - 1;
			y1 = Integer.parseInt(year1.getString());
			dt = Integer.parseInt(days.getString());
			if (now.correctDate(d1, m1, y1) == false)
			{
				er.show("Дата " + now.getDMYDate(d1, m1, y1) + " некорректна!");
			}
			else
			{
				otherStr.setText(now.datePlusDays(d1, m1, y1, dt));
				dateWorkShift.days[5][0] = d1;
				dateWorkShift.days[5][1] = m1;
				dateWorkShift.days[5][2] = y1;
			}
		}
	}
}