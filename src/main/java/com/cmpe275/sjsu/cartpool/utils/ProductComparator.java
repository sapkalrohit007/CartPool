package com.cmpe275.sjsu.cartpool.utils;

import java.util.Comparator;

import com.cmpe275.sjsu.cartpool.model.Product;

public class ProductComparator implements Comparator {

	@Override
	public int compare(Object o1, Object o2) {
		
		Product p1 = (Product) o1;
		Product p2 = (Product) o2;
		
		int p1SKU = p1.getSku();
		int p2SKU = p2.getSku();
		
		if( p1SKU == p2SKU) {
			return 0;
		}else if(p1SKU > p2SKU) {
			return 1;
		}else {
			return -1;
		}
	}
	
}
