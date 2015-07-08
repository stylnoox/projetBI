package src;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class CalculerCSV
{
	// Les ArrayList contiennent le résutlat des calculs pour :
	// 3M, 6M, 1Y, 3Y, 5Y
	private ArrayList<Integer> period = new ArrayList<Integer>();

	private ArrayList<Double> perfAnnualisee;
	private ArrayList<Double> perfRelative;
	private ArrayList<Double> volatilite;
	private ArrayList<Double> volatiliteAnnualisee;
	private double trackingError;
	private double trackingErrorAnnualise;
	private double ratioInformation;
	private ArrayList<Double> beta;
	private ArrayList<Double> alpha;

	private Stock s;
	private Date startDate, today, threeMonths, sixMonths, oneYear, threeYears, fiveYears;

	@SuppressWarnings("deprecation")
	public CalculerCSV(Stock s, Date startDate)
	{
		System.out.println("\nInitialisation CalculerCSV");
		this.s = s;
		this.setStartDate(startDate);
		period.add(3);
		period.add(6);
		period.add(12);
		period.add(36);
		period.add(50);

		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		today = c.getTime();
		// RECHERCHE DU COURS DE 3 MOIS
		threeMonths = c.getTime();
		threeMonths.setMonth(threeMonths.getMonth() - 3);
		sixMonths = c.getTime();
		sixMonths.setMonth(sixMonths.getMonth() - 6);
		oneYear = c.getTime();
		oneYear.setYear(threeMonths.getYear() - 1);
		threeYears = c.getTime();
		threeYears.setYear(threeYears.getYear() - 3);
		fiveYears = c.getTime();
		fiveYears.setYear(fiveYears.getYear() - 5);

	}

	public ArrayList<Double> getPerfAnnualisee()
	{
		System.out.println("\nPerformance annualisee :\n");
		// double perfAnnu=0.0;
		ArrayList<Double> perfAnnuMap = new ArrayList<Double>();
		double daysDiff3M = getDaysBetweenDates(threeMonths,today);
		double daysDiff6M = getDaysBetweenDates(sixMonths,today);
		double daysDiff1Y = getDaysBetweenDates(oneYear,today);
		double daysDiff3Y = getDaysBetweenDates(threeYears,today);
		double daysDiff5Y = getDaysBetweenDates(fiveYears,today);
		
		double perfAnnu3M = Math.pow((coursToday()/coursHisto(threeMonths)),(365/(daysDiff3M)))-1;
		double perfAnnu6M = Math.pow((coursToday()/coursHisto(sixMonths)),(365/(daysDiff6M)))-1;
		double perfAnnu1Y = Math.pow((coursToday()/coursHisto(oneYear)),(365/(daysDiff1Y)))-1;
		double perfAnnu3Y = Math.pow((coursToday()/coursHisto(threeYears)),(365/(daysDiff3Y)))-1;
		double perfAnnu5Y = Math.pow((coursToday()/coursHisto(fiveYears)),(365/(daysDiff5Y)))-1;
		perfAnnuMap.add(perfAnnu3M);
		perfAnnuMap.add(perfAnnu6M);
		perfAnnuMap.add(perfAnnu1Y);
		perfAnnuMap.add(perfAnnu3Y);
		perfAnnuMap.add(perfAnnu5Y);
		System.out.println("Perf annualisée : "+perfAnnuMap
				+"\n---------------------------"
				+ "---------------------\n");
		// TRI PAR DATE
		// System.out.println("\ntoday :\n" + today);
		// System.out.println("\n3M :\n" + threeMonths);
		// System.out.println("\n6M :\n" + sixMonths);
		// System.out.println("\n1Y :\n" + oneYear);
		// System.out.println("\n3Y :\n" + threeYears);
		// System.out.println("\n5Y :\n" + fiveYears);
		
		return perfAnnuMap;
	}

	/**
	 * @return Retourne la derniere valeur de cloture
	 */
	public double coursToday()
	{
		Map<Date, Double> m = s.getHistoCours();
		SortedSet<Date> keys = new TreeSet<Date>(m.keySet());
		return m.get(keys.last());
	}

	/**
	 * @param d
	 *            -> Date du cours souhaite
	 * @return la valeur du cours a la date d
	 */
	public double coursHisto(Date d)
	{
		Map<Date, Double> m = s.getHistoCours();
		SortedSet<Date> keys = new TreeSet<Date>(m.keySet());
		double cours = 0.0;

		for (Object key : keys)
		{
			Date k = (Date) key;
			if (k.compareTo(d) == 1)
			{
				cours = m.get(key);
				System.out.println("Date: " + key + "\nCours: " + cours);
				break;
			}
		}
		return cours;
	}

	public static double getDaysBetweenDates(Date theEarlierDate,
			Date theLaterDate)
	{
		double result = Double.POSITIVE_INFINITY;
		if (theEarlierDate != null && theLaterDate != null)
		{
			final long MILLISECONDS_PER_DAY = 1000 * 60 * 60 * 24;
			Calendar aCal = Calendar.getInstance();
			aCal.setTime(theEarlierDate);
			long aFromOffset = aCal.get(Calendar.DST_OFFSET);
			aCal.setTime(theLaterDate);
			long aToOffset = aCal.get(Calendar.DST_OFFSET);
			long aDayDiffInMili = (theLaterDate.getTime() + aToOffset)
					- (theEarlierDate.getTime() + aFromOffset);
			result = ((double) aDayDiffInMili / MILLISECONDS_PER_DAY);
		}
		return result;
	}

	public void setPerfAnnualisee(ArrayList<Double> perfAnnualisee)
	{
		this.perfAnnualisee = perfAnnualisee;
	}

	public ArrayList<Double> getPerfRelative()
	{
		return perfRelative;
	}

	public void setPerfRelative(ArrayList<Double> perfRelative)
	{
		this.perfRelative = perfRelative;
	}

	public ArrayList<Double> getVolatilite()
	{
		return volatilite;
	}

	public void setVolatilite(ArrayList<Double> volatilite)
	{
		this.volatilite = volatilite;
	}

	public ArrayList<Double> getVolatiliteAnnualisee()
	{
		return volatiliteAnnualisee;
	}

	public void setVolatiliteAnnualisee(ArrayList<Double> volatiliteAnnualisee)
	{
		this.volatiliteAnnualisee = volatiliteAnnualisee;
	}

	public double getTrackingError()
	{
		return trackingError;
	}

	public void setTrackingError(double trackingError)
	{
		this.trackingError = trackingError;
	}

	public double getTrackingErrorAnnualise()
	{
		return trackingErrorAnnualise;
	}

	public void setTrackingErrorAnnualise(double trackingErrorAnnualise)
	{
		this.trackingErrorAnnualise = trackingErrorAnnualise;
	}

	public double getRatioInformation()
	{
		return ratioInformation;
	}

	public void setRatioInformation(double ratioInformation)
	{
		this.ratioInformation = ratioInformation;
	}

	public ArrayList<Double> getBeta()
	{
		return beta;
	}

	public void setBeta(ArrayList<Double> beta)
	{
		this.beta = beta;
	}

	public ArrayList<Double> getAlpha()
	{
		return alpha;
	}

	public void setAlpha(ArrayList<Double> alpha)
	{
		this.alpha = alpha;
	}

	/**
	 * @return the s
	 */
	public Stock getS()
	{
		return s;
	}

	/**
	 * @param s
	 *            the s to set
	 */
	public void setS(Stock s)
	{
		this.s = s;
	}

	/**
	 * @return the fiveYears
	 */
	public Date getFiveYears()
	{
		return fiveYears;
	}

	/**
	 * @param fiveYears
	 *            the fiveYears to set
	 */
	public void setFiveYears(Date fiveYears)
	{
		this.fiveYears = fiveYears;
	}

	/**
	 * @return the threeYears
	 */
	public Date getThreeYears()
	{
		return threeYears;
	}

	/**
	 * @param threeYears
	 *            the threeYears to set
	 */
	public void setThreeYears(Date threeYears)
	{
		this.threeYears = threeYears;
	}

	/**
	 * @return the oneYear
	 */
	public Date getOneYear()
	{
		return oneYear;
	}

	/**
	 * @param oneYear
	 *            the oneYear to set
	 */
	public void setOneYear(Date oneYear)
	{
		this.oneYear = oneYear;
	}

	/**
	 * @return the sixMonths
	 */
	public Date getSixMonths()
	{
		return sixMonths;
	}

	/**
	 * @param sixMonths
	 *            the sixMonths to set
	 */
	public void setSixMonths(Date sixMonths)
	{
		this.sixMonths = sixMonths;
	}

	/**
	 * @return the threeMonths
	 */
	public Date getThreeMonths()
	{
		return threeMonths;
	}

	/**
	 * @param threeMonths
	 *            the threeMonths to set
	 */
	public void setThreeMonths(Date threeMonths)
	{
		this.threeMonths = threeMonths;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate()
	{
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

}
