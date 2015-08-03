
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
		er = new errorForm(this.d, "������");
		try
		{
			rs = null;
			rs = RecordStore.openRecordStore(name, true);
		}
		catch (RecordStoreFullException e)
		{
			er.show("��� ����� ��� ���������� ������");
			rs = null;
		}
		catch (RecordStoreNotOpenException e)
		{
			er.show("�� ���� ������� ������");
			rs = null;
		}
		catch (RecordStoreException e)
		{
			er.show("������ ������ �������");
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
		//er = new errorForm (d,"������");
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
				//er.show ("������ �����/������ ��� ������ ������");
				return null;
			}
			catch (RecordStoreException ex)
			{
				//er.show ("������ RecordStore ��� ������ ������");
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
		er = new errorForm(d, "������");
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
				er.show("������ ������ ������ � �����");
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
					er.show("�� ���� �������� ������ " + n);
					return false;
				}
			}
			catch (RecordStoreException ex)
			{
				er.show("�� ���� ��������� ������" + n);
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
		er = new errorForm(d, "������");
		try
		{
			rs.closeRecordStore();
			rs = null;
		}
		catch (RecordStoreException ex)
		{
			er.show("�� ���� ������� ������");
			return false;
		}
		return true;
	}

	/*
	 * public boolean deleteRecord (int n) { er = new errorForm (d,"������");
	 * try { rs.deleteRecord (n); } catch (RecordStoreException ex) {
	 * er.show("�� ���� ������� ������� "+n); return false; } return true; }
	 *
	 * public boolean deleteRecords() { er = new errorForm (d,"������"); try {
	 * rs.deleteRecordStore (name); rs=null; } catch
	 * (RecordStoreNotFoundException ex) { er.show("�� ���� ����� ������ ���
	 * ��������"); return false; } catch (RecordStoreException ex) { er.show("��
	 * ���� ������� ������"); return false; } return true; }
	 */
}