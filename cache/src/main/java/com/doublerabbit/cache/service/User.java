package com.doublerabbit.cache.service;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User implements Serializable{

	private static final long serialVersionUID = -1120848805925820039L;
	
	private String name;
	private int age;
}
