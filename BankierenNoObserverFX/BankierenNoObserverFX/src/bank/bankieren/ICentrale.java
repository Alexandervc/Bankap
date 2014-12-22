/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package bank.bankieren;

import bank.internettoegang.IBalie;
import java.util.ArrayList;

/**
 *
 * @author Melanie / Alexander
 */
public interface ICentrale
{    
    /**
     * Voeg balie toe aan list of IBalies
     * @param balie, not null, naam bank bestaat nog niet
     * @return toevoegen geslaagd
     */
    public boolean addBalie(IBalie balie);
    
    /**
     * Verwijder balie van list of IBalies
     * @param balie, not null
     * @return verwijderen geslaagd
     */
    public boolean removeBalie(IBalie balie);
    
    /**
     * Get alle banken geregistreerd bij de Centrale
     * @return list van IBank
     */
    public ArrayList<IBank> getBanken();
    
    /**
     * Get IBank met ingegeven naam
     * @param naam, not empty
     * @return IBank met naam, null wanneer deze niet gevonden wordt
     */
    public IBank getBank(String naam);
    
    /**
     * Get IBank met ingegeven rekeningnr
     * @param rekeningnr > 0
     * @return IBank die rekeningnr bevat, null wanneer deze niet gevonden wordt
     */
    public IBank getBank(int rekeningnr);
    
    /**
     * Get IBalie met ingegeven rekeningnr
     * @param rekeningnr > 0
     * @return IBalie die rekeningnr bevat, null wanneer deze niet gevonden wordt
     */
    public IBalie getBalie(int rekeningnr);
    
    /**
     * Get volgend nieuw rekeningnr
     * @return nieuwe rekeningnr
     */
    public int getNextRekeningNr();
}
