
import javax.microedition.lcdui.DateField;
import java.util.Calendar;
import java.util.Date;

class globalCalendar
{

	private Calendar now;
	public String[] fullDays =
	{
		"Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"
	};
	public String[] shortDays =
	{
		"Вс", "Пн", "Вт", "Ср", "Чт", "Пт", "Сб"
	};
	public String[] monthStr =
	{
		"Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
	};
	public final int[] monthDays =
	{
		31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31
	};
	public int day, mon, year, weekDay, hour, min, sec;
	private String s;
	private Date dt;

	public Date initDateField(int d, int m, int y)
	{
		now = Calendar.getInstance();
		now.set(Calendar.DAY_OF_MONTH, d);
		now.set(Calendar.MONTH, m);
		now.set(Calendar.YEAR, y);
		return now.getTime();
	}

	public int getDateFields(int index, DateField date1)
	{
		now = Calendar.getInstance();
		dt = date1.getDate();
		now.setTime(dt);
		switch (index)
		{
			case 0:
				return now.get(Calendar.DAY_OF_MONTH);
			case 1:
				return now.get(Calendar.MONTH); //январь=0
			case 2:
				return now.get(Calendar.YEAR);
		}
		return 0;
	}

	public String oNum(int value)
	{
		if (value < 10)
		{
			return new String("0" + value);
		}
		else
		{
			return new String("" + value);
		}
	}

	public String oNum4(int v)
	{
		String s = "";
		if (v < -99)
		{
			s = "" + v;
		}
		else
		{
			if (v < -9)
			{
				s = "-0" + (-v);
			}
			else
			{
				if (v < 0)
				{
					s = "-00" + (-v);
				}
				else
				{
					if (v < 10)
					{
						s = "000" + v;
					}
					else
					{
						if (v < 100)
						{
							s = "00" + v;
						}
						else
						{
							if (v < 1000)
							{
								s = "0" + v;
							}
							else
							{
								s = "" + v;
							}
						}
					}
				}
			}
		}
		return s;
	}

	public globalCalendar()
	{
		dt = new Date(0);
		globalTime();
		globalDate();
	}

	public String globalTime()
	{
		now = Calendar.getInstance();
		sec = now.get(Calendar.SECOND);
		min = now.get(Calendar.MINUTE);
		hour = now.get(Calendar.HOUR_OF_DAY);
		return (oNum(hour) + ":" + oNum(min) + ":" + oNum(sec));
	}

	public String shortDay(int n)
	{
		return (shortDays[n]);
	}

	public String globalDate()
	{
		now = Calendar.getInstance();
		day = now.get(Calendar.DAY_OF_MONTH);
		weekDay = now.get(Calendar.DAY_OF_WEEK) - 1;
		mon = now.get(Calendar.MONTH);
		year = now.get(Calendar.YEAR);
		return (shortDay(weekDay) + ", " + oNum(day) + "." + oNum(mon + 1) + "." + oNum4(year));
	}

	public String getDMYDate(int day, int mon, int year)
	{
		//globalDate ();
		return oNum(day) + "." + oNum(mon + 1) + "." + oNum4(year);
	}

	public String getDateStr(int d, int m, int y)
	{
		int w = getWeekDay(d, m, y);
		return (oNum(d) + "." + oNum(m + 1) + "." + oNum4(y) + ", " + shortDays[w]);
	}

	public int getDay()
	{
		globalDate();
		return day;
	}

	public int getWeekDay()
	{
		globalDate();
		return weekDay;
	}

	public int getMonth()
	{
		globalDate();
		return mon;
	}

	public int getYear()
	{
		globalDate();
		return year;
	}

	public int getWeekDay(int day, int month, int year)
	{
		now = Calendar.getInstance();
		now.set(Calendar.YEAR, year);
		now.set(Calendar.MONTH, month);
		now.set(Calendar.DAY_OF_MONTH, day);
		int dayOfWeek = now.get(Calendar.DAY_OF_WEEK) - 1; //вернет 1 = Вс
		return dayOfWeek;
	}

	public int getMonthDays(int month, int year)
	{
		leapYear(year);
		return monthDays[month];
	}

