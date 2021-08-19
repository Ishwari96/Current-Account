package com.ishwari.accountapi.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerAccountInformation {

	private String customerName;

	private String customerSurname;

	private List<AccountInformation> accounts;

}
