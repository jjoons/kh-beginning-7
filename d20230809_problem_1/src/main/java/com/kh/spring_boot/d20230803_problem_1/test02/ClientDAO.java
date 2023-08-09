package com.kh.spring_boot.d20230803_problem_1.test02;

import java.util.ArrayList;
import java.util.HashMap;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDAO {
  private String companyName;
  private ArrayList<String> clientList1;
  private HashMap<String, String> clientMap1;
  private HashMap<String, Client> clientMap2;
}
