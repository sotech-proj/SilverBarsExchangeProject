package com.silverbars.application;

import com.silverbars.service.IOrderBoard;
import com.silverbars.service.OrderBoardFactory;

/** 
 * Client App for Silver Bars Order Board 
 */ 
public class SilverBarsClientApp {
	
	public static void main(String[] args)
	{
		IOrderBoard orderBoard = OrderBoardFactory.getInstance();
		
		orderBoard.registerOrder("1", 21, 68.34, "SELL");
		orderBoard.registerOrder("2", 32, 59.12, "SELL");
		orderBoard.registerOrder("3", 43, 55.12, "SELL");
		orderBoard.registerOrder("4", 45, 35.3, "SELL");
		orderBoard.registerOrder("7", 73, 55.12, "BUY");
		orderBoard.registerOrder("8", 62, 55.12, "BUY");
		
		System.out.println(orderBoard.getOrderSummaryForDisplay());
	}
}
