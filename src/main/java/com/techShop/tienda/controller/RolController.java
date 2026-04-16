package com.techShop.tienda.controller;

import com.techShop.tienda.domain.Rol;
import com.techShop.tienda.service.RolService;
import jakarta.validation.Valid;
import java.util.Locale;
import java.util.NoSuchElementException;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/rol")
public class RolController {

    private final RolService rolService;
    private final MessageSource messageSource;

    public RolController(RolService rolService, MessageSource messageSource) {
        this.rolService = rolService;
        this.messageSource = messageSource;
    }

    @GetMapping("/listado")
    public String listado(Model model) {
        var lista = rolService.getRoles();
        model.addAttribute("roles", lista);
        model.addAttribute("totalRoles", lista.size());
        return "/rol/listado";
    }

    @PostMapping("/guardar")
    public String guardar(@Valid Rol rol, RedirectAttributes redirectAttributes) {
        rolService.save(rol);
        redirectAttributes.addFlashAttribute("todoOk", messageSource.getMessage("mensaje.actualizado", null, Locale.getDefault()));
        return "redirect:/rol/listado";
    }

    @PostMapping("/eliminar")
    public String eliminar(@RequestParam Integer idRol, RedirectAttributes redirectAttributes) {
        String titulo = "todoOk";
        String detalle = "mensaje.eliminado";
        try {
            rolService.delete(idRol);
        } catch (IllegalArgumentException e) {
            titulo = "error"; // Captura la excepción de argumento inválido para el mensaje de "no existe"
            detalle = "rol.error01";
        } catch (IllegalStateException e) {
            titulo = "error"; // Captura la excepción de estado ilegal para el mensaje de "datos asociados"
            detalle = "rol.error02";
        } catch (Exception e) {
            titulo = "error";  // Captura cualquier otra excepción inesperada
            detalle = "rol.error03";
        }
        redirectAttributes.addFlashAttribute(titulo, messageSource.getMessage(detalle, null, Locale.getDefault()));
        return "redirect:/rol/listado";
    }

    @GetMapping("/modificar/{idRol}")
    public String modificar(@PathVariable("idRol") Integer idRol, Model model, RedirectAttributes redirectAttributes) {
        try {
            Rol rol = rolService.getRol(idRol);
            model.addAttribute("rol", rol);
            return "/rol/modifica";
        } catch (NoSuchElementException e) {
            // Captura la excepción de 'no encontrado' del servicio
            redirectAttributes.addFlashAttribute("error", messageSource.getMessage("rol.error01", null, Locale.getDefault()));
            return "redirect:/rol/listado";
        }
    }
}
