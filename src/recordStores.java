
import java.io.*;
import javax.microedition.lcdui.Display;
import javax.microedition.rms.*;

class recordStores
{

	private RecordStore rs = null;
	private Display d;
	private String name;
	private errorForm er;
	private int recordLength = 0;

	public recordStores(Display d, String name, int len)
	{
		this.d = d;
		this.name = name;
		recordLength = len;
		er = new errorForm(this.d, "Ошибка");
		try
		{
			rs = null;
			rs = RecordStore.openRecordStore(name, true);
		}
		catch (RecordStoreFullException e)
		{
			er.show("Нет места для сохранения данных");
			rs = null;
		}
		catch (RecordStoreNotOpenException e)
		{
			er.show("Не могу открыть записи");
			rs = null;
		}
		catch (RecordStoreException e)
		{
			er.show("Ошибка чтения записей");
			rs = null;
		}
	}

	public int getNumRecords()
	{
		if (rs != null)
		{
			try
			{
				return rs.getNumRecords();
			}
			catch (RecordStoreNotOpenException e)
			{
				return 0;
			}
			catch (RecordStoreException e)
			{
				return 0;
			}
		}
		else
		{
			return 0;
		}
	}

	public String getRecord(int n)
	{
		String s = null;
		//er = new errorForm (d,"Ошибка");
		if (rs != null)
		{
			try
			{
				byte[] arrData = rs.getRecord(n);
				ByteArrayInputStream bytes = new ByteArrayInputStream(arrData, 0, recordLength);
				DataInputStream dis = new DataInputStream(bytes);
				s = dis.readUTF();
				if (s.length() < recordLength)
				{
					dis.skip(recordLength - s.length());
				}
			}
			catch (IOException ioe)
			{
				//er.show ("Ошибка ввода/вывода при чтении данных");
				return null;
			}
			catch (RecordStoreException ex)
			{
				//er.show ("Ошибка RecordStore при чтении данных");
				return null;
			}
			return s;
		}
		else
		{
			return null;
		}
	}

	public boolean setRecord(int n, String s)
	{
		er = new errorForm(d, "Ошибка");
		if (rs != null)
		{
			ByteArrayOutputStream bytes = new ByteArrayOutputStream();
			DataOutputStream dos = new DataOutputStream(bytes);
			try
			{
				dos.writeUTF(s);
				if (s.length() < recordLength)
				{
					for (int j = s.length(); j < recordLength; j++)
					{
						dos.writeByte(0);
					}
				}
				rs.setRecord(n, bytes.toByteArray(), 0, bytes.toByteArray().length);
			}
			catch (IOException ioe)
			{
				er.show("Ошибка записи данных в поток");
				return false;
			}
			catch (InvalidRecordIDException ridex)
			{
				try
				{
					rs.addRecord(bytes.toByteArray(), 0, bytes.toByteArray().length);
				}
				catch (RecordStoreException ex)
				{
					er.show("Не могу добавить запись " + n);
					return false;
				}
			}
			catch (RecordStoreException ex)
			{
				er.show("Не могу сохранить запись" + n);
				return false;
			}
			return true;
		}
		else
		{
			return false;
		}
	}

	public boolean closeRecords()
	{
		er = new errorForm(d, "Ошибка");
		try
		{
			rs.closeRecordStore();
			rs = null;
		}
		catch (RecordStoreException ex)
		{
			er.show("Не могу закрыть записи");
			return false;
		}
		return true;
	}

	/*
	 * public boolean deleteRecord (int n) { er = new errorForm (d,"Ошибка");
	 * try { rs.deleteRecord (n); } catch (RecordStoreException ex) {
	 * er.show("Не могу удалить событие "+n); return false; } return true; }
	 *
	 * public boolean deleteRecords() { er = new errorForm (d,"Ошибка"); try {
	 * rs.deleteRecordStore (name); rs=null; } catch
	 * (RecordStoreNotFoundException ex) { er.show("Не могу найти записи для
	 * удаления"); return false; } catch (RecordStoreException ex) { er.show("Не
	 * могу удалить записи"); return false; } return true; }
	 */
}