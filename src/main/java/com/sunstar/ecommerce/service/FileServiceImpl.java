package com.sunstar.ecommerce.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {

	@Override
	public String uploadImage(String path, MultipartFile imageFile)
			throws IOException {
		String originalFilename = imageFile.getOriginalFilename();

		String randomId = UUID.randomUUID().toString();

		String fileName = randomId.concat(originalFilename.substring(originalFilename.lastIndexOf('.')));

		String filePath = path + File.pathSeparator + fileName;

		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
		}

		Files.copy(imageFile.getInputStream(), Paths.get(filePath));

		return fileName;
	}
}
