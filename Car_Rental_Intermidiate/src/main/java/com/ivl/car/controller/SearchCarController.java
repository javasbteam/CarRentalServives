package com.ivl.car.controller;

import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.ivl.car.entity.BookedCar;
import com.ivl.car.entity.CarModel;
import com.ivl.car.entity.SearchModel;
import com.ivl.car.service.ServiceProvider;
import com.sun.xml.internal.ws.api.ha.StickyFeature;

@Controller
public class SearchCarController {
	
	@Autowired
	private ServiceProvider serviceProvider;
	
	@Autowired
	private HttpSession session;
	
	
	@GetMapping("/searchCar")
	public String getCarData(@ModelAttribute SearchModel search,Map<String,Object> map) {
		
		session.setAttribute("search", search);
		List<CarModel> list=serviceProvider.searchCar(search);
		
		map.put("api1", list);
		return "search";
	}
	
	@GetMapping("/bookcar")
	public String  bookCar(@RequestParam("carId") String carId,Map<String,Object> map) {
		BookedCar bookedCar=null;
		SearchModel searchModel=null;
		int bookingId=new Random().nextInt(100000);
		
		searchModel=(SearchModel) session.getAttribute("search");
		bookedCar=new BookedCar();
		bookedCar.setBookId(bookingId);
		bookedCar.setBkFromDt(searchModel.getStartDt());
		bookedCar.setBkToDt(searchModel.getEndDt());
		bookedCar.setCarId(Integer.valueOf(carId));
		String msg=serviceProvider.saveBookedCar(bookedCar);
		map.put("msg", msg);
		return "search";
	}
	
}
