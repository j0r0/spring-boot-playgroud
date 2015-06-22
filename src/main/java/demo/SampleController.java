package demo;

import java.util.Date;

import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.stereotype.*;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

@RestController
@EnableAutoConfiguration
public class SampleController {

	@RequestMapping("/")
	@ResponseBody
	public ModelAndView home() {
		ModelAndView model = new ModelAndView("home");
		model.addObject("time", new Date());
		model.addObject("message", "hello");
		return model;
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public Login addStudent(@ModelAttribute("SpringWeb") Login login,
			ModelMap model) {
		model.addAttribute("username", login.getUsername());
		model.addAttribute("password", login.getPassword());

		return login;
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	@ResponseBody
	public String addStudent1(@ModelAttribute("SpringWeb") Login login,
			ModelMap model) throws JsonProcessingException {
		Login l = new Login();
		l.setUsername("user");
		l.setPassword("123");

		ObjectMapper mapper = new ObjectMapper();
		// first, construct filter provider to exclude all properties but
		// 'name', bind it as 'myFilter'
		FilterProvider filters = new SimpleFilterProvider()
				.addFilter("myFilter",
						SimpleBeanPropertyFilter.filterOutAllExcept("username"));
		// and then serialize using that filter provider:
		
		String json = mapper.writer(filters).writeValueAsString(l);

		return json;
	}

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SampleController.class, args);
	}

	@RequestMapping("/ships/{name}-{imo}.html")
	public ModelAndView ship(@PathVariable String name, @PathVariable String imo) {
		ModelAndView model = new ModelAndView("home");
		model.addObject("time", name);
		model.addObject("message", imo);
		return model;
	}
}
