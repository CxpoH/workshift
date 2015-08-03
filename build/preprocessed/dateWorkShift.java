
import javax.microedition.lcdui.*;
import javax.microedition.midlet.MIDlet;

public class dateWorkShift extends MIDlet implements CommandListener
{

	private Display display;
	private recordStores rsDays, rsEvents;
	private static final int EVENT_LENGTH = 16; //Длина описания события в символах
	private int EVENTS = 0; //текущее число событий
	private globalCalendar now;
	private dateWorkShiftCanvas dateWorkShiftCanvas;
	private calendarShiftsForm monthShiftsForm;
	private calcShiftsForm calcShiftsForm;
	private numberForm numberForm;
	private dateWorkShiftsForm dateWorkShiftsForm;
	private dateDaysForm dateDaysForm;
	private settingsForm settingsForm;
	private errorForm er;
	private static final int MODE_MENU = 0, //Режим меню
			MODE_EVENTS = 1; //Режим списка событий
	private int mode = MODE_MENU;
	private boolean isVoid = true; //Пуст ли список событий
	private boolean eventReplace = false; //Заменять ли событие в списке
	public int[][] days = {
		{1, 0, 2012}, //days[0]
		{1, 0, 2012}, //days[1]
		{1, 0, 2012}, //days[2]
		{1, 0, 2012}, //days[3] Для введеной даты в меню 2
		{1, 0, 2012}, //days[4] For EVENTS
		{1, 0, 2012}, //days[5]
		{12, 5, 2012}, //days[6] Для начала отсчета рабочих смен
	};
	private String eventStr;
//	private int eventDay, eventMonth, eventYear;
	private int selector = 0;    //Выбор в меню
//	private int eventNum = -1;   //Выбор события
	private Command cmdExit = new Command("Выход", Command.EXIT, 2);
	private Command cmdMenu = new Command("Назад", Command.ITEM, 1);
	private Command cmdBack = new Command("Назад", Command.ITEM, 1);
	private Command cmdAdd = new Command("Добавить", Command.ITEM, 3);
	private Command cmdShow = new Command("Смотреть", Command.ITEM, 3);
	private Command cmdChange = new Command("Изменить", Command.ITEM, 3);
	private Command cmdClear = new Command("Удалить", Command.ITEM, 3);
	private Command cmdGo = new Command("Ok", Command.ITEM, 1);
	private static String[] mainMenuStr = {
		"Лоторейка", // dateWorkShiftsForm
		"Календарь смен", // calendarShiftsForm -> calendarShiftsCanvas
		//	"Номер дня/недели",	// numberForm
		//	"Дата + дни",		// dateDaysForm
		//	"Текущее время",	// dateWorkShiftCanvas -> dateWorkShiftTimer
		"Настройки", //
		"О программе"
	};
	private List mainMenu = new List("Дата и время", Choice.IMPLICIT, mainMenuStr, null);
//	private List eventList = new List("События", Choice.IMPLICIT, mainMenuStr, null);
//	private Form showEventForm = new Form("Событие");
//	private StringItem showEventHdr, showEventDays, showEventTimes;
//	private Form cnangeEventForm = new Form("Ввод события");
//	private DateField eventDate;
//	private TextField eventText;

	public dateWorkShift()
	{
		mainMenu.addCommand(cmdExit);
		mainMenu.setCommandListener(this);
	}

