package com.mindex.challenge.service;

import com.mindex.challenge.data.Employee;

public interface CompensationService {
	//The Create and read interface for the services.
	Compensation create(Compensation compensation);
	Compensation read(String id);
}
