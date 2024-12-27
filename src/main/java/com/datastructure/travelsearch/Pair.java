package com.datastructure.travelsearch;

public class Pair<T1, T2> {
	T1 field1;
	T2 field2;
	
	Pair(T1 value1, T2 value2) {
		field1 = value1;
		field2 = value2;
	}
	
	public T1 get1() {
		return field1;
	}
	
	public T2 get2() {
		return field2;
	}
}
