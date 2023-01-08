package home;

import java.math.BigInteger;

import controller.User1Controller;
import serpent.Serpent;
import dh.DiffieHellman;
import edu.rit.Hex;
import javafx.application.Application;
import javafx.stage.Stage;

public class Launch extends Application {

	public static boolean firstMsg1 = true;
	public static boolean tr1fa2 = true;
	public static Serpent serpent;
	public static DiffieHellman dh;
	public static BigInteger user1PubK;
	public static BigInteger user2PubK;
	public static BigInteger user1SecK;
	public static BigInteger user2SecK;
	public static byte[] dhkey;
	public static void main(String[] args) throws Exception {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		dh = new DiffieHellman();
		dh.genPrimeAndPrimitiveRoot(); // initialize p and g for DH key exchange
		user1PubK = dh.getAliceMessage(BigInteger.valueOf(123));
		user2PubK = dh.getBobMessage(BigInteger.valueOf(560));

		System.out.println("D-H Key Exchange:\nGenerated Prime (p): " + dh.getP() + "\nGenerated Primitive Root (g): "
				+ dh.getG());
		System.out.println("User 1 chooses Private Key (a) as 123\nUser 2 chooses Private Key (b) as 560");
		System.out.println("User 1 generated key to share publicly ((g^a) mod p): " + user1PubK);
		System.out.println("User 2 generated key to share publicly ((g^b) mod p): " + user2PubK);
		user1SecK = dh.aliceCalculationOfKey(user2PubK, BigInteger.valueOf(123));
		user2SecK = dh.bobCalculationOfKey(user1PubK, BigInteger.valueOf(560));
		System.out.println("User 1 Secret Shared Key (user2PubK^a mod p): " + user1SecK);
		System.out.println("User 2 Secret Shared Key (user1PubK^b mod p): " + user2SecK);

		// serpent code
		String Hexkey = Hex.StringtoHex(user1SecK.toString());
		dhkey = Hex.toByteArray(Hexkey);
		System.out.println("Serpent Symmetric Key (before padding): "+ Hexkey);
		serpent = new Serpent();
		serpent.setKey(dhkey);

		User1Controller sb = new User1Controller();
		sb.start(primaryStage);
	}
}