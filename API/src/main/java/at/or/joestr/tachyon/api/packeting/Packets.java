// 
// Private License
// 
// Copyright (c) 2019-2020 Joel Strasser
// 
// Only the owner is allowed to use this software.
// 
package at.or.joestr.tachyon.api.packeting;

/**
 *
 * @author Joel
 */
public class Packets {
	
	private Packets() {
    throw new IllegalStateException("Utility class");
  }
	
	public static final int MAX_BYTES = 5242880; // 5 Mebibytes
	
	public enum Provided {
		INIT,
		X1,
		X2,
		ERROR_RECEIVE,
		ERROR_SEND,
		GET_ALL_PLAYERS
	}
}
