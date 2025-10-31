package com.belval.crudrest.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.belval.crudrest.model.Arquivo;
import com.belval.crudrest.repository.ArquivoRepository;

import jakarta.xml.bind.DatatypeConverter;

@Controller
public class UploadController {
	
	@Autowired
	private ArquivoRepository repository;
	
	private static final String UPLOAD_DIR = "./uploads/";

	@GetMapping("/arquivos")
	public ResponseEntity<List<String>> listArquivos(Model model) {
		List<String> listaArqs = new ArrayList<>();
		repository.findAll().forEach(arq -> {
			listaArqs.add(arq.toString());
		});
		return ResponseEntity.ok(listaArqs);
	}
	
	@GetMapping("/files")
	public String listFiles(Model model) {
		
		List<String> fileNameList = new ArrayList<>();
		
		Path dirPath = Paths.get(UPLOAD_DIR);
		try(Stream<Path> paths = Files.list(dirPath)) {
            paths.forEach(path -> {
                if (Files.isRegularFile(path)) {
                    //System.out.println("File: " + path.getFileName());
                	fileNameList.add(path.getFileName().toString());
                } else if (Files.isDirectory(path)) {
                    //System.out.println("Directory: " + path.getFileName());
                }
            });
		} catch (Exception e) {
			// TODO: handle exception
		}
		model.addAttribute("files", fileNameList);
		return "list-files";
	}
	
	@GetMapping("/upload")
	public String getUploadPage() {
		return "upload";
	}

    @GetMapping("/download/{fileName:.*}")
    public ResponseEntity<InputStreamResource> downloadFile(
    		@PathVariable String fileName) throws IOException {
    	
		Path dirPath = Paths.get(UPLOAD_DIR);
		
		//Path uploadPath = Paths.get(UPLOAD_DIR, fileName);
		Path uploadPath = Paths.get(UPLOAD_DIR, fileName);

        if (!Files.exists(uploadPath)) {
            // Handle file not found scenario, e.g., return 404 Not Found
            return ResponseEntity.notFound().build();
        }
        

        File file = uploadPath.toFile();
		InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        HttpHeaders headers = new HttpHeaders();
        // Set Content-Disposition to "attachment" to force download
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getName() + "\"");
        // Set Content-Type based on the file type (you might need a utility for this)
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE); // Generic binary stream
        // Set Content-Length
        headers.setContentLength(file.length());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

	//curl -X POST http://localhost:8081/upload -F file=@logo-git.png

	@PostMapping("/upload")
	public ResponseEntity<String> uploadFile(
			@RequestParam("file") MultipartFile[] files) {
		
		Map<String, MultipartFile> falhasUpload = new HashMap<>();
		
		for (MultipartFile file : files) {
			try {
				if (file.isEmpty()) {
					return ResponseEntity
							.badRequest()
							.body("Arquivo vazio!");
							//.status(HttpStatus.BAD_REQUEST)
							//.body("");
				}
				
				Path dirPath = Paths.get(UPLOAD_DIR);
				
				//Path uploadPath = Paths.get(UPLOAD_DIR, fileName);
				Path uploadPath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
				
				
				if (!Files.exists(dirPath)) {
					Files.createDirectories(dirPath);
				}
				
				Files.createFile(uploadPath);
				
				// Save the file to the server
				byte[] bytes = file.getBytes();
				Files.write(uploadPath, bytes);

				Arquivo arq = new Arquivo();
				arq.setHash("hash");
				arq.setConteudo(bytes);
				arq.setHash(getHashFromByteArray(bytes));
				arq.setContentType(file.getContentType());
				repository.save(arq);
				
			} catch (IOException e) {
				e.printStackTrace();
				falhasUpload.put(file.getOriginalFilename(), file);
			}			
		}

		if (falhasUpload.keySet().size() > 0) {
			
			StringBuffer mensagemDeErroUpload = new StringBuffer("Erro ao efetuar o upload dos seguintes arquivos:");
			for (MultipartFile file : falhasUpload.values()) {
				mensagemDeErroUpload.append(file.getOriginalFilename()).append("\n");
			}
			
			return ResponseEntity
					.badRequest()
					.body(mensagemDeErroUpload.toString());			
		}


		return ResponseEntity.ok("Todos os arquivos foram recebidos com sucesso!");
	}
	
	private String getHashFromByteArray(byte[] data) {
		String hexHash = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");//"SHA-256 Or "MD5", "SHA-1", etc.
            byte[] hashBytes = md.digest(data);

            // Convert the byte array hash to a hexadecimal string for display
            hexHash = DatatypeConverter.printHexBinary(hashBytes).toLowerCase();
            System.out.println("MD5 hash: " + hexHash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hexHash;
	}
	
    @GetMapping("/view/{fileName:.*}")
    public ResponseEntity<InputStreamResource> viewFile(
    		@PathVariable String fileName) throws IOException {
    	
       
        Path uploadPath = Paths.get(UPLOAD_DIR, fileName);
        File file = uploadPath.toFile();

        // Determine the content type based on the file extension
        String contentType = determineContentType(fileName);
        if (contentType == null) {
            return ResponseEntity.badRequest().build();
        }

        HttpHeaders headers = new HttpHeaders();
        // The "inline" disposition tells the browser to display the file
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");

        InputStreamResource inputStreamResource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType(contentType))
                .body(inputStreamResource);
    }
    
    @GetMapping("/view2/{fileId}")
    public ResponseEntity<InputStreamResource> viewFile(
    		@PathVariable UUID fileId) throws IOException {
    	
       
        Optional<Arquivo> arqOpt = repository.findById(fileId);
        if (arqOpt.isEmpty()) {
        	return ResponseEntity.notFound().build();
        }
        
        Arquivo arquivo = arqOpt.get();

        String fileName = arquivo.getId().toString();
        // Determine the content type based on the file extension
        String contentType = arquivo.getContentType();

        HttpHeaders headers = new HttpHeaders();
        // The "inline" disposition tells the browser to display the file
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileName + "\"");

        InputStreamResource inputStreamResource = 
        	new InputStreamResource(
        		new ByteArrayInputStream(arquivo.getConteudo()));

        int fileLen = arquivo.getConteudo().length;
        
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(fileLen)
                .contentType(MediaType.parseMediaType(contentType))
                .body(inputStreamResource);
    }
    
    /**
     * Determines the appropriate content type based on the file extension.
     */
    private String determineContentType(String filename) {
        if (filename.endsWith(".pdf")) {
            return "application/pdf";
        }
        if (filename.endsWith(".png")) {
            return "image/png";
        }
        if (filename.endsWith(".jpg") || filename.endsWith(".jpeg")) {
            return "image/jpeg";
        }
        if (filename.endsWith(".gif")) {
            return "image/gif";
        }
        // Add more file types as needed
        return null;
    }
}
