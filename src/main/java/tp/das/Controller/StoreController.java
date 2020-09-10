package tp.das.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tp.das.Service.StoreService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/store")
public class StoreController {

    @GetMapping(path = "/list")
    public ResponseEntity filenames() {
        return ResponseEntity.ok(StoreService.filenames());
    }

    @DeleteMapping(path = "/{filename}")
    public ResponseEntity delete(@PathVariable("filename") String filename) {
        StoreService.delete(filename);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/reset")
    public ResponseEntity reset() {
        StoreService.reset();
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{filename}/save")
    public ResponseEntity save(@PathVariable("filename") String filename) {
        StoreService.save(filename);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{filename}/load")
    public ResponseEntity load(@PathVariable("filename") String filename) {
        StoreService.load(filename);
        return ResponseEntity.ok().build();
    }
}
