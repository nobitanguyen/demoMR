package com.mr.springmvc.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.mr.springmvc.common.ReadWriteExcelFile;
import com.mr.springmvc.model.Restaurant;
import com.mr.springmvc.model.Review;
import com.mr.springmvc.repository.ReviewRepository;
import com.mr.springmvc.service.RestaurantService;
import com.mr.springmvc.service.ReviewService;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;

@Controller
@RequestMapping(value = "/restaurant")
public class RestaurantRestController {

	@Autowired
	private RestaurantService restaurantService;
	private ReviewService reviewService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> listAllRestaurants() {
		List<Restaurant> restaurants = restaurantService.findAll();

		if (restaurants == null || restaurants.isEmpty()) {
			return new ResponseEntity<List<Restaurant>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
	}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ResponseEntity<List<Restaurant>> searchByCondition(@RequestParam("name") String name,
			@RequestParam("address") String address, @RequestParam("description") String description) {
		List<Restaurant> restaurants = restaurantService.findByCondition(name, address, description);

		if (restaurants == null || restaurants.isEmpty()) {
			return new ResponseEntity<List<Restaurant>>(HttpStatus.NO_CONTENT);
			// You many decide to return HttpStatus.NOT_FOUND
		}

		return new ResponseEntity<List<Restaurant>>(restaurants, HttpStatus.OK);
	}

	@RequestMapping(value = "/detail/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Restaurant> getRestaurant(@PathVariable("id") int id) {
		Restaurant restaurant = restaurantService.findById(id);

		if (restaurant == null) {
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public ResponseEntity<Restaurant> createRestaurant(@RequestBody Restaurant restaurant) {
		try {
			restaurantService.create(restaurant);

			return new ResponseEntity<Restaurant>(restaurant, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/update/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Restaurant> updateRestaurant(@PathVariable("id") int id, @RequestBody Restaurant restaurant) {
		try {
			restaurant.setId(id);
			restaurant = restaurantService.update(restaurant);

			return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Restaurant> deleteRestaurant(@PathVariable("id") int id) {
		try {
			restaurantService.delete(id);

			return new ResponseEntity<Restaurant>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<Restaurant>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/fileUpload", method = RequestMethod.POST)
	public ResponseEntity<List<Restaurant>> fileUpload(@RequestParam("file") MultipartFile file) {
		try {
			List<Restaurant> lst = new ArrayList<Restaurant>();
			//upload file
			if(file.getOriginalFilename().endsWith("xls"))
				lst = ReadWriteExcelFile.readXLSFile(file);
			else if(file.getOriginalFilename().endsWith("xlsx"))
				lst = ReadWriteExcelFile.readXLSXFile(file);
			for (Restaurant restaurant : lst) {
				restaurantService.create(restaurant);
			}
			return new ResponseEntity<List<Restaurant>>(lst, HttpStatus.CREATED);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<List<Restaurant>> (HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/downloadFile", method = RequestMethod.GET)
	public String downloadFile(HttpServletResponse httpServletResponse, HttpServletRequest request) {
		try {
			List<Restaurant> lst = restaurantService.findAll();
			
			JRBeanCollectionDataSource beanCollectionDataSource = new JRBeanCollectionDataSource(lst);
			//ExternalContext eC = FacesContext.getCurrentInstance().getExternalContext();
			String report = request.getRealPath("/reports/report1.jasper");
			//InputStream report = eC.getResourceAsStream("/reports/report.jasper");
			JasperPrint jasperPrint = JasperFillManager.fillReport(report, new HashMap<String, Object>(), beanCollectionDataSource);

//			HttpServletResponse httpServletResponse = (HttpServletResponse) FacesContext.getCurrentInstance()
//					.getExternalContext().getResponse();
			httpServletResponse.addHeader("Content-disposition", "attachment; filename=report.xlsx");
			ServletOutputStream servletOutputStream = httpServletResponse.getOutputStream();
			JRXlsxExporter docxExporter = new JRXlsxExporter();
			docxExporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			docxExporter.setParameter(JRExporterParameter.OUTPUT_STREAM, servletOutputStream);
			docxExporter.exportReport();
			//FacesContext.getCurrentInstance().responseComplete();
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	@RequestMapping(value = { "/analyze/{id}" }, method = RequestMethod.GET)
	public void  loadChart(HttpServletResponse response, @PathVariable("id") int id) {
		//model.addAttribute("user", getPrincipal());
		Restaurant rest = restaurantService.findById(id);
		String name = "";
		if(rest!=null){
			name = rest.getName();
		}
//		response.setContentType("image/png");
//		JFreeChart chart = reviewService.createChart(1, name + " Analyze");
//		try {
//			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
//					750, 400);
//			response.getOutputStream().close();
//		} catch (IOException ex) {
//			ex.printStackTrace();
//		}
		response.setContentType("image/png");
		PieDataset pdSet = createDataSet(id);

		JFreeChart chart = createChart(pdSet, name);

		try {
			ChartUtilities.writeChartAsPNG(response.getOutputStream(), chart,
					750, 400);
			response.getOutputStream().close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	

	private PieDataset createDataSet(int id) {
		DefaultPieDataset dpd = new DefaultPieDataset();
		
//		List<Review> lst = reviewService.findByRestaurantId(id);
//		//without year 
//		List<String> month = new ArrayList<String>();
//		for (Review review : lst) {
//			if(!month.contains(review.getReviewDate().getMonth()))
//				month.add(review.getReviewDate().getMonth());
//		}
		dpd.setValue("April", 31);
		dpd.setValue("May", 69);
//		dpd.setValue("Window", 40);
//		dpd.setValue("Others", 9);
		return dpd;
	}

	private JFreeChart createChart(PieDataset pdSet, String chartTitle) {

		JFreeChart chart = ChartFactory.createPieChart3D(chartTitle, pdSet,
				true, true, false);
		PiePlot3D plot = (PiePlot3D) chart.getPlot();
		plot.setStartAngle(290);
		plot.setDirection(Rotation.CLOCKWISE);
		plot.setForegroundAlpha(0.5f);
		return chart;
	}
}
