package com.silverbars.service;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.silverbars.model.IOrder;

import static org.junit.Assert.assertEquals;

/** 
 * Test case for Live Order Board
 */ 
public class LiveOrderBoardTest {
	private LiveOrderBoard liveOrderBoard;

	@Before 
    public void before() throws Exception {
		liveOrderBoard = new LiveOrderBoard();
    }
	
	@Test
	public void testBuyOrdersDisplayedWithDescendingPriceOrder() {		
		liveOrderBoard.registerOrder("1", 71, 55.12, "BUY");
		liveOrderBoard.registerOrder("2", 72, 66.12, "BUY");
		liveOrderBoard.registerOrder("3", 73, 77.12, "BUY");
		List<IOrder> orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(3,orders.size());	
		assertEquals(77.12, orders.get(0).getUnitPrice(), 0);
		assertEquals(66.12, orders.get(1).getUnitPrice(), 0);
		assertEquals(55.12, orders.get(2).getUnitPrice(), 0);
	}
	
	@Test
	public void testSellOrdersDisplayedWithAscendingPriceOrder() {
		liveOrderBoard.registerOrder("1", 71, 54.12, "SELL");
		liveOrderBoard.registerOrder("2", 72, 32.12, "SELL");
		liveOrderBoard.registerOrder("3", 73, 56.22, "SELL");
		List<IOrder> orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(3,orders.size());	
		assertEquals(32.12, orders.get(0).getUnitPrice(), 0);
		assertEquals(54.12, orders.get(1).getUnitPrice(), 0);
		assertEquals(56.22, orders.get(2).getUnitPrice(), 0);
	}
	
	@Test
	public void testOrdersWithSamePriceAreQtyMergedForDisplay() {
		liveOrderBoard.registerOrder("1", 71, 55.12, "BUY");
		liveOrderBoard.registerOrder("2", 72, 55.12, "BUY");
		liveOrderBoard.registerOrder("3", 73, 77.12, "BUY");
		List<IOrder> orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(2,orders.size());	
		assertEquals(77.12, orders.get(0).getUnitPrice(), 0);
		assertEquals(55.12, orders.get(1).getUnitPrice(), 0);
	}
	
	@Test
	public void testBuyOrderWithSamePriceAsSellOrderNotMerged() {
		liveOrderBoard.registerOrder("1", 71, 55.12, "BUY");
		liveOrderBoard.registerOrder("2", 72, 55.12, "SELL");
		List<IOrder> orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(2,orders.size());	
		assertEquals(55.12, orders.get(0).getUnitPrice(), 0);
		assertEquals(55.12, orders.get(1).getUnitPrice(), 0);
	}
	
	@Test
	public void testBuyOrdersSortedAheadOfSellOrders() {
		liveOrderBoard.registerOrder("1", 71, 55.12, "SELL");
		liveOrderBoard.registerOrder("2", 72, 66.12, "BUY");
		liveOrderBoard.registerOrder("3", 73, 77.12, "SELL");
		liveOrderBoard.registerOrder("4", 73, 88.12, "BUY");
		List<IOrder> orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(4,orders.size());	
		assertEquals("4", orders.get(0).getUserId());
		assertEquals("2", orders.get(1).getUserId());
		assertEquals("1", orders.get(2).getUserId());
		assertEquals("3", orders.get(3).getUserId());
	}
	
	@Test
	public void testOrderCancellation() {
		liveOrderBoard.registerOrder("1", 71, 55.12, "BUY");
		liveOrderBoard.registerOrder("2", 72, 66.12, "BUY");
		List<IOrder> orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(2,orders.size());	
		liveOrderBoard.cancelOrder("1"); 
		orders = liveOrderBoard.getOrderListForDisplay();
		assertEquals(1,orders.size());
		assertEquals(66.12, orders.get(0).getUnitPrice(), 0);
	}
	
	@Test
	public void testOrderSummaryDisplay() {		
		liveOrderBoard.registerOrder("3", 43, 55.12, "SELL");
		liveOrderBoard.registerOrder("4", 45, 35.3, "SELL");
		liveOrderBoard.registerOrder("7", 73, 55.12, "BUY");
		liveOrderBoard.registerOrder("8", 62, 55.12, "BUY");
		
		StringBuilder expextedDisplaySummary = new StringBuilder();
		expextedDisplaySummary.append("BUY: 135.00kg for £55.12 // order 7 + 8");
		expextedDisplaySummary.append(System.getProperty("line.separator"));
		expextedDisplaySummary.append("SELL: 45.00kg for £35.30 // order 4");
		expextedDisplaySummary.append(System.getProperty("line.separator"));
		expextedDisplaySummary.append("SELL: 43.00kg for £55.12 // order 3");
		expextedDisplaySummary.append(System.getProperty("line.separator"));
		
		assertEquals(expextedDisplaySummary.toString(), liveOrderBoard.getOrderSummaryForDisplay());
	}
	
	@After
    public void after() throws Exception {
		liveOrderBoard = null;
    }
}
