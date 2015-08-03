
import javax.microedition.lcdui.*;

public class calcShiftsForm extends Form implements CommandListener
{

	private dateWorkShift dateWorkShift;
	private Display dpy;
	private Displayable prev;
	private globalCalendar now;
	private errorForm er;
	private Command cmdMenu, cmdGo;
	private TextField day1, day2, month1, month2, year1, year2;
	private StringItem dateWorkShiftStr, otherStr;
	private int d1, m1, y1, d2, m2, y2;
	private boolean correct = true;

	public calcShiftsForm(dateWorkShift dateWorkShift, String hdr, int d1, int m1, int y1, int d2, int m2, int y2)
	{
		super(hdr);
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		this.d1 = d1;
		this.m1 = m1;
		this.y1 = y1;
		this.d2 = d2;
		this.m2 = m2;
		this.y2 = y2;
		cmdMenu = new Command("Назад", Command.BACK, 0);
		cmdGo = new Command("Ввод", Command.ITEM, 1);
		day1 = new TextField("День 1", d1 + "", 2, TextField.NUMERIC);
		month1 = new TextField("Месяц 1", m1 + 1 + "", 2, TextField.NUMERIC);
		year1 = new TextField("Год 1", y1 + "", 4, TextField.NUMERIC);
		day2 = new TextField("День 2", d2 + "", 2, TextField.NUMERIC);
		month2 = new TextField("Месяц 2", m2 + 1 + "", 2, TextField.NUMERIC);
		year2 = new TextField("Год 2", y2 + "", 4, TextField.NUMERIC);
		now = new globalCalendar();
		dateWorkShiftStr = new StringItem("Количество дней:", "" + now.kolDays(d1, m1, y1, d2, m2, y2));
		otherStr = new StringItem("Разница дат:", now.kolTimes(d1, m1, y1, d2, m2, y2));
		append(day1);
		append(month1);
		append(year1);
		append(day2);
		append(month2);
		append(year2);
		append(dateWorkShiftStr);
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
			d1 = Integer.parseInt(day1.getString().trim());
			m1 = Integer.parseInt(month1.getString().trim()) - 1;
			y1 = Integer.parseInt(year1.getString().trim());
			if (now.correctDate(d1, m1, y1) == false)
			{
				correct = false;
				er.show("Дата " + now.getDMYDate(d1, m1, y1) + " некорректна!");
			}
			else
			{
				d2 = Integer.parseInt(day2.getString().trim());
				m2 = Integer.parseInt(month2.getString().trim()) - 1;
				y2 = Integer.parseInt(year2.getString().trim());
				if (now.correctDate(d2, m2, y2) == false)
				{
					correct = false;
					er.show("Дата " + now.getDMYDate(d2, m2, y2) + " некорректна!");
				}
				else
				{
					correct = true;
					dateWorkShiftStr.setText("" + now.kolDays(d1, m1, y1, d2, m2, y2));
					otherStr.setText(now.kolTimes(d1, m1, y1, d2, m2, y2));
					dateWorkShift.days[0][0] = d1;
					dateWorkShift.days[0][1] = m1;
					dateWorkShift.days[0][2] = y1;
					dateWorkShift.days[1][0] = d2;
					dateWorkShift.days[1][1] = m2;
					dateWorkShift.days[1][2] = y2;
				}
			}
		}
	}
}