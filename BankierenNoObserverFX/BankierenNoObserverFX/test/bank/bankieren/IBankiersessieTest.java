/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import bank.internettoegang.Balie;
import bank.internettoegang.Bankiersessie;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.rmi.RemoteException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Melanie / Mickey
 */
public class IBankiersessieTest
{
    IBankiersessie sessie;
    IBank bank;
    IBalie balie;
    IKlant klant1;
    IKlant klant2;
    IRekening rek1;
    IRekening rek2;
    int reknr1;
    int reknr2;    
    long GELDIGHEIDSDUUR; 
    long time;
    
    @Before
    public void setUp() throws RemoteException
    {
        bank = new Bank("MAMBank");
        balie = new Balie(bank);

        klant1 = new Klant("Trixy", "Lutjebroek");
        reknr1 = bank.openRekening(klant1.getNaam(), klant1.getPlaats());
        rek1 = bank.getRekening(reknr1);
        
        klant2 = new Klant("Loesje", "Lampegat");
        reknr2 = bank.openRekening(klant2.getNaam(), klant2.getPlaats());
        rek2 = bank.getRekening(reknr2);
        
        sessie = new Bankiersessie(balie, reknr1, bank);  
        GELDIGHEIDSDUUR = 60000; 
        time = 0;
    }

    /**
     * Test Bankiersessie Constructor
     * @throws java.rmi.RemoteException wanneer er iets misgaat met de RMI, dit is niet relevant voor het testen van het systeem 
     */
    @Test
    public void testConctructor() throws RemoteException, InvalidSessionException
    {
        // juiste waarden
        assertNotNull("Bankiersessie mag niet null zijn", this.sessie);
        
        IBankiersessie sessie1;
        IBank bank1;
        int rekeningnummer;
        
        // Correct aanmaken
        bank1 = new Bank("Bank");
        rekeningnummer = 0;
        
        sessie1 = new Bankiersessie(balie, rekeningnummer, bank1);
        assertNotNull("Balie is niet aangemaakt ondanks correcte waarde", sessie1);
        
        // Negatief rekeningnummer 
        bank1 = new Bank("Bank");
        rekeningnummer = -1;
        
        try
        {
            sessie1 = new Bankiersessie(balie, rekeningnummer, bank1);
            assertNull("Bankiersessie is aangemaakt ondanks een negatief rekeningnummer", sessie1);
        }
        catch(IllegalArgumentException e)
        {
        }
        
        // Bank is null
        bank1 = null;
        rekeningnummer = 0;
        
        try
        {
            sessie1 = new Bankiersessie(balie, rekeningnummer, bank1);
            assertNull("Bankiersessie is aangemaakt ondanks een null-waarde voor bank", sessie1);   
        }
        catch(IllegalArgumentException e)
        {
        }
        
        // Kijk na of rekening klopt
        IRekening rekeningnummer1 = this.bank.getRekening(reknr1);
        assertTrue("Sessie-rekening is niet gelijk aan de opgegeven rekening", bank.getRekening(rekeningnummer1.getNr()).equals(this.sessie.getRekening()));  
    }
    
    @Test //Melanie
    public void testIsGeldig() throws RemoteException, InvalidSessionException, InterruptedException, NumberDoesntExistException
    {        
        // * @returns true als de laatste aanroep van getRekening of maakOver voor deze
        // *          sessie minder dan GELDIGHEIDSDUUR geleden is
        // *          en er geen communicatiestoornis in de tussentijd is opgetreden, 
        // *          anders false             
        
        //Direct na getRekening()
        IRekening rek = sessie.getRekening();
        boolean isgeldig = sessie.isGeldig();        
        assertTrue("Tijd na aanroep langer dan geldigheidsduur", isgeldig);
        
        //Direct na maakOver()
        Money bedrag = new Money(1200, Money.EURO);
        bank.maakOver(reknr1, reknr2, bedrag);
        isgeldig = sessie.isGeldig();        
        assertTrue("Tijd na aanroep langer dan geldigheidsduur", isgeldig);
        
        //Na wachttijd langer dan geldigheidsduur
        Thread.sleep(GELDIGHEIDSDUUR + 1000);     
        
        isgeldig = sessie.isGeldig();
        assertFalse("Tijd na aanroep korter dan geldigheidsduur", isgeldig);
    }
    
