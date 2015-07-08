package src;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.commons.math3.stat.correlation.Covariance;

public class CalculerCSV
{
	// Les ArrayList contiennent le résutlat des calculs pour :
	// 3M, 6M, 1Y, 3Y, 5Y

	private ArrayList<Integer> period = new ArrayList<Integer>();

	private ArrayList<Double> perfAnnualisee;
	private ArrayList<Double> perfRelative;
	private ArrayList<Double> volatilite;
	private ArrayList<Double> volatiliteAnnualisee;
	private ArrayList<Double> trackingError;
	private ArrayList<Double> trackingErrorAnnualise;
	private ArrayList<Double> ratioInformation;
	private Double beta;
	private Double alpha;

	private Stock s;
	private Date startDate, today, threeMonths, sixMonths, oneYear, threeYears,
			fiveYears;

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
		double daysDiff3M = getDaysBetweenDates(threeMonths, today);
		double daysDiff6M = getDaysBetweenDates(sixMonths, today);
		double daysDiff1Y = getDaysBetweenDates(oneYear, today);
		double daysDiff3Y = getDaysBetweenDates(threeYears, today);
		double daysDiff5Y = getDaysBetweenDates(fiveYears, today);

		double perfAnnu3M = Math.pow((coursToday() / coursHisto(threeMonths)),
				(365 / (daysDiff3M))) - 1;
		double perfAnnu6M = Math.pow((coursToday() / coursHisto(sixMonths)),
				(365 / (daysDiff6M))) - 1;
		double perfAnnu1Y = Math.pow((coursToday() / coursHisto(oneYear)),
				(365 / (daysDiff1Y))) - 1;
		double perfAnnu3Y = Math.pow((coursToday() / coursHisto(threeYears)),
				(365 / (daysDiff3Y))) - 1;
		double perfAnnu5Y = Math.pow((coursToday() / coursHisto(fiveYears)),
				(365 / (daysDiff5Y))) - 1;
		perfAnnuMap.add(perfAnnu3M);
		perfAnnuMap.add(perfAnnu6M);
		perfAnnuMap.add(perfAnnu1Y);
		perfAnnuMap.add(perfAnnu3Y);
		perfAnnuMap.add(perfAnnu5Y);
		System.out.println("Perf annualisée : " + perfAnnuMap);
		// TRI PAR DATE
		// System.out.println("\ntoday :\n" + today);
		// System.out.println("\n3M :\n" + threeMonths);
		// System.out.println("\n6M :\n" + sixMonths);
		// System.out.println("\n1Y :\n" + oneYear);
		// System.out.println("\n3Y :\n" + threeYears);
		// System.out.println("\n5Y :\n" + fiveYears);

