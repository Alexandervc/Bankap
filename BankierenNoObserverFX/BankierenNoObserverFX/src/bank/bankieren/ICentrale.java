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
public interface ICentrale
{
    /**
     * Voeg bank toe aan list of IBanks
     * @param bank, not null
     * @return toevoegen geslaagd
     */
    public boolean addBank(IBank bank);
    
    /**
     * Verwijder bank van list of IBanks
     * @param bank, not null
     * @return verwijderen geslaagd
     */
    public boolean removeBank(IBank bank);
    
    /**
     * Get alle banken geregistreerd bij de Centrale
     * @return list van IBanks
     */
    public ArrayList<IBank> getBanken();
    
    /**
     * Get IBank met ingegeven naam
     * @param naam, not empty
     * @return IBank met naam, null wanneer deze niet gevonden wordt
     */
    public IBank getBank(String naam);
}
