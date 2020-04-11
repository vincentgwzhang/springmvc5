package org.personal.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.personal.dto.DownloadDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("file")
@Controller
public class UploadDownloadController
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static final String BASE_URL = "file";

    @GetMapping
    public String index()
    {
        logger.info("UploadDownloadController::index");
        return BASE_URL;
    }

    @PostMapping("/fileUpload")
    public ResponseEntity<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException
    {
        logger.info("UploadDownloadController::fileUpload");
        if (!file.getOriginalFilename().isEmpty())
        {
            BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(new File("/home/vzhang/Downloads/", file.getOriginalFilename())));
            outputStream.write(file.getBytes());
            outputStream.flush();
            outputStream.close();
        }
        else
        {
            return new ResponseEntity<>("Invalid file.", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("File Uploaded Successfully.", HttpStatus.OK);
    }

    /**
     * Prevent Cross-Referencing
     * @RequestHeader String referer
     *
     * Read in FileSystemResource, avoid OOM
     */
    @GetMapping("fileDownload")
    @ResponseBody
    public ResponseEntity<Resource> downloadBackupFile(@RequestHeader String referer, @RequestHeader(value = "User-Agent", required = false) String userAgent)
    {
        logger.info("UploadDownloadController::downloadBackupFile");
        DownloadDTO fileInfo = new DownloadDTO("123.avi", new File("/home/vzhang/Downloads", "123.avi"));
        File file = fileInfo.getFile();
        String fileName = fileInfo.getFileName();
        Resource body = new FileSystemResource(file);

        String header = userAgent.toUpperCase();
        HttpStatus status = HttpStatus.CREATED;
        try
        {
            if (header.contains("MSIE") || header.contains("TRIDENT") || header.contains("EDGE"))
            {
                fileName = URLEncoder.encode(fileName, "UTF-8");
                fileName = fileName.replace("+", "%20");
                status = HttpStatus.OK;
            }
            else
            {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
        }
        catch (UnsupportedEncodingException e)
        {

        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", fileName);
        headers.setContentLength(file.length());

        return new ResponseEntity<>(body, headers, status);
    }

}
