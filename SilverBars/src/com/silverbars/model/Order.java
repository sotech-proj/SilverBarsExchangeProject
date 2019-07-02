package com.silverbars.model;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparing;

/** 
 * This class is an implementation of the IOrder interface to allow instances of 
 * buy and sell orders to be created. This class is immutable. 
 */ 
public class Order implements IOrder {
	
	enum OrderType {BUY, SELL}
	
	/**
	 * This comparator allows orders to be ranked according to their type (BUY orders ahead 
	 * of SELL orders) and then in ascending order of price as is required for SELL orders 
	 */
	private static final Comparator<IOrder> ASCENDING_PRICE_COMPARATOR = 
			comparing(IOrder::getOrderType).thenComparingDouble(IOrder::getUnitPrice)
			.thenComparing(IOrder::getUserId).thenComparingDouble(IOrder::getQuantity);
	
	/**
	 * This comparator allows orders to be ranked according to their type (BUY orders ahead 
	 * of SELL orders) and then in descending order of price as is required for BUY orders 
	 */
	private static final Comparator<IOrder> DESCENDING_PRICE_COMPARATOR = 
			comparing(IOrder::getOrderType).thenComparing(comparingDouble(IOrder::getUnitPrice).reversed())
			.thenComparing(IOrder::getUserId).thenComparingDouble(IOrder::getQuantity);
	
	private final String userId;
	private final double quantity;
	private final double unitPrice;
	private final OrderType orderType;
	
	//Clients will get instances of this class though the static factory method [Order.newInstance]
	private Order(String userId, double quantity, double unitPrice, OrderType type){
		this.userId = userId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
		this.orderType = type;
	}
	
	@Override
	public String getUserId() {		
		return userId;
	}

	@Override
	public double getQuantity() {
		return quantity;
	}

	@Override
	public double getUnitPrice() {
		return unitPrice;
	}

	@Override
	public OrderType getOrderType() {
		return orderType;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (!(obj instanceof Order))
			return false;
		Order order = (Order) obj;		
		return userId.equals(order.userId) && order.orderType == orderType && (Double.compare(order.quantity, quantity)==0) 
				&& (Double.compare(order.unitPrice, unitPrice)==0);
	}
	
	@Override
	public int hashCode() {
		int result = userId.hashCode();
		result = 31 * result + ((orderType == null) ? 0 : orderType.hashCode());
		result = 31 * result + Double.hashCode(quantity);
		result = 31 * result + Double.hashCode(unitPrice);
		return result;
	}
	
	@Override
	public String toString() {
		return String.format("%s: %.2fkg for £%.2f // order %s", orderType, quantity, unitPrice, userId);
	}
	
	/**
	 * This allows orders to function with sorted collections, which will rank as below: 
	 * First, BUY orders in descending order of price.
	 * Then SELL orders in ascending order of price.
	 */
	@Override
	public int compareTo(Order order) {
		return OrderType.SELL == order.orderType 
				? ASCENDING_PRICE_COMPARATOR.compare(this, order) 
				: DESCENDING_PRICE_COMPARATOR.compare(this, order);
	}	

	/**
	 * Creates IOrder instances for clients
	 */
	public static IOrder newInstance(String userId, double quantity, double unitPrice, String orderType){
		return new Order(userId, quantity, unitPrice, OrderType.valueOf(orderType.toUpperCase()));
	}
	
	/**
	 * Merges a list of orders into a new IOrder instance with an aggregate quantity
	 */
	public static IOrder mergeOrders(List<IOrder> orders){
		double mergedQuantity = 0;
		OrderType orderType = null;
		StringBuilder mergedUserIdBuilder = new StringBuilder();
		String userIdPrefix = "";
		for(IOrder order : orders){			
			mergedQuantity += order.getQuantity(); 
			mergedUserIdBuilder.append(userIdPrefix);
			userIdPrefix = " + ";
			mergedUserIdBuilder.append(order.getUserId());
			OrderType nextOrderType = order.getOrderType();
			
			//ensure that we only merge orders of the same type. All SELL orders or all BUY orders
			if (orderType != null && orderType != nextOrderType){
				throw new IllegalArgumentException("List of orders to be merged must be of same type i.e. BUY or SELL");
			}
		}
		
		return new Order(mergedUserIdBuilder.toString(), mergedQuantity, orders.get(0).getUnitPrice(), orders.get(0).getOrderType());
	}
}
