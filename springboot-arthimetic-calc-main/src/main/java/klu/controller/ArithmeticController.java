package klu.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin("*")
public class ArithmeticController {
	@GetMapping("/add/{A}/{B}")
	public String add(@PathVariable("A") int a,@PathVariable("B") int b)
	{
		return "Addition = " + (a+b);
	}
	@GetMapping("/sub/{A}/{B}")
	public String sub(@PathVariable("A") int a,@PathVariable("B") int b)
	{
		return "Subraction = " + (a-b);
	}
	@GetMapping("/prod/{A}/{B}")
	public String prod(@PathVariable("A") int a,@PathVariable("B") int b)
	{
		return "Product = " + (a*b);
	}
	@GetMapping("/div/{A}/{B}")
	public String div(@PathVariable("A") int a,@PathVariable("B") int b)
	{
		return "Division = " + (a/b);
	}
	@GetMapping("/modu/{A}/{B}")
	public String modu(@PathVariable("A") int a,@PathVariable("B") int b)
	{
		return "Modulo = " + (a%b);
	}
	@GetMapping("/squ/{A}/{B}")
	public String squ(@PathVariable("A") int a,@PathVariable("B") int b)
	{
		return "Power = " + Math.pow(a, b);
	}
	
	
}
