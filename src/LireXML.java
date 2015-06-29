package src;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Vincent
 *
 */
public class LireXML
{

	private RecupererCours rc;
	private Date startdate;
	private ArrayList<Stock> stockArray;

	/**
	 * Instancie un DocumentBuilderFactory pour parser l'input.xml
	 */
	public LireXML()
	{
		// OUVERTURE XML
		File input = new File("trunk/src/input.xml");
		Document d = ouvrirXML(input);

		// CONVERSION XML -> ARRAYLIST DE STOCK
		setStockArray(recupererStocks(d));

		// RECUPERATION DATE DU JOUR
		Calendar c = new GregorianCalendar();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date today = c.getTime();

		// RAJOUT HISTORIQUE COURS POUR CHAQUE STOCK
		for (Stock s : stockArray)
			s.setHistoCours(rc.getCours(startdate, today, url));
		;
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
		ArrayList<Stock> as = new ArrayList<Stock>();

		// MAJ StartDate
		Element e = (Element) doc.getDocumentElement();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = e.getAttribute("startdate");
		try
		{
			startdate = sdf.parse(sd);
			System.out.println(startdate);
		} catch (ParseException e1)
		{
			System.out.println("Format date incorrect : YYYY-MM-DD attendu !");
			e1.printStackTrace();
		}
		// System.out.println("getLength() => " + stockList.getLength());
		// Récupération des stocks XML vers Objet
		for (int i = 1; i < stockList.getLength(); i = i + 2)
		{
			// as.add(stockXML2Node(stockList.item(i)));
			// System.out.println("LECTURE : \nbenchID: "+s.getBenchId()+"\nbenchmark: "+s.getBenchMark()+
			// "\ncountry: "+s.getCountry()+"\nid: "+s.getId()+"\nindustry: "+s.getIndustry()+
			// "\nname: "+s.getName()+"\nsector: "+s.getSector()+"\nzone: "+s.getZone());
			as.add(stockXML2Node(stockList.item(i)));
		}
		return as;

	}

	/**
	 * @param n
	 *            -> Noeud DOM correspondant à un Stock
	 * @return un objet Stock correspondant au noeud n
	 */
	// *
	public Stock stockXML2Node(Node n)
	{
		Stock s = new Stock();
		// System.out.println("type :"+n.getNodeType()+"\nvalue :"+n.getNodeValue()+"\nname :"+n.getNodeName());
		// Element e = n.; // A CORRIGER
		// System.out.println("nodeValue : "+n.getNodeValue()+n);
		// s.setBenchId(n.getAttributes().getNamedItem("benchID").getNodeValue());
		// System.out.println(n.getAttributes().getNamedItem("benchmark"));
		s.setBenchId(n.getAttributes().getNamedItem("benchID").toString());
		s.setBenchMark(n.getAttributes().getNamedItem("benchmark").toString());
		s.setCountry(n.getAttributes().getNamedItem("country").toString());
		s.setId(n.getAttributes().getNamedItem("id").toString());
		s.setIndustry(n.getAttributes().getNamedItem("industry").toString());
		s.setName(n.getAttributes().getNamedItem("name").toString());
		s.setSector(n.getAttributes().getNamedItem("sector").toString());
		s.setZone(n.getAttributes().getNamedItem("zone").toString());

		System.out.println("\nLECTURE : \nbenchID: " + s.getBenchId()
				+ "\nbenchmark: " + s.getBenchMark() + "\ncountry: "
				+ s.getCountry() + "\nid: " + s.getId() + "\nindustry: "
				+ s.getIndustry() + "\nname: " + s.getName() + "\nsector: "
				+ s.getSector() + "\nzone: " + s.getZone() + "\n");
		return s;
	}

	// */
	// VERSION JUANSHU
	/*
	 * public Stock stockXML2Node(Node n) { Element e = (Element) n; Stock s =
	 * new Stock(); //récupérer les élements de cette racine et les mettre dans
	 * Stocks //---getElement plutôt? ---
	 * s.setBenchId(e.getElementsByTagName("benchID").item(0).getTextContent());
	 * s
	 * .setBenchMark(e.getElementsByTagName("benchmark").item(0).getTextContent(
	 * ));
	 * s.setCountry(e.getElementsByTagName("country").item(0).getTextContent());
	 * s.setId(e.getElementsByTagName("id").item(0).getTextContent());
	 * s.setIndustry
	 * (e.getElementsByTagName("industry").item(0).getTextContent());
	 * s.setName(e.getElementsByTagName("name").item(0).getTextContent());
	 * s.setSector(e.getElementsByTagName("sector").item(0).getTextContent());
	 * s.setZone(e.getElementsByTagName("zone").item(0).getTextContent());
	 * return s; } //
	 */

	/**
	 * @return the startdate
	 */
	/**
	 * @return
	 */
	/**
	 * @return
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
	 * @param stockArray
	 *            the stockArray to set
	 */
	public void setStockArray(ArrayList<Stock> stockArray)
	{
		this.stockArray = stockArray;
	}

}
