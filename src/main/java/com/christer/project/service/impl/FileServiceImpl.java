package com.christer.project.service.impl;

import com.christer.project.exception.BusinessException;
import com.christer.project.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-31 16:41
 * Description:
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Value("${file.temp-dir}")
    private String tempDir;

    @Value("${file.store-dir}")
    private String storeDir;

    private static final String PATH_SPLIT = FileSystems.getDefault().getSeparator();

    @Override
    public String transferToTempPath(final InputStream inputStream, final String originalFilename) {
        try {
            // 生成新的文件名（UUID + 原始文件扩展名）
            String fileExtension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            String storedFileName = UUID.randomUUID() + fileExtension;
            // 构建上传目标路径
            Path destinationPath = Paths.get(uploadDir, tempDir)
                    .resolve(storedFileName)
                    .normalize().
                    toAbsolutePath();

            // 确保目录存在
            if (!destinationPath.getParent().toFile().exists()) {
                Files.createDirectories(destinationPath.getParent());
            }

            // 保存文件到目标目录
            Files.copy(inputStream, destinationPath, StandardCopyOption.REPLACE_EXISTING);

            // 返回相对路径
            return tempDir + PATH_SPLIT + storedFileName;
        } catch (IOException e) {
            log.error("文件保存失败原因:{0}", e);
            throw new BusinessException("文件保存失败！");
        }

    }

    @Override
    public String transferToFinalPath(final String tempFilePath) {
        log.info("上传文件, 保存为持久文件, {}", tempFilePath);
        if (!StringUtils.hasText(tempFilePath)) {
            return tempFilePath;
        }
        // 生成当前保存路径
        final Path filePath = FileSystems.getDefault().getPath(uploadDir).resolve(tempFilePath);
        // 生成永久保存路径
        final Path storeDirPath = FileSystems.getDefault().getPath(uploadDir, storeDir);
        log.info("判断待保存文件是否存在");
        if (Files.notExists(filePath)) {
            throw new BusinessException("要保存的文件不存在, 请重新上传.");
        }

        if (filePath.toAbsolutePath().normalize().startsWith(storeDirPath.toAbsolutePath().normalize())) {
            log.info("上传文件, 文件已经永久保存, 不需要移动, {}", tempFilePath);
            return tempFilePath;
        }

        final Path fileStorePath = storeDirPath.resolve(filePath.getFileName());

        if (!Files.exists(fileStorePath)) {
            try {
                Files.createDirectories(fileStorePath.getParent());
                Files.copy(filePath, fileStorePath);
            } catch (IOException e) {
                log.error("文件保存为永久路径，失败原因:{0}", e);
                throw new BusinessException("文件保存为永久路径失败！");
            }
        } else {
            log.info("保存为永久路径, {}, 已经存在文件不需要再次保存: {}", tempFilePath, fileStorePath.toAbsolutePath());
        }

        final String fileStoreRelativePath = storeDir + PATH_SPLIT + filePath.getFileName();
        log.info("上传文件, 保存为持久文件, {}, 返回文件相对路径: {}", tempFilePath, fileStoreRelativePath);
        return fileStoreRelativePath;
    }
}
