package src;

import java.io.File;
import java.io.FileReader;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import net.sf.opencsv;

public class RecupererCours
{

	Stock s;
	Date startDate;

	public RecupererCours(Stock s, Date startDate)
	{
		this.s = s;
		this.startDate = startDate;
		System.out.println(genererAdresse());
		// getHistoricalPrices(genererAdresse());

	}

	/**
	 * @return Une MAP associant chaque cours de l'action de s à son cours
	 */
	public Map<Date, Double> getCours()
	{
		File file = new File(genererAdresse());
		FileReader fr = new FileReader(file);
		
		CSVReader csvReader = new CSVReader(fr, SEPARATOR);

		return null;
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
