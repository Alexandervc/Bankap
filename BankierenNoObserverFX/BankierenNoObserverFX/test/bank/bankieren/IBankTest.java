/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import fontys.util.NumberDoesntExistException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mickey / Melanie / Alexander 
 */
public class IBankTest {
    
    IBank bank;
    IKlant klant1;
    IKlant klant2;
    IRekening rek1;
    IRekening rek2;
    int reknr1;
    int reknr2;
    
    @Before
    public void setUp() {
        bank = new Bank("MAMBank");
        
        klant1 = new Klant("Trixy", "Lutjebroek");
        reknr1 = bank.openRekening(klant1.getNaam(), klant1.getPlaats());
        rek1 = bank.getRekening(reknr1);
        
        klant2 = new Klant("Loesje", "Lampegat");
        reknr2 = bank.openRekening(klant2.getNaam(), klant2.getPlaats());
        rek2 = bank.getRekening(reknr2);
    }
    
    /**
     * Test of constructor of Bank
     */
    @Test
    public void testConstructor() {
        // juiste waarden
        assertNotNull("Bank mag niet null zijn", this.bank);
        assertEquals("Bank naam is niet juist; expected MAMBank", "MAMBank", this.bank.getName());
        
        // null waarde voor naam
        IBank bank2 = new Bank(null);
        assertNotNull("Bank wordt toch aangemaakt", bank2);
        assertNull("Banknaam is geen null", bank2.getName());
        
        // lege string voor naam
        IBank bank3 = new Bank("");
        assertNotNull("Bank wordt toch aangemaakt", bank3);
        assertEquals("Banknaam is lege string", "", bank3.getName());
    }

    /**
     * Test of openRekening method, of class IBank.
     */
    @Test
    public void testOpenRekening() {        
        // creatie van een nieuwe bankrekening met een identificerend rekeningnummer; 
        
        int rekeningnr = this.bank.openRekening(klant1.getNaam(), klant1.getPlaats());
        IRekening rekening = this.bank.getRekening(rekeningnr);
        IKlant eigenaar = rekening.getEigenaar();
        
        assertTrue("bestaande klant, rekeningnr -1", rekeningnr != -1);
        assertNotNull("bestaande klant, rekening null", rekening);
        assertEquals("bestaande klant, naam klant fout", eigenaar.getNaam(), klant1.getNaam());
        assertEquals("bestaande klant, plaats klant fout", eigenaar.getPlaats(), klant1.getPlaats());
        
        // alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat 
        // wordt er ook een nieuwe klant aangemaakt
        
        String naam = "Trixy";
        String plaats = "Eindhoven";
        rekeningnr = this.bank.openRekening(naam, plaats);
        rekening = this.bank.getRekening(rekeningnr);
        eigenaar = rekening.getEigenaar();
        
        assertTrue("nieuwe klant, rekeningnr -1", rekeningnr != -1);
        assertNotNull("nieuwe klant, rekening null", rekening);
        assertNotNull("nieuwe klant, eigenaar null", eigenaar);
        assertEquals("nieuwe klant, naam klant fout", eigenaar.getNaam(), naam);
        assertEquals("nieuwe klant, plaats klant fout", eigenaar.getPlaats(), plaats);
        
        // @param naam
        //            van de eigenaar van de nieuwe bankrekening
        // @param plaats
        //            de woonplaats van de eigenaar van de nieuwe bankrekening        
        // @return -1 zodra naam of plaats een lege string en anders het nummer van de
        //         gecreeerde bankrekening        

        // null naam        
        plaats = "Eindhoven";
        rekeningnr = this.bank.openRekening(null, plaats);
        rekening = bank.getRekening(rekeningnr);
        
        assertEquals("naam null, rekeningnr niet -1", rekeningnr, -1);
        assertNull("naam null, rekening niet null", rekening);
        
        // empty naam
        plaats = "Eindhoven";
        rekeningnr = this.bank.openRekening("", plaats);
        rekening = bank.getRekening(rekeningnr);
        
        assertEquals("naam leeg, rekeningnr niet -1", rekeningnr, -1);
        assertNull("naam leeg, rekening niet null", rekening);
        
        // null plaats
        naam = "Trixy";
        rekeningnr = this.bank.openRekening(naam, null);
        rekening = bank.getRekening(rekeningnr);
        
        assertEquals("plaats null, rekeningnr niet -1", rekeningnr, -1);
        assertNull("plaats leeg, rekening niet null", rekening);
        
        // empty plaats
        naam = "Trixy";
        rekeningnr = this.bank.openRekening(naam, "");
        rekening = bank.getRekening(rekeningnr);
        
        assertEquals("plaats leeg, rekeningnr niet -1", rekeningnr, -1);
        assertNull("plaats leeg, rekening niet null", rekening);
    }

    /**
     * Test of maakOver method, of class IBank.
     */
    @Test
    public void testMaakOver() throws Exception {
        // er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
        // bankrekening met nummer bestemming, mits het afschrijven van het bedrag
        // van de rekening met nr bron niet lager wordt dan de kredietlimiet van deze
        // rekening         
        
        Money bedrag = new Money(1200, Money.EURO);
        
        // @param bron
        // @param bestemming
        //            ongelijk aan bron
        // @param bedrag
        //            is groter dan 0
        // @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
        // @throws NumberDoesntExistException
        //             als een van de twee bankrekeningnummers onbekend is
        
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
            bank.maakOver(reknr1, 2, bedrag);
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
    }

    /**
     * Test of getRekening method, of class IBank.
     */
    @Test
    public void testGetRekening() {
        // @param nr
        // @return de bankrekening met nummer nr mits bij deze bank bekend, anders null
        Rekening r1 = (Rekening) this.bank.getRekening(this.reknr1);
        Rekening r2 = (Rekening) this.bank.getRekening(this.reknr2);
        Rekening r3 = (Rekening) this.bank.getRekening(3);
        Rekening r4 = (Rekening) this.bank.getRekening(-1);
        
        // juiste waardes
        assertEquals("Rekening niet gelijk aan verwacht rekeningnummer", this.rek1.getNr(), r1.getNr());
        assertEquals("Rekening niet gelijk aan verwacht rekeningnummer", this.rek2.getNr(), r2.getNr());
        
        // onbekend rekeningnummer
        assertNull("Rekening not null", r3);
        
        // rekening met min waarde
        assertNull("Rekening not null", r4);
    } 
}
