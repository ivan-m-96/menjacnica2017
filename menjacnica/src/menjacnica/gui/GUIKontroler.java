package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.Valuta;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	
	private static MenjacnicaGUI menjacnicaGui;
	private static MenjacnicaInterface sistem;
	private static ObrisiKursGUI obrisiKursGui;
	private static DodajKursGUI dodajKursGui;
	private static IzvrsiZamenuGUI izvrsiZamenuGui;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				sistem = new Menjacnica();
				menjacnicaGui = new MenjacnicaGUI();
				menjacnicaGui.setVisible(true);
				menjacnicaGui.setLocationRelativeTo(null);
				menjacnicaGui.addWindowListener(new WindowAdapter() {
					@Override
					public void windowClosing(WindowEvent e) {
						ugasiAplikaciju();
						
					}

					
					
				});
				
				
			}
		});
		
	}
	public static void prikaziIzvrsiZamenuGUI() {
		
		
		if (menjacnicaGui.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(menjacnicaGui.getTable().getModel());
			izvrsiZamenuGui = new IzvrsiZamenuGUI(
					model.vratiValutu(menjacnicaGui.getTable().getSelectedRow()));
			izvrsiZamenuGui.setLocationRelativeTo(menjacnicaGui);
			izvrsiZamenuGui.setVisible(true);
		}
	}
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	public static void prikaziDodajKursGUI() {
		dodajKursGui = new DodajKursGUI();
		dodajKursGui.setLocationRelativeTo(menjacnicaGui.getContentPane());
		dodajKursGui.setVisible(true);
	}
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGui.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				sistem.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
	public static void ucitajIzFajla() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(menjacnicaGui.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				sistem.ucitajIzFajla(file.getAbsolutePath());
				menjacnicaGui.prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	

	public static void prikaziObrisiKursGUI() {
		
		if (menjacnicaGui.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(menjacnicaGui.getTable().getModel());
			obrisiKursGui = new ObrisiKursGUI(
					model.vratiValutu(menjacnicaGui.getTable().getSelectedRow()));
			obrisiKursGui.setLocationRelativeTo(menjacnicaGui.getContentPane());
			obrisiKursGui.setVisible(true);
		}
	}
	public static void dodajValutu(int sifra, String naziv, String skraceniNaziv, double prodajni, double kupovni, double srednji) {
		try{
		Valuta valuta = new Valuta();
		valuta.setNaziv(naziv);
		valuta.setSkraceniNaziv(skraceniNaziv);
		valuta.setSifra(sifra);
		valuta.setProdajni(prodajni);
		valuta.setKupovni(kupovni);
		valuta.setSrednji(srednji);
		sistem.dodajValutu(valuta);
		menjacnicaGui.prikaziSveValute();
		dodajKursGui.dispose();
		} catch (Exception e1) {
		JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(), e1.getMessage(),
				"Greska", JOptionPane.ERROR_MESSAGE);
	}
	}
	public static LinkedList<Valuta> vratiKursnuListu() {
		return sistem.vratiKursnuListu();
	}
	public static void obrisiValutu(Valuta valuta) {
		try{
			sistem.obrisiValutu(valuta);
			menjacnicaGui.prikaziSveValute();
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	public static double izvrsiZamenu(Valuta valuta, boolean selected, String textFieldIznos) {
		return sistem.izvrsiTransakciju(valuta,
				selected, 
				Double.parseDouble(textFieldIznos));

	}
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGui,
				"Da li ZAISTA zelite da izadjete iz aplikacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
}
