package es.codeurjc.daw;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class EntradaController {

	@Autowired
	private EntradaRepository repository;
	
	@GetMapping
	public List<Entrada> entradas() {
		return repository.findAll();
	}
	
	@PostMapping
	@ResponseStatus(code=HttpStatus.CREATED)
	public Entrada addEntrada(@RequestBody Entrada entrada) {
		return repository.save(entrada);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<Entrada> updateEntrada(@RequestBody Entrada entrada, @PathVariable long id) {
		Optional<Entrada> saved = repository.findById(id);
		if(!saved.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		Entrada updated = repository.save(entrada);
		return new ResponseEntity<>(updated, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<Entrada> deleteEntrada(@PathVariable long id) {
		Optional<Entrada> saved = repository.findById(id);
		
		if(!saved.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		repository.deleteById(id);
		
		return new ResponseEntity<>(saved.get(), HttpStatus.OK);
	}
	
	@GetMapping("{id}") 
	public ResponseEntity<Entrada> getEntrada(@PathVariable long id) {
		Optional<Entrada> saved = repository.findById(id);
		
		if(!saved.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(saved.get(), HttpStatus.OK);

	}
	
	@PostMapping("{id}/comments/")
	public ResponseEntity<Entrada> addComment(@PathVariable long id, @RequestBody Comment comment) {
		Optional<Entrada> found = repository.findById(id);
		
		if(!found.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		found.get().getComments().add(comment);
		Entrada saved = repository.save(found.get());
		return new ResponseEntity<>(saved, HttpStatus.OK);
		
	}
	
}