    @Test //Melanie
    public void testMaakOver() throws NumberDoesntExistException, InterruptedException, RemoteException
    {       
        // * er wordt bedrag overgemaakt van de bankrekening met het nummer bron naar
        // * de bankrekening met nummer bestemming
        
        Money bedrag = new Money(1200, Money.EURO);
        
        // * er wordt bedrag overgemaakt van de bankrekening met het nummer bron naar
        // * de bankrekening met nummer bestemming
        // * 
        // * @param bron
        // * @param bestemming
        // *            is ongelijk aan rekeningnummer van deze bankiersessie
        // * @param bedrag
        // *            is groter dan 0
        // * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
        // * @throws NumberDoesntExistException
        // *             als bestemming onbekend is
        // * @throws InvalidSessionException
        // *             als sessie niet meer geldig is 
        
        // geldige waarden        
        boolean gelukt = bank.maakOver(reknr1, reknr2, bedrag);
        assertEquals("Nieuw saldo klopt niet", -1200, rek1.getSaldo().getCents());
        assertEquals("Overmaken mislukt", true, gelukt);
        
        // dezelfde bestemming
        try
        {
            bank.maakOver(reknr1, reknr1, bedrag);
            fail("Rekeningnummers gelijk");
        }
        catch (RuntimeException ex)
        {
            System.out.println("Rekeningnummers gelijk");
        }
        
        // bedrag is 0        
        try
        {   
            bedrag = new Money(0, Money.EURO);
            gelukt = bank.maakOver(reknr1, reknr2, bedrag);
            fail("Bedrag is 0");
        }
        catch (RuntimeException ex)
        {
            System.out.println("Bedrag is 0");
        }
        
        // bedrag is null
        try
        {
            gelukt = bank.maakOver(reknr1, reknr2, null);
            fail("Bedrag is null");
        }
        catch (RuntimeException ex)
        {
            System.out.println("Bedrag is null");
        }
        
        // bedrag is kleiner dan 0
        try
        {
            bedrag = new Money(-1000, Money.EURO);
            bank.maakOver(reknr1, reknr2, bedrag);
            fail("Bedrag is kleiner dan 0");
        }
        catch (RuntimeException ex)
        {
            System.out.println("Bedrag is kleiner dan 0");
        }
     
        // onbekend rekeningnummer klant1
        bedrag = new Money(1000, Money.EURO);
        
        try
        {
            bank.maakOver(1, reknr2, bedrag);
            fail("Onjuist rekeningnummer bron");
        }
        catch (NumberDoesntExistException ex)
        {
            System.out.println("Onjuist rekeningnummer bron");
        }
        
        // onbekend rekeningnummer klant2        
        try
        {
            boolean maakOver = bank.maakOver(reknr1, 2, bedrag);
            fail("Onjuist rekeningnummer bestemming");
        }
        catch (NumberDoesntExistException ex)
        {
            System.out.println("Onjuist rekeningnummer bestemming");
        }
        catch(NullPointerException ex) {
            System.out.println(ex.toString());
        }
        
        // kredietlimiet overschreden
        int kredietlimiet = rek1.getKredietLimietInCenten();
        long saldo = rek1.getSaldo().getCents();
        long overLimiet = saldo + kredietlimiet + 100;
        bedrag = new Money(overLimiet, Money.EURO);
        
        try
        {
            bank.maakOver(reknr1, reknr2, bedrag);
            fail("Kredietlimiet overschreden");
        }
        catch (RuntimeException ex)
        {
            System.out.println("Kredietlimiet overschreden");
        }
        
        //Na wachttijd langer dan geldigheidsduur
        Thread.sleep(GELDIGHEIDSDUUR + 1000);
        
        boolean isgeldig = sessie.isGeldig();
        assertFalse("Tijd na aanroep korter dan geldigheidsduur", isgeldig);
    }
    
    @Test //Melanie
    public void testLogUit() throws InvalidSessionException, RemoteException
    {
        // * sessie wordt beeindigd
        
        //Geldige sessie
        IRekening rek = sessie.getRekening();
        boolean isgeldig = sessie.isGeldig();        
        assertTrue("Sessie niet geldig", isgeldig);  
        sessie.logUit();        
        //Niet te testen zonder RMI
    }
    
    //TODO: Mickey
    @Test
    public void push() throws RemoteException, InvalidSessionException, NumberDoesntExistException {
        /**
         * Pusht de nieuwe rekening naar de geabonneerde listeners
         * @throws RemoteException 
         */
        
        IBank bnk;
        IBalie bl;
        
        IKlant k1, k2;
        
        String acc1, acc2;
        String ww1, ww2;
        
        IBankiersessie s1, s2;
        
        Money oldk1, oldk2;
        Money newk1, newk2;
        
        int rk1, rk2;
        
        // Assign values
        
        bnk = new Bank("BankTest");
        bl = new Balie(bank);
        
        k1 = new Klant("A", "B");
        ww1 = "pass1";
        acc1 = bl.openRekening(k1.getNaam(), k1.getPlaats(), ww1);
        
        k2 = new Klant("C", "D");
        ww2 = "pass2";
        acc2 = bl.openRekening(k2.getNaam(), k2.getPlaats(), ww2);
        
        s1 = bl.logIn(acc1, ww1);
        s2 = bl.logIn(acc2, ww2);
        
        rk1 = s1.getRekening().getNr();
        rk2 = s2.getRekening().getNr();
        
        // Test new k1 with push // k1 changed, k2 changed
       
        oldk1 = s1.getRekening().getSaldo();
        oldk2 = s2.getRekening().getSaldo();
        
        s1.maakOver(rk2, new Money(10, Money.EURO));
        
        newk1 = s1.getRekening().getSaldo();
        newk2 = s2.getRekening().getSaldo();
        
        assertFalse("Het geld is niet afgeschreven van de hoofdrekening", oldk1.equals(newk1));
        assertFalse("Er is geen geld bijgeschreven bij de tegenrekening", oldk2.equals(newk2));           
    }
}
