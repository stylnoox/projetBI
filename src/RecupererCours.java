package src;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.io.*;

import au.com.bytecode.opencsv.CSVReader;


public class RecupererCours
{
	Map<Date, Double> cours;
	
	public RecupererCours()
	{
		this.cours = new Map<Date, Double>();
	}
	
	//récupère le prix du close de chaque jour
	public void getCours(Date startDate, Date endDate, File fcsv)
	{
		this.cours = new Map<Date, Double>();
		
		//lire et récupérer les données de fichier
		CSVReader reader = new CSVReader(new FileReader(fcsv));
		String [] nextLine;
			 while ((nextLine = reader.readNext()) != null)
			 	 {
				 String date = nextLine[1].toString();
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mi:ss");
				 Date d = sdf.parse(date);
				 
				 String text = nextLine[7].toString() ; // example String
				 double v = Double.parseDouble(text);
				 
			 	  // Afficher date et clôture ajustée de chaque ligne
				 this.cours.put(d, v);
		    }
		}
		
	 public Double getPrix(Date date){
		 Double prix = 0,0;
		 Set cles = this.cours.keySet();
		 Iterator<E> it = cles.iterator();
		 
		 while (it.hasNext()){
			Object cle = it.next(); 
		    if(cle.toString() == date.toString())
		    	prix = this.cours.get(cle);
		 }
		  
		 return prix;
		 x
	 }
	 
	 public void affichageCours(){
		 Set cles = this.cours.keySet();
		 Iterator<E> it = cles.iterator();
		 
		 while (it.hasNext()){
			 Object cle = it.next();
			 System.out.println("Date : "+ cle.toString());
			 System.out.println("Clôture ajustée : "+ this.cours.get(cle).toString());
		 }
	 }
	 
	 //un nouveau fichier pour le calcul?
}
