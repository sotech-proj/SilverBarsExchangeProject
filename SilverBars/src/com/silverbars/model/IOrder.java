package com.silverbars.model;

import com.silverbars.model.Order.OrderType;

/** 
 * Marks orders for silver bars 
 */ 
public interface IOrder extends Comparable<Order> {
	
	/**
	 * This gets the user id of this order
	 * @return String returns user id
	 */
	public String getUserId();

	/**
	 * This gets the quantity of this order
	 * @return double returns order quantity
	 */
	public double getQuantity();
	
	/**
	 * This gets the unit price of this order
	 * @return double returns order unit price
	 */
	public double getUnitPrice(); 
	
	/**
	 * This gets the order type - BUY or SELL
	 * @return OrderType returns order type
	 */
	public OrderType getOrderType(); 
}
