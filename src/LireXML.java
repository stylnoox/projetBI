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

/**
 * @author Vincent
 *
 */
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

		// MAJ StartDate
		Element e = (Element) doc.getDocumentElement();
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
		System.out.println("getLength() => " + stockList.getLength());
		// Récupération des stocks XML vers Objet
		for (int i = 0; i < stockList.getLength(); i++)
		{
			System.out.println(i + "->" + stockList.item(i).getNodeValue());
//			as.add(stockXML2Node(stockList.item(i)));
			stockXML2Node(stockList.item(i));
		}
		return as;

	}

	/**
	 * @param n
	 *            -> Noeud DOM correspondant à un Stock
	 * @return un objet Stock correspondant au noeud n
	 */
	//*
	public Stock stockXML2Node(Node n)
	{
		Stock s = new Stock();
		System.out.println("type :"+n.getNodeType()+"\nvalue :"+n.getNodeValue()+"\nname :"+n.getNodeName());
			// Element e = n.; // A CORRIGER
			s.setBenchId(n.getAttributes().getNamedItem("benchID").getNodeValue());
			s.setBenchMark(n.getAttributes().getNamedItem("benchmark").getNodeValue());
			s.setCountry(n.getAttributes().getNamedItem("country").getNodeValue());
			s.setId(n.getAttributes().getNamedItem("id").getNodeValue());
			s.setIndustry(n.getAttributes().getNamedItem("industry").getNodeValue());
			s.setName(n.getAttributes().getNamedItem("name").getNodeValue());
			s.setSector(n.getAttributes().getNamedItem("sector").getNodeValue());
			s.setZone(n.getAttributes().getNamedItem("zone").getNodeValue());
		
		return s;
	}
	//*/
//	VERSION JUANSHU
	/*
	public Stock stockXML2Node(Node n)
	{
		Element e = (Element) n;
		Stock s = new Stock();
		//récupérer les élements de cette racine et les mettre dans Stocks
	    //---getElement plutôt? ---
		s.setBenchId(e.getElementsByTagName("benchID").item(0).getTextContent());
		s.setBenchMark(e.getElementsByTagName("benchmark").item(0).getTextContent());
		s.setCountry(e.getElementsByTagName("country").item(0).getTextContent());
		s.setId(e.getElementsByTagName("id").item(0).getTextContent());
		s.setIndustry(e.getElementsByTagName("industry").item(0).getTextContent());
		s.setName(e.getElementsByTagName("name").item(0).getTextContent());
		s.setSector(e.getElementsByTagName("sector").item(0).getTextContent());
		s.setZone(e.getElementsByTagName("zone").item(0).getTextContent());
		return s;
	}
	//*/

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
