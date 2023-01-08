package home;

import java.io.IOException;
import java.math.BigInteger;

import edu.rit.Hex;
//import Mars.MARS;
//import ecElGamal.EcElGamal;
//import ecElGamal.PairP;
//import ecElGamal.Point;
//import rabinSignature.myRabin;
import serpent.Serpent;
public class Main {
	
	public static void main(String[] args) throws IOException {
		
		Serpent serpent = new Serpent();
        
//            byte[] test_in = new byte[] {
//                0x01,0x01,0x00,0x00,0x00,0x00,0x00,0x00,
//                0x00,0x00,0x00,0x02,0x00,0x00,0x00,0x00,
//            };
//            byte[] test_key = new byte[] {
//                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
//                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
//                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
//                0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00
//            };
////        int iters = Integer.parseInt(args[0]);
////            for(int n = 0; n < iters; n++){
//                serpent.setKey(test_key);
//                serpent.encrypt(test_in);
////            }
//                String str =Hex.HexadecimalToString("6df94638b83e01458f3e30c9a1d6af1c");
//            System.out.println(Hex.StringtoHex(str));
//            System.out.println(Hex.toString(test_in));
//            System.out.println("\n\n");
//            serpent.decrypt(test_in);
//            
//            System.out.println(Hex.toString(test_in));

		String Mail = "hi my nameggggggg ggggg kk name is jhhhhhjgjg";
		System.out.println("The Orginal text is: " + Mail + "\n");
		System.out.println("encrypting with Serpent:");
//		String HexMail = Hex.StringtoHex(Mail);
//		byte[] plaintext = Hex.toByteArray(HexMail);

		/* encrypting info */
		BigInteger k = BigInteger.valueOf(227);
		String Hexkey = Hex.StringtoHex(k.toString());
		byte[] key = Hex.toByteArray(Hexkey);
		
		serpent.setKey(key);
			byte[] serpEncrypt = serpent.EncryptMessage(Mail);


		//String ciphertext = Hex.StringtoHex(serpEncrypt);
		/* encrypting info */
	//	System.out.println("encrypted mail : " + ciphertext + "\n");
		String serpDecrypt = serpent.decryptMessage(serpEncrypt);
	//	String Hexorigin = Hex.toString(serpDecrypt);
		//String original = Hex.HexadecimalToString(serpDecrypt);
		System.out.println("decrypted mail :  " + serpDecrypt + "\n");
	//	System.out.println("decrypted mail :  " + original + "\n");

            
            
            
            

//		System.out.println("encrypting key with EC Elgamal:");
//		/* encrypting key */
//		List<PairP<Point, Point>> enc = EcElGamal.encryptAndSendKey(k);
//
//		StringBuilder str = new StringBuilder();
//
//		System.out.println("");
//		for (PairP<Point, Point> pp : enc) {
//			str.append(String.format("%02x", pp.left.x.intValue()));
//			str.append(String.format("%02x", pp.left.y.intValue()));
//			str.append(String.format("%02x", pp.right.x.intValue()));
//			str.append(String.format("%02x", pp.right.y.intValue()));
//		}
//
//		String encryptedEcElgamal = str.toString();
//		System.out.println("String = " + encryptedEcElgamal);
//
//		System.out.println("\n");
//		/* encrypting key */
//
//		System.out.println("Signing with Rabin:");
//		/* Sign with Rabin */
//		int p = 11;
//		int q = 7;
//		int b = myRabin.generateKey(p, q);
//		byte[] msg = encryptedEcElgamal.getBytes(StandardCharsets.UTF_8);
//
//		ArrayList<Integer> arrayList = myRabin.Sign(msg, p, q, b);
//		int U = arrayList.get(0);// get u
//		int x = arrayList.get(1);// get x
//		System.out.println("the signature is : (U =" + U + ", x= " + x + ").");
//		/* Sign with Rabin */
	}
}
