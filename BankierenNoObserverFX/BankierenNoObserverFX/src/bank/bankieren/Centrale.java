/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import java.util.ArrayList;

/**
 *
 * @author Melanie
 */
public class Centrale implements ICentrale
{
    ArrayList<IBank> banken;
    
    public Centrale()
    {
        banken = new ArrayList<>();
    }    
    
    @Override
    public boolean addBank(IBank bank)
    {
        if (bank != null)
        {
            return banken.add(bank);
        }
        
        return false;
    }
    
    @Override
    public boolean removeBank(IBank bank)
    {
        if (bank != null)
        {
            return banken.remove(bank);
        }
        
        return false;
    }
    
    @Override
    public ArrayList<IBank> getBanken()
    {
        return this.banken;
    }

    @Override
    public IBank getBank(String naam)
    {
        IBank bank = null;
        
        if (naam != "" && naam != null)
        {
            for (IBank b : banken)
            {
                if (b.getName().toLowerCase().equals(naam.toLowerCase()))
                {
                    bank = b;
                }
            }
        }
        
        return bank;
    }     
}
