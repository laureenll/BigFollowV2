package miage.fr.gestionprojet.outils;


import java.util.Calendar;
import java.util.Date;

/**
 * Created by Audrey on 27/02/2017.
 */

public class Outils {
    private static final long CONST_DURATION_OF_DAY = 1000L * 60 * 60 * 24;

    private Outils() {
    }

    public static int calculerPourcentage(double valeurReleve, double valeurCible){
        return (int) ((valeurReleve/valeurCible)*100);
    }

    public static Date weekOfYearToDate(int year, int week){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.WEEK_OF_YEAR,week);
        c.set(Calendar.YEAR,year);
        return c.getTime();
    }

    public static long dureeEntreDeuxDate(Date dateInf, Date datePost){
        long duree = 1;
        if (dateInf != null && datePost != null) {
            duree = datePost.getTime() - dateInf.getTime();
        }
        return duree/CONST_DURATION_OF_DAY;
    }
}
