package br.com.projetoSimpleOS.objetos;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.tomcat.util.codec.binary.Base64;

public class Autenticacao{
	
	
	public String BS64MD5(String senhaa, String salt) throws NoSuchAlgorithmException, UnsupportedEncodingException{
		
		byte[] encodedBytes = Base64.encodeBase64(senhaa.getBytes());
	   
		String bs64 = criptoMD5(new String(encodedBytes, Charset.forName("UTF-8"))+ salt);
		return bs64;
	}
	
	
	public String criptoMD5 (String senha) throws NoSuchAlgorithmException, UnsupportedEncodingException  {
			
		
			MessageDigest algorithm = MessageDigest.getInstance("MD5");
			byte messageDigest[] = algorithm.digest(senha.getBytes("UTF-8"));//Executa uma atualização final no resumo usando a matriz de bytes especificada e, em seguida, completa a computação digest.
			
			StringBuilder hexString = new StringBuilder();
			for (byte b : messageDigest) {
			  hexString.append(String.format("%02X", 0xFF & b));
			}
			String passw = hexString.toString();
			
			return passw;
		}
		
		
		
	}
