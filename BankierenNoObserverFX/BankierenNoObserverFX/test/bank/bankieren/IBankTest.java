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
 * @author Mickey
 */
public class IBankTest {
    
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
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of openRekening method, of class IBank.
     */
    @Test
    public void testOpenRekening() {
        System.out.println("openRekening");
        String naam = "";
        String plaats = "";
        IBank instance = new IBankImpl();
        int expResult = 0;
        int result = instance.openRekening(naam, plaats);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of maakOver method, of class IBank.
     */
    @Test
    public void testMaakOver() throws Exception {
        System.out.println("maakOver");
        int bron = 0;
        int bestemming = 0;
        Money bedrag = null;
        IBank instance = new IBankImpl();
        boolean expResult = false;
        boolean result = instance.maakOver(bron, bestemming, bedrag);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRekening method, of class IBank.
     */
    @Test
    public void testGetRekening() {
        System.out.println("getRekening");
        int nr = 0;
        IBank instance = new IBankImpl();
        IRekening expResult = null;
        IRekening result = instance.getRekening(nr);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getName method, of class IBank.
     */
    @Test
    public void testGetName() {
        System.out.println("getName");
        IBank instance = new IBankImpl();
        String expResult = "";
        String result = instance.getName();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    public class IBankImpl implements IBank {

        public int openRekening(String naam, String plaats) {
            return 0;
        }

        public boolean maakOver(int bron, int bestemming, Money bedrag) throws NumberDoesntExistException {
            return false;
        }

        public IRekening getRekening(int nr) {
            return null;
        }

        public String getName() {
            return "";
        }
    }
    
}
