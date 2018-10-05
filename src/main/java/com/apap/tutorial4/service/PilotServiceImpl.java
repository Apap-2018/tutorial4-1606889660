package com.apap.tutorial4.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.repository.PilotDB;

@Service
@Transactional
public class PilotServiceImpl implements PilotService{
	@Autowired
	private PilotDB pilotDb;
	
	@Override
	public PilotModel getPilotDetailByLicenseNumber(String licenseNumber) {
		return pilotDb.findByLicenseNumber(licenseNumber);
	}
	
	@Override
	public void addPilot(PilotModel pilot) {
		pilotDb.save(pilot);
	}

	@Override
	public void removePilotById(Long id) {
		pilotDb.deleteById(id);
	}

	@Override
	public void updatePilot(PilotModel newPilot, String licenseNumber) {
		PilotModel pilot = pilotDb.findByLicenseNumber(newPilot.getLicenseNumber());
		pilot.setFlyHour(newPilot.getFlyHour());
		pilot.setName(newPilot.getName());
		pilotDb.save(pilot);
	}
}
