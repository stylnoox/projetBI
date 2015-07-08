package src;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class creerXML
{

	ArrayList<Stock> as;
	Date startDate;

	public creerXML(ArrayList<Stock> as, Date startDate)
	{
		this.as = as;
		this.startDate = startDate;
		creerFichier();
	}

	private void creerFichier()
	{
		try
		{
			DocumentBuilderFactory docFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

			// root elements
			Document doc = docBuilder.newDocument();
			Element rootElement = doc.createElement("stocks");
			rootElement.setAttribute("startdate", startDate.toString());
			doc.appendChild(rootElement);

			for (Stock s : as)
			{
				Element stockNode = doc.createElement("stock");
				stockNode.setAttribute("name", s.getName());
				stockNode.setAttribute("id", s.getId());
				stockNode.setAttribute("benchmark", s.getBenchMark());
				stockNode.setAttribute("benchid", s.getBenchId());
				stockNode.setAttribute("industry", s.getIndustry());
				stockNode.setAttribute("sector", s.getSector());
				stockNode.setAttribute("zone", s.getZone());
				stockNode.setAttribute("country", s.getCountry());
				
				for (Date d : s.getHistoCours().keySet())
				{
					Element value = doc.createElement("value");
					value.appendChild(doc.createTextNode(String.valueOf(s
							.getHistoCours().get(d))));
					value.setAttribute("date", d.toString());
					stockNode.appendChild(value);
				}
				
				for (Date d : s.getHistoCoursIndex().keySet())
				{
					Element value = doc.createElement("indexValue");
					value.appendChild(doc.createTextNode(String.valueOf(s
							.getHistoCoursIndex().get(d))));
					value.setAttribute("date", d.toString());
					stockNode.appendChild(value);
				}
				
				
				CalculerCSV ccsv = s.getCcsv();
				
				ArrayList<Double> al;
				
				al = ccsv.getPerfAnnualisee();
				Element perfAnnualisee = doc.createElement("perfAnnualisee");
				Element valuePerfAnnualisee3M = doc.createElement("value");
				Element valuePerfAnnualisee6M = doc.createElement("value");
				Element valuePerfAnnualisee1Y = doc.createElement("value");
				Element valuePerfAnnualisee3Y = doc.createElement("value");
				Element valuePerfAnnualisee5Y = doc.createElement("value");
				valuePerfAnnualisee3M.setAttribute("period", "3M");
				valuePerfAnnualisee6M.setAttribute("period", "6M");
				valuePerfAnnualisee1Y.setAttribute("period", "1Y");
				valuePerfAnnualisee3Y.setAttribute("period", "3Y");
				valuePerfAnnualisee5Y.setAttribute("period", "5Y");
				valuePerfAnnualisee3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valuePerfAnnualisee6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valuePerfAnnualisee1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valuePerfAnnualisee3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valuePerfAnnualisee5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				perfAnnualisee.appendChild(valuePerfAnnualisee3M);
				perfAnnualisee.appendChild(valuePerfAnnualisee6M);
				perfAnnualisee.appendChild(valuePerfAnnualisee1Y);
				perfAnnualisee.appendChild(valuePerfAnnualisee3Y);
				perfAnnualisee.appendChild(valuePerfAnnualisee5Y);
				
				al = ccsv.getPerfRelative();
				Element perfRelative = doc.createElement("perfRelative");
				Element valuePerfRelative3M = doc.createElement("value");
				Element valuePerfRelative6M = doc.createElement("value");
				Element valuePerfRelative1Y = doc.createElement("value");
				Element valuePerfRelative3Y = doc.createElement("value");
				Element valuePerfRelative5Y = doc.createElement("value");
				valuePerfRelative3M.setAttribute("period", "3M");
				valuePerfRelative6M.setAttribute("period", "6M");
				valuePerfRelative1Y.setAttribute("period", "1Y");
				valuePerfRelative3Y.setAttribute("period", "3Y");
				valuePerfRelative5Y.setAttribute("period", "5Y");
				valuePerfRelative3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valuePerfRelative6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valuePerfRelative1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valuePerfRelative3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valuePerfRelative5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				perfRelative.appendChild(valuePerfRelative3M);
				perfRelative.appendChild(valuePerfRelative6M);
				perfRelative.appendChild(valuePerfRelative1Y);
				perfRelative.appendChild(valuePerfRelative3Y);
				perfRelative.appendChild(valuePerfRelative5Y);
								
				al = ccsv.getVolatilite();
				Element volatilite = doc.createElement("volatilite");
				Element valuevolatilite3M = doc.createElement("value");
				Element valuevolatilite6M = doc.createElement("value");
				Element valuevolatilite1Y = doc.createElement("value");
				Element valuevolatilite3Y = doc.createElement("value");
				Element valuevolatilite5Y = doc.createElement("value");
				valuevolatilite3M.setAttribute("period", "3M");
				valuevolatilite6M.setAttribute("period", "6M");
				valuevolatilite1Y.setAttribute("period", "1Y");
				valuevolatilite3Y.setAttribute("period", "3Y");
				valuevolatilite5Y.setAttribute("period", "5Y");
				valuevolatilite3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valuevolatilite6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valuevolatilite1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valuevolatilite3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valuevolatilite5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				volatilite.appendChild(valuevolatilite3M);
				volatilite.appendChild(valuevolatilite6M);
				volatilite.appendChild(valuevolatilite1Y);
				volatilite.appendChild(valuevolatilite3Y);
				volatilite.appendChild(valuevolatilite5Y);

				al = ccsv.getVolatiliteAnnualisee();
				Element volatiliteAnnualisee = doc
						.createElement("volatiliteAnnualisee");
				Element valuevolatiliteAnnualisee3M = doc.createElement("value");
				Element valuevolatiliteAnnualisee6M = doc.createElement("value");
				Element valuevolatiliteAnnualisee1Y = doc.createElement("value");
				Element valuevolatiliteAnnualisee3Y = doc.createElement("value");
				Element valuevolatiliteAnnualisee5Y = doc.createElement("value");
				valuevolatiliteAnnualisee3M.setAttribute("period", "3M");
				valuevolatiliteAnnualisee6M.setAttribute("period", "6M");
				valuevolatiliteAnnualisee1Y.setAttribute("period", "1Y");
				valuevolatiliteAnnualisee3Y.setAttribute("period", "3Y");
				valuevolatiliteAnnualisee5Y.setAttribute("period", "5Y");
				valuevolatiliteAnnualisee3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valuevolatiliteAnnualisee6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valuevolatiliteAnnualisee1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valuevolatiliteAnnualisee3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valuevolatiliteAnnualisee5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				volatiliteAnnualisee.appendChild(valuevolatiliteAnnualisee3M);
				volatiliteAnnualisee.appendChild(valuevolatiliteAnnualisee6M);
				volatiliteAnnualisee.appendChild(valuevolatiliteAnnualisee1Y);
				volatiliteAnnualisee.appendChild(valuevolatiliteAnnualisee3Y);
				volatiliteAnnualisee.appendChild(valuevolatiliteAnnualisee5Y);
				
				al = ccsv.getTrackingError();
				Element trackingError = doc.createElement("trackingError");
				Element valuetrackingError3M = doc.createElement("value");
				Element valuetrackingError6M = doc.createElement("value");
				Element valuetrackingError1Y = doc.createElement("value");
				Element valuetrackingError3Y = doc.createElement("value");
				Element valuetrackingError5Y = doc.createElement("value");
				valuetrackingError3M.setAttribute("period", "3M");
				valuetrackingError6M.setAttribute("period", "6M");
				valuetrackingError1Y.setAttribute("period", "1Y");
				valuetrackingError3Y.setAttribute("period", "3Y");
				valuetrackingError5Y.setAttribute("period", "5Y");
				valuetrackingError3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valuetrackingError6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valuetrackingError1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valuetrackingError3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valuetrackingError5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				trackingError.appendChild(valuetrackingError3M);
				trackingError.appendChild(valuetrackingError6M);
				trackingError.appendChild(valuetrackingError1Y);
				trackingError.appendChild(valuetrackingError3Y);
				trackingError.appendChild(valuetrackingError5Y);
				
				al = ccsv.getTrackingErrorAnnualise();
				Element trackingErrorAnnualise = doc
						.createElement("trackingErrorAnnualise");
				Element valuetrackingErrorAnnualise3M = doc.createElement("value");
				Element valuetrackingErrorAnnualise6M = doc.createElement("value");
				Element valuetrackingErrorAnnualise1Y = doc.createElement("value");
				Element valuetrackingErrorAnnualise3Y = doc.createElement("value");
				Element valuetrackingErrorAnnualise5Y = doc.createElement("value");
				valuetrackingErrorAnnualise3M.setAttribute("period", "3M");
				valuetrackingErrorAnnualise6M.setAttribute("period", "6M");
				valuetrackingErrorAnnualise1Y.setAttribute("period", "1Y");
				valuetrackingErrorAnnualise3Y.setAttribute("period", "3Y");
				valuetrackingErrorAnnualise5Y.setAttribute("period", "5Y");
				valuetrackingErrorAnnualise3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valuetrackingErrorAnnualise6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valuetrackingErrorAnnualise1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valuetrackingErrorAnnualise3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valuetrackingErrorAnnualise5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				trackingErrorAnnualise.appendChild(valuetrackingErrorAnnualise3M);
				trackingErrorAnnualise.appendChild(valuetrackingErrorAnnualise6M);
				trackingErrorAnnualise.appendChild(valuetrackingErrorAnnualise1Y);
				trackingErrorAnnualise.appendChild(valuetrackingErrorAnnualise3Y);
				trackingErrorAnnualise.appendChild(valuetrackingErrorAnnualise5Y);
				
				al = ccsv.getRatioInformation();
				Element ratioInformation = doc
						.createElement("ratioInformation");
				Element valueratioInformation3M = doc.createElement("value");
				Element valueratioInformation6M = doc.createElement("value");
				Element valueratioInformation1Y = doc.createElement("value");
				Element valueratioInformation3Y = doc.createElement("value");
				Element valueratioInformation5Y = doc.createElement("value");
				valueratioInformation3M.setAttribute("period", "3M");
				valueratioInformation6M.setAttribute("period", "6M");
				valueratioInformation1Y.setAttribute("period", "1Y");
				valueratioInformation3Y.setAttribute("period", "3Y");
				valueratioInformation5Y.setAttribute("period", "5Y");
				valueratioInformation3M.appendChild(doc.createTextNode(String.valueOf(al.get(0))));
				valueratioInformation6M.appendChild(doc.createTextNode(String.valueOf(al.get(1))));
				valueratioInformation1Y.appendChild(doc.createTextNode(String.valueOf(al.get(2))));
				valueratioInformation3Y.appendChild(doc.createTextNode(String.valueOf(al.get(3))));
				valueratioInformation5Y.appendChild(doc.createTextNode(String.valueOf(al.get(4))));
				ratioInformation.appendChild(valueratioInformation3M);
				ratioInformation.appendChild(valueratioInformation6M);
				ratioInformation.appendChild(valueratioInformation1Y);
				ratioInformation.appendChild(valueratioInformation3Y);
				ratioInformation.appendChild(valueratioInformation5Y);
				
				Element beta = doc.createElement("beta");
				beta.appendChild(doc.createTextNode(String.valueOf(ccsv.getBeta())));
				
				Element alpha = doc.createElement("alpha");
				alpha.appendChild(doc.createTextNode(String.valueOf(ccsv.getAlpha())));

				
				stockNode.appendChild(perfAnnualisee);
				stockNode.appendChild(perfRelative);
				stockNode.appendChild(volatilite);
				stockNode.appendChild(volatiliteAnnualisee);
				stockNode.appendChild(trackingError);
				stockNode.appendChild(trackingErrorAnnualise);
				stockNode.appendChild(ratioInformation);
				stockNode.appendChild(beta);
				stockNode.appendChild(alpha);
				

				rootElement.appendChild(stockNode);

			}
			TransformerFactory transformerFactory = TransformerFactory
					.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File("file.xml"));
			transformer.transform(source, result);

			System.out.println("File saved!");

		} catch (ParserConfigurationException pce)
		{
			pce.printStackTrace();
		} catch (TransformerException tfe)
		{
			tfe.printStackTrace();
		}
	}

}
