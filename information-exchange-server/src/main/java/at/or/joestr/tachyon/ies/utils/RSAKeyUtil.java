// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package at.or.joestr.tachyon.ies.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 * @author joestr
 */
public class RSAKeyUtil {
   
  /**
   * Writes a private key with encoding
   *
   * @param file       The file where the key will be saved
   * @param privateKey The private key
   *
   * @throws FileNotFoundException If the file was not found
   * @throws IOException           If an I/O error occours
   */
  public static void savePrivateKey(File file, PrivateKey privateKey) throws FileNotFoundException, IOException {
    PKCS8EncodedKeySpec privateKeySpec
      = new PKCS8EncodedKeySpec(
        privateKey.getEncoded()
      );
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(privateKeySpec.getEncoded());
    fos.close();
  }

  /**
   * Writes a public key with encoding
   *
   * @param file      The file where the key will be saved
   * @param publicKey The public key
   *
   * @throws FileNotFoundException If the file was not found
   * @throws IOException           If an I/O error occours
   */
  public static void savePublicKey(File file, PublicKey publicKey) throws FileNotFoundException, IOException {
    X509EncodedKeySpec publicKeySpec
      = new X509EncodedKeySpec(
        publicKey.getEncoded()
      );
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(publicKeySpec.getEncoded());
    fos.close();
  }

  /**
   * Reads a private key with encoding
   *
   * @param file      The file where the key will be read from
   * @param algorithm The algorithm of the key
   *
   * @return
   *
   * @throws FileNotFoundException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  public static PrivateKey loadPrivateKey(File file, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    File filePublicKey = file;
    FileInputStream fis = new FileInputStream(file);
    byte[] encodedPrivateKey = new byte[(int) filePublicKey.length()];
    fis.read(encodedPrivateKey);
    fis.close();

    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    PKCS8EncodedKeySpec privateKeySpec
      = new PKCS8EncodedKeySpec(
        encodedPrivateKey
      );

    return keyFactory.generatePrivate(privateKeySpec);
  }

  /**
   * Reads a private key with encoding
   *
   * @param file      The file where the key will be read from
   * @param algorithm The algorithm of the key
   *
   * @return
   *
   * @throws FileNotFoundException
   * @throws IOException
   * @throws NoSuchAlgorithmException
   * @throws InvalidKeySpecException
   */
  public static PublicKey loadPublicKey(File file, String algorithm) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
    File filePublicKey = file;
    FileInputStream fis = new FileInputStream(file);
    byte[] encodedPublicKey = new byte[(int) filePublicKey.length()];
    fis.read(encodedPublicKey);
    fis.close();

    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    X509EncodedKeySpec publicKeySpec
      = new X509EncodedKeySpec(
        encodedPublicKey
      );

    return keyFactory.generatePublic(publicKeySpec);
  }
}