	public void startApp()
	{
		display = Display.getDisplay(this);
		display.setCurrent(mainMenu);
		now = new globalCalendar();

		rsDays = new recordStores(display, "dateWorkShift_days", 8 * 2);
		int n = rsDays.getNumRecords();
		String s;
		for (int i = 0; i < 6; i++) {
			s = rsDays.getRecord(i + 1);
			if ((n < 6) || (s == null)) {
				days[i][0] = now.getDay();
				days[i][1] = now.getMonth();
				days[i][2] = now.getYear();
			}
			else {
				days[i][0] = Integer.parseInt(s.substring(0, 2));
				days[i][1] = Integer.parseInt(s.substring(2, 4));
				days[i][2] = Integer.parseInt(s.substring(4));
			}
		}
/*
		if (n < 6) {
			EVENTS = 0;
		}
		else {
			EVENTS = days[4][0];
		}

		rsEvents = new recordStores(display, "dateWorkShift_events", (8 + EVENT_LENGTH) * 2);
		if (EVENTS < 1) {
			createEventList();
		}
		else {
			clearEventList();
			isVoid = false;
			int d0, m0, y0;
			for (int i = 0; i < EVENTS; i++) {
				s = rsEvents.getRecord(i + 1);
				if (s != null) {
					if (s.length() > 8) {
						eventStr = s.substring(8) + " (";
					}
					else {
						eventStr = " (";
					}
					d0 = Integer.parseInt(s.substring(0, 2));
					m0 = Integer.parseInt(s.substring(2, 4));
					y0 = Integer.parseInt(s.substring(4, 8));
					eventStr = eventStr + now.getDateStr(d0, m0, y0) + ")";
					eventList.append(eventStr, null);
				}
				else {
					eventList.append("Пусто (01.01.2001, Пн)", null);
				}
			}
		}
*/
		initListHeader();
/*
		eventList.addCommand(cmdMenu);
		eventList.addCommand(cmdShow);
		eventList.addCommand(cmdAdd);
		eventList.addCommand(cmdChange);
		eventList.addCommand(cmdClear);
		eventList.setCommandListener(this);

		showEventHdr = new StringItem("Событие:", "");
		showEventDays = new StringItem("Число дней:", "");
		showEventTimes = new StringItem("Разница дат:", "");
		showEventForm.append(showEventHdr);
		showEventForm.append(showEventDays);
		showEventForm.append(showEventTimes);
		showEventForm.addCommand(cmdBack);
		showEventForm.setCommandListener(this);

		eventDate = new DateField("Дата события", DateField.DATE);
		eventText = new TextField("Описание события", null, EVENT_LENGTH, TextField.ANY);
		cnangeEventForm.append(eventDate);
		cnangeEventForm.append(eventText);
		cnangeEventForm.addCommand(cmdBack);
		cnangeEventForm.addCommand(cmdGo);
		cnangeEventForm.setCommandListener(this);
*/
	}

	public void pauseApp()
	{
	}

	public void destroyApp(boolean unconditional)
	{
		saveRecords();
	}

	public void initListHeader()
	{
		mainMenu.setTitle(now.globalDate());
	}

	public void saveRecords()
	{
		String s;
		int i;
//		days[4][0] = EVENTS;
		for (i = 0; i < 6; i++) {
			s = "" + now.oNum(days[i][0]) + now.oNum(days[i][1]) + now.oNum4(days[i][2]);
			rsDays.setRecord(i + 1, s);
		}
		rsDays.closeRecords();
/*		if (rsEvents != null) {
			for (i = 0; i < EVENTS; i++) {
				getEventItems(i);
				rsEvents.setRecord(i + 1, "" + now.oNum(eventDay) + now.oNum(eventMonth) + now.oNum4(eventYear) + eventStr);
			}
		}
		rsEvents.closeRecords();
*/		notifyDestroyed();
	}

	public void addEvent()
	{
/*		eventDate.setDate(now.initDateField(now.getDay(), now.getMonth(), now.getYear()));
		eventText.setString("");
		eventReplace = false;
		display.setCurrent(cnangeEventForm);
*/	}

	public void clearEventList()
	{
/*		int i;
		do {
			i = eventList.size();
			if (i > 0) {
				eventList.delete(0);
			}
		}
		while (i > 0);
*/	}

	public void createEventList()
	{
/*		isVoid = true;
		EVENTS = 0;
		clearEventList();
		eventList.append("Список пуст", null);
*/	}

	public void getEventItems(int i)
	{
/*		String s = eventList.getString(i).trim();
		int sp = s.lastIndexOf('(') + 1;
		String dt = s.substring(sp);
		eventDay = Integer.parseInt(dt.substring(0, 2));
		eventMonth = Integer.parseInt(dt.substring(3, 5)) - 1;
		eventYear = Integer.parseInt(dt.substring(6, 10));
		eventStr = s.substring(0, sp - 1).trim();
*/	}

