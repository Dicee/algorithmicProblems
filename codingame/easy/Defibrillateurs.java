package codingame.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

class Defibrillateurs {

    public static class Defibrillateur {
        public double longitude, latitude;
        public String nom, adresse, tel;
        public int id;
        
        public Defibrillateur(double longitude, double latitude, String nom, String adresse, String tel, int id) {
            super();
            this.longitude = longitude;
            this.latitude = latitude;
            this.nom = nom;
            this.adresse = adresse;
            this.tel = tel;
            this.id = id;
        }
		
        public double distance(double longitude, double latitude) {
            double x = (this.longitude - longitude)*Math.cos((this.latitude + latitude)/2);
            double y = (this.latitude - latitude);
            return Math.sqrt(x*x + y*y) * 6371;
        }
    }
    
    public static double degToRad(double theta) {
    	return Math.PI/180*theta;
    }
    
    public static void main(String args[]) {
        Map<Integer,Defibrillateur> defibrillateurs = new HashMap<>();
        Scanner in = new Scanner(System.in);
        double longitude = degToRad(Double.parseDouble(in.next().replace(',','.')));
        double latitude  = degToRad(Double.parseDouble(in.next().replace(',','.')));
        
        int n = in.nextInt();
        in.nextLine();
        for (int i = 0; i < n; i++) {
            String[] split = in.nextLine().split(";");
            int id = Integer.parseInt(split[0]);
            String nom = split[1];
            String adresse = split[2];
            String tel = split[3];
            double longitudeDef = degToRad(Double.parseDouble(split[4].replace(',','.')));
            double latitudeDef  = degToRad(Double.parseDouble(split[5].replace(',','.')));
            defibrillateurs.put(id,new Defibrillateur(longitudeDef, latitudeDef, nom, adresse, tel, id));
        }
        
        double min = -1;
        int idMin = 0;
        
        for (Defibrillateur def : defibrillateurs.values()) {
            double d = def.distance(longitude, latitude);
            if (min == -1 || min > d) {
                min = d;
                idMin = def.id;
            }
        }
        System.out.println(defibrillateurs.get(idMin).nom);
        in.close();
    }
}
