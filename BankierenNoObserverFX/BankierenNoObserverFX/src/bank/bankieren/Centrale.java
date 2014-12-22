/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import java.util.ArrayList;

/**
 *
 * @author Melanie / Alexander
 */
public class Centrale implements ICentrale
{
    private static Centrale instance = null;
    
    private ArrayList<IBank> banken;
    private int nieuwReknr;
    
    private Centrale()
    {
        banken = new ArrayList<>();
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
     * Voeg bank toe aan list of IBanks
     * @param bank, not null, naam bestaat nog niet
     * @return toevoegen geslaagd
     */
    @Override
    public boolean addBank(IBank bank)
    {
        if (bank == null )
        {
            throw new IllegalArgumentException("bank is null");
        }
        
        if (this.getBank(bank.getName()) != null) 
        {
            throw new IllegalArgumentException("banknaam bestaat al");
        }
        
        return banken.add(bank);
    }
    
    /**
     * Verwijder bank van list of IBanks
     * @param bank, not null
     * @return verwijderen geslaagd
     */
    @Override
    public boolean removeBank(IBank bank)
    {
        if (bank == null)
        {
            throw new IllegalArgumentException("bank is null");
        }
        
        IBank b = this.getBank(bank.getName());
        
        return banken.remove(b);
    }
    
    /**
     * Get alle banken geregistreerd bij de Centrale
     * @return list van IBanks
     */    
    @Override
    public ArrayList<IBank> getBanken()
    {
        return this.banken;
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
        
        for(IBank b : this.banken) 
        {
            if(b.getName().toLowerCase().equals(naam.toLowerCase())) 
            {
                bank = b;
            }
        }
        
        return bank;
    }     

    @Override
    public IBank getBank(int rekeningnr)
    {
        IBank bank = null;
        
        if (rekeningnr <= 0)
        {
            throw new IllegalArgumentException("rekeningnr niet geldig");
        }
        
        for(IBank b : this.banken)
        {
            if (b.getRekening(rekeningnr) != null)
            {
                bank = b;
            }
        }
        
        return bank;
    }

    @Override
    public synchronized int getNextRekeningNr()
    {
        int rekeningnr = nieuwReknr;
        nieuwReknr++;
        return rekeningnr;
    }
}
