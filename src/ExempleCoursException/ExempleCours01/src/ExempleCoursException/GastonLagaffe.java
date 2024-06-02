package ExempleCoursException;


public class GastonLagaffe {
    public void TrierCourrierEnRetard(int nbLettres) {
        System.out.println("Quoi, " + nbLettres + " lettre(s) à trier ? ");
        try {
            System.out.println("OK, OK, je vais m'y mettre...");
            if (nbLettres > 2) {
                throw new Exception("Beaucoup trop de lettres...");
            }
            System.out.println("Ouf, j'ai fini.");
        } catch (Exception e) {
            System.out.println("M'enfin ! " + e.getMessage());
        }
        System.out.println("Après tout ce travail, une sieste s'impose.");
    }

    public void FaireSignerContrats()
    {
        try
        {
            System.out.println("Encore ces contrats ? OK, je les imprime...");
            ImprimerContrats();
            System.out.println("A présent une petite signature...");
            AjouterSignature();
            System.out.println("Fantasio, les contrats sont signés !");
        }
        catch (Exception e)
        {
            System.out.println("M'enfin ! " + e.getMessage());
        }
    }

    public void AjouterSignature()
    {

        System.out.println("Signez ici, M'sieur Demesmaeker.");
    }

    public void ImprimerContrats() throws Exception
    {
        System.out.println("D'abord, mettre en route l'imprimante.");
        AllumerImprimante();
        System.out.println("Voilà, c'est fait !");
    }

    public void AllumerImprimante() throws Exception
    {
        System.out.println("Voyons comment allumer cette machine...");
        throw new Exception("Mais qui a démonté tout l'intérieur ?");
    }

    public void RepondreAuTelephone(String appelant) throws ExceptionMenfin, ExceptionBof
    {
        if (appelant.equals("Mr. Boulier"))
        {
            throw new ExceptionMenfin("Je finis un puzzle.");
        }
        else if (appelant.equals("Prunelle"))
        {
            throw new ExceptionBof("Pas le temps, je suis dé-bor-dé !");
        }
        else
        {
            System.out.println("Allô, ici Gaston, j'écoute...");
        }
    }
}
