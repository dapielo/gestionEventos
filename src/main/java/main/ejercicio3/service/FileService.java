package main.ejercicio3.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import main.ejercicio3.exceptions.CartelException;

@Service
public class FileService {
    private final Path directorio = Paths.get("carteles").toAbsolutePath();

    public FileService() throws IOException{
        if (!Files.exists(directorio)){
            Files.createDirectories(directorio);
        }
    }

    public Path getDirectorio() {
        return this.directorio.toAbsolutePath();
    }

    public String guardarCartel(MultipartFile cartel) throws CartelException{
        String nombre = cartel.getOriginalFilename();
        String extension = nombre.contains(".")?nombre.substring(nombre.indexOf(".")):"";
        String nombreUnico = UUID.randomUUID()+extension;

        Path rutaDestino = directorio.resolve(nombreUnico);
        try{
            Files.copy(cartel.getInputStream(), rutaDestino);
        } catch (IOException ioe) {
            throw new CartelException("ERROR: No se ha podido guardar la imagen del cartel");
        }
        return nombreUnico;
    }
}
