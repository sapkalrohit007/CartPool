package com.cmpe275.sjsu.cartpool.utils;

import java.util.Comparator;

import com.cmpe275.sjsu.cartpool.model.Product;
import com.cmpe275.sjsu.cartpool.model.Store;

public class StoreComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		
		Store s1 = (Store) o1;
		Store s2 = (Store) o2;
		
		int s1SKU = s1.getId();
		int s2SKU = s2.getId();
		
		if( s1SKU == s2SKU) {
			return 0;
		}else if(s1SKU > s2SKU) {
			return 1;
		}else {
			return -1;
		}
	}
	
}
