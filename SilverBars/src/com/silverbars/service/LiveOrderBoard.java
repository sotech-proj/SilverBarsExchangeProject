package com.silverbars.service;

import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.ArrayList;
import java.util.List;

import com.silverbars.model.IOrder;
import com.silverbars.model.Order;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

/** 
 * This class is an implementation of the IOrderBoard interface that allows orders to be registered,
 * cancelled and displayed.
 */ 
class LiveOrderBoard implements IOrderBoard{
	private final Map<String, IOrder> liveOrders = new ConcurrentHashMap<>();
	
	LiveOrderBoard(){}

	@Override
	public void registerOrder(String userId, double quantity, double unitPrice, String orderType) {
		liveOrders.put(userId, Order.newInstance(userId, quantity, unitPrice, orderType));		
	}

	@Override
	public void cancelOrder(String userId) {
		liveOrders.remove(userId);	
	}

	@Override
	public String getOrderSummaryForDisplay() {	
		List<IOrder> orders = getOrderListForDisplay();
		
		StringBuilder displaySummary = new StringBuilder();
		for(IOrder order : orders){
			displaySummary.append(order);
			displaySummary.append(System.getProperty("line.separator"));
		}
		
		return displaySummary.toString();
	}
	
	@Override
	public List<IOrder> getOrderListForDisplay(){
		Map<String, IOrder> groupByOrderTypeAndPriceMap =
				liveOrders.values().stream().collect(groupingBy(o -> getOrderGroupingKey(o),
						collectingAndThen(toList(), Order::mergeOrders )));
		
		//Use tree set to leverage sorting functionality on our comparable IOrder instances
		TreeSet<IOrder> sortedOrders = new TreeSet<IOrder>(groupByOrderTypeAndPriceMap.values());		
		
		return new ArrayList<IOrder>(sortedOrders);
	}
	
	private String getOrderGroupingKey(IOrder order){
		return order.getOrderType()+"-"+order.getUnitPrice();
	}
}
