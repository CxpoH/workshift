
import javax.microedition.lcdui.*;

public class dateWorkShiftsForm extends Form implements CommandListener
{

	private dateWorkShift dateWorkShift;
	private Display dpy;
	private Displayable prev;
	private globalCalendar now;
	private Command cmdMenu, cmdGo;
	private DateField dateUserInput;
	private StringItem strShiftFull, strNumFullDaysUntil, strNumFullDaysSinse;
	private String strShiftTime, strShiftNum;
	//private int dUser, mUser, yUser, dayearNow, monthNow, yearNow, i, yearStart;
	private int numFullDay, //Количество дней от точки нуля
			numShifts, //Количество пройденых блоков
			numDay, //Количество оставшихся дней в блоке
			numFullDaysUntil, //количество дней до указанной даты
			numFullDaysSinse, //количество дней от начала
			numBlocks, //количество блоков общее
			dayUserSet, monthUserSet, yearUserSet, //дата которую вводит пользователь
			dayearNow, monthNow, yearNow, //дата текущая
			dBegin, mBegin, yBegin;	//дата отсчета смен (точка нуля)

	public dateWorkShiftsForm(dateWorkShift dateWorkShift, String hdr,
								int dUser, int mUser, int yUser,
								int dayStart, int monthStart, int yearStart)
	{
		super(hdr);
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		
		//пользовательская дата
		this.dayUserSet = dUser;
		this.monthUserSet = mUser;
		this.yearUserSet = yUser;
		
		//дата отсчета
		this.dBegin = dayStart;
		this.mBegin = monthStart;
		this.yBegin = yearStart;

		//текущая дата
		now = new globalCalendar();
		dayearNow = now.getDay();
		monthNow = now.getMonth();
		yearNow = now.getYear();

		cmdMenu = new Command("Назад", Command.BACK, 0);
		cmdGo = new Command("Ввод", Command.ITEM, 1);

		//поле ввода даты
		dateUserInput = new DateField("Ввод даты", DateField.DATE);
		dateUserInput.setDate(now.initDateField(dayearNow, monthNow, yearNow));

		//строка для вывода что это будет (смена/выходной)
		strShiftFull = new StringItem("Это будет:", "");
		strShiftNum = "";
		strShiftTime = "";

		//количество дней от указанной даты до начала отсчета
		numFullDay = now.kolDays(dayUserSet, monthUserSet, yearUserSet, dBegin, mBegin, yBegin);
		if (numFullDay < 0)
		{
			numFullDay *= -1;
		}

		//количество дней от текущей даты до указанной
		numFullDaysUntil = now.kolDays(dayearNow, monthNow, yearNow, dayUserSet, monthUserSet, yearUserSet);
		if (numFullDaysUntil < 0)
		{
			numFullDaysUntil *= -1;
		}
		strNumFullDaysUntil = new StringItem("Дней до даты:", "");

		//строка для дебага (отключена)
		numFullDaysSinse = now.kolDays(dayearNow, monthNow, yearNow, dBegin, mBegin, yBegin);
		if (numFullDaysSinse < 0)
		{
			numFullDaysSinse *= -1;
		}
		strNumFullDaysSinse = new StringItem("Дней с:", "" + (numFullDaysSinse + 1));

		append(dateUserInput);
		append(strShiftFull);
		append(strNumFullDaysUntil);
		//append(strNumFullDaysSinse);
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
		else if (c == cmdGo)
		{
			dayUserSet = now.getDateFields(0, dateUserInput);
			monthUserSet = now.getDateFields(1, dateUserInput);
			yearUserSet = now.getDateFields(2, dateUserInput);

			dateWorkShift.days[3][0] = dayUserSet;
			dateWorkShift.days[3][1] = monthUserSet;
			dateWorkShift.days[3][2] = yearUserSet;
			numFullDay = now.kolDays(dayUserSet, monthUserSet, yearUserSet, dBegin, mBegin, yBegin);
			if (numFullDay < 0)
			{
				numFullDay *= -1;
			}
			numBlocks = numFullDay * 3 + 3;
			numShifts = (numBlocks) / 16;
			numDay = numBlocks - numShifts * 16;
			if ((numShifts % 3 == 0) && (numDay == 0))
			{
				strShiftNum = "ВЫ-ХОД-НОЙ!";
				strShiftTime = "";
			}
			else if (numShifts % 3 == 0)
			{

				strShiftTime = "Утренняя,";
				if (numDay == 3)
				{
					strShiftNum = "Первая";
				}
				else if (numDay == 6)
				{
					strShiftNum = "Вторая";
				}
				else if (numDay == 9)
				{
					strShiftNum = "Третья";
				}
				else if (numDay == 12)
				{
					strShiftNum = "Крайняя";
				}
				else if (numDay == 15)
				{
					strShiftNum = "Выходной, однако";
					strShiftTime = "";
				}

			}
			else if (numShifts % 3 == 1)
			{
				strShiftTime = "Трешная,";
				if (numDay == 2)
				{
					strShiftNum = "Первая";
				}
				else if (numDay == 5)
				{
					strShiftNum = "Вторая";
				}
				else if (numDay == 8)
				{
					strShiftNum = "Третья";
				}
				else if (numDay == 11)
				{
					strShiftNum = "Крайняя";
				}
				else if (numDay == 14)
				{
					strShiftNum = "Е-хоу";
					strShiftTime = "";
				}
			}
			else if (numShifts % 3 == 2)
			{
				strShiftTime = "Ночная,";
				if (numDay == 1)
				{
					strShiftNum = "Первая";
				}
				else if (numDay == 4)
				{
					strShiftNum = "Вторая";
				}
				else if (numDay == 7)
				{
					strShiftNum = "Третья";
				}
				else if (numDay == 10)
				{
					strShiftNum = "Крайняя";
				}
				else if (numDay == 13)
				{
					strShiftNum = "Спать после ночной!";
					strShiftTime = "";
				}
			}

			numFullDaysUntil = now.kolDays(dayearNow, monthNow, yearNow, dayUserSet, monthUserSet, yearUserSet);
			if (numFullDaysUntil < 0)
				numFullDaysUntil *= -1;

			numFullDaysSinse = now.kolDays(dayUserSet, monthUserSet, yearUserSet, dBegin, mBegin, yBegin);
			if (numFullDaysSinse < 0)
				numFullDaysSinse *= -1;

			//вывод всей информации
			strShiftFull.setText("" + strShiftTime + " " + strShiftNum);
			strNumFullDaysUntil.setText("" + numFullDaysUntil);
			strNumFullDaysSinse.setText("" + (numFullDaysSinse + 1));
		}
	}
}