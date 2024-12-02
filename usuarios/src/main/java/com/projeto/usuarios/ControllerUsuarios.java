package com.projeto.usuarios;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequestMapping("/")
public class ControllerUsuarios {

    @Autowired
    private UsuarioRepository usuarioRepository;

    
    @GetMapping("/")
    public String index(
        Model model
    ){
        return "login";
    }

    @PostMapping("/login")
    public String login(
        @RequestParam String email,
        @RequestParam String senha,
        Model model
        ) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndSenha(email, senha);

        if (usuario.isPresent()) {
            model.addAttribute("mensagem", "Login realizado com sucesso!");
            List<Usuario> usuarios = usuarioRepository.findAll();
            model.addAttribute("usuarios", usuarios);
            return "usuarios";
        } else {
            model.addAttribute("mensagemErro", "Login ou senha incorretos.");
        }

        return "login";
    }

    @GetMapping("/cadastro")
    public String mostrarFormularioCadastro(Model model) {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarUsuario(@RequestParam String email, @RequestParam String senha, 
                                   @RequestParam String nome, @RequestParam String nivelAcesso,  Model model) {
        
                                    Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setNome(nome);
        usuario.setNivelAcesso(nivelAcesso);
        usuario.setDataDeCadastro(LocalDate.now());

        usuarioRepository.save(usuario);
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/deletar/{id}")
    public String deletarUsuario(@PathVariable("id") Long id,  Model model) {
        usuarioRepository.deleteById(id);
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @PostMapping("/editar/{id}")
    public String editarUsuario(@PathVariable("id") Long id, @RequestParam String email, @RequestParam String senha, 
                                @RequestParam String nome, @RequestParam String nivelAcesso, Model model) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(id);
        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();
            usuario.setEmail(email);
            usuario.setSenha(senha);
            usuario.setNome(nome);
            usuario.setNivelAcesso(nivelAcesso);

            usuarioRepository.save(usuario);
        }
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/usuarios")
    public String listarUsuarios(
        Model model,
        @RequestParam(value = "nome", required = false, defaultValue = "") String nome
        ) {

        List<Usuario> usuarios;
        if (nome.isEmpty()) {
            usuarios = usuarioRepository.findAll();
        } else {
            usuarios = usuarioRepository.findByNomeContainingIgnoreCase(nome);
        }

        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable("id") Long id, Model model) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "editar";
        }
        List<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "usuarios";
    }

    @GetMapping("/relatorio")
    public String relatorio(Model model) {
          // Contagem de usuários
          long totalUsuarios = usuarioRepository.count();
          long totalAdmins = usuarioRepository.countByNivelAcesso("admin");
          long totalNormais = usuarioRepository.countByNivelAcesso("normal");
  
          // Adicionando as informações no modelo
          model.addAttribute("totalUsuarios", totalUsuarios);
          model.addAttribute("totalAdmins", totalAdmins);
          model.addAttribute("totalNormais", totalNormais);

          
  
          return "relatorio"; 
    }
 
}