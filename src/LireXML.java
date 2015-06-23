package src;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class LireXML
{

	private Date startdate;
	private ArrayList<Stock> stockArray;

	/**
	 * Instancie un DocumentBuilderFactory pour parser l'input.xml
	 */
	public LireXML()
	{
		File input = new File("trunk/src/input.xml");
		Document d = ouvrirXML(input);
		setStockArray(recupererStocks(d));
	}

	/**
	 * @param f
	 *            -> Fichier XML qu'on veut parser
	 * @return -> objet Document avec le contenu et la structure de f
	 */
	public Document ouvrirXML(File f)
	{
		Document doc = null;
		final DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();
		try
		{
			final DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(f);

			// Affiche la version de XML
			System.out.println(doc.getXmlVersion());
			// Affiche l'encodage
			System.out.println(doc.getXmlEncoding());
			// Affiche s'il s'agit d'un document standalone
			System.out.println(doc.getXmlStandalone());

		} catch (final ParserConfigurationException e)
		{
			e.printStackTrace();
		} catch (final SAXException e)
		{
			e.printStackTrace();
		} catch (final IOException e)
		{
			e.printStackTrace();
		}
		return doc;
	}

	/**
	 * @param doc
	 *            -> Document récupéré à partir de l'input.xml
	 * @return une ArrayList de Stock La startdate est aussi mise à jour
	 */
	@SuppressWarnings("null")
	public ArrayList<Stock> recupererStocks(Document doc)
	{
		// Récupération Document sous forme de Nodelist
		// Plus simple pour les traitements
		NodeList stockList = doc.getDocumentElement().getChildNodes();
		ArrayList<Stock> as = null;
		Element e = (Element) doc;

		// MAJ StartDate
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = e.getAttribute("startdate");
		try
		{
			startdate = sdf.parse(sd);
		} catch (ParseException e1)
		{
			System.out.println("Format date incorrect : YYYY-MM-DD attendu !");
			e1.printStackTrace();
		}

		// Récupération des stocks XML vers Objet
		for (int i = 0; i < stockList.getLength(); i++)
			as.add(stockXML2Node(stockList.item(i)));
		return as;
	}

	/**
	 * @param n
	 *            -> Noeud DOM correspondant à un Stock
	 * @return un objet Stock correspondant au noeud n
	 */
	public Stock stockXML2Node(Node n)
	{
		Element e = (Element) n;
		Stock s = new Stock();
		s.setBenchId(e.getAttribute("benchID"));
		s.setBenchMark(e.getAttribute("benchmark"));
		s.setCountry(e.getAttribute("country"));
		s.setId(e.getAttribute("id"));
		s.setIndustry(e.getAttribute("industry"));
		s.setName(e.getAttribute("name"));
		s.setSector(e.getAttribute("sector"));
		s.setZone(e.getAttribute("zone"));
		return s;
	}

	/**
	 * @return the startdate
	 */
	public Date getStartdate()
	{
		return startdate;
	}

	/**
	 * @param startdate
	 *            the startdate to set
	 */
	public void setStartdate(Date startdate)
	{
		this.startdate = startdate;
	}

	/**
	 * @return the stockArray
	 */
	public ArrayList<Stock> getStockArray()
	{
		return stockArray;
	}

	/**
	 * @param stockArray the stockArray to set
	 */
	public void setStockArray(ArrayList<Stock> stockArray)
	{
		this.stockArray = stockArray;
	}

}
