package br.com.zaratech.security;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;

import java.util.Random;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

public class Scrypt {
	private static SecretKey skey;
	private static KeySpec ks;
	private static PBEParameterSpec ps;
	private static final String algorithm = "PBEWithMD5AndDES";
	private static Logger log = Logger.getLogger(Scrypt.class);

	static {
		try {
			final SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
			ps = new PBEParameterSpec(new byte[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 20);

			ks = new PBEKeySpec("1".toCharArray());

			skey = skf.generateSecret(ks);
		} catch (final java.security.NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (final java.security.spec.InvalidKeySpecException ex) {
			ex.printStackTrace();
		}
	}

	public static final String encrypt(final String text)
			throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, UnsupportedEncodingException {

		final Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.ENCRYPT_MODE, skey, ps);
		String criptografado = new String(Base64.encodeBase64(cipher.doFinal(text.getBytes())), "UTF-8");
		log.debug("Cripto Original: " + criptografado);
		criptografado = criptografado.replace("/", "{");
		log.debug("Cripto Substitu: " + criptografado);
		return java.net.URLEncoder.encode(criptografado, "UTF-8");
	}

	public static final String encryptHash(final String text)
			throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, UnsupportedEncodingException {
		try {
			final SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
			PBEParameterSpec ps1 = new PBEParameterSpec(new byte[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 20);

			String hash = gerarHash();

			KeySpec ks1 = new PBEKeySpec(hash.toCharArray());
			SecretKey skey1 = skf.generateSecret(ks1);

			final Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.ENCRYPT_MODE, skey1, ps1);
			String criptografado = new String(Base64.encodeBase64(cipher.doFinal(text.getBytes())), "UTF-8");
			criptografado = criptografado.replace("/", "{");
			String senhaCripto = java.net.URLEncoder.encode(criptografado, "UTF-8");
			log.info("Encrypt: " + text + " Hash (" + hash + ") Cripyo " + criptografado);
			return hash.concat(senhaCripto);
		} catch (final java.security.NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (final java.security.spec.InvalidKeySpecException ex) {
			ex.printStackTrace();
		}
		return text;

	}

	private static String gerarHash() {

		String letras = "0123456789ABCDEFGHIJKLMNOPQRSTUVYWXZabcdefghijklmnopqrstuvwxyz";
		Random random = new Random();

		String armazenaChaves = "";
		int index = -1;
		for (int i = 0; i < 3; i++) {
			index = random.nextInt(letras.length());
			armazenaChaves += letras.substring(index, index + 1);
		}
		return armazenaChaves;
	}

	public static final String decryptHash(String text)
			throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, UnsupportedEncodingException {

		text = java.net.URLDecoder.decode(text, "UTF-8");
		text = text.replace("{", "/");
		try {
			final SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
			PBEParameterSpec ps1 = new PBEParameterSpec(new byte[] { 3, 1, 4, 1, 5, 9, 2, 6 }, 20);

			String hash = text.substring(0, 3);
			text = text.substring(3, text.length());

			KeySpec ks1 = new PBEKeySpec(hash.toCharArray());
			SecretKey skey1 = skf.generateSecret(ks1);

			final Cipher cipher = Cipher.getInstance(algorithm);
			cipher.init(Cipher.DECRYPT_MODE, skey1, ps1);
			String ret = null;
			try {
				ret = new String(cipher.doFinal(Base64.decodeBase64(text.getBytes())), "UTF-8");
			} catch (final Exception ex) {
			}
			log.info("Encrypt: " + ret + " Hash (" + hash + ") Cripto " + text);
			return ret;
		} catch (final java.security.NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (final java.security.spec.InvalidKeySpecException ex) {
			ex.printStackTrace();
		}
		return "";
	}

	public static final String decrypt(String text)
			throws BadPaddingException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException,
			NoSuchAlgorithmException, InvalidAlgorithmParameterException, UnsupportedEncodingException {

		text = java.net.URLDecoder.decode(text, "UTF-8");
		log.debug("Cripto Original: " + text);
		text = text.replace("{", "/");
		text = text.replace(" ", "+");
		log.debug("Cripto Substitu: " + text);

		final Cipher cipher = Cipher.getInstance(algorithm);
		cipher.init(Cipher.DECRYPT_MODE, skey, ps);
		String ret = null;
		try {
			ret = new String(cipher.doFinal(Base64.decodeBase64(text.getBytes())), "UTF-8");
		} catch (final Exception ex) {
		}
		return ret;
	}
}
