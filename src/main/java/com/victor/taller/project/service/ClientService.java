package com.victor.taller.project.service;

import com.victor.taller.project.soa.bean.ClientBean;

public interface ClientService {
	
	abstract ClientBean saveClient(ClientBean clientBean);
	abstract ClientBean getClientById(Integer id);
	abstract ClientBean getClientByUsername(String username);

}