		return perfAnnuMap;
	}

	public ArrayList<Double> getPerfRelative()
	{
		System.out.println("\nPerformance relative annualisee :\n");
		// double perfAnnu=0.0;
		ArrayList<Double> perfAnnuMap = new ArrayList<Double>();
		double daysDiff3M = getDaysBetweenDates(threeMonths, today);
		double daysDiff6M = getDaysBetweenDates(sixMonths, today);
		double daysDiff1Y = getDaysBetweenDates(oneYear, today);
		double daysDiff3Y = getDaysBetweenDates(threeYears, today);
		double daysDiff5Y = getDaysBetweenDates(fiveYears, today);

		double perfAnnu3M = Math.pow(
				(coursTodayIndex() / coursHistoIndex(threeMonths)),
				(365 / (daysDiff3M))) - 1;
		double perfAnnu6M = Math.pow(
				(coursTodayIndex() / coursHistoIndex(sixMonths)),
				(365 / (daysDiff6M))) - 1;
		double perfAnnu1Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(oneYear)),
				(365 / (daysDiff1Y))) - 1;
		double perfAnnu3Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(threeYears)),
				(365 / (daysDiff3Y))) - 1;
		double perfAnnu5Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(fiveYears)),
				(365 / (daysDiff5Y))) - 1;

		ArrayList<Double> stock = this.getPerfAnnualisee();

		perfAnnuMap.add(perfAnnu3M - stock.get(0));
		perfAnnuMap.add(perfAnnu6M - stock.get(1));
		perfAnnuMap.add(perfAnnu1Y - stock.get(2));
		perfAnnuMap.add(perfAnnu3Y - stock.get(3));
		perfAnnuMap.add(perfAnnu5Y - stock.get(4));
		System.out.println("Perf relative annualisée : " + perfAnnuMap);
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
	 * @return Retourne la derniere valeur de cloture
	 */
	public double coursTodayIndex()
	{
		Map<Date, Double> m = s.getHistoCoursIndex();
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

	/**
	 * @param d
	 *            -> Date du cours souhaite
	 * @return la valeur du cours a la date d
	 */
	public double coursHistoIndex(Date d)
	{
		Map<Date, Double> m = s.getHistoCoursIndex();
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

	public void setPerfRelative(ArrayList<Double> perfRelative)
	{
		this.perfRelative = perfRelative;
	}

	/**
	 * @param d
	 *            -> détermine la période de volatilité à calculer
	 * @return
	 */
	public ArrayList<Double> getVolatilite()
	{
		ArrayList<Double> volatilite = new ArrayList<Double>();
		volatilite.add(calculVolatilite(threeMonths));
		volatilite.add(calculVolatilite(sixMonths));
		volatilite.add(calculVolatilite(oneYear));
		volatilite.add(calculVolatilite(threeYears));
		volatilite.add(calculVolatilite(fiveYears));
		System.out.println("\nVolatilite : " + volatilite);
		return volatilite;
	}

	public ArrayList<Double> getVolatiliteAnnualisee()
	{
		ArrayList<Double> volatiliteAnnualisee = new ArrayList<Double>();
		volatiliteAnnualisee.add(Math.sqrt(calculVolatilite(threeMonths))
				* Math.sqrt(52));
		volatiliteAnnualisee.add(Math.sqrt(calculVolatilite(sixMonths))
				* Math.sqrt(52));
		volatiliteAnnualisee.add(Math.sqrt(calculVolatilite(oneYear))
				* Math.sqrt(52));
		volatiliteAnnualisee.add(Math.sqrt(calculVolatilite(threeYears))
				* Math.sqrt(52));
		volatiliteAnnualisee.add(Math.sqrt(calculVolatilite(fiveYears))
				* Math.sqrt(52));
		System.out.println("\nVolatilite annualisee: " + volatiliteAnnualisee);
		return volatiliteAnnualisee;
	}

	public double calculVolatilite(Date d)
	{
		Map<Date, Double> m = s.getHistoCours();
		SortedSet<Date> keys = new TreeSet<Date>(m.keySet());
		double sum = 0, r = 0;
		int cpt = 0;

		// CALCUL R moyenne
		for (Date key : keys)
		{
			if (key.compareTo(d) == 1)
			{
				r += m.get(key);
				cpt++;
			}
		}
		r /= cpt;
		System.out.println("\nR Moyenne : " + r);
		System.out.println("\nCpt : " + cpt);

		// CALCUL Ri - R moyenne
		for (Date key : keys)
		{
			if (key.compareTo(d) == 1)
				sum += Math.pow(m.get(key) - r, 2);
		}

		// return m.get(keys.last());

		return sum / (cpt - 1);
	}

	public void setVolatilite(ArrayList<Double> volatilite)
	{
		this.volatilite = volatilite;
	}

	public void setVolatiliteAnnualisee(ArrayList<Double> volatiliteAnnualisee)
	{
		this.volatiliteAnnualisee = volatiliteAnnualisee;
	}

	/**
	 * @return
	 */
	public ArrayList<Double> getTrackingError()
	{

		// System.out.println("\nTracking error:\n");
		ArrayList<Double> prMap = getPerfRelative(); // performances relatives
		double prm = 0;// moyenne performances relatives
		for (double d : prMap)
			prm += d;
		prm /= prMap.size();// calcul moyenne

		ArrayList<Double> teMap = new ArrayList<Double>();
		System.out.println("\nPR(0) : " + prMap.get(0) + "\nPRM : " + prm);
		for (int i = 0; i < prMap.size(); i++)
			teMap.add(Math.pow(prMap.get(i) - prm, 2) / (i + 1));

		System.out.println("Tracking Error : " + teMap);
		// TRI PAR DATE
		// System.out.println("\ntoday :\n" + today);
		// System.out.println("\n3M :\n" + threeMonths);
		// System.out.println("\n6M :\n" + sixMonths);
		// System.out.println("\n1Y :\n" + oneYear);
		// System.out.println("\n3Y :\n" + threeYears);
		// System.out.println("\n5Y :\n" + fiveYears);

		return teMap;
		// return trackingError;
	}

	public void setTrackingError(ArrayList<Double> trackingError)
	{
		this.trackingError = trackingError;
	}

	public ArrayList<Double> getTrackingErrorAnnualise()
	{
		ArrayList<Double> tea = new ArrayList<Double>();
		for (Double te : getTrackingError())
			tea.add(Math.sqrt(te) * Math.sqrt(52));
		System.out.println("Tracking error annualise : " + tea);
		return tea;
	}

	public void setTrackingErrorAnnualise(
			ArrayList<Double> trackingErrorAnnualise)
	{
		this.trackingErrorAnnualise = trackingErrorAnnualise;
	}

	public ArrayList<Double> getRatioInformation()
	{
		ArrayList<Double> ri = new ArrayList<Double>();
		ArrayList<Double> pr = getPerfRelative();
		ArrayList<Double> te = getTrackingError();
		for (int i = 0; i < pr.size(); i++)
			ri.add(pr.get(i) / te.get(i));
		System.out.println("Ratio d'information : " + ri);
		return ri;
	}

	public void setRatioInformation(ArrayList<Double> ratioInformation)
	{
		this.ratioInformation = ratioInformation;
	}

	/**
	 * @param array
	 * @return un array de double à partir d'une ArrayList de Double
	 */
	public static double[] toPrimitive(Double[] array)
	{
		if (array == null)
		{
			return null;
		} else if (array.length == 0)
		{
			return null;
		}
		final double[] result = new double[array.length];
		for (int i = 0; i < array.length; i++)
		{
			result[i] = array[i].doubleValue();
		}
		return result;
	}

	/**
	 * @return ArrayList des Beta
	 */
	public Double getBeta()
	{
		Double beta;

		ArrayList<Double> paf = getPerfAnnualisee();
		double[] pafArray = { paf.get(0), paf.get(1), paf.get(2), paf.get(3),
				paf.get(4) };

		ArrayList<Double> paBench = new ArrayList<Double>();
		double daysDiff3M = getDaysBetweenDates(threeMonths, today);
		double daysDiff6M = getDaysBetweenDates(sixMonths, today);
		double daysDiff1Y = getDaysBetweenDates(oneYear, today);
		double daysDiff3Y = getDaysBetweenDates(threeYears, today);
		double daysDiff5Y = getDaysBetweenDates(fiveYears, today);
		double perfAnnu3M = Math.pow(
				(coursTodayIndex() / coursHistoIndex(threeMonths)),
				(365 / (daysDiff3M))) - 1;
		double perfAnnu6M = Math.pow(
				(coursTodayIndex() / coursHistoIndex(sixMonths)),
				(365 / (daysDiff6M))) - 1;
		double perfAnnu1Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(oneYear)),
				(365 / (daysDiff1Y))) - 1;
		double perfAnnu3Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(threeYears)),
				(365 / (daysDiff3Y))) - 1;
		double perfAnnu5Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(fiveYears)),
				(365 / (daysDiff5Y))) - 1;
		paBench.add(perfAnnu3M);
		paBench.add(perfAnnu6M);
		paBench.add(perfAnnu1Y);
		paBench.add(perfAnnu3Y);
		paBench.add(perfAnnu5Y);
		double[] paBenchArray = { paBench.get(0), paBench.get(1),
				paBench.get(2), paBench.get(3), paBench.get(4) };
		

		Covariance c = new Covariance();

		double var = 0;
		for (Double d : paf)
			var += d;
		var /= paf.size();
		beta = (c.covariance(pafArray, paBenchArray) / var);

		System.out.println("Beta : " + beta);
		return beta;
	}

	public void setBeta(Double beta)
	{
		this.beta = beta;
	}

	public Double getAlpha()
	{
		
		ArrayList<Double> paf = getPerfAnnualisee();
		ArrayList<Double> paBench = new ArrayList<Double>();
		double daysDiff3M = getDaysBetweenDates(threeMonths, today);
		double daysDiff6M = getDaysBetweenDates(sixMonths, today);
		double daysDiff1Y = getDaysBetweenDates(oneYear, today);
		double daysDiff3Y = getDaysBetweenDates(threeYears, today);
		double daysDiff5Y = getDaysBetweenDates(fiveYears, today);
		double perfAnnu3M = Math.pow(
				(coursTodayIndex() / coursHistoIndex(threeMonths)),
				(365 / (daysDiff3M))) - 1;
		double perfAnnu6M = Math.pow(
				(coursTodayIndex() / coursHistoIndex(sixMonths)),
				(365 / (daysDiff6M))) - 1;
		double perfAnnu1Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(oneYear)),
				(365 / (daysDiff1Y))) - 1;
		double perfAnnu3Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(threeYears)),
				(365 / (daysDiff3Y))) - 1;
		double perfAnnu5Y = Math.pow(
				(coursTodayIndex() / coursHistoIndex(fiveYears)),
				(365 / (daysDiff5Y))) - 1;
		paBench.add(perfAnnu3M);
		paBench.add(perfAnnu6M);
		paBench.add(perfAnnu1Y);
		paBench.add(perfAnnu3Y);
		paBench.add(perfAnnu5Y);
		
		double meanPA=0,meanPAB=0;
		//Calcul moyenne
		for(Double d : paf) 
			meanPA+=d;
		meanPA/=paf.size();
		for(Double d : paBench)
			meanPA+=d;
		meanPA/=paBench.size();
		double alpha = meanPA - getBeta()*meanPAB;
		System.out.println("Alpha: " + alpha);
		return alpha;
	}

	public void setAlpha(Double alpha)
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
	 * @param startDate
	 *            the startDate to set
	 */
	public void setStartDate(Date startDate)
	{
		this.startDate = startDate;
	}

}
