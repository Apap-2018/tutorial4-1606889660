package com.apap.tutorial4.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial4.model.FlightModel;
import com.apap.tutorial4.model.PilotModel;
import com.apap.tutorial4.service.FlightService;
import com.apap.tutorial4.service.PilotService;

@Controller
public class FlightController {
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PilotService pilotService;
	
	@RequestMapping(value = "/flight/add/{licenseNumber}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = new FlightModel();
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		
		model.addAttribute("flight", flight);
		return "addFlight";
	}
	
	@RequestMapping(value = "/flight/add", method = RequestMethod.POST)
	private String addFlightSubmit(@ModelAttribute FlightModel flight) {
		flightService.addFlight(flight);
		return "add";
	}
	
	@RequestMapping(value = "/flight/{licenseNumber}/update/{id}", method = RequestMethod.GET)
	private String updateFlight(@PathVariable(value = "id") String id,@PathVariable(value = "licenseNumber") String licenseNumber, Model model) {
		FlightModel flight = flightService.getFlightDetailByIdAndPilotLicenseNumber(Long.parseLong(id), licenseNumber);
		PilotModel pilot = pilotService.getPilotDetailByLicenseNumber(licenseNumber);
		flight.setPilot(pilot);
		model.addAttribute("licenseNumber", licenseNumber);
		model.addAttribute("flight", flight);
		return "updateFlight";
	}
	
	@RequestMapping(value = "/flight/update/", method = RequestMethod.POST)
	private String updateFlightSubmit(@ModelAttribute FlightModel flight) {
		
		flightService.update(flight);
		return "update";
	}
	
	@RequestMapping(value="/pilot/view")
	private String pilotView(@RequestParam(value = "licenseNumber", required = true) String licenseNumber, Model model) {
		model.addAttribute("pilot", pilotService.getPilotDetailByLicenseNumber(licenseNumber));
		List<FlightModel> flights = flightService.getFlightDetailByLicenseNumber(licenseNumber);
		model.addAttribute("flights", flights);
		return "view-pilot";
	}
	
	@RequestMapping(value = "/flight/delete/{id}", method = RequestMethod.GET)
	private String deleteFlight(@PathVariable(value = "id") String id) {
		flightService.removeById(Long.parseLong(id));
		return "delete";
	}
	
	@RequestMapping(value = "/flight/viewAll")
	private String viewFlights(Model model) {
		List<FlightModel> flights = flightService.getAllFlights();
		model.addAttribute("flights", flights);
		return "viewFlights";
	}
	
	
	
	
}
