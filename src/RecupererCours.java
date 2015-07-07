package src;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opencsv.CSVReader;

public class RecupererCours
{

	Stock s;
	Date startDate;

	public RecupererCours(Stock s, Date startDate)
	{
		this.s = s;
		this.startDate = startDate;
		System.out.println(genererAdresse());
		getCours();
		// getHistoricalPrices(genererAdresse());

	}

	/**
	 * @return Une MAP associant chaque cours de l'action de s à son cours
	 */
	@SuppressWarnings("resource")
	public Map<Date, Double> getCours()
	{	
		Map<Date, Double> map = new HashMap<Date, Double>();
		try
		{
//			Generation url
			URL url = new URL(genererAdresse());
			BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
			CSVReader cr = new CSVReader(in);
			
			List<String[]> ls = cr.readAll();
			ls.remove(0);
			
//			Recuperation date et close
			for (String[] line : ls)
			{
				Date d=new Date(1900, 1, 1);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String sd = line[0];
				try
				{
					d = sdf.parse(sd);
				} catch (ParseException e1)
				{
					System.out.println("Format date incorrect : YYYY-MM-DD attendu !");
					e1.printStackTrace();
				}
				map.put(d, Double.parseDouble(line[4]));
				
			}
			
			
			
		} catch (IOException e1)
		{
			System.out.println("Ouverture du fichier impossible");
			 
		}
			return map;
	}

	@SuppressWarnings("deprecation")
	private String genererAdresse()
	{
		Date today = new Date();

		// Utilisation de Calendar à la place de Date
		// Plus simple à gérer
		Calendar calSd = Calendar.getInstance();
		Calendar calEd = Calendar.getInstance();
		calSd.setTime(startDate);
		calEd.setTime(today);

		return "http://real-chart.finance.yahoo.com/table.csv?s=" + s.getId()
				+ "&a=" + calSd.get(Calendar.MONTH) + "&b="
				+ calSd.get(Calendar.DAY_OF_MONTH) + "&c="
				+ calSd.get(Calendar.YEAR) + "&d=" + calEd.get(Calendar.MONTH)
				+ "&e=" + calEd.get(Calendar.DAY_OF_MONTH) + "&f="
				+ calEd.get(Calendar.YEAR) + "&g=d&ignore=.csv";

	}

}
