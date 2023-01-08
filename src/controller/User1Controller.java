package controller;


import eceg.ElGamal;
import edu.rit.Hex;
import home.Launch;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class User1Controller {

    //static ArrayList<Byte> encryptedMsg;
    static String decryptedMsg, encryptedText;
    static byte[] dSignature;
    static ElGamal eceg1;
    static ElGamal.DataChunk user2Public;
    static ElGamal eceg2;
    static ElGamal.DataChunk user1Public;
    static byte[] cipherText, ShaByte;
    @FXML
    private TextArea plainTxt;
    @FXML
    private TextArea encryptedTxt;
    @FXML
    private Button sendBtn;
    @FXML
    private Button encBtn;
    @FXML
    private TextArea dsTxt;
    @FXML
    private Button decBtn;
    @FXML
    private Button clr;
    @FXML
    private TextField verify;
    @FXML
    private Label serpentK;

    public void initialize() {
        plainTxt.setWrapText(true);
        encryptedTxt.setWrapText(true);
        dsTxt.setWrapText(true);
        serpentK.setText("Serpent Symmetric Key: " + Hex.HexadecimalToString(Hex.toString(Launch.dhkey)));

        if (Launch.firstMsg1 == true) {
            encryptedTxt.setText("");
            dsTxt.setText("");

            System.out.println("Elliptic Curve - ElGamal:");
            eceg1 = new ElGamal();
            ElGamal.flag = 0;
            eceg2 = new ElGamal();
            user1Public = eceg1.public_chunk;
            user2Public = eceg2.public_chunk;
            ElGamal.flag = 0;
            Launch.firstMsg1 = false;
            System.out.println("Start User1 SMS window.");
        } else {
            encryptedTxt.setText(User2Controller.encryptedText2);
            dsTxt.setText(Hex.HexadecimalToString(Hex.toString(User2Controller.dSignature2)));
            dSignature = User2Controller.dSignature2;
        }

    }

    public void start(Stage primaryStage) throws Exception {
        if (Launch.firstMsg1 == false)

            System.out.println("Start User1 SMS window.");
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/user1.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setTitle("User1 SMS Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void encrypt(ActionEvent event) {
        if (plainTxt.getText() == "") {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error plain text");
            alert.setHeaderText("Invalid plain text!");
            alert.setContentText("Must enter plain text to send.");
            alert.showAndWait();
            return;
        }

        if (Launch.tr1fa2 == true) {
            cipherText = Launch.serpent.EncryptMessage(plainTxt.getText());
            encryptedText = Hex.HexadecimalToString(Hex.toString(cipherText));
            ShaByte = eceg1.sha256_Gen(plainTxt.getText().getBytes());
            System.out.println("shaaa: " + ShaByte.toString());

//			System.out.println(Hex.HexadecimalToString(encryptedText));
//			System.out.println(encryptedText+"/n");


            dSignature = eceg2.encrypt(Hex.HexadecimalToString(Hex.toString(ShaByte)));
            Launch.tr1fa2 = false;
        } else {
            cipherText = User2Controller.cipherText2;
            encryptedText = User2Controller.encryptedText2;
            dSignature = User2Controller.dSignature2;
        }


        encryptedTxt.setText(encryptedText);
        dsTxt.setText(Hex.HexadecimalToString(Hex.toString(dSignature)));

        System.out.println("Plain text encrypted. Digital Signature generated.");
        System.out.println("Plain text: " + plainTxt.getText());
        System.out.println("Encrypted text: " + Hex.StringtoHex(encryptedText));
//		System.out.println("Digital signature: \n" + Hex.HexadecimalToString(Hex.toString(dSignature)));
    }

    @FXML
    void decrypt(ActionEvent event) {
        if (encryptedTxt.getText() == "") {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error encrypted text");
            alert.setHeaderText("Invalid encrypted text!");
            alert.setContentText("Must use encrypted text to decrypt.");
            alert.showAndWait();
            return;
        }

        decryptedMsg = Launch.serpent.decryptMessage(User2Controller.cipherText2);
        plainTxt.setText(decryptedMsg);

        System.out.println("Encrypted text has been decrypted.");
        System.out.println("Encrypted text: " + encryptedTxt.getText());
        System.out.println("Decrypted text: " + decryptedMsg);


        String enPtTmp = eceg1.decrypt(dSignature);
        ShaByte = User1Controller.eceg1.sha256_Gen(decryptedMsg.getBytes());
        System.out.println("\nOriginal message SHA-256: " + Hex.toString(User2Controller.ShaByte2));
        System.out.println("\nDecrypted message SHA-256: " + Hex.toString(ShaByte));

        if (Hex.toString(User2Controller.ShaByte2).equals(Hex.toString(ShaByte))) {
            System.out.println("Digital signature has been decrypted.");
            System.out.println("Digital signature: \n" + dsTxt.getText());
            verify.setText(verify.getText() + "Verified successfully!");
            verify.setStyle("-fx-control-inner-background: #4ec029");
        } else {
            verify.setText(verify.getText() + "Verification FAILED!");
            verify.setStyle("-fx-text-fill: #4080bf; -fx-control-inner-background: #fff");
        }
    }

    @FXML
    void send(ActionEvent event) throws Exception {
        System.out.println("\nStart User2 SMS window.");
        Stage primaryStage = new Stage();
        Parent root1 = FXMLLoader.load(getClass().getResource("/fxml/user2.fxml"));
        Scene scene = new Scene(root1);
        primaryStage.setTitle("User2 SMS Screen");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @FXML
    void clearTxt(ActionEvent event) throws Exception {
        plainTxt.clear();
        encryptedTxt.clear();
        dsTxt.clear();
    }
}