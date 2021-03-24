package palvelino.fi.KittyTinder.web;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import palvelino.fi.KittyTinder.KittyTinderApplication;
import palvelino.fi.KittyTinder.domain.CategoryRepository;
import palvelino.fi.KittyTinder.domain.Kitty;
import palvelino.fi.KittyTinder.domain.KittyRepository;



@Controller
public class KittyController {
	
	@Autowired
	private KittyRepository repository;
	
	@Autowired
	private CategoryRepository crepository;
	
	@GetMapping("/kittyprofiles")
	public String showProfiles(Model model) {
		model.addAttribute("kitties", repository.findAll());
		return "kittyprofiles";
	}
	
	@GetMapping("/addkitty")
	public String kittyForm(Model model) {
		model.addAttribute("kitty", new Kitty());
		model.addAttribute("categories", crepository.findAll());
		return "addkitty";
	}
	
	
	@PostMapping("/addkitty")
	public String kittySubmit(Kitty kitty) {
		repository.save(kitty);
		return "redirect:kittyprofiles";
	}
	
	//delete kitty
	@GetMapping("/delete/{id}")
	public String deleteKitty(@PathVariable("id") Long kittyId, Model model) {
		repository.deleteById(kittyId);
		return "redirect:../kittyprofiles";
	}
		
	//edit kitty
	@GetMapping("/edit/{id}")
	public String editKitty(@PathVariable("id") Long kittyId, Model model){
		model.addAttribute("kitty", repository.findById(kittyId));
		model.addAttribute("agecategories", crepository.findAll());
		return "editkitty";
	}
	
	//save edited kitty
	@PostMapping("/save")
	public String saveKitty(@Valid Kitty kitty, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "editkitty";
		}
		repository.save(kitty);
		return "redirect:kittyprofiles";
	}
	
		
	//RESTful service to get all kitties
	@RequestMapping(value="/kitties")
	public @ResponseBody List<Kitty> kittyListRest() {	
		return (List<Kitty>) repository.findAll();
	}    

	//RESTful service to get kitty by id
	@RequestMapping(value="/kitty/{id}", method = RequestMethod.GET)
	public @ResponseBody Optional<Kitty> findKittyRest(@PathVariable("id") Long kittyId) {	
		return repository.findById(kittyId);
	}     
	
	//run add photo
	@GetMapping("/addphoto/{id}")
	public String addPhoto(@PathVariable("id") Long kittyId, Model model){
		model.addAttribute("kitty", repository.findById(kittyId));
		model.addAttribute("agecategories", crepository.findAll());
	    return "addphoto";
	}


	//add photo
	 @PostMapping("/upload") 
	 public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file ) {

	    String fileName = file.getOriginalFilename();
	    
		try {
			file.transferTo( new File("C:\\upload\\" + fileName));
		 } catch (Exception e) {
		      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
		 } 
		
		return ResponseEntity.ok("");
	  }
	 
	//save photo
	@PostMapping("/savephoto")
	public String savePhoto(@Valid Kitty kitty, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "addphoto";
		}
		repository.save(kitty);
		System.out.println(kitty.getPhotoFile());
		return "redirect:kittyprofiles";
		}
		
	  	
}
