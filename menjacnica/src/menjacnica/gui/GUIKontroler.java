package menjacnica.gui;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.MenjacnicaInterface;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {
	
	private static MenjacnicaGUI menjacnicaGui;
	private static MenjacnicaInterface menjacnica;
	private static ObrisiKursGUI obrisiKursGui;
	private static DodajKursGUI dodajKursGui;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				menjacnica = new Menjacnica();
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
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(menjacnicaGui,
					model.vratiValutu(menjacnicaGui.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(menjacnicaGui);
			prozor.setVisible(true);
		}
	}
	public static void prikaziAboutProzor(){
		JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(),
				"Autor: Bojan Tomic, Verzija 1.0", "O programu Menjacnica",
				JOptionPane.INFORMATION_MESSAGE);
	}
	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI(menjacnicaGui);
		prozor.setLocationRelativeTo(menjacnicaGui.getContentPane());
		prozor.setVisible(true);
	}
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(menjacnicaGui.getContentPane());

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();

				menjacnicaGui.sistem.sacuvajUFajl(file.getAbsolutePath());
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
				menjacnicaGui.sistem.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute();
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(menjacnicaGui.getContentPane(), e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	protected static void prikaziSveValute() {

		MenjacnicaTableModel model = (MenjacnicaTableModel)(menjacnicaGui.getTable().getModel());
		model.staviSveValuteUModel(menjacnicaGui.sistem.vratiKursnuListu());

	}

	public static void prikaziObrisiKursGUI() {
		
		if (menjacnicaGui.getTable().getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(menjacnicaGui.getTable().getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(menjacnicaGui,
					model.vratiValutu(menjacnicaGui.getTable().getSelectedRow()));
			prozor.setLocationRelativeTo(menjacnicaGui.getContentPane());
			prozor.setVisible(true);
		}
	}
	
	public static void ugasiAplikaciju() {
		int opcija = JOptionPane.showConfirmDialog(menjacnicaGui,
				"Da li ZAISTA zelite da izadjete iz aplikacije", "Izlazak",
				JOptionPane.YES_NO_OPTION);

		if (opcija == JOptionPane.YES_OPTION)
			System.exit(0);
	}
}
