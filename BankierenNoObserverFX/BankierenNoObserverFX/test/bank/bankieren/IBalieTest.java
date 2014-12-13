/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import bank.internettoegang.IBankiersessie;
import fontys.util.InvalidSessionException;
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
    String ww1;
    String acc2;
    String ww2;
    
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
        ww1 = "secret";
        acc1 = balie.openRekening(klant1.getNaam(), klant1.getPlaats(), ww1);
        
        klant2 = new Klant("Loesje", "Lampegat");
        ww2 = "ookiets";
        acc2 = balie.openRekening(klant2.getNaam(), klant2.getPlaats(), ww2);
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
    public void testLogin() throws RemoteException, InvalidSessionException
    {
        String acc;
        String ww;
        IBankiersessie sessie;
        IKlant eigenaar;
//   * er wordt een sessie opgestart voor het login-account met de naam
//   * accountnaam mits het wachtwoord correct is
//   * @return de gegenereerde sessie waarbinnen de gebruiker 
//   * toegang krijgt tot de bankrekening die hoort bij het betreffende login-
//   * account mits accountnaam en wachtwoord matchen, anders null
        // wachtwoord correct
        acc = acc1;
        ww = ww1;
        
        sessie = balie.logIn(acc, ww);
        eigenaar = sessie.getRekening().getEigenaar();
        
        // met de naam accountnaam?
        assertNotNull("ww correct, sessie null", sessie);
        assertEquals("ww correct, eigenaar naam incorrect", eigenaar.getNaam(), klant1.getNaam());
        assertEquals("ww correct, eigenaar plaats incorrect", eigenaar.getPlaats(), klant1.getPlaats());
        
        // wachtwoord niet correct
        acc = acc1;
        ww = ww2;
        
        sessie = balie.logIn(acc, ww);
        
        assertNull("ww incorrect, sessie not null", sessie);
        
        // andere inlognaam
        acc = acc2;
        ww = ww1;
        
        sessie = balie.logIn(acc, ww);
        
        assertNull("andere inlognaam, sessie not null", sessie);
        
        // niet bestaande inlognaam
        acc = "bestaatNiet";
        ww = ww1;
        
        sessie = balie.logIn(acc, ww);
        
        assertNull("niet bestaande inlognaam, sessie not null", sessie);
        
//   * @param accountnaam
//   * @param wachtwoord
        // accountnaam null
        ww = ww1;
        
        sessie = balie.logIn(null, ww);
        
        assertNull("acc null, sessie not null", sessie);
        
        // accountnaam empty
        ww = ww1;
        
        sessie = balie.logIn("", ww);
        
        assertNull("acc empty, sessie not null", sessie);
        
        // wachtwoord null
        acc = acc1;
        
        sessie = balie.logIn(acc, null);
        
        assertNull("ww null, sessie not null", sessie);
        
        // wachtwoord empty
        acc = acc1;
        
        sessie = balie.logIn(acc, "");
        
        assertNull("ww empty, sessie not null", sessie);
    }
}
