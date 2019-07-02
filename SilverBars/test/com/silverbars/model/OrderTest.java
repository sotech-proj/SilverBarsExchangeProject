package com.silverbars.model;

import org.junit.Test;

import com.silverbars.model.Order.OrderType;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** 
 * Test case for Order
 */ 
public class OrderTest {

	@Test
	public void testOrderFields() {			
		IOrder order = Order.newInstance("1", 50, 23.12, "BUY");
		assertEquals(50, order.getQuantity(), 0);
		assertEquals(23.12, order.getUnitPrice(), 0);
		assertEquals("1", order.getUserId());
		assertEquals(OrderType.BUY, order.getOrderType());
	}
	
	@Test
	public void testBuyOrdersRankedInDescendingPrice() {			
		IOrder buyOrderPrice25 = Order.newInstance("1", 50, 25, "BUY");
		IOrder buyOrderPrice23 = Order.newInstance("1", 50, 23, "BUY");
		int compareResult = buyOrderPrice25.compareTo((Order) buyOrderPrice23);
		assertTrue("Buy Orders not ranked in descending price", compareResult < 0);
	}
	
	@Test
	public void testSellOrdersRankedInAscendingPrice() {			
		IOrder sellOrderPrice25 = Order.newInstance("1", 50, 25, "SELL");
		IOrder sellOrderPrice23 = Order.newInstance("1", 50, 23, "SELL");
		int compareResult = sellOrderPrice25.compareTo((Order) sellOrderPrice23);
		assertTrue("Sell Orders not ranked in ascending price", compareResult > 0);
	}
	
	@Test
	public void testBuyOrderLessThanSellOrder() {			
		IOrder buyOrder = Order.newInstance("1", 50, 23.12, "BUY");
		IOrder sellOrder = Order.newInstance("1", 50, 23.12, "SELL");
		int compareResult = buyOrder.compareTo((Order) sellOrder);
		assertTrue("Sell Order is not ranked ahead of Buy order", compareResult < 0);
	}
}