	public String getMonthName(int i)
	{
		return monthStr[i];
	}

	public boolean leapYear(int year)
	{
		if ((year % 4 == 0) && (year % 100 != 0) || (year % 400 == 0))
		{
			monthDays[1] = 29;
			return true;
		}
		else
		{
			monthDays[1] = 28;
			return false;
		}
	}

	public int compareDates(int day1, int month1, int year1, int day2, int month2, int year2)
	{
		if (year1 < year2)
		{
			return -1;
		}
		else
		{
			if (year1 > year2)
			{
				return 1;
			}
			else
			{
				if (month1 < month2)
				{
					return -1;
				}
				else
				{
					if (month1 > month2)
					{
						return 1;
					}
					else
					{
						if (day1 < day2)
						{
							return -1;
						}
						else
						{
							if (day1 > day2)
							{
								return 1;
							}
						}
						return 0;
					}
				}
			}
		}
	}

	public int kolDays0(int d1, int m1, int d2, int m2, int y)
	{
		int i, s;
		leapYear(y);
		if (m1 == m2)
		{
			s = d2 - d1;
		}
		else
		{
			s = monthDays[m1] - d1 + 1;
			for (i = m1 + 1; i < m2; i++)
			{
				s += monthDays[i];
			}
			s += (d2 - 1);
		}
		return s;
	}

	public int kolDays(int day1, int month1, int year1, int day2, int month2, int year2)
	{
		int i, k = 0, z = 1;
		if (compareDates(day1, month1, year1, day2, month2, year2) > 0)
		{
			i = day1;
			day1 = day2;
			day2 = i;
			i = month1;
			month1 = month2;
			month2 = i;
			i = year1;
			year1 = year2;
			year2 = i;
			z = -1;
		}
		if (year1 == year2)
		{
			k = kolDays0(day1, month1, day2, month2, year1);
		}
		else
		{
			k = kolDays0(day1, month1, 31, 11, year1) + 1;
			for (i = year1 + 1; i < year2; i++)
			{
				k += 365;
				if (leapYear(i) == true)
				{
					k++;
				}
			}
			leapYear(year2);
			k += (kolDays0(1, 0, day2, month2, year2));
		}
		return z * k;
	}

	public int abs(int a)
	{
		if (a < 0)
		{
			return -a;
		}
		else
		{
			return a;
		}
	}

	public String goodWordForm(int d, int field)
	{
		String[][] suf =
		{
			{
				"неделя", "недели", "недель"
			},
			{
				"день", "дня", "дней"
			},
			{
				"месяц", "месяца", "месяцев"
			},
			{
				"год", "года", "лет"
			}
		};
		int index;
		if ((d % 100 > 10) && (d % 100 < 20) || (d % 10 == 0) || (d % 10 > 4))
		{
			index = 2;
		}
		else
		{
			if ((d % 10 > 1) && (d % 10 < 5))
			{
				index = 1;
			}
			else
			{
				index = 0;
			}
		}
		return suf[field][index];
	}

