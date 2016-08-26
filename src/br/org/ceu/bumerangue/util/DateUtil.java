package br.org.ceu.bumerangue.util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.ibm.icu.text.SimpleDateFormat;

//*****************************************************************************
/** Utilitários para manipulacao de datas */
// *****************************************************************************
public class DateUtil {

	// -------------------------------------------------------------------------
	/**
	 * Retorna uma data distante "daysDiference" em relação ao dia de hoje,
	 * truncado em DD/MM/YYYY. O parâmetro define quantos dias é a distancia em
	 * relação ao dia de hoje. Ex. -1 para o dia de ontem, 1 para o dia de
	 * amanhã.
	 */
	// -------------------------------------------------------------------------
	public static Date getDistantDayToday(int daysDiference) {
		return getDistantDayDate(daysDiference, new Date());
	}

	// -------------------------------------------------------------------------
	/**
	 * Retorna uma data distante "daysDiference" em relação a data informada,
	 * truncado em DD/MM/YYYY. O parâmetro define quantos dias é a distancia em
	 * relação ao dia de hoje. Ex. -1 para o dia de ontem, 1 para o dia de
	 * amanhã.
	 */
	// -------------------------------------------------------------------------
	public static Date getDistantDayDate(int daysDiference, Date referenceDate) {
		Calendar gc = new GregorianCalendar();
		gc.setTime(referenceDate);
		gc.set(Calendar.HOUR_OF_DAY, 0);
		gc.set(Calendar.MINUTE, 0);
		gc.set(Calendar.SECOND, 0);
		gc.add(Calendar.DAY_OF_MONTH, daysDiference);
		return gc.getTime();
	}

	/**
	 * Retorna a data atual no formato dd/mm/yyyy
	 * @return
	 */
	public static String getFormattedCurrentDate() {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(new Date());
	}

	/**
	 * Compara duas datas, truncando pelo formato passado como parâmetro.<br>
	 * Retorna valor > 0, caso date1 > date2<br>
	 * Retorna valor < 0, caso date1 < date2<br>
	 * Retorna 0, caso date1 = date2<br>
	 * @param date1
	 * @param date2
	 * @param format
	 * @return
	 */
	public static int compare(Date date1, Date date2, String format){
		String strD1 = new SimpleDateFormat(format).format(date1);
		String strD2 = new SimpleDateFormat(format).format(date2);
		Date d1 = null;
		Date d2 = null;
		try {
			d1 = new SimpleDateFormat(format).parse(strD1);
			d2 = new SimpleDateFormat(format).parse(strD2);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return d1.compareTo(d2);
	}

	/**
	 * Compara a data informada com o dia de hoje, truncando no formato dd/MM/yyyy.<br>
	 * Retorna valor > 0, caso hoje > d2<br>
	 * Retorna valor < 0, caso hoje < d2<br>
	 * Retorna 0, caso hoje = d2<br>
	 * @param d
	 * @return
	 */
	public static int compareTodayDay(Date date){
		return compare(new Date(),date,"dd/MM/yyyy");
	}

	/**
	 * Compara duas datas, truncando no formato dd/MM/yyyy.<br>
	 * Retorna valor > 0, caso date1 > date2<br>
	 * Retorna valor < 0, caso date1 < date2<br>
	 * Retorna 0, caso date1 = date2<br>
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int compareDay(Date date1, Date date2){
		return compare(date1,date2,"dd/MM/yyyy");
	}

	/**
	 * Retorna uma Data, de acordo com a String de data e formato passados como argumento.<br>
	 * Caso a data seja inválida, retorna null.
	 * @param strDate
	 * @param format
	 * @return
	 */
	public static Date getDate(String strDate, String format){
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setLenient(false);
		try {
			return dateFormat.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}
}
