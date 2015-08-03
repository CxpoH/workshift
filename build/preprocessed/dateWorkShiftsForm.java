
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
	private int numFullDay, //���������� ���� �� ����� ����
			numShifts, //���������� ��������� ������
			numDay, //���������� ���������� ���� � �����
			numFullDaysUntil, //���������� ���� �� ��������� ����
			numFullDaysSinse, //���������� ���� �� ������
			numBlocks, //���������� ������ �����
			dayUserSet, monthUserSet, yearUserSet, //���� ������� ������ ������������
			dayearNow, monthNow, yearNow, //���� �������
			dBegin, mBegin, yBegin;	//���� ������� ���� (����� ����)

	public dateWorkShiftsForm(dateWorkShift dateWorkShift, String hdr,
								int dUser, int mUser, int yUser,
								int dayStart, int monthStart, int yearStart)
	{
		super(hdr);
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		
		//���������������� ����
		this.dayUserSet = dUser;
		this.monthUserSet = mUser;
		this.yearUserSet = yUser;
		
		//���� �������
		this.dBegin = dayStart;
		this.mBegin = monthStart;
		this.yBegin = yearStart;

		//������� ����
		now = new globalCalendar();
		dayearNow = now.getDay();
		monthNow = now.getMonth();
		yearNow = now.getYear();

		cmdMenu = new Command("�����", Command.BACK, 0);
		cmdGo = new Command("����", Command.ITEM, 1);

		//���� ����� ����
		dateUserInput = new DateField("���� ����", DateField.DATE);
		dateUserInput.setDate(now.initDateField(dayearNow, monthNow, yearNow));

		//������ ��� ������ ��� ��� ����� (�����/��������)
		strShiftFull = new StringItem("��� �����:", "");
		strShiftNum = "";
		strShiftTime = "";

		//���������� ���� �� ��������� ���� �� ������ �������
		numFullDay = now.kolDays(dayUserSet, monthUserSet, yearUserSet, dBegin, mBegin, yBegin);
		if (numFullDay < 0)
		{
			numFullDay *= -1;
		}

		//���������� ���� �� ������� ���� �� ���������
		numFullDaysUntil = now.kolDays(dayearNow, monthNow, yearNow, dayUserSet, monthUserSet, yearUserSet);
		if (numFullDaysUntil < 0)
		{
			numFullDaysUntil *= -1;
		}
		strNumFullDaysUntil = new StringItem("���� �� ����:", "");

		//������ ��� ������ (���������)
		numFullDaysSinse = now.kolDays(dayearNow, monthNow, yearNow, dBegin, mBegin, yBegin);
		if (numFullDaysSinse < 0)
		{
			numFullDaysSinse *= -1;
		}
		strNumFullDaysSinse = new StringItem("���� �:", "" + (numFullDaysSinse + 1));

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
				strShiftNum = "��-���-���!";
				strShiftTime = "";
			}
			else if (numShifts % 3 == 0)
			{

				strShiftTime = "��������,";
				if (numDay == 3)
				{
					strShiftNum = "������";
				}
				else if (numDay == 6)
				{
					strShiftNum = "������";
				}
				else if (numDay == 9)
				{
					strShiftNum = "������";
				}
				else if (numDay == 12)
				{
					strShiftNum = "�������";
				}
				else if (numDay == 15)
				{
					strShiftNum = "��������, ������";
					strShiftTime = "";
				}

			}
			else if (numShifts % 3 == 1)
			{
				strShiftTime = "�������,";
				if (numDay == 2)
				{
					strShiftNum = "������";
				}
				else if (numDay == 5)
				{
					strShiftNum = "������";
				}
				else if (numDay == 8)
				{
					strShiftNum = "������";
				}
				else if (numDay == 11)
				{
					strShiftNum = "�������";
				}
				else if (numDay == 14)
				{
					strShiftNum = "�-���";
					strShiftTime = "";
				}
			}
			else if (numShifts % 3 == 2)
			{
				strShiftTime = "������,";
				if (numDay == 1)
				{
					strShiftNum = "������";
				}
				else if (numDay == 4)
				{
					strShiftNum = "������";
				}
				else if (numDay == 7)
				{
					strShiftNum = "������";
				}
				else if (numDay == 10)
				{
					strShiftNum = "�������";
				}
				else if (numDay == 13)
				{
					strShiftNum = "����� ����� ������!";
					strShiftTime = "";
				}
			}

			numFullDaysUntil = now.kolDays(dayearNow, monthNow, yearNow, dayUserSet, monthUserSet, yearUserSet);
			if (numFullDaysUntil < 0)
				numFullDaysUntil *= -1;

			numFullDaysSinse = now.kolDays(dayUserSet, monthUserSet, yearUserSet, dBegin, mBegin, yBegin);
			if (numFullDaysSinse < 0)
				numFullDaysSinse *= -1;

			//����� ���� ����������
			strShiftFull.setText("" + strShiftTime + " " + strShiftNum);
			strNumFullDaysUntil.setText("" + numFullDaysUntil);
			strNumFullDaysSinse.setText("" + (numFullDaysSinse + 1));
		}
	}
}