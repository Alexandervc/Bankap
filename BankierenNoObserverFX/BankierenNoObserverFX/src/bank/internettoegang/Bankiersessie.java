package bank.internettoegang;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import bank.bankieren.IBank;
import bank.bankieren.IRekening;
import bank.bankieren.Money;
import fontys.observer.BasicPublisher;
import fontys.observer.RemotePropertyListener;

import fontys.util.InvalidSessionException;
import fontys.util.NumberDoesntExistException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Bankiersessie extends UnicastRemoteObject implements
		IBankiersessie {
        private static final String propertyname = "rekening";
	private static final long serialVersionUID = 1L;
	private long laatsteAanroep;
	private int reknr;
	private IBank bank;
        private BasicPublisher bp;
        private IBalie balie;

	public Bankiersessie(IBalie balie, int reknr, IBank bank) throws RemoteException {
                this.balie = balie;
		laatsteAanroep = System.currentTimeMillis();
                
                if(reknr < 0 || bank == null) throw new IllegalArgumentException();
                
		this.reknr = reknr;
		this.bank = bank;
                this.bp = new BasicPublisher(new String[] { propertyname });
		
	}

	public boolean isGeldig() {
		return System.currentTimeMillis() - laatsteAanroep < GELDIGHEIDSDUUR;
	}

	@Override
	public boolean maakOver(int bestemming, Money bedrag)
			throws NumberDoesntExistException, InvalidSessionException,
			RemoteException {
		
		updateLaatsteAanroep();
		
		if (reknr == bestemming)
			throw new RuntimeException(
					"source and destination must be different");
		if (!bedrag.isPositive())
			throw new RuntimeException("amount must be positive");
		
                boolean geslaagd = bank.maakOver(reknr, bestemming, bedrag);
                if(geslaagd) {
                    // inform overmaker
                    this.push();
                    // inform bestemming
                    IBankiersessie b = this.balie.getBankiersessie(bestemming);
                    if(b != null) {
                        b.push();
                    }
                }
                return geslaagd;
	}

	private void updateLaatsteAanroep() throws InvalidSessionException {
		if (!isGeldig()) {
			throw new InvalidSessionException("session has been expired");
		}
		
		laatsteAanroep = System.currentTimeMillis();
	}

	@Override
	public IRekening getRekening() throws InvalidSessionException,
			RemoteException {

		updateLaatsteAanroep();
		
		return bank.getRekening(reknr);
	}

	@Override
	public void logUit() throws RemoteException {
		UnicastRemoteObject.unexportObject(this, true);
	}

    @Override
    public void addListener(RemotePropertyListener rl, String string) throws RemoteException {
        this.bp.addListener(rl, string);
    }

    @Override
    public void removeListener(RemotePropertyListener rl, String string) throws RemoteException {
        this.bp.removeListener(rl, string);
    }

    @Override
    public void push() throws RemoteException {
        try {
            if(!this.isGeldig()) {
                this.bp.inform(this, propertyname, null, null);
            }
            this.bp.inform(this, propertyname, null, getRekening());
        } catch (InvalidSessionException ex) {
            ex.printStackTrace();
        }
    }

}
