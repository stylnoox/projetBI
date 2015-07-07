package src;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

public class CalculerCSV
{
//	Les ArrayList contiennent le résutlat des calculs pour :
//	3M, 6M, 1Y, 3Y, 5Y
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
	
	
	public CalculerCSV(Stock s)
	{
		System.out.println("\nInitialisation CalculerCSV");
		this.s=s;
		period.add(3);
		period.add(6);
		period.add(12);
		period.add(36);
		period.add(50);
	}
	
	public ArrayList<Double> getPerfAnnualisee()
	{
		System.out.println("\nPerformance annualisee :\n");
//		double perfAnnu=0.0;
		Map<Date,Double> m = s.getHistoCours();
		ArrayList<Double> perfAnnuMap = null;

		//TRI PAR DATE
		SortedSet<Date> keys = new TreeSet<Date>();
		Object[] dates = m.keySet().toArray();
		
//		keys = (SortedSet<Date>) m.keySet();
		for (Object key : dates) 
		{ 
		   Double value = m.get(key);
		   System.out.println("\nDate: "+key+"\nCours"+value);
		}
		
		
//		System.out.println("\nkeyset :\n"+m.keySet());
		
		
		return perfAnnuMap;
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
	 * @param s the s to set
	 */
	public void setS(Stock s)
	{
		this.s = s;
	}
	

}
