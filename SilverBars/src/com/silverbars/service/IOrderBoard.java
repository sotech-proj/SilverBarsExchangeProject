package com.silverbars.service;

import java.util.List;

import com.silverbars.model.IOrder;

/** 
 * This represents the interface for an Order Board 
 */ 
public interface IOrderBoard {
	
	/**
	 * This method registers an order onto the order board
	 * @param userId User id for the order 
	 * @param quantity Quantity for the order
	 * @param unitPrice Unit price for the order
	 * @param orderType Type of order - BUY or SELL
	 */
	public void registerOrder(String userId, double quantity, double unitPrice, String orderType);
	
	/**
	 * This method cancels a registered order
	 * @param userId User id for the order to be cancelled 
	 */
	public void cancelOrder(String userId);
	
	/**
	 * This method returns a display string for the list of registered orders.
	 * BUY orders are displayed first in descending order of price.
	 * SELL orders are displayed last in ascending order of price.
	 * Orders for the same price are merged if they are of the same type meaning that a BUY order
	 * will not be merged with a SELL order even if they are for the same price. 
	 * @return String returns order summary display string
	 */
	public String getOrderSummaryForDisplay();
	
	/**
	 * This method returns a list of registered orders sorted as indicated below:
	 * BUY orders descending order of price.
	 * SELL orders in ascending order of price.
	 * Orders for the same price are merged if they are of the same type meaning that a BUY order
	 * will not be merged with a SELL order even if they are for the same price. 
	 * @return List<IOrder> returns list of registered orders
	 */
	public List<IOrder> getOrderListForDisplay();
}