	public String kolTimes(int day1, int month1, int year1, int day2, int month2, int year2)
	{
		String s = "";
		int y = 0, m = 0, d0 = 0, w = 0, i;
		boolean out = false;
		if (compareDates(day1, month1, year1, day2, month2, year2) > 0)
		{
			i = day1;
			day1 = day2;
			day2 = i;
			i = month1;
			month1 = month2;
			month2 = i;
			i = year1;
			year1 = year2;
			year2 = i;
		}
		int d = kolDays(day1, month1, year1, day2, month2, year2);
		do
		{
			if (out == true)
			{
				w = d / 7;
				if (w > 0)
				{
					s += "" + w + " " + goodWordForm(w, 0);
					int w0 = d - w * 7;
					if (w0 > 0)
					{
						s += " и " + w0 + " " + goodWordForm(w0, 1);
					}
					s += ";\n";
				}
				if (y > 0)
				{
					s += y + " " + goodWordForm(y, 3);
				}
				if (m > 0)
				{
					if (y > 0)
					{
						s += ", ";
					}
					s += m + " " + goodWordForm(m, 2);
				}
				if (d0 > 0)
				{
					if ((y > 0) || (m > 0))
					{
						s += ", ";
					}
					s += d0 + " " + goodWordForm(d0, 1);
				}
				if (s.length() < 1)
				{
					s = ":-)";
				}
				return s;
			}
			if (year1 < year2 - 1)
			{
				year1++;
				y++;
			}
			else
			{
				if (year1 == year2 - 1)
				{
					m = 12 - month1 + month2;
					if (day1 > day2)
					{
						m--;
						month1 = month2 - 1;
						if (month1 < 0)
						{
							month1 = 11;
							year1 = year2 - 1;
						}
						else
						{
							year1 = year2;
						}
						d0 = kolDays(day1, month1, year1, day2, month2, year2);
					}
					else
					{
						d0 = day2 - day1;
					}
					if (m > 11)
					{
						y++;
						m -= 12;
					}
					out = true;
				}
				else
				{
					if (year1 == year2)
					{
						m = month2 - month1;
						if (day1 > day2)
						{
							m--;
							int year_ = year2;
							int month_ = month2 - 1;
							if (month_ < 1)
							{
								month_ = 12;
								year_--;
							}
							d0 = kolDays(day1, month_, year_, day2, month2, year2);
						}
						else
						{
							d0 = day2 - day1;
						}
						out = true;
					}
				}
			}
		}
		while (true);
	}

	public String datePlusDays(int day1, int month1, int year1, int days)
	{
		int day2 = day1, month2 = month1, year2 = year1, i;
		boolean t = leapYear(year2);
		if (days > 0)
		{
			for (i = 0; i < days; i++)
			{
				day2++;
				if (day2 > monthDays[month2])
				{
					day2 = 1;
					month2++;
					if (month2 > 11)
					{
						month2 = 0;
						year2++;
						leapYear(year2);
					}
				}
			}
		}
		else
		{
			if (days < 0)
			{
				for (i = days; i < 0; i++)
				{
					day2--;
					if (day2 < 1)
					{
						month2--;
						if (month2 < 0)
						{
							month2 = 11;
							year2--;
							leapYear(year2);
						}
						day2 = monthDays[month2];
					}
				}
			}
		}
		return getDMYDate(day2, month2, year2);
	}

	public boolean correctDate(int day, int month, int year)
	{
		leapYear(year);
		if ((month < 0) || (month > 11))
		{
			return false;
		}
		if ((day < 1) || (day > monthDays[month]) || (year < 0))
		{
			return false;
		}
		//Если разрешать годы<0 то исправить oNum4 ()
		return true;
	}

	public int getNumberOfWeek(int day, int month, int year)
	{
		int m = 0;
		int d = 1;
		int w = 1;
		leapYear(year);
		int wd = getWeekDay(d, m, year);
		if (wd == 0)
		{
			wd = 7;
		}
		if (wd != 1)
		{ //неполная 1 неделя
			do
			{
				if ((m == month) && (d == day))
				{
					return 1;
				}
				d++;
				if (d > monthDays[m])
				{
					d = 1;
					m++;
					if (m > 11)
					{
						m = 0;
						year++;
						leapYear(year);
					}
				}
				wd++;
				if (wd > 7)
				{
					wd = 1;
					break;
				}
			}
			while (true);
			w = 2;
		}
		do
		{
			if ((m == month) && (d == day))
			{
				break;
			}
			d++;
			if (d > monthDays[m])
			{
				d = 1;
				m++;
				if (m > 11)
				{
					m = 0;
					year++;
					leapYear(year);
				}
			}
			wd++;
			if (wd > 7)
			{
				wd = 1;
				w++;
			}
		}
		while (true);
		return w;
	}

	public int getWeekDayInAnyYear(int day, int month, int year)
	{
		month++;
		if (month < 3)
		{
			month += 10;
		}
		else
		{
			month -= 2;
		}
		if (month > 10)
		{
			year--;
		}
		int cent = year / 100;
		year %= 100;
		int dday = (26 * month - 2) / 10 + day + year + year / 4 + cent / 4 - 2 * cent;
		dday = (dday + 777) % 7;
		return dday;
	}
}
