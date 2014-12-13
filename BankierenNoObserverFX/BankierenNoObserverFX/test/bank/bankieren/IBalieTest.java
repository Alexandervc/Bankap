/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import java.rmi.RemoteException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Mickey / Alexander
 */
public class IBalieTest
{
    IBank bank;
    IBalie balie;
    IKlant klant1;
    IKlant klant2;
    String acc1;
    String acc2;
    
    public IBalieTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws RemoteException {
        bank = new Bank("MAMBank");
        this.balie = new Balie(bank);
        
        klant1 = new Klant("Trixy", "Lutjebroek");
        acc1 = balie.openRekening(klant1.getNaam(), klant1.getPlaats(), "secret");
        
        klant2 = new Klant("Loesje", "Lampegat");
        acc2 = balie.openRekening(klant2.getNaam(), klant2.getPlaats(), "ookiets");
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testConstructor()
    {
        //todo Mickey
    }
    
    
    // Alexander
    @Test
    public void testOpenRekening() throws RemoteException
    {
        String naam;
        String plaats;
        String wachtwoord;
        String acc;
//   * creatie van een nieuwe bankrekening; het gegenereerde bankrekeningnummer is
//   * identificerend voor de nieuwe bankrekening en heeft een saldo van 0 euro
//   * @return en anders de gegenereerde 
//   * accountnaam(8 karakters lang) waarmee er toegang tot de nieuwe bankrekening
//   * kan worden verkregen
        naam = "Trixy";
        plaats = "Lutjebroek";
        wachtwoord = "geheim";
        
        acc = balie.openRekening(naam, plaats, wachtwoord);
        
        // Rekening en reknr en saldo niet op te halen?
        assertNotNull("normaal, acc null", acc);
        assertEquals("normaal, acc niet 8 karakters", acc.length(), 8);
        
//   * @param naam van de eigenaar van de nieuwe bankrekening
//   * @param plaats de woonplaats van de eigenaar van de nieuwe bankrekening
//   * @param wachtwoord van het account waarmee er toegang kan worden verkregen 
//   * tot de nieuwe bankrekening
//   * @return null zodra naam of plaats een lege string of wachtwoord minder dan 
//   * vier of meer dan acht karakters lang is 
        // null naam
        plaats = "Eindhoven";
        wachtwoord = "geheim";
        
        acc = balie.openRekening(null, plaats, wachtwoord);
        
        assertNull("null naam, acc not null", acc);
        
        // empty naam
        plaats = "Eindhoven";
        wachtwoord = "geheim";
        
        acc = balie.openRekening("", plaats, wachtwoord);
        
        assertNull("empty naam, acc not null", acc);
        
        // null plaats
        naam = "Tineke";
        wachtwoord = "geheim";
        
        acc = balie.openRekening(naam, null, wachtwoord);
        
        assertNull("null plaats, acc not null", acc);
        
        // empty plaats
        naam = "Tineke";
        wachtwoord = "geheim";
        
        acc = balie.openRekening(naam, "", wachtwoord);
        
        assertNull("empty plaats, acc not null", acc);
        
        // null wachtwoord
        naam = "Tineke";
        plaats = "Eindhoven";
        
        acc = balie.openRekening(naam, plaats, null);
        
        assertNull("null wachtwoord, acc not null", acc);
        
        // empty wachtwoord
        naam = "Tineke";
        plaats = "Eindhoven";
        
        acc = balie.openRekening(naam, plaats, "");
        
        assertNull("empty wachtwoord, acc not null", acc);
        
        // wachtwoord < 4
        naam = "Tineke";
        plaats = "Eindhoven";
        wachtwoord = "geh";
        
        acc = balie.openRekening(naam, plaats, wachtwoord);
        
        assertNull("wachtwoord < 4, acc not null", acc);
        
        // wachtwoord 4
        naam = "Tineke";
        plaats = "Eindhoven";
        wachtwoord = "heim";
        
        acc = balie.openRekening(naam, plaats, wachtwoord);
        
        // Rekening en reknr en saldo niet op te halen?
        assertNotNull("wachtwoord 4, acc null", acc);
        assertEquals("wachtwoord 4, acc niet 8 karakters", acc.length(), 8);
        
        // wachtwoord > 8
        naam = "Tineke";
        plaats = "Eindhoven";
        wachtwoord = "geheimenVanDeDerdePlank";
        
        acc = balie.openRekening(naam, plaats, wachtwoord);
        
        assertNull("wachtwoord > 8, acc not null", acc);
        
        // wachtwoord 8
        naam = "Tineke";
        plaats = "Eindhoven";
        wachtwoord = "geheimen";
        
        acc = balie.openRekening(naam, plaats, wachtwoord);
        
        // Rekening en reknr en saldo niet op te halen?
        assertNotNull("wachtwoord 8, acc null", acc);
        assertEquals("wachtwoord 8, acc niet 8 karakters", acc.length(), 8);
    }            
    
    
    // Alexander
    @Test
    public void testLogin()
    {
//   * er wordt een sessie opgestart voor het login-account met de naam
//   * accountnaam mits het wachtwoord correct is
//   * @param accountnaam
//   * @param wachtwoord
//   * @return de gegenereerde sessie waarbinnen de gebruiker 
//   * toegang krijgt tot de bankrekening die hoort bij het betreffende login-
//   * account mits accountnaam en wachtwoord matchen, anders null
    }
}
