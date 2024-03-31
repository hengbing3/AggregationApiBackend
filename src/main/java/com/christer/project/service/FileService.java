package com.christer.project.service;

import java.io.InputStream;

/**
 * @author Christer
 * @version 1.0
 * @date 2024-03-31 16:41
 * Description:
 */
public interface FileService {
    /**
     * 保存文件到临时路径
     *
     * @param inputStream      文件输入流
     * @param originalFilename 原始文件名
     * @return 临时文件路径
     */
    String transferToTempPath(InputStream inputStream, String originalFilename);

    /**
     * 保存文件到永久路径
     * @param tempFilePath 临时文件路径
     * @return 永久文件路径
     */
    String transferToFinalPath(String tempFilePath);
}
