package com.tanoshi.codestar.template.controller;

import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;

import com.tanoshi.codestar.earth.SatelliteSet;

@Controller
public class TanoshiController {

	private final String siteName;
	private final String API_KEY;
	
	public TanoshiController(final String siteName, final String apiKey) {
			this.siteName = siteName;
			this.API_KEY = apiKey;
	}

	/*@RequestMapping(method = RequestMethod.GET)*/
	
	@RequestMapping("/")
	public ModelAndView helloWorld() {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("siteName", this.siteName);
		return mav;
	}
	
	@RequestMapping("/{year}/{month}/{day}")
	public ModelAndView helloWorld(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
		ModelAndView mav = new ModelAndView("index");
		mav.addObject("siteName", this.siteName);
		mav.addObject("year", year);
		mav.addObject("month", month);
		mav.addObject("day", day);
		return mav;
	}
	
	@RequestMapping(value="/get/{year}/{month}/{day}", method=RequestMethod.GET)
	public @ResponseBody String epicApi(@PathVariable("year") int year, @PathVariable("month") int month, @PathVariable("day") int day) {
		RestTemplate restTemplate = new RestTemplate();
		String epicQueryString = "https://epic.gsfc.nasa.gov/api/natural/date/"+year+"-"+month+"-"+day+"?api_key=" + API_KEY;
		ResponseEntity<String> response = restTemplate.getForEntity(epicQueryString,String.class);
		
		SatelliteSet set = new SatelliteSet(year, month, day, API_KEY).init(response.getBody());   
		return set.toJson();
	}
}
