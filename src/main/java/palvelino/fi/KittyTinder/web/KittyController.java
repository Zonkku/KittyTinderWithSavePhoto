package palvelino.fi.KittyTinder.web;

import javax.validation.Valid;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
	
	  @GetMapping("/index")
	  public String hello() {
	    return "uploader";
	  }

	  @PostMapping("/upload") 
	  public ResponseEntity<?> handleFileUpload( @RequestParam("file") MultipartFile file ) {

	    String fileName = file.getOriginalFilename();
	    try {
	      file.transferTo( new File("C:\\upload\\" + fileName));
	    } catch (Exception e) {
	      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	    } 
	    return ResponseEntity.ok("File uploaded successfully.");
	  }


	
	
}
