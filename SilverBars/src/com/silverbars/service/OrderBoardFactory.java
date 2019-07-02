package com.silverbars.service;

/** 
 * Creates and provides an IOrderBoard instance for clients
 */ 
public class OrderBoardFactory {
	private static final LiveOrderBoard INSTANCE = new LiveOrderBoard();
	
	/** 
	 * Returns the IOrderBoard singleton to clients
	 * @return IOrderBoard returns the order board instance
	 */ 
	public static IOrderBoard getInstance() {
		return INSTANCE;
	}
}
