
import javax.microedition.lcdui.*;

public class errorForm extends Form implements CommandListener
{

	private Command ok;
	private Display dpy;
	private Displayable prev;
	private StringItem msg;

	public errorForm(Display dpy, String hdr)
	{
		super(hdr);
		this.dpy = dpy;
		prev = dpy.getCurrent();
		ok = new Command("OK", Command.OK, 1);
		addCommand(ok);
		msg = new StringItem("", "");
		append(msg);
		setCommandListener(this);
	}

	public void show(String s)
	{
		msg.setText(s);
		dpy.setCurrent(this);
	}

	public void commandAction(Command c, Displayable d)
	{
		if (c == ok)
		{
			dpy.setCurrent(prev);
		}
	}
}