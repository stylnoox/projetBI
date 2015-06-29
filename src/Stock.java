package src;

import java.util.Date;
import java.util.Map;

/**
 * @author Vincent
 *
 */
/**
 * @author Vincent
 *
 */
public class Stock
{

	private String benchId;
	private String benchMark;
	private String id;
	private String industry;
	private String name;
	private String sector;
	private String zone;
	private String country;
	private Map<Date,Double> histoCours;
	
	public Stock()
	{
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
	
	
	/**
	 * @return the benchId
	 */
	public String getBenchId()
	{
		return benchId;
	}

	/**
	 * @param benchId the benchId to set
	 */
	public void setBenchId(String benchId)
	{
		this.benchId = benchId;
	}
	
	public String getId()
	{
		return id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getIndustry()
	{
		return industry;
	}
	
	public void setIndustry(String industry)
	{
		this.industry = industry;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getSector()
	{
		return sector;
	}
	
	public void setSector(String sector)
	{
		this.sector = sector;
	}
	
	public String getZone()
	{
		return zone;
	}
	
	public void setZone(String zone)
	{
		this.zone = zone;
	}

	/**
	 * @return the benchMark
	 */
	public String getBenchMark()
	{
		return benchMark;
	}

	/**
	 * @param benchMark the benchMark to set
	 */
	public void setBenchMark(String benchMark)
	{
		this.benchMark = benchMark;
	}









	/**
	 * @return the country
	 */
	public String getCountry()
	{
		return country;
	}









	/**
	 * @param country the country to set
	 */
	public void setCountry(String country)
	{
		this.country = country;
	}









	/**
	 * @return the histoCours
	 */
	public Map<Date,Double> getHistoCours()
	{
		return histoCours;
	}









	/**
	 * @param histoCours the histoCours to set
	 */
	public void setHistoCours(Map<Date,Double> histoCours)
	{
		this.histoCours = histoCours;
	}

}
