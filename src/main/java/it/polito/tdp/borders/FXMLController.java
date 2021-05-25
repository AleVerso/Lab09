package it.polito.tdp.borders;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.borders.model.Country;
import it.polito.tdp.borders.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {

	private Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="txtAnno"
    private TextField txtAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    private ComboBox<Country> cBoxNazione;

    @FXML
    private Button btnStatiRaggiugibili;
    
	@FXML
	void DoStatiRaggiugibili(ActionEvent event) {

		Country c = this.cBoxNazione.getValue();

		if (c == null) {
			this.txtResult.setText("Scegli una nazione");
			return;
		}

		String s = "Stati raggiungibili da: " + c.toString() + "\n";


		for (Country y : this.model.nazioniConnesse(c)) {
			s += y.toString() + "\n ";

		}

		this.txtResult.setText(s);

	}

	@FXML
	void doCalcolaConfini(ActionEvent event) {

		int annoI;

		try {
			annoI = Integer.parseInt(this.txtAnno.getText());
		} catch (NumberFormatException e) {
			this.txtResult.setText("Inserisci un valore numerico");
			return;
		}
		
		if(annoI<1816 || annoI>2006) {
			this.txtResult.setText("Inserisci un anno compreso tra 1816 e 2006");
			return;
		}

		this.model.createGraph(annoI);
		this.txtResult.setText(this.model.getStampa());

	}

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert txtAnno != null : "fx:id=\"txtAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cBoxNazione != null : "fx:id=\"cBoxNazione\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnStatiRaggiugibili != null : "fx:id=\"btnStatiRaggiugibili\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.cBoxNazione.getItems().addAll(model.getCountries());
    }
}
