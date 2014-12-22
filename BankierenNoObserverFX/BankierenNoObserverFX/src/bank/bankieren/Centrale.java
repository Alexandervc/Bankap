/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import bank.internettoegang.Balie;
import bank.internettoegang.IBalie;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Melanie / Alexander
 */
public class Centrale implements ICentrale
{
    private static Centrale instance = null;
    
    private ArrayList<Balie> balies;
    private int nieuwReknr;
    
    private Centrale()
    {
        balies = new ArrayList<>();
        nieuwReknr = 100000000;
    } 
    
    public static Centrale getInstance() 
    {
        if(instance == null) 
        {
            instance = new Centrale();
        }
        return instance;
    }
    
    /**
     * Voeg balie toe aan list of IBalies
     * @param balie, not null, naam bank bestaat nog niet
     * @return toevoegen geslaagd
     */
    @Override
    public boolean addBalie(IBalie balie)
    {
        if (balie == null )
        {
            throw new IllegalArgumentException("balie is null");
        }
        Balie b = (Balie) balie;
        
        if (this.getBank(b.getBank().getName()) != null) 
        {
            throw new IllegalArgumentException("banknaam bestaat al");
        }
        
        return balies.add(b);
    }
    
    /**
     * Verwijder balie van list of IBalies
     * @param balie, not null
     * @return verwijderen geslaagd
     */
    @Override
    public boolean removeBalie(IBalie balie)
    {
        if (balie == null)
        {
            throw new IllegalArgumentException("balie is null");
        }
        
        Balie b = (Balie) balie;
        Balie bToRemove = this.getBalie(b.getBank().getName());
        
        return balies.remove(bToRemove);
    }
    
    /**
     * Get alle banken geregistreerd bij de Centrale
     * @return list van IBank
     */
    @Override
    public ArrayList<IBank> getBanken()
    {
        ArrayList<IBank> banken = new ArrayList<>();
        for(Balie b : this.balies) 
        {
            banken.add(b.getBank());
        }
        return banken;
    }
    
    /**
     * Get IBank met ingegeven naam
     * @param naam, not empty
     * @return IBank met naam, null wanneer deze niet gevonden wordt
     */
    @Override
    public IBank getBank(String naam)
    {
        IBank bank = null;
        
        if (naam == null || naam.isEmpty()) 
        {
            throw new IllegalArgumentException("naam empty or null");
        }
        
        for(Balie b : this.balies) 
        {
            if(b.getBank().getName().toLowerCase().equals(naam.toLowerCase())) 
            {
                bank = b.getBank();
            }
        }
        
        return bank;
    }
    
    /**
     * Get IBank met ingegeven rekeningnr
     * @param rekeningnr > 0
     * @return IBank die rekeningnr bevat, null wanneer deze niet gevonden wordt
     */
    @Override
    public IBank getBank(int rekeningnr)
    {
        IBank bank = null;
        
        if (rekeningnr <= 0)
        {
            throw new IllegalArgumentException("rekeningnr niet geldig");
        }
        
        for(Balie b : this.balies)
        {
            if (b.getBank().getRekening(rekeningnr) != null)
            {
                bank = b.getBank();
            }
        }
        
        return bank;
    }
    
    /**
     * Get IBalie met ingegeven rekeningnr
     * @param rekeningnr > 0
     * @return IBalie die rekeningnr bevat, null wanneer deze niet gevonden wordt
     */
    @Override
    public IBalie getBalie(int rekeningnr)
    {
        IBalie balie = null;
        
        if (rekeningnr <= 0)
        {
            throw new IllegalArgumentException("rekeningnr niet geldig");
        }
        
        for(Balie b : this.balies)
        {
            if (b.getBank().getRekening(rekeningnr) != null)
            {
                balie = (IBalie) b;
            }
        }
        
        return balie;
    }
    
    private Balie getBalie(String naam) {
        Balie balie = null;
        
        if (naam == null || naam.isEmpty()) 
        {
            throw new IllegalArgumentException("naam empty or null");
        }
        
        for(Balie b : this.balies) 
        {
            if(b.getBank().getName().toLowerCase().equals(naam.toLowerCase())) 
            {
                balie = b;
            }
        }
        
        return balie;
    }
    
    /**
     * Get volgend nieuw rekeningnr
     * @return nieuwe rekeningnr
     */
    @Override
    public synchronized int getNextRekeningNr()
    {
        int rekeningnr = nieuwReknr;
        nieuwReknr++;
        return rekeningnr;
    }
}
