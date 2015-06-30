package src;

import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.io.*;
import jxl.*; 


public class RecupererCours
{
	Map<Date, Double> cours;
	
	public RecupererCours()
	{
		this.cours = new Map<Date, Double>();
	}
	
	//r�cup�re le prix du close de chaque jour
	public void getCours(Date startDate, Date endDate, File fcsv)
	{
		this.cours = new Map<Date, Double>();
		
		//lire et r�cup�rer les donn�es de fichier
		CSVReader reader = new CSVReader(new FileReader(fcsv));
		String [] nextLine;
			 while ((nextLine = reader.readNext()) != null)
			 	 {
				 String date = nextLine[1].toString();
				 SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy hh:mi:ss");
				 Date d = sdf.parse(date);
				 
				 String text = nextLine[7].toString() ; // example String
				 double v = Double.parseDouble(text);
				 
			 	  // Afficher date et cl�ture ajust�e de chaque ligne
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
		 
	 }
	 
	 public void affichageCours(){
		 Set cles = this.cours.keySet();
		 Iterator<E> it = cles.iterator();
		 
		 while (it.hasNext()){
			 Object cle = it.next();
			 System.out.println("Date : "+ cle.toString());
			 System.out.println("Cl�ture ajust�e : "+ this.cours.get(cle).toString());
		 }
	 }
 
	 //output in excel? Chemin d�termin�
	 public void export(){
		 try { 
			 WritableWorkbook workbook = Workbook.createWorkbook(new File("sortie.xls")); 
			 WritableSheet sheet = workbook.createSheet("Cours R�cup�r�s", 0); 
			 
			 //Cr�e le format d�une cellule 
			 WritableFont arial10font = new WritableFont(WritableFont.ARIAL, 20,WritableFont.BOLD, true, UnderlineStyle.NO_UNDERLINE,Colour.BLUE, ScriptStyle.NORMAL_SCRIPT); 
			 WritableCellFormat arial10format = new WritableCellFormat(arial10font);
			 
			 //Cr�e un label � la ligne 0, colonne 0 avec le format sp�cifique 
			 Label label = new Label(0, 0, "Date",arial10format); 
			 //Cr�e un label � la ligne 0, colonne 1 sans style pr�d�fini 
			 Label label2 = new Label(1, 0, "Prix R�cup�r�"); 
			 //Ajout des cellules 
			 sheet.addCell(label); 
			 sheet.addCell(label2); 
			 
			 //r�cup�rer des donn�es � partir de ligne 1
			 int ligne = 1; int colonne = 0;
			 Set cles = this.cours.keySet();
			 Iterator<E> it = cles.iterator();
			 
			 while (it.hasNext()){
				 Object cle = it.next();
				 Label date = new Label(ligne, colonne, cle.toString());
				 Label prix = new Label(ligne, colonne,this.cours.get(cle).toString());
				 ligne ++;
				 colonne ++;
				 
				 sheet.addCell(date);
				 sheet.addCell(prix);
			 }
			
			 //Ecriture et fermeture du classeur 
			 workbook.write(); 
			 workbook.close(); 
			 }
		 catch (RowsExceededException e1) { 
			 e1.printStackTrace(); 
			 }
		 catch (WriteException e1) { 
			 e1.printStackTrace(); 
			 } 
		 catch (IOException e) { 
			 e.printStackTrace(); 
			 }
		 finally{ 
			 System.out.println("Le fichier \"sortie.xls\" � �t� g�n�r� correctement."); 
			 } 
	 }
	 //un nouveau fichier pour le calcul et tableau dynamique?!
}
