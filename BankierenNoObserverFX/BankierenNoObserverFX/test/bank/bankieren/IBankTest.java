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
    Rekening rek1;
    Rekening rek2;
    
    public IBankTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        IBank bank = new Bank("MAMBank");
        
        IKlant klant1 = new Klant("Trixy", "Lutjebroek");
        Money mon1 = new Money(50000, "euro");
        Rekening rek1 = new Rekening(1, klant1, mon1);
        
        IKlant klant2 = new Klant("Loesje", "Lampegat");
        Money mon2 = new Money(120000, "euro");
        Rekening rek2 = new Rekening(2, klant2, mon2);
    }
    
    @After
    public void tearDown() {
    }
    
    /**
     * Test of constructor of Bank
     */
    @Test
    public void testConstructor() {
        
    }


    /**
     * Test of openRekening method, of class IBank.
     */
    @Test
    public void testOpenRekening() {
//     * creatie van een nieuwe bankrekening met een identificerend rekeningnummer; 
//     * alleen als de klant, geidentificeerd door naam en plaats, nog niet bestaat 
//     * wordt er ook een nieuwe klant aangemaakt
//     * 
//     * @param naam
//     *            van de eigenaar van de nieuwe bankrekening
//     * @param plaats
//     *            de woonplaats van de eigenaar van de nieuwe bankrekening
//     * @return -1 zodra naam of plaats een lege string en anders het nummer van de
//     *         gecreeerde bankrekening
    }

    /**
     * Test of maakOver method, of class IBank.
     */
    @Test
    public void testMaakOver() throws Exception {
//     * er wordt bedrag overgemaakt van de bankrekening met nummer bron naar de
//     * bankrekening met nummer bestemming, mits het afschrijven van het bedrag
//     * van de rekening met nr bron niet lager wordt dan de kredietlimiet van deze
//     * rekening 
//     * 
//     * @param bron
//     * @param bestemming
//     *            ongelijk aan bron
//     * @param bedrag
//     *            is groter dan 0
//     * @return <b>true</b> als de overmaking is gelukt, anders <b>false</b>
//     * @throws NumberDoesntExistException
//     *             als een van de twee bankrekeningnummers onbekend is
    }

    /**
     * Test of getRekening method, of class IBank.
     */
    @Test
    public void testGetRekening() {
//     * @param nr
//     * @return de bankrekening met nummer nr mits bij deze bank bekend, anders null
    } 
}
