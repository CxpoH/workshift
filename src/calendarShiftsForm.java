
import javax.microedition.lcdui.*;

public class calendarShiftsForm extends Form implements CommandListener
{

	private Command ok, cancel;
	private TextField yearInput;
	private globalCalendar cal;
	private calendarShiftsCanvas canvas;
	private dateWorkShift dateWorkShift;
	private Display dpy;
	private Displayable prev;
	public String[] monthes =
	{
		"������", "�������", "����", "������", "���", "����", "����", "������", "��������", "�������", "������", "�������"
	};
	private ChoiceGroup monthInput = new ChoiceGroup("�������� �����", ChoiceGroup.EXCLUSIVE, monthes, null);

	public calendarShiftsForm(dateWorkShift dateWorkShift, int mon, int year)
	{
		super("���� ������ � ����");
		this.dateWorkShift = dateWorkShift;
		dpy = Display.getDisplay(dateWorkShift);
		prev = dpy.getCurrent();
		cancel = new Command("�����", Command.CANCEL, 1);
		ok = new Command("��������", Command.OK, 3);
		addCommand(ok);
		addCommand(cancel);
		cal = new globalCalendar();
		append(monthInput);
		monthInput.setSelectedIndex(mon, true);
		yearInput = new TextField("������� ���", year + "", 4, TextField.NUMERIC);
		append(yearInput);
		setCommandListener(this);
	}

	public void commandAction(Command c, Displayable d)
	{
		if (c == ok)
		{
			int month = monthInput.getSelectedIndex();
			int year = Integer.parseInt(yearInput.getString());
			if (cal.correctDate(1, month, year) == true)
			{
				canvas = new calendarShiftsCanvas(dateWorkShift, month, year);
				dpy.setCurrent(canvas);
			}
			else
			{
				errorForm er = new errorForm(dpy, "������");
				er.show("��� " + year + " �����������!");
			}
		}
		else if (c == cancel)
		{
			dpy.setCurrent(prev);
		}
	}
}
