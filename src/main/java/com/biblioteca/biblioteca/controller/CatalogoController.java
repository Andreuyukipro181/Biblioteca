package biblioteca.biblioteca.controller;

import biblioteca.biblioteca.domain.Libro;
import biblioteca.biblioteca.service.CatalogoService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
@RequestMapping("/catalogo")
public class CatalogoController {

    @Autowired
    private CatalogoService catalogoService;
    
    @GetMapping("/listado")
    public String listado(Model model) {
        var libro = catalogoService.getLibro(false);
        model.addAttribute("catalogos", libro);
        model.addAttribute("Totalcatálogos", libro.size());
        return "/catalogo/listado";
    }
}a 