	public void showEvent()
	{
/*		eventNum = eventList.getSelectedIndex();
		getEventItems(eventNum);
		showEventHdr.setText(eventStr + " (" + now.getDateStr(eventDay, eventMonth, eventYear) + ")");
		int k = now.kolDays(now.getDay(), now.getMonth(), now.getYear(), eventDay, eventMonth, eventYear);
		showEventDays.setText(k + "");
		showEventTimes.setText(now.kolTimes(now.getDay(), now.getMonth(), now.getYear(), eventDay, eventMonth, eventYear));
		display.setCurrent(showEventForm);
*/	}

	public void commandAction(Command c, Displayable d)
	{
		initListHeader();
		if (c == cmdExit) {
			destroyApp(true);
		}
		else if (c == cmdMenu) {
			mode = MODE_MENU;
			display.setCurrent(mainMenu);
		}
		else if (c == List.SELECT_COMMAND) {
			if (mode == MODE_MENU) {
				selector = mainMenu.getSelectedIndex();
				switch (selector) {
					case 0:
						dateWorkShiftsForm = new dateWorkShiftsForm(this, mainMenuStr[0], days[3][0], days[3][1], days[3][2], days[6][0], days[6][1], days[6][2]);
						display.setCurrent(dateWorkShiftsForm);
						break;
					case 1:
						monthShiftsForm = new calendarShiftsForm(this, days[4][1], days[4][2]);
						display.setCurrent(monthShiftsForm);
						break;
					case 2:
						settingsForm = new settingsForm(this, mainMenuStr[2], days[6][0], days[6][1], days[6][2]);
						display.setCurrent(settingsForm);
						break;
					case 3:
						er = new errorForm(display, mainMenuStr[3]);
						er.show(
								"Лоторейка:\n Показывает какая будет смена в указанный день. А ты, угадаешь?\n\n"
								+ "Календарь смен:\n Показывает волшебный цветной календарик.\n\n"
								+ "Настройки:\n Ввод даты отсчета. Введенная дата запоминается.\n\n"
								+ "(С) Yoshka, pre@front.ru, 2012");
						break;
				}
			}
			else {
				display.setCurrent(mainMenu);
			}
		}
	}
}

/*
 * case 0:
			calcShiftsForm = new calcShiftsForm(this, mainMenuStr[0], days[0][0], days[0][1], days[0][2], days[1][0], days[1][1], days[1][2]);
			display.setCurrent(calcShiftsForm);
			break;
		    case 1:
			numberForm = new numberForm(this, mainMenuStr[1], days[2][0], days[2][1], days[2][2]);
			display.setCurrent(numberForm);
			break;
		    case 2:
			dateDaysForm = new dateDaysForm(this, mainMenuStr[2], days[5][0], days[5][1], days[5][2]);
			display.setCurrent(dateDaysForm);
			break;
		    case 3:
			dateWorkShiftsForm = new dateWorkShiftsForm(this, mainMenuStr[3], days[3][0], days[3][1], days[3][2]);
			display.setCurrent(dateWorkShiftsForm);
			break;
		    case 4:
			dateWorkShiftCanvas = new dateWorkShiftCanvas(this);
			display.setCurrent(dateWorkShiftCanvas);
			break;
		    case 5:
			monthForm = new calendarShiftsForm(this, days[4][1], days[4][2]);
			display.setCurrent(monthForm);
			break;
		    case 6:
			settingsForm = new settingsForm(this, mainMenuStr[6], days[6][0], days[6][1], days[6][2]);
			display.setCurrent(settingsForm);
			break;
		    case 7:
			er = new errorForm(display, mainMenuStr[6]);
			er.show(
				"Калькулятор дат - считает количество дней между 2 любыми датами.\n\n"
				+ "Номер дня/недели - показывает номер дня и недели в году для введенной даты.\n\n"
				+ "Дата + дни - складывает указанную дату и количество дней\n\n"
				+ "Мои дни - считает Ваш возраст в днях и число дней до ДР.\n\n"
				+ "Текущее время - идущие цифровые часы.\n\n"
				+ "Вечный календарь - календарь на выбранные месяц и год, выбор также не ограничен календарем телефона. Календарь можно листать джойстиком.\n\n"
				+ "Все введенные Вами даты запоминаются программой.\n\n"
				+ "(С) Yoshka, pre@front.ru, 2012");
			break;
 */