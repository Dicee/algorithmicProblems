package codingame.medium;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class CorrecteurOrthographique {

	public static final int	M		= 0;
	public static final int	F		= 1;
	public static final int	UNDEF	= 2;

	public static final int	S		= 0;
	public static final int	P		= 1;
	
	enum Pronoms { 
		JE   (0,S,"je"), 
		TU   (1,S,"tu"),
		IL   (2,S,"il"), 
		ELLE (2,S,"elle"),
		NOUS (3,P,"nous"),
		VOUS (4,P,"vous"),
		ILS  (5,P,"ils"),
		ELLES(5,P,"elles");
		
		public int index, nombre;
		public String text;
		
		private Pronoms(int n, int nombre, String text) {
			this.index  = n;
			this.nombre = nombre;
			this.text   = text;
		}		
	}
	
	enum Articles { 		
		LE (Pronoms.IL,M,S,"le"), 
		LA (Pronoms.IL,F,S,"la"),
		UNE(Pronoms.IL,F,S,"une"), 
		UN (Pronoms.IL,M,S,"un"),
		LES(Pronoms.ILS,UNDEF,P,"les"),
		DES(Pronoms.ILS,UNDEF,P,"des"); 
		
		public int genre;
		public int nombre;
		public String text;
		public Pronoms conjug;
		public int indexNom, indexAdj;
		
		private Articles(Pronoms conjug, int genre, int nombre, String text) {
			this.genre    = genre;
			this.nombre   = nombre;
			this.text     = text;
			this.conjug   = conjug;
			this.indexNom = nombre == S ? 1 : 2;
			this.indexAdj = genre  == M ? 
							nombre == S ? 0 : 1 : nombre == S ? 2 : 3;
		}		
	}
	
	private static String conjuguer(String mot, List<String[]> noms, int index) {
		for (String[] versions : noms) 
			for (String version : versions)
				if (version.equals(mot)) 
					return versions[index];
		return null;
	}
	
	private static Pronoms getPronom(String pronom) {
		pronom = pronom.toLowerCase();
		for (Pronoms p : Pronoms.values())
			if (p.text.equals(pronom))
				return p;
		return null;
	}

	private static Articles getArticle(String article) {
		article = article.toLowerCase();
		for (Articles a : Articles.values()) 
			if (a.text.equals(article))
				return a;
		return null;
	}
	
	private static String groupeNominal(int begin, int end, List<String[]> adj, List<String[]> noms, String[] split, Articles article) {
		//On détermine l'indice du nom dans le groupe nominal
		int indexNom  = begin - 1;
		String[] nom  = null;
		boolean found = false;
		while (!found) {
			indexNom++;
			for (String[] versions : noms) {
				for (String version : versions)
					if (found = split[indexNom].equals(version)) {
						nom = versions;
						break;	
					}
				if (found) break;
			}
		}
		//Si l'article a un genre indéfini on détermine le genre à l'aide du nom
		int genre    = article.genre == UNDEF    ? 
				       nom[0].equals("masculin") ? M : F : article.genre;
		int indexAdj = genre  == M ? 
					   article.nombre == S ? 0 : 1 : article.nombre == S ? 2 : 3;
		//On bâtit enfin le groupe nominal
		String result = "";
		for (int i=begin ; i<end ; i++)
			if (i == indexNom)
				result += conjuguer(split[i],noms,article.indexNom) + " ";
			else
				result += conjuguer(split[i],adj,indexAdj) + " ";
		return result.trim();
	}
	
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		
		//Récupération du verbe et sa conjugaison
		String[] conjugVerb = in.nextLine().split(" ");
		
		//Récupération des adjectifs
		int nAdj = in.nextInt();
		in.nextLine();
		ArrayList<String[]> adjList = new ArrayList<>();
		for (int i=0; i<nAdj; i++) 
			adjList.add(in.nextLine().split(" "));
		
		//Récupération des noms
		int nNoms = in.nextInt();
		in.nextLine();
		ArrayList<String[]> noms = new ArrayList<>();
		for (int i=0; i<nNoms; i++) 
			noms.add(in.nextLine().split(" "));
		
		//Récupération de la phrase
		String phrase  = in.nextLine();
		String[] split = phrase.split(" ");
		
		int k            = 0;
		Articles article = getArticle(split[k]);
		Pronoms conjug   = article == null ? getPronom(split[k]) : article.conjug;
		String verbe     = conjugVerb[conjug.index];
		k++;
		
		String correct   = "";
		if (article == null) 
			correct += conjug.text + " ";
		else {
			//On détermine l'indice du verbe dans la phrase
			int verbIndex    = k - 1;
			boolean found    = false;
			while (!found) {
				verbIndex++;
				for (int i=0 ; i<6 && !found ; i++)
					found = split[verbIndex].equals(conjugVerb[i]);						
			}
			correct += article.text + " ";
			correct += groupeNominal(k,verbIndex,adjList,noms,split, article) + " ";
			k        = verbIndex;
		}
		
		correct += verbe;
		k++;
		if (k < split.length - 1) {
			article  = getArticle(split[k]);
			correct += " " + article.text + " ";
			k++;
			correct += groupeNominal(k,split.length,adjList,noms,split, article) + " ";
		}
		System.out.println(correct.trim());
		in.close();
	}
}