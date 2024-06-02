package ExempleCoursException;

public class Main{

    public static void main(String[] args) throws Exception {

          GastonLagaffe gaston = new GastonLagaffe();
          System.out.println("Debout Gaston ! Il faut trier le courrier !");
          gaston.TrierCourrierEnRetard(2);

        //GastonLagaffe gaston = new GastonLagaffe();
        //System.out.println("Gaston, Mr, Demesmaeker arrive, faites vite !");
        //gaston.FaireSignerContrats();


        //GastonLagaffe gaston = new GastonLagaffe();
        //GererAppel(gaston, "Mr. Boulier");
        //GererAppel(gaston, "Prunelle");
        //GererAppel(gaston, "Jules-de-chez-Smith");

        }

    private static void GererAppel(GastonLagaffe gaston, String appelant)
    {
        System.out.println("Gaston, " + appelant + " au téléphone !");
        try
        {
            gaston.RepondreAuTelephone(appelant);
        }
        catch (ExceptionMenfin e)
        {
            System.out.println("Pas de réponse... Et pourquoi ?");
            System.out.println(e.getMessage());
        }
        catch (ExceptionBof e)
        {
            System.out.println("Ca sonne toujours... vous dormez ou quoi ?");
            System.out.println(e.getMessage());
        }

    }

}